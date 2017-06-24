#	Forbes Turley fot3@pitt.edu
.data
maze:	.ascii
	# 0123456701234567012345670123456701234567012345670123456701234567
	 "  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",    # 0
	 "       xx      xx      xx      xx      xx      xx      xx      x",    # 1
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 2
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 3
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 4
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 5
	 "x                                                              x",    # 6
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 7
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 8
	 "x      xx      xx      xx      xx      xx      xx      xx      x",    # 9
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 10
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 11
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 12
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 13
	 "x                                                              x",    # 14
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 15
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 16
	 "x      xx      xx      xx      xx      xx      xx      xx      x",    # 17
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 18
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 19
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 20
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 21
	 "x                                                              x",    # 22
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 23
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 24
	 "x      xx      xx      xx      xx      xx      xx      xx      x",    # 25
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 26
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 27
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 28
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 29
	 "x                                                              x",    # 30
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 31
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 32
	 "x      xx      xx      xx      xx      xx      xx      xx      x",    # 33
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 34
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 35
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 36
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 37
	 "x                                                              x",    # 38
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 39
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 40
	 "x      xx      xx      xx      xx      xx      xx      xx      x",    # 41
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 42
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 43
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 44
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 45
	 "x                                                              x",    # 46
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 47
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 48
	 "x      xx      xx      xx      xx      xx      xx      xx      x",    # 49
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 50
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 51
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 52
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 53
	 "x                                                              x",    # 54
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 55
	 "x xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxxx xxxxxx",    # 56
	 "x      xx      xx      xx      xx      xx      xx      xx      x",    # 57
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 58
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 59
	 "x x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x xx x  x x",    # 60
	 "x xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx xx xxxx x",    # 61
	 "x                                                               ",    # 62
	 "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  "     # 63
	# for each "x", turn the corresponding LED to orange.  The other LEDs should
	# be set to off.
ghost1: .byte 17,14,0,33,0,31,1,2,3,4
ghost2: .byte 45,14,32,63,0,31,1,2,3,4
ghost3: .byte 17,45,0,33,32,63,1,2,3,4
ghost4: .byte 45,46,32,63,32,63,1,2,3,4

endMsg: .asciiz "Success! You won! Your score is "
endMsg2: .asciiz " moves"
lossMsg: .asciiz "You were captured. Your number of moves was "

.text



# Initialization

li $s0,0 #X
li $s1,0 #Y
la $s3,maze
li $a2,2
li $s5,0
setup:
#Loading in the maze
li $t0,120
li $t1,64
mul $t1,$t1,$s1
add $t1,$t1,$s0
add $t1,$t1,$s3
lb $t1,0($t1)
bne $t1,$t0,next

move $a0,$s0
move $a1,$s1
jal _setLED

next:
add $s0,$s0,1
li $t0,64
bne $t0,$s0,continue

#Reset x, increment y
li $s0,0
addi $s1,$s1,1

continue:
beq $s1,$t0,playerBegin
j setup

end:
li $v0,10
syscall

#Initializing player
playerBegin:
li $a0,0
li $a1,0
li $a2,3

jal _setLED

#$s0 is the players position X
#$s1 is the players position y
li $s0,0
li $s1,0

li $v0,30
syscall
move $s6,$a0

waitingToStart:
	la	$v0,0xffff0000		# address for reading key press status
	lw	$t0,0($v0)		# read the key press status
	andi	$t0,$t0,1
	beq	$t0,$0,waitingToStart		# no key pressed
	lw	$t0,4($v0)		# read key value
bkeyWaiting:	addi	$v0,$t0,-66		# check for center key press
	bne	$v0,$0,waitingToStart		# invalid key, ignore it
		
#s6 is the time that the last move occured
#s5 is the total number of moves made




poll:
		li $v0,30
		syscall
		sub $t0,$a0,$s6
		li $t1,300
		blt $t0,$t1,poll
		move $s6,$a0

		#Determine which quadrant the player is in, move ghosts accordingly
		li $t0,32
		bgt $s0,$t0, greaterX
		bgt $s1,$t0, greaterY
		#If this is reached, player is in quadrant 1 (Left,Top)
		li $t7,1
		j ghostLogic
		
		greaterX:
		bgt $s1,$t0,greaterXY
		#Quadrant 2 (Right, top)
		li $t7,2
		j ghostLogic
		
		#Quadrant 4 (Lower right)
		greaterXY:
		li $t7,4
		j ghostLogic
		
		#Quadrant 3 (Lower left)
		greaterY:
		li $t7,3

		li $a0,400
		li $v0,32
		syscall
		

	ghostLogic:
		#Address of ghost variables are loaded in sequentially for each ghost. Function spookyGhosts causes random motion of ghost
		#If the player is in a quadrant, spookyGhost for the ghost in that quadrant is skipped. Instead, hungryGhost function is called
		#for that ghost, which causes ghost to seek the player.
		la $s3,ghost1
		la $s4,ghost1
		
		li $t1,1
		beq $t7,$t1,skipGhost1
		jal spookyGhosts
		
		ghost2movement:
		
		la $s3,ghost2
		la $s4,ghost2
		
		li $t1,2
		beq $t7,$t1,skipGhost2
		jal spookyGhosts
		
		ghost3movement:
		la $s3,ghost3
		la $s4,ghost3
		li $t1,3
		beq $t7,$t1,skipGhost3
		jal spookyGhosts
		
		ghost4movement:
		
		la $s3,ghost4
		la $s4,ghost4
		li $t1,4
		beq $t7,$t1,skipGhost4
		jal spookyGhosts
		j aiContinue	
		
		skipGhost1:
		jal hungryGhost
		j ghost2movement
		
		skipGhost2:
		jal hungryGhost
		j ghost3movement
		
		skipGhost3:
		jal hungryGhost
		j ghost4movement
		
		skipGhost4:
		jal hungryGhost
		j aiContinue
		
	hungryGhost:
	li $v0,42
	li $a1,2
	syscall
	move $t9,$a0
	#Will attempt the x seeking first if number is 0. Otherwise, y seeking is tried first.
	
	#Determine x,y motion that should occur. 
	lb $a0,0($s3)
	lb $a1,1($s3)
	beq $t9,$zero,tryY
	beq $a0,$s0,tryY
	tryX:
	bgt $a0,$s0,negX
	
	#Attempt to move in positive X
	addi	$a0,$a0,1
	lb $t5,3($s3)
	beq $t5,$a0,wallHitRightHungry								
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal	_getLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	li	$t2,2
	beq	$v0,$t2,wallHitRightHungry
	li $a2,1
	
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	addi $a0,$a0,-1
	li $a2,0
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
	addi $a0,$a0,1
	beq $a0,$s0,lossCheckHungryReturnX	
	j hungryReturn
		
	wallHitRightHungry:
	addi $a0,$a0,-1
	li $t8,2
	beq $t9,$t8,hungryReturn
	j tryY
	
	negX:
	addi	$a0,$a0,-1
	lb $t5,2($s3)
	beq $t5,$a0,wallHitLeftHungry								
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal	_getLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	li	$t2,2
	beq	$v0,$t2,wallHitLeftHungry
	li $a2,1
	
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	addi $a0,$a0,1
	li $a2,0
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
	addi $a0,$a0,-1
	beq $a0,$s0,lossCheckHungryReturnX
	j hungryReturn
		
	wallHitLeftHungry:
	addi $a0,$a0,1
	li $t8,2
	beq $t9,$t8,hungryReturn
	j tryY
	
	#Attempt to move in Y direction
	tryY:
	li $t9,2
	beq $a1,$s1,tryX
	bgt $a1,$s1,negY
	
	addi	$a1,$a1,1
	lb $t5,5($s3)
	beq $t5,$a1,wallHitUpHungry								
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal	_getLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	li	$t2,2
	beq	$v0,$t2,wallHitUpHungry
	li $a2,1
	
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	addi $a1,$a1,-1
	li $a2,0
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
	addi $a1,$a1,1
	beq $a1,$s1,lossCheckHungryReturnY
	j hungryReturn
		
	wallHitUpHungry:
	addi $a1,$a1,-1
	j tryX
	
	negY:
	addi	$a1,$a1,-1
	lb $t5,4($s3)
	beq $t5,$a1,wallHitDownHungry								
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal	_getLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	li	$t2,2
	beq	$v0,$t2,wallHitDownHungry
	li $a2,1
	
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
		
	addi $a1,$a1,1
	li $a2,0
	addi $sp,$sp,-4
	sw $ra,0($sp)
	jal _setLED
	lw $ra,0($sp)
	addi $sp,$sp,4
	addi $a1,$a1,-1
	beq $a1,$s1,lossCheckHungryReturnY
	j hungryReturn
		
	wallHitDownHungry:
	addi $a1,$a1,1
	j tryX
	
	hungryReturn:
	sb $a0,0($s3)
	sb $a1,1($s3)
	jr $ra
	
	lossCheckHungryReturnY:
	sb $a0,0($s3)
	sb $a1,1($s3)
	beq $a0,$s0,loss
	jr $ra
	
	lossCheckHungryReturnX:
	sb $a0,0($s3)
	sb $a1,1($s3)
	beq $a1,$s1,loss
	jr $ra
	#Checking capture conditions
	lossCheck:
	beq $a0,$s0,loss
	jr $ra
	
	#Shuffling stuff in ghost1
	#S3 contains original address of ghost, s4 contains incremented address
	
	spookyGhosts:
		li $v0,42
		li $a1,3
		syscall
		move $t1,$a0
		li $v0,42
		li $a1,3
		syscall
		move $t2,$a0
		
		addi $t1,$t1,6
		addi $t2,$t2,6
		#Correcting indices
		add $t1,$t1,$s3
		add $t2,$t2,$s3
		#Converting to address
		lb $t3,0($t1)
		lb $t4,0($t2)
		
		sb $t4,0($t1)
		sb $t3,0($t2)
	
		lb $a0,0($s3)
		lb $a1,1($s3)
	
		tryAgain:
		addi $s4,$s4,1
		lb $t5,5($s4)
		
		li $t2,4
		beq $t2,$t5,moveRight
		
		li $t2,3
		beq $t2,$t5,moveLeft
		
		li $t2,1
		beq $t2,$t5,moveUp
		
		li $t2,2
		beq $t2,$t5,moveDown
	
		moveRight:
		addi	$a0,$a0,1
		lb $t5,3($s3)
		beq $t5,$a0,wallHitRight
				
								
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal	_getLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		
		li	$t2,2
		beq	$v0,$t2,wallHitRight
		
		li $a2,1
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		addi $a0,$a0,-1
		li $a2,0
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		addi $a0,$a0,1
		
		#Last byte in array is backtracking direction
		li $t3,1
		sb $t3,6($s3)
		li $t3,2
		sb $t3,7($s3)
		li $t3,4
		sb $t3,8($s3)
		li $t3,3
		sb $t3,9($s3)
		
		j ghost2jump
		
		wallHitRight:
		addi $a0,$a0,-1
		j tryAgain
		
		moveLeft:
		addi	$a0,$a0,-1
		lb $t5,2($s3)
		beq $t5,$a0,wallHitLeft
		
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal	_getLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		
		li	$t2,2
		beq	$v0,$t2,wallHitLeft

		li $a2,1
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		
		addi $a0,$a0,1
		li $a2,0
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		addi $a0,$a0,-1
		
		#Last byte in array is backtracking direction
		li $t3,1
		sb $t3,6($s3)
		li $t3,2
		sb $t3,7($s3)
		li $t3,3
		sb $t3,8($s3)
		li $t3,4
		sb $t3,9($s3)
		j ghost2jump
		
		wallHitLeft:
		addi $a0,$a0,1
		j tryAgain
		
		moveUp:
		addi	$a1,$a1,1
		lb $t5,5($s3)
		beq $t5,$a1,wallHitUp
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal	_getLED
		lw $ra,0($sp)
		addi $sp,$sp,4

		li	$t2,2
		beq	$v0,$t2,wallHitUp

		li $a2,1
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		
		addi $a1,$a1,-1
		li $a2,0
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		addi $a1,$a1,1
		
		#Last byte in array is backtracking direction
		li $t3,1
		sb $t3,6($s3)
		li $t3,3
		sb $t3,7($s3)
		li $t3,4
		sb $t3,8($s3)
		li $t3,2
		sb $t3,9($s3)
		j ghost2jump
		
		wallHitUp:
		addi $a1,$a1,-1
		j tryAgain
		
		
		moveDown:
		addi	$a1,$a1,-1
		lb $t5,4($s3)
		beq $t5,$a1,wallHitDown
		
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal	_getLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		
		li	$t2,2
		beq	$v0,$t2,wallHitDown
		
		li $a2,1
		
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		
		addi $a1,$a1,1
		li $a2,0
		addi $sp,$sp,-4
		sw $ra,0($sp)
		jal _setLED
		lw $ra,0($sp)
		addi $sp,$sp,4
		addi $a1,$a1,-1
		
		#Last byte in array is backtracking direction
		li $t3,2
		sb $t3,6($s3)
		li $t3,3
		sb $t3,7($s3)
		li $t3,4
		sb $t3,8($s3)
		li $t3,1
		sb $t3,9($s3)
		
		j ghost2jump
		
		wallHitDown:
		addi $a1,$a1,1
		j tryAgain
		
		ghost2jump:
		sb $a0,0($s3)
		sb $a1,1($s3)
		jr $ra

	aiContinue:
	la	$v0,0xffff0000		# address for reading key press status
	lw	$t0,0($v0)		# read the key press status
	andi	$t0,$t0,1
	beq	$t0,$0,poll		# no key pressed
	lw	$t0,4($v0)		# read key value
lkey:	addi	$v0,$t0,-226		# check for left key press
	bne	$v0,$0,rkey		# wasn't left key, so try right key
	
	
	move	$a0,$s0
	beq $a0,$zero,poll
	addi	$a0,$a0,-1
	move	$a1,$s1
	jal	_getLED
	li	$t0,2
	beq	$v0,$t0,poll
	li	$a0,-1
	li	$a1,0			# decrement current color
	jal	_drawPlayer		# redraw box in new color
	addi $s5,$s5,1
	j	poll
	
rkey:	addi	$v0,$t0,-227		# check for right key press
	bne	$v0,$0,dkey		# wasn't right key, so check for center
	move	$a0,$s0
	li 	$t0,63
	beq $a0,$t0,poll
	addi	$a0,$a0,1
	move	$a1,$s1
	jal	_getLED
	li	$t0,2
	beq	$v0,$t0,poll
	
	li	$a0,1
	li	$a1,0
	jal	_drawPlayer		# redraw box in new color
	addi 	$s5,$s5,1
	li 	$t9,63
	beq	 $a0,$t9,maybeWin
	j	poll
	

dkey:	addi	$v0,$t0,-224		# check for right key press
	bne	$v0,$0,ukey		# wasn't right key, so check for center
	beq $s1,$zero,poll
	move	$a1,$s1
	addi	$a1,$a1,-1
	move	$a0,$s0
	jal	_getLED
	li	$t0,2
	beq	$v0,$t0,poll

	li	$a1,-1
	li	$a0,0
	jal	_drawPlayer		# redraw box in new color
	addi $s5,$s5,1
	j	poll	
	
ukey:	addi	$v0,$t0,-225		# check for right key press
	bne	$v0,$0,poll
	
	li $t0,63
	beq $s1,$t0,poll
					# wasn't right key, so check for center
	move	$a1,$s1
	
	addi	$a1,$a1,1
	move	$a0,$s0
	jal	_getLED
	li	$t0,2
	beq	$v0,$t0,poll
	li	$a1,1
	li	$a0,0
	jal	_drawPlayer		# redraw box in new color
	addi $s5,$s5,1
	li $t9,63
	beq $a0,$t9,maybeWin
	j	poll

#Checking if victory conditions met
maybeWin:
beq $a1,$t9,win
j poll

_drawPlayer:
li $a2,0
move $t4,$a0
move $t5,$a1
move $a0,$s0
move $a1,$s1

#Prologue
addi $sp,$sp,-4
sw $ra,0($sp)

jal _setLED

#Epilogue
lw $ra,0($sp)
addi $sp,$sp,4

add $s0,$s0,$t4
add $s1,$s1,$t5
move $a0,$s0
move $a1,$s1
li $a2,3

#Prologue
addi $sp,$sp,-4
sw $ra,0($sp)

jal _setLED

#Epilogue
lw $ra,0($sp)
addi $sp,$sp,4

jr $ra







_getLED:
	# byte offset into display = y * 16 bytes + (x / 4)
	sll  $t0,$a1,4      # y * 16 bytes
	srl  $t1,$a0,2      # x / 4
	add  $t0,$t0,$t1    # byte offset into display
	la   $t2,0xffff0008
	add  $t0,$t2,$t0    # address of byte with the LED
	# now, compute bit position in the byte and the mask for it
	andi $t1,$a0,0x3    # remainder is bit position in byte
	neg  $t1,$t1        # negate position for subtraction
	addi $t1,$t1,3      # bit positions in reverse order
    	sll  $t1,$t1,1      # led is 2 bits
	# load LED value, get the desired bit in the loaded byte
	lbu  $t2,0($t0)
	srlv $t2,$t2,$t1    # shift LED value to lsb position
	andi $v0,$t2,0x3    # mask off any remaining upper bits
	jr   $ra

_setLED:
	# byte offset into display = y * 16 bytes + (x / 4)
	sll	$t0,$a1,4      # y * 16 bytes
	srl	$t1,$a0,2      # x / 4
	add	$t0,$t0,$t1    # byte offset into display
	li	$t2,0xffff0008 # base address of LED display
	add	$t0,$t2,$t0    # address of byte with the LED
	# now, compute led position in the byte and the mask for it
	andi	$t1,$a0,0x3    # remainder is led position in byte
	neg	$t1,$t1        # negate position for subtraction
	addi	$t1,$t1,3      # bit positions in reverse order
	sll	$t1,$t1,1      # led is 2 bits
	# compute two masks: one to clear field, one to set new color
	li	$t2,3		
	sllv	$t2,$t2,$t1
	not	$t2,$t2        # bit mask for clearing current color
	sllv	$t1,$a2,$t1    # bit mask for setting color
	# get current LED value, set the new field, store it back to LED
	lbu	$t3,0($t0)     # read current LED value	
	and	$t3,$t3,$t2    # clear the field for the color
	or	$t3,$t3,$t1    # set color field
	sb	$t3,0($t0)     # update display
	jr	$ra

loss: 
la $a0,lossMsg
li $v0,4
syscall
move $a0,$s5
li $v0,1
syscall
li $v0,10
syscall


win:
la $a0,endMsg
li $v0,4
syscall
move $a0,$s5
li $v0,1
syscall
la $a0,endMsg2
li $v0,4
syscall
li $v0,10
syscall


