package kh.soduku;

import java.util.Deque;
import java.util.LinkedList;

public class SudokuSolverWithDLX {

    /**
     * List of the given solutions in the puzzle. These are removed from the candidate matrix
     * when the solver starts.
     */
    private Deque<ConstraintCell> givenSolutionCandiates = new LinkedList<>();
    
    /**
     * LinkedList of the potentialCandiates identified so far
     */
    private Deque<ConstraintCell> potentialSolutionCandiates = new LinkedList<>();
    
    /**
     * Adds a row (represented by the cell in column 0) to the list of possible solutions.
     * 
     * @param cell
     */
    public void addRowToPossibleSolutions(ConstraintCell cell) {
        
        potentialSolutionCandiates.add(cell);        
    }
}
