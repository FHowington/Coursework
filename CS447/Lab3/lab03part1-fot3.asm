.data
y: .byte 33
z: .byte 0
x: .byte 16

.text
lb $t1,y
lb $t2,x
sub $t2,$t1,$t2
la $t1,z
sb $t2,0($t1)
sb $t2,-1($t1)
sb $t2, 1($t1)
