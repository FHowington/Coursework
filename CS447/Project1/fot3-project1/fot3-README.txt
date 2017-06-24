Forbes Turley fot3@pitt.edu
1. Zombie Navigation

The zombies move at random until the player enters their quadrant. Instead of writing four seperate movement blocks for the zombies, the zombies each have seperate variables defined. Each zombie has the address of its variable loaded into a register, and then the program jump and links to a control block for the zombie. Based on the parameters defined within each zombie variable, the control block knows what the boundaries are for the zombies movement and limits its movement to those boundaries. Furthermore, it keeps track of the backtracking direction using an array of the 4 possible movement directions. The backtracking direction is the last direction in the array. The first three directions are shuffled then attempted prior to every movement to produce a truly random motion, with only the first successful motion being executed. In this way, the backtracking movement is given the lowest priority.

Once the player enters one of the zombie's quadrants, the program skips the random motion code block for the zombie, and instead jumps and links to a player seeking code block. Because the zombie's variable was again loaded into a register prior to the jal, the code block knows the boundaries in which it can attempt to follow the player, and will not move out of its quadrant.
When seeking the player, the ghost will randomly attempt to move towards the player along the x-axis or y-axis. Ths ghost will never make a move that increases the distance to the player while the player is in the same quadrant as the ghost.

The zombies detect the walls by checking the color of the block into which they want to move prior to performing the motion, and will not perform it if the block is yellow. After the zombies move, they set the block that they just left to off. When the zombies move, they check to see if they are in the same block as the player. If so, the program terminates and the player capture message is displayed.

2. Player movement

The player is restricted to moving within the actual confines of the LED display, so that the player cannot move outside of the game area. This is checked every time the player moves, and the move is not allowed if it would move the player out of bounds.
Whenever the player moves, if the movement is successful (not into a wall or out of bounds), the register containing the count of moves the player has made is incremented. 
When the player presses a button to move, the code checks in the same way as the zombies to see if the move is legal. It checks the color of the block that the move is towards, and does not allow the move if the block is yellow. If the move is legal, the players LED is moved, and the block that the player just left is set to off. Whenever the player makes a downward or rightward move, the game checks to see if the player has entered block (63,63). If so, the victory message and number of moves is printed and the game terminates.

3. Issues

There are no known issues with the game. As stated in the instructions, MARS may crash if the movement buttons are pressed too rapidly.
