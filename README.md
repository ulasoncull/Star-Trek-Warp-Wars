# Star-Trek-Warp-Wars
The aim of the project is to develop a robot "space maze" game, 
in which treasures are collected while walking in it.

General Information

The game is played in a 23*55 game field including walls. There are two competitors: Player (P) and Computer (C). There are some treasures/numbers in the game, which the players collect to increase their scores. The aim of the game is gaining the highest end-game score.

Game Elements
 
P : Player
•	Player uses cursor keys to control P.
•	Player has a backpack (size of 8 elements).
•	Player uses WASD keys to remove an element from the backpack.
•	Player has an energy for quick movement (2 times faster).

C : Computer robots
•	Computer controls all C robots.
•	Treasures are 2 times valuable for computer.
•	C robot selects a target, then tries to go to that target directly.   

Numbers (1-5): Treasure elements
•	1-3 : Static numbers. They cannot move.
•	4-5 : Moving numbers. They move randomly.

Other treasures:
•	= : Trap device. It stops the numbers and C robots.
•	* : Warp device. It warps the numbers and C robots (So, they are out of game).
o	Computer robots and numbers cannot detect/avoid trap/warp devices. 
o	Trap and warp devices do not affect human player.
o	Trap and warp devices have an effect (trap/warp) area: Square of the device and 8 neighbor squares (total 3*3 area) 
o	Trap and warp devices have a duration: They are active for 25 seconds after activation, then they disappear. 

Game Playing Information
 
The players (P or C) collect the number when they reach to the number’s square. Movements are in 4 directions (no diagonal movement). There cannot be more than one game element in the same square. Game elements which cannot take or harm each other, behave like walls when they encounter.

Normal game speed is 2 frames per second for all movements. Player quick movement is 2 times faster than normal speed. When the player has no energy, the player loses the quick movement ability temporarily and can move only at normal speed.

When the human player reaches a number's square, he/she takes the number's score points and also places the number (for 2, 3, 4 or 5) into the backpack. At the top of the backpack, if two elements are identical numbers, they turn into another power or device. If two elements are different numbers, they disappear.

Two identical numbers	turns into Power or Device
2	Energy for 30 seconds
3	Trap device
4	Energy for 240 seconds
5	Warp device

Backpack elements (2, 3, 4, 5, = or *) can be removed from the backpack by using WASD keys. In that situation, removed element is placed into the neighbor square of P (using related direction with the key). If the removed element is a number, it disappears. If removed element is a trap or warp device, it is activated there for 25 seconds. Trap and warp devices are Starfleet technology. So, the human player gains the score of every warped number (static or moving) or warped C robots.  

Computer robots have normal speed but treasures are 2 times valuable for score points. If a C robot becomes neighbor with P, P loses 2 elements of backpack. If a C robot reaches the square of P, P loses 1 life. If the human player loses all 5 lives, game ends. When the game ends, end-game score is calculated.
