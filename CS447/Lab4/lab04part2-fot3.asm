.data
some_string: .space 64

msg: .asciiz "Please enter a name:\n"
msg2: .asciiz "City is: "
msg3: .asciiz "Not found!"

names: .asciiz "alex", "sam", "jamie", "andi", "riley"
cities: .asciiz "boston", "new york", "chicago", "pittsburgh", "denver"

.text

#Reading the input
li $v0,4
la $a0,msg
syscall
la $a0,some_string
li $v0,8
li $a1,64
syscall

# $s0 contains the count of the current index
li $s0,4
# $s1 contains starting address of the input, $s2 contains start of the names array
la $s1,some_string
la $s2,names


#beginning check loop
checkLoop:
move $a0,$s2
move $a1,$s0
jal Lookup
# $t0 contains the starting address of next element to check
move $t0,$v0

move $a0,$s1
move $a1,$t0
#Here, $a0 must contain start of input, while $a1 must contain start index of next string. Set $a0 = $s1 for this, while process $a1 using Lookup
jal CheckName
beq $v0,1,FoundAtIndex
addi $s0,$s0,-1
li $t0,-1
beq $s0,$t0,notFound
j checkLoop

FoundAtIndex:
li $v0,4
la $a0,msg2
syscall
la $a0,cities
move $a1,$s0
jal Lookup
move $a0,$v0
li $v0,4
syscall



li $v0,10
syscall


notFound:
li $v0,4
la $a0,msg3
syscall
li $v0,10
syscall

# $a0 is the address of the first string, $a1 is the address of the second. $v0 returns 1 if match, 0 if no match
CheckName:
move $t0,$a0
move $t1,$a1

iterator:
li $v0,0
lb $t2,0($t0)
lb $t3,0($t1)

addi $t0,$t0,1
addi $t1,$t1,1
beq $t2,$t3, checker

bne $t2,10,return
li $v0,1
beq $t3,$zero,return
li $v0,0
j return

checker:
li $v0,1
j iterator

return:
jr $ra

# $a0 contains the string array base, $a1 contains the index, returns $v0 with the starting address of the desired index
Lookup:
move $v0,$a0
beq $a1,$zero,return
addi $a1,$a1,-1
j stringLoop

stringLoop:
lb $t0,0($a0)
beq $t0,$zero,addOne
addi $a0,$a0,1
j stringLoop

addOne:
addi $a0,$a0,1
j Lookup

