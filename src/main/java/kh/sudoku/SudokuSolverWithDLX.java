package kh.sudoku;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kh.sudoku.io.GridInputReader;
import kh.sudoku.io.GridOutputWriter;

public class SudokuSolverWithDLX {
    
    private ConstraintCell rootNode;
    private CombinationGenerator generator = new CombinationGenerator();
    private DancingLinks dancingLinks = new DancingLinks();
    
    private int recursiveDepthCount;
    private int valuesTriedCount;
    private boolean endSearch = false;;
    
    private int solutions = 0;
    private long startTime = 0;
    private long endTime = 0;
    
    private String solution = null;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SudokuSolverWithDLX.class);
    
    /**
     * LinkedList of the potentialCandiates identified so far
     */
    private Deque<ConstraintCell> potentialSolutionCandiates = new LinkedList<>();
    
    private List<String> givenSolutions = new ArrayList<>();
    
    public static void main(String[] args) {
        //moved sample call to SudokuSolverWithDLXSamplePuzzlesTest
    }
    
    public SudokuSolverWithDLX() {
    }
    
    public String run(List<String> givenSolutionsShorthand) {
        
        GridInputReader reader = new GridInputReader();
        this.givenSolutions = reader.readGivenSolutions(givenSolutionsShorthand);        
        
        //initialize with givens
        this.initiateCandidateMatrix(givenSolutions);        

        try {
            GridOutputWriter writer = new GridOutputWriter();
            this.startTime = System.currentTimeMillis();
            this.solve();
            this.endTime = System.currentTimeMillis();
            System.out.println("... search ended, nodes in solution list: " + this.potentialSolutionCandiates.size());
            this.printSolutionList();
            
        }
        catch(Error e) {
            System.out.println("candidate solution rows so far: " + this.potentialSolutionCandiates.size());
        }
        return this.solution;
    }
    
    private List<String> convertSolutionCandidateListToListString(){
        List<String> values = new ArrayList<>();
        for(ConstraintCell c : this.potentialSolutionCandiates) {
            values.add(c.getName());
        }
        values.addAll(this.givenSolutions);
        
        return values;
    }
    
    private void printSolutionList() {
        if(LOGGER.isDebugEnabled()) {
            for(ConstraintCell c : this.potentialSolutionCandiates) {
                LOGGER.debug(c.getName());
            }
        }
    }
    
    /**
     * Removes each of the given solutions from the matrix.
     */
    public void removeGivenSolutionsFromSolutionMatrix(ConstraintCell rootNode, List<String> givenCells) {

        for(String givenCell : givenCells) {
            LOGGER.debug("Removing given solution: " + givenCell);
            this.dancingLinks.coverCandidateRow(this.rootNode, givenCell);
        }
    }
    
    public void solve() {
        recursiveDepthCount++;
        if(!endSearch) {
            //Knuth DLX: if R[h] = h print solution and return
            //interpretation 1: if node on right of root node is the root node, there are no columns left
            //interpretation 2: if constraint matrix still has columns that have not yet been satisfied, continue, else end
            if(this.rootNode.getRight() == this.rootNode || 
                    this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) == 0) {
                System.out.println("end: solution found");
                this.printSolutionList();
                
                //TODO: continue searching for additional solutions
                //endSearch = true;
            }
            else {
                LOGGER.debug("columns remaining: " 
                        + this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) 
                        + " (depth: " + this.recursiveDepthCount + ")");
                                
                //Knuth DLX: Otherwise chose a column object c
                //interpretation: select a constraint column from matrix
                // - non-deterministic approach: chose next available column
                // - deterministic approach: chose column with least number of 1s
                ConstraintCell c = this.getNextColumn(this.rootNode);
                if(c == null) {
                    LOGGER.error("*** c is null!");
                    return;
                }
                LOGGER.debug("next column constraint: " + c.getName());
                
                this.dancingLinks.coverColumn(c);
                
                //Knuth DLX: for each r <- D[c], D[D[c]], ..., while r != c
                //interpretation: for each row in the current column, moving down
                for(ConstraintCell r = c.getDown(); r != c; r = r.getDown()) {
                    //Knuth DLX: set Ok <- r
                    //interpretation: add current row to solution
                    potentialSolutionCandiates.add(r);
                    valuesTriedCount++;
                    
                    //Knuth DLX: for each j <- R[r], R[R[r]], ..., while j != r
                    //interpretation: for each cell to the right in the current row
                    ConstraintCell j = r;
                    for(j = r.getRight(); j != r; j = j.getRight()) {
                        //Knuth DLX: cover column C[j]
                        this.dancingLinks.coverColumn(this.dancingLinks.getColumnHeaderForCell(j));
                    }

                    //check if we've found a solution
                    this.checkForSolution();
                    
                    //Knuth DLX: search(k+1)
                    this.solve();
                    
                    //Knuth DLX: set r <- Ok and c <- C[r]
                    r = this.potentialSolutionCandiates.removeLast();
                    c = this.dancingLinks.getColumnHeaderForCell(r);
    
                    ConstraintCell jj = r;
                    //Knuth DLX: for each j <- L[r],L[L[r]], ..., while j != r
                    for(jj = r.getLeft(); jj != r; jj = jj.getLeft()) {
                        //Knuth: uncover column C[j]
                        this.dancingLinks.uncoverColumn(this.dancingLinks.getColumnHeaderForCell(jj));
                    }
                    
                }
                //Knuth DLX: uncover column c and return
                this.dancingLinks.uncoverColumn(c);
            }
        }
        recursiveDepthCount--;
    }

    public void checkForSolution() {
        if(this.potentialSolutionCandiates.size() == (CombinationGenerator.MAX_COLS * CombinationGenerator.MAX_ROWS) 
                - this.givenSolutions.size()) {
            this.endTime = System.currentTimeMillis();
            LOGGER.debug("current solution rows: ");
            this.printSolutionList();
            GridOutputWriter writer = new GridOutputWriter();
            System.out.println("Starting puzzle:");
            writer.writeGrid(givenSolutions, 9, 9);
            System.out.println("Solution:");
            this.solution = writer.writeGrid(this.convertSolutionCandidateListToListString(), 9, 9);
            
            System.out.println("recursive depth count: " + this.recursiveDepthCount);
            System.out.println("potential candidates tried count: " + this.valuesTriedCount);
            System.out.println("Elapsed ms: " + (endTime - startTime));
            this.solutions++;
            if(this.solutions == 2) {
                System.exit(0);
            }
        }
    }

    /**
     * Get next column sequentially from the list.
     * 
     * Note: the suggested better alternative is to get the next column with the 
     * least number of satisfied rows.
     * 
     * 
     * @param rootNode
     * @return
     */
    private ConstraintCell getNextColumn(ConstraintCell c) {
        int remainingConstraints = this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode);
        int solutionsForColumn = 0;
        ConstraintCell nextColumn = null;   
        
        
        //NOTE: getting the next column based on the next that has any remaining constraints is a terrible approach
        
        //get next column based on the next column with least number of constraints remaining
        //this performs far better than next the next sequential remaining column
        ConstraintCell colWithLeastRemaining = null;
        int leastRemaining = 0;
        for(nextColumn = c.getRight(); nextColumn.getRight() != c; nextColumn = nextColumn.getRight()) {
            solutionsForColumn = this.dancingLinks.countRemainingCandidateSolutionRowsInColumn(nextColumn);

            if(colWithLeastRemaining == null) {
                colWithLeastRemaining = nextColumn;
                leastRemaining = solutionsForColumn;
            }
            else {
                if(solutionsForColumn > 0 && solutionsForColumn <= leastRemaining) {
                    leastRemaining = solutionsForColumn;
                    colWithLeastRemaining = nextColumn;
                }
            }
//            //terrible approach: first with more than 0
//            if(solutionsForColumn > 0) {
//                //System.out.println("... remaining rows for column " + nextColumn.getName() + " : " + solutionsForColumn);
//                break;
//            }
//            else {
//                //System.out.println("... remaining rows for column " + nextColumn.getName() + " : " + solutionsForColumn + " ... trying next");
//            }
        }
        return colWithLeastRemaining;
    }

    public ConstraintCell initiateCandidateMatrix(List<String> givenSolutions) {
        this.rootNode = this.generator.generateConstraintGrid(givenSolutions);
        
        this.removeGivenSolutionsFromSolutionMatrix(this.rootNode, givenSolutions);
        
        return this.rootNode;
    }
    
}
