.data
Array_A: .word 0xa1a2a3a4, 0xa5a6a7a8, 0xacabaaa9
msg1:	.asciiz "Please enter index;\n"
msg2:	.asciiz "Here is the output (word): \n"
msg3:	.asciiz "\nHere is the output (HalfWord): \n"
msg4:	.asciiz "\nHere is the output (Byte): \n"


.text
li $v0,4
la $a0, msg1
syscall
li $v0,5
syscall
move $t0,$v0
li $v0,4
la $a0,msg2
syscall

subi $t0,$t0,1
li $t1,4
multu $t1,$t0
mflo $t1
la $t2,Array_A

add $t3,$t2,$t1 #Getting address of correct word
lw $a0,($t3)
li $v0,34
syscall

li $v0,4
la $a0,msg3
syscall
li $v0,34







#need to determine whether we are on an even or odd number


andi $t4, $t0, 1
bne $t4,0,skip1
addi $t4,$t0,1
j skip2
skip1:
subi $t4,$t0,1
skip2:
li $t1,2
multu $t1,$t4
mflo $t1
add $t3,$t2,$t1





lhu $a0,($t3)
syscall


li $v0,4
la $a0,msg4
syscall
li $v0,34




#determining the index for byte based on equation 3-x, where x is byte index in BE
li $t4 3
sub $t4,$t4,$t0

add $t3,$t2,$t4
lbu $a0,($t3)
syscall














