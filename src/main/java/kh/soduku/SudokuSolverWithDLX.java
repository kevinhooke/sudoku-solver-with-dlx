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
    
    private int recursiveDepthCount;
    
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
    
    public static void main(String[] args) {
        new SudokuSolverWithDLX().run();
    }
    
    public SudokuSolverWithDLX() {

    }
    public void run() {
        this.initiateCandidateMatrix();
        try {
        this.solve();
        }
        catch(Error e) {
            System.out.println("candidate solution rows so far: " + this.potentialSolutionCandiates.size());
        }
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
        }
    
    
    }
    
    public void solve() {
        recursiveDepthCount++;
        
        //Knuth DLX: if R[h] = h print solution and return
        //interpretation 1: if node on right of root node is the root node, there are no columns left
        //interpretation 2: if constraint matrix still has columns that have not yet been satisfied, continue, else end
        if(this.rootNode.getRight() == this.rootNode) {
            //TODO: print solution
            System.out.println("end: solution found");
        }
        else {
            System.out.println("columns remaining... (depth: " + this.recursiveDepthCount + ")");
            
            //Knuth DLX: Otherwise chose a column object c
            //interpretation: select a constraint column from matrix
            // - non-deterministic approach: chose next available column
            // - deterministic approach: chose column with least number of 1s
            //TODO: get next unsolved column
            ConstraintCell nextUnsatisfiedColumn = this.getNextColumn(this.rootNode);
            System.out.println("next column constraint: " + nextUnsatisfiedColumn.getName());
            this.dancingLinks.coverColumn(nextUnsatisfiedColumn);
            
            ConstraintCell columnCell = nextUnsatisfiedColumn;
            ConstraintCell row = null;
            
            //Knuth DLX: for each r <- D[c], D[D[c]], ..., while r != c
            //interpretation: for each row in the current column, moving down
            while((columnCell = columnCell.getDown()) != nextUnsatisfiedColumn) {
                //Knuth DLX: set Ok <- r
                //interpretation: add current row to solution
                potentialSolutionCandiates.add(columnCell);
                row = columnCell;
                
                //Knuth DLX: for each j <- R[r], R[R[r]], ..., while j != r
                //interpretation: for each cell to the right in the current row
                while((row = row.getRight()) != columnCell) {
                    //Knuth DLX: cover column C[j]
                    this.dancingLinks.coverColumn(row);
                }
                
                //Knuth DLX: search(k+1)
                this.solve();
                
                //Knuth DLX: set r <- Ok and c <- C[r]
                columnCell = this.potentialSolutionCandiates.getLast();
                nextUnsatisfiedColumn = columnCell;
                ConstraintCell uncoverRow = columnCell;
                //Knuth DLX: for each j <- L[r],L[L[r]], ..., while j != r
                while((uncoverRow = uncoverRow.getLeft()) != nextUnsatisfiedColumn ) {
                    //Knuth: uncover column C[j]
                    this.dancingLinks.uncoverColumn(uncoverRow);
                }
            }
            //Knuth DLX: uncover column c and return
            this.dancingLinks.uncoverColumn(columnCell);
        }
    }

    /**
     * Get next column sequentially from the list.
     * 
     * Note: the suggested better alterantive is to get the next column with the 
     * least number of satisfied rows.
     * 
     * //TODO: add getNextColumnWithLeastRows() later.
     * 
     * @param rootNode
     * @return
     */
    private ConstraintCell getNextColumn(ConstraintCell rootNode) {
        return rootNode.getRight();
    }

    private void initiateCandidateMatrix() {
        this.rootNode = this.generator.generateConstraintGrid();
        
    }
    
}
