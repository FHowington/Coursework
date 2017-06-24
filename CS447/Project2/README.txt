Forbes Turley fot3@pitt.edu

1. Known Issues/Working Instructions
There are no known issues. Every jrMIPS instruction mentioned in the project description has been tested and is working correctly to the best of my knowledge.

2. Control Signals
The control is segmented into a number of smalled control circuits. The primary control circuit, MainControl, takes as inputs the operation and suboperation code. Output signals: BrnchCntrl selects between the four possible boolean operations used by the four brach control instructions (bx,bz,bp,bn). Myop controls the operation performed by the ALU. Imm controls where the input to the input B of the ALU is sourced from, whether that be a register file, or an immedite value or a sign extended immediate. Ain and Bin invert A and B within the ALU, respectively. Regwrt enables the register file to be written to, and memstr enables the RAM to be written to. Brsrc controls the input to the program counter. It is switched depending on whether a branch, jump, or register is used to control the PC. Regsrc control the input to the regiser file to allow for inputs from RAM, the ALU, and the PC. 

There are also a number of smaller control circuits. The circuit Dispcontrol has only a single output, dispUpdate, which is enabled when the Put instruction is used so that the display is enabled. The circuit halter also has only a single output, named halt, which is enabled during a halt instruction to freeze the PC and enable the LED. It does this by disabling the clock updates to the program counter register. Muldivcontrol has a single output, Reg2Enable, which feeds into the register file. This is enabled during a multiplication or division operation to enable writing to a second register. 

From the ALU, the zero signal carries the result of branch operations. The result line carries the result of addition/subtraction/nor. It also carries the low order bits from multiplication and the quotient from divion. The remainder and high order bits from multiplication are fed into a mux which selects between the two outputs to send one to the second utilized register during either of these operations.

3. Mult/Div
When multiplcation or division is performed, a second register must be used. The second destination register is calculated by adding 1 to the first destination register, and sending the remainder or high order bits to that register. In order for two seperate control signals to select registers to write to, a number of OR gates were utilized.


