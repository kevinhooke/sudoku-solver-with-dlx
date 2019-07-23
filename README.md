#Java Sudoku Solver

This is an implementation of Donald Knuth's Algorithm X to solve Sudoku puzzles
using Dancing Links.

This started as an investigation into whether you could solve a problem
(e.g. a Sudoku Puzzle Solver) without fully understanding the problem (in this
case without knowledge that Sudoku puzzle's being an example of a mathematical
problem that can be solved as a type of problem called 'exact cover').

I wrote a number of articles about my experience implementing a Sudoku Solver, which you can
read here:

https://www.kevinhooke.com/2016/11/12/can-you-develop-code-to-effectively-solve-a-problem-without-understanding-the-problem-first/

https://www.kevinhooke.com/2019/01/22/revisiting-donald-knuths-algorithm-x-and-dancing-links-to-solve-sudoku-puzzles/

https://www.kevinhooke.com/2019/04/22/building-a-react-frontend-for-my-aws-lambda-sudoku-solver/

A version of the code in this repo is deployed as an AWS Lambda, and is invoked via the React app
on this page here: http://react-sudoku-solver.s3-website-us-west-1.amazonaws.com/index.html