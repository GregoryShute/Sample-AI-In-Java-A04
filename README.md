Gregory Shute

This a Kakuro game solver and validator ( you can look it up) for an AI class I took. The input format which was given by the instructor follows these rules.

The first line is the board dimensions (space delimited).
The subsequent lines specify constraints. Each constraint line has the coordinates on the tile the constraint is on followed by an x, v or h specifying whether the constraint is blank, vertical or horizontal. Lastly the value of the constraint is specified. All of this is space delimited.

You can also list multiple constraints on the same tile on different lines.

You can list constraints in any order you want.

This solver can usually handle up to 16 by 16 boards easily (under a second). It can possibly handle even larger boards but I have not tested that.

Example input:

6 6  
0 0 x 0  
1 0 v 5    
2 0 v 35  
3 0 x 0   
4 0 v 35  
5 0 v 3  
0 1 h 13  
3 1 v 24   
3 1 h 7     
0 2 h 21   
0 3 x 0   
1 3 v 4  
1 3 h 24  
5 3 v 5    
0 4 h 23  
0 5 h 11  
3 5 h 12   




Optimizations: 

Parser:
Each tile of the board starts with an array of available values 1-9. The parser reduces this number by finding an upper and lower bound for each tile in which the player can place a value. The parser does this by checking a tiles vertical constraint and looking at the corresponding number of other cells in it's vertical contiguous cell path. It finds the upper bound by checking the scenario where all those other cells are the least possible distinct values and subtracting this from the constraint. It finds the lower bound by checking the scenario where all those other cells are the greatest possible distinct values and subtracting this from the constraint. The parser checks a tiles horizontal constraint in a similar way.  

Assignment and removal of Value to Variable:
When a value is assigned to a tile (variable), available values of tiles that are in that tiles contiguous cross are removed from those tiles available values list. When a value is unassigned from a tile, available values of tiles that are in that tiles contiguous cross are added to those tiles available values except in some cases they are immediately removed again. This is because they'd add an available value to a tile in another contiguous cross which would be inconsistant based on the values already assigned to in the contiguous cross or because the parser removed them at the beginning (in this case they should never be added back in).   

Consistency Check:

My consistency check makes sure that it is still possible meet all the constraints based on the current state of the board, after every assignment.

Forward Check:

My forward check makes sure that every tile has at least one available value left in it's list. 

What I got Working:

As far as I know, everything should be working. If my program thinks there are multiple solutions to a board, it will print two solutions because it the search does not stop after I print the solution. I haven't found a board yet that gives multiple solutions. Even though the search does not stop after finding a solution, it still finished the 13x13 and 16x16 boards posted on moodle in under a second on my machine. 