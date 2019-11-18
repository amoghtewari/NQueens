# NQueens

This is a code question in which you are tasked with implementing a tool to solve arbitrary versions of the
n-queens problem. The N-queens problem is a classical constraint problem where the goal is to determine
where queens can be placed on an n ∗ n chessboard such that no two queens threaten the other.

For this problem you will develop a python or java package that is called as follows:

NQueens.py ALG N CFile RFile

or

java -jar NQueens.jar ALG N CFile RFile

where: ALG is one of FOR or MAC representing Backtracking search with forward checking or Maintaining
Arc Consistency respectively. N represents the number of rows and columns in the chessboard as well as the
number of queens to be assigned. CFile is an output filename for your constraint problem. And RFile is an
output file for your results.
When called your code must generate and maintain an internal representation for each problem as an
instance of an object called QueenGraph and use that to calculate as many unique solutions for the problem
as it can find up to up to 2*N using the specified algorithm. At the start of the execution your code must
write a human-readable representation of the variables, domains, and constraints out to CFile. And as each
solution is found it must write it in human-readable form out to RFile. When the code completes it should
report the number of solutions found, the real time taken, and the number of backtracking steps. As always
your code should be clear, readable, and well-written.
