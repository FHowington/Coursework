.data
some_string: .space 64
msg: .asciiz "Please enter your string:\n"
msg2: .asciiz "Please enter the character to replace:\n"
msg3: .asciiz "\nHere is the output: "

.text
la $a0,msg
li $v0,4
syscall

#String read
la $a0,some_string
li $v0,8
li $a1,64
li $a2,63
syscall

#Determine character to replace
li $v0,4
la $a0,msg2
syscall
li $v0,12
syscall
move $a1,$v0

la $a0,some_string
jal ReplaceLetterWithAsterisk

li $a1,64
jal Rotate13

#Print String, terminate program
la $a0,msg3
li $v0,4
syscall
la $a0,some_string
syscall
li $v0,10
syscall


#$a0 should contain string address, $a1 should contain the character to be replaced, $a2 should contain the string length
ReplaceLetterWithAsterisk:
beq $a2,$zero,return
addi $a2,$a2,-1
add $t0,$a0,$a2
lb $t1,0($t0)
bne $t1,$a1,ReplaceLetterWithAsterisk
li $t1, 42
sb $t1,0($t0)
j ReplaceLetterWithAsterisk

#$a0 should contain string address, $a1 should contain string length
Rotate13:
beq $a1,$zero,return
addi $a1,$a1,-1
add $t0,$a0,$a1
lb $t1,0($t0)
li $t3,42
beq $t1,$t3,Rotate13
beq $t1,$zero,Rotate13
li $t3,32
beq $t1,$t3,Rotate13
li $t2,0x5B
blt $t1,$t2,Upper

#Character is lower case
addi $t1,$t1,13
li $t2,0x7B
blt $t1,$t2,Store
subi $t1,$t1,0x1A
j Store

#Character is upper case
Upper:
addi $t1,$t1,13
li $t2,0x5B
blt $t1,$t2,Store
subi $t1,$t1,0x1A
j Store

Store:
sb $t1,0($t0)
j Rotate13

return:
jr $ra




