package kh.sudoku;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SudokuSolverWithDLX {
    
    private ConstraintCell rootNode;
    private CombinationGenerator generator = new CombinationGenerator();
    private DancingLinks dancingLinks = new DancingLinks();
    
    private int recursiveDepthCount;
    private boolean endSearch = false;;
    
    private int solutions = 0;
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * LinkedList of the potentialCandiates identified so far
     */
    private Deque<ConstraintCell> potentialSolutionCandiates = new LinkedList<>();
    
    private List<String> givenSolutions = new ArrayList<>();
    
    public static void main(String[] args) {
        new SudokuSolverWithDLX().run();
    }
    
    public SudokuSolverWithDLX() {
    }
    
    
    /*   1  2  3  4  5  6  7  8  9
      1{ 0, 0, 0, 8, 1, 0, 6, 7, 0 }, 
      2{ 0, 0, 7, 4, 9, 0, 2, 0, 8 },
      3{ 0, 6, 0, 0, 5, 0, 1, 0, 4 }, 
      4{ 1, 0, 0, 0, 0, 3, 9, 0, 0 }, 
      5{ 4, 0, 0, 0, 8, 0, 0, 0, 7 },
      6{ 0, 0, 6, 9, 0, 0, 0, 0, 3 }, 
      7{ 9, 0, 2, 0, 3, 0, 0, 6, 0 }, 
      8{ 6, 0, 1, 0, 7, 4, 3, 0, 0 },
      9{ 0, 3, 4, 0, 6, 9, 0, 0, 0 }
    */
    
    public void run() {
        GridOutputWriter writer = new GridOutputWriter();
        
        givenSolutions.add("8:r1:c4");
        givenSolutions.add("1:r1:c5");
        givenSolutions.add("6:r1:c7");
        givenSolutions.add("7:r1:c8");
        givenSolutions.add("7:r2:c3");
        givenSolutions.add("4:r2:c4");
        givenSolutions.add("9:r2:c5");
        givenSolutions.add("2:r2:c7");
        givenSolutions.add("8:r2:c9");
        givenSolutions.add("6:r3:c2");
        givenSolutions.add("5:r3:c5");
        givenSolutions.add("1:r3:c7");
        givenSolutions.add("4:r3:c9");
        givenSolutions.add("1:r4:c1");
        givenSolutions.add("3:r4:c6");
        givenSolutions.add("9:r4:c7");
        givenSolutions.add("4:r5:c1");
        givenSolutions.add("8:r5:c5");
        givenSolutions.add("7:r5:c9");
        givenSolutions.add("6:r6:c3");
        givenSolutions.add("9:r6:c4");
        givenSolutions.add("3:r6:c9");
        givenSolutions.add("9:r7:c1");
        givenSolutions.add("2:r7:c3");
        givenSolutions.add("3:r7:c5");
        givenSolutions.add("6:r7:c8");
        givenSolutions.add("6:r8:c1");
        givenSolutions.add("1:r8:c3");
        givenSolutions.add("7:r8:c5");
        givenSolutions.add("4:r8:c6");
        givenSolutions.add("3:r8:c7");
        givenSolutions.add("3:r9:c2");
        givenSolutions.add("4:r9:c3");
        givenSolutions.add("6:r9:c5");
        givenSolutions.add("9:r9:c6");

        //3x3 grid
        //givenSolutions.add("9:r1:c1");
        //givenSolutions.add("5:r1:c1");
        //givenSolutions.add("2:r1:c1");
        //givenSolutions.add("2:r1:c2");
        //givenSolutions.add("3:r1:c3");
        
        System.out.println("Starting puzzle:");
        writer.writeGrid(givenSolutions, 9, 9);
        //writer.writeGrid(givenSolutions, 3, 3);
        //initialize with givens
        this.initiateCandidateMatrix(givenSolutions);
        

        try {
            this.solve();
            System.out.println("... search ended, nodes in solution list: " + this.potentialSolutionCandiates.size());
            this.printSolutionList();
            
            writer.writeGrid(this.convertSolutionCandidateListToListString(), 3, 3);
        }
        catch(Error e) {
            System.out.println("candidate solution rows so far: " + this.potentialSolutionCandiates.size());
        }
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
                endSearch = true;
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
                    
                    //Knuth DLX: for each j <- R[r], R[R[r]], ..., while j != r
                    //interpretation: for each cell to the right in the current row
                    ConstraintCell j = r;
                    for(j = r.getRight(); j != r; j = j.getRight()) {
                        //Knuth DLX: cover column C[j]
                        this.dancingLinks.coverColumn(this.dancingLinks.getColumnHeaderForCell(j));
                    }

                    //check if we've found a solution
                    if(this.potentialSolutionCandiates.size() == (CombinationGenerator.MAX_COLS * CombinationGenerator.MAX_ROWS) 
                            - this.givenSolutions.size()) {
                        LOGGER.debug("current solution rows: ");
                        this.printSolutionList();
                        GridOutputWriter writer = new GridOutputWriter();
                        System.out.println("Starting puzzle:");
                        writer.writeGrid(givenSolutions, 9, 9);
                        System.out.println("Solution:");
                        writer.writeGrid(this.convertSolutionCandidateListToListString(), 9, 9);
                        this.solutions++;
                        if(this.solutions == 1) {
                            System.exit(0);
                        }
                    }
                    
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
