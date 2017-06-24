.data
msg1: .asciiz "Please enter a number n:\n"
msg2: .asciiz "Please enter another number k:\n"
msg3: .asciiz "The chosen value is "
.text

#Asking user for input
li $v0,4
la $a0,msg1
syscall
li $v0,5
syscall
move $t0,$v0
li $v0,4
la $a0,msg2
syscall
li $v0,5
syscall
move $a1,$v0
move $a0,$t0
li $v1,0
# $a0 now contains n, $a1 contains k

jal choose
add $v1,$v1,$v0

li $v0,4
la $a0,msg3
syscall

move $a0,$v1
li $v0,1
syscall
li $v0,10
syscall

choose:
move $s0,$a0
move $s1,$a1

beq $a0,$a1,equals
beq $a1,$zero,equals
blt $a0,$a1,lessThan

#prologue
addi $sp,$sp,-12
sw $ra,0($sp)
sw $s0,4($sp)
sw $s1,8($sp)
#end prologue

addi $a0,$s0,-1
addi $a1,$s1,-1
jal choose
add $v1,$v1,$v0

#epilogue
lw $ra,0($sp)
lw $s0,4($sp)
lw $s1,8($sp)
addi $sp,$sp,12
#end epilogue


#prologue
addi $sp,$sp,-12
sw $ra,0($sp)
sw $s0,4($sp)
sw $s1,8($sp)
#end prologue

addi $a0,$s0,-1
move $a1,$s1
jal choose



#epilogue
lw $ra,0($sp)
lw $s0,4($sp)
lw $s1,8($sp)
addi $sp,$sp,12
#end epilogue

jr $ra

lessThan:
li $v0,0
jr $ra

equals:
li $v0,1
jr $ra



