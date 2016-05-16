Gregory Shute

My fixed version (I think fixed). Previous implementation had a problem with parsing certain input orders. The problem could have been fixed in by chaning the validator as well.

Optimizations: 

Parser:
Each tile of the board starts with an array of available values 1-9. The parser reduces this number by finding an upper and lower bound for each tile in which the player can place a value. The parser does this by checking a tiles verticle constraint and looking at the corresponding number of other cells in it's verticle contiguous cell path. It finds the upper bound by checking the scenario where all those other cells are the least possible distinct values and subtracting this from the constraint. It finds the lower bound by checking the scenario where all those other cells are the greatest possible distinct values and subtracting this from the constraint. The parser checks a tiles horizontal constraint in a similar way.  

Assignment and removal of Value to Variable:
When a value is assigned to a tile (variable), available values of tiles that are in that tiles contiguous cross are removed from those tiles available values list. When a value is unassigned from a tile, available values of tiles that are in that tiles contiguous cross are added to those tiles avaiable values except in some cases they are immediatley removed again. This is because they'd add an available value to a tile in another contiguous cross which would be inconsist based on the values already assigned to in the contiguous cross or because the parser removed them at the beginning (in this case they should never be added back in).   

Consistency Check:

My consistency check makes sure that it is still possible meet all the constraints based on the current state of the baord, after every assignment.

Forward Check:

My forward check makes sure that every tile has at least one available value left in it's list. 

What I got Working:

As far as I know, everything should be working. If my program thinks there are multiple solutions to a board, it will print two solutions because it the search does not stop after I print the solution. I haven't found a board yet that gives multiple solutions. Even though the search does not stop after finding a solution, it still finished the 13x13 and 16x16 boards posted on moodle in under a second on my machine. 