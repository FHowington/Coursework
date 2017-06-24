	li		$v0,4		# print prompt
	la		$a0,x_msg
	syscall
	li		$v0,5		# get value (x)
	syscall
	move		$t0,$v0		# put x into a
	li		$v0,4		# print input binary string
	la		$a0,msg		
	syscall
	move		$a0,$t0		
	li		$v0,35		# print input hex
	syscall
	li		$v0,4
	la		$a0,msg2
	syscall
	move		$a0,$t0
	li		$v0,34
	syscall
	
	li		$v0,4
	la		$a0,msg3
	syscall
	andi		$t0,$t0,0x0000f000 #narrow binary
	srl		$t0,$t0,12
	move		$a0,$t0			#print result binary
	li		$v0,35
	syscall
	li		$v0,4
	la		$a0,msg4
	syscall
	move		$a0,$t0
	la		$v0,34		#print result hex
	syscall
														
	li		$v0,10		# terminate
	syscall				
.data
	x_msg:	.asciiz		"Please input your integer:\n"
	msg:	.asciiz		"Here is the input in binary: "
	msg2:	.asciiz		"\nHere is the input in hexadecimal: "
	msg3:	.asciiz		"\nHere is the output in binary: "
	msg4:	.asciiz		"\nHere is the output in hexadecimal: "
	
	
