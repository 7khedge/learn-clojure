# Peg Game
Aim of game , take a pegs organised in a triangle , default 5 rows
Remove the middles peg and remove the pegs by moving a peg over another peg into a hole.
Then remove peg which moved over.


Code Organisation

1.  Create a new board
2.  Returning a bord with the results of the player's move
3.  Representing the board textually
4.  Handling user interaction


Two Layers 
    View 
        User Interaction - side effects
        Print out the board
        Uses functions in the Model layer
    Model
        Functions 
            create a new board
            make moves
            create textual representation
            do use functions in the View Layer
            
 
        