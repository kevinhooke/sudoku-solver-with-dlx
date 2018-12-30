package kh.soduku;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SudokuSolverWithDLX {

    /*
    { 0, 0, 0, 8, 1, 0, 6, 7, 0 }, 
    { 0, 0, 7, 4, 9, 0, 2, 0, 8 },
    { 0, 6, 0, 0, 5, 0, 1, 0, 4 }, 
    { 1, 0, 0, 0, 0, 3, 9, 0, 0 }, 
    { 4, 0, 0, 0, 8, 0, 0, 0, 7 },
    { 0, 0, 6, 9, 0, 0, 0, 0, 3 }, 
    { 9, 0, 2, 0, 3, 0, 0, 6, 0 }, 
    { 6, 0, 1, 0, 7, 4, 3, 0, 0 },
    { 0, 3, 4, 0, 6, 9, 0, 0, 0 }
    */
    

    
    private ConstraintCell rootNode;
    private CombinationGenerator generator = new CombinationGenerator();
    private DancingLinks dancingLinks = new DancingLinks();
    /**
     * List of the given solutions in the puzzle. These are removed from the candidate matrix
     * when the solver starts.
     */
    private Deque<ConstraintCell> givenSolutionCandidates = new LinkedList<>();
    
    /**
     * LinkedList of the potentialCandiates identified so far
     */
    private Deque<ConstraintCell> potentialSolutionCandiates = new LinkedList<>();
    
    /**
     * Linked list of constraint cells removed from the matrix each time a potential solution row
     * is chosen. This list is used to backtrack or uncover previously removed nodes to retry
     * another solution.
     * 
     * When nodes are removed, the solution candidate cell is added first (the row header cell on the left
     * of the matrix) followed by each of the removed already covered cells.
     * 
     * To backtrack, each of the removed cells is uncovered / added back in to the matrix until the last
     * solution candidate cell is reached. At this point a different candidate solution is chosen.
     */
    private Deque<ConstraintCell> removedConstraintCells = new LinkedList<>();
    
    /**
     * Map of previously tried candidates, keyed by candidate name (e.g. 1:r1:c1)
     */
    private Map<String, Deque<ConstraintCell>> previouslyTriedCandidateCells = new HashMap<>();
    
    
    public SudokuSolverWithDLX() {
        this.initiateCandidateMatrix();
        this.solve();
    }
    
    /**
     * Adds a row (represented by the cell in column 0) to the list of possible solutions.
     * 
     * @param cell
     */
    public void addRowToPossibleSolutions(ConstraintCell cell) {
        
        potentialSolutionCandiates.add(cell);        
    }
    
    /**
     * Removes each of the given solutions from the matrix.
     */
    public void removeGivenSolutionsFromSolutionMatrix(ConstraintCell rootNode, List<String> givenCells) {

        ConstraintCell nodeToRemove = rootNode;
        for(String givenCell : givenCells) {
            
            while( !(nodeToRemove.getName().equals(givenCell)) ) {
                nodeToRemove = nodeToRemove.getDown();
            }
            System.out.println("Found candidate node: " + nodeToRemove.getName());
            this.dancingLinks.removeCandidateRow(nodeToRemove);
            
            //TODO:need to cover all matching constraints in row AND and other rows with matching constraints in same column

        }
    
    
    }
    
    public void solve() {

        
        // if constraint matrix still has columns with 1s, continue, else end
        
        // select a constraint column from matrix with least number of 1s
        
        // select row in matrix that satisfies this contraint (has a 1 in this column)
        // note: chose row that has not yet been tried as a solution, implying we need to track which have already been tried
        
        // ? cover what here
        
        // repeat
        
    }

    private void initiateCandidateMatrix() {
        this.rootNode = this.generator.generateConstraintGrid();
        
    }
    
}
