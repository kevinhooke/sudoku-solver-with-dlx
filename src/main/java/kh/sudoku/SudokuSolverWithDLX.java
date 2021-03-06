package kh.sudoku;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kh.sudoku.io.GridInputReader;
import kh.sudoku.io.GridOutputWriter;

public class SudokuSolverWithDLX {
    
    private ConstraintCell rootNode;
    private CombinationGenerator generator = new CombinationGenerator();
    private DancingLinks dancingLinks = new DancingLinks();
    private GridOutputWriter writer = new GridOutputWriter();
    
    private int maximumSolutions;
    
    private int recursiveDepthCount;
    private int totalRecursiveDepthCount;
    private int valuesTriedCount;
    private boolean endSearch = false;;
    
    private int solutions = 0;
    private long startTime = 0;
    private long endTime = 0;
    
    private String solution = null;
    private List<String> currentSolution = new ArrayList<>();
    private PuzzleResults results = new PuzzleResults();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SudokuSolverWithDLX.class);
    
    /**
     * LinkedList of the potentialCandiates identified so far
     */
    private Deque<ConstraintCell> potentialSolutionCandiates = new LinkedList<>();
    
    private List<String> givenSolutions = new ArrayList<>();
    
    public SudokuSolverWithDLX() {
    }
    
    /**
     * Run solver and return results as formatted grid output for display.
     * 
     * @param givenSolutionsShorthand
     * @return
     */
    public String runWithFormattedOutput(List<String> givenSolutionsShorthand, int maxSolutions) {
        this.run(givenSolutionsShorthand, maxSolutions);
        return this.solution;
    }
    
    /**
     * Run solver and return first result as list of Strings in shorthand format,
     * e.g. List of Strings representing each row "123456789"
     * 
     * @param givenSolutionsShorthand
     * @return
     */
    public PuzzleResults run(List<String> givenSolutionsShorthand, int maxSolutions) {
        this.maximumSolutions = maxSolutions;
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
        if(this.results.getResults().size() == 1) {
            this.results.setValidPuzzle(true);
        }
        return this.results;
    }
    
    public PuzzleResults generateNewPuzzle(List<String> givenSolutionsShorthand, int maxSolutions) {
        this.maximumSolutions = maxSolutions;
        GridInputReader reader = new GridInputReader();
        this.givenSolutions = reader.readGivenSolutions(givenSolutionsShorthand);        
        
        //initialize with givens
        this.initiateCandidateMatrix(givenSolutions);        

        try {
            GridOutputWriter writer = new GridOutputWriter();
            this.startTime = System.currentTimeMillis();
            this.generatePuzzle();
            this.endTime = System.currentTimeMillis();
            this.printSolutionList();
            
        }
        catch(Error e) {
            System.out.println("candidate solution rows so far: " + this.potentialSolutionCandiates.size());
        }
        if(this.results.getResults().size() == 1) {
            this.results.setValidPuzzle(true);
        }
        return this.results;
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
        totalRecursiveDepthCount++;
        if(!endSearch) {
            //Knuth DLX: if R[h] = h print solution and return
            //interpretation 1: if node on right of root node is the root node, there are no columns left
            //interpretation 2: if constraint matrix still has columns that have not yet been satisfied, continue, else end
            if(this.rootNode.getRight() == this.rootNode || 
                    this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) == 0) {
                System.out.println("end: solution found");
                this.printSolutionList();
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
                    //this should never happen if the implementation is working correctly
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

    public void generatePuzzle() {
        
        LOGGER.debug("current generated puzzle rows: ");
        this.solution = this.writer.writeGrid(this.convertSolutionCandidateListToListString(), 9, 9);
        System.out.println(this.solution);
        System.out.println("recursive depth: " + recursiveDepthCount);
        //this.currentSolution = this.writer.writeShorthand(this.convertSolutionCandidateListToListString(), 9, 9);
        
        recursiveDepthCount++;
        if(!endSearch) {
            //Knuth DLX: if R[h] = h print solution and return
            //interpretation 1: if node on right of root node is the root node, there are no columns left
            //interpretation 2: if constraint matrix still has columns that have not yet been satisfied, continue, else end
            if(this.rootNode.getRight() == this.rootNode || 
                    this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) == 0) {
                System.out.println("end: puzzle generated");
                this.printSolutionList();
            }
            else {
                LOGGER.debug("columns remaining: " 
                        + this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) 
                        + " (depth: " + this.recursiveDepthCount + ")");
                                
                //Knuth DLX: Otherwise chose a column object c
                //interpretation: select a constraint column from matrix
                // - for generating a new puzzle, pick a random column
                //ConstraintCell c = this.getRandomNextColumn(this.rootNode);
                ConstraintCell c = this.getNextColumn(this.rootNode);
                if(c == null) {
                    //this should never happen if the implementation is working correctly
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

                    //check if we've generated enough of a puzzle yet
                    this.checkForGeneratedPuzzle();
                    
                    //Knuth DLX: search(k+1)
                    this.generatePuzzle();
                    
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
            
            this.solutions++;
            
            this.endTime = System.currentTimeMillis();
            LOGGER.debug("current solution rows: ");
            this.printSolutionList();
            System.out.println("Starting puzzle:");
            this.writer.writeGrid(givenSolutions, 9, 9);
            System.out.println("Solution:");
            this.solution = this.writer.writeGrid(this.convertSolutionCandidateListToListString(), 9, 9);
            this.currentSolution = this.writer.writeShorthand(this.convertSolutionCandidateListToListString(), 9, 9);
            this.results.addResult(this.currentSolution);
            
            //result current solution for next
            this.currentSolution = new ArrayList<>();
            
            System.out.println("recursive depth count: " + this.recursiveDepthCount);
            System.out.println("deepest recursive depth count: " + this.totalRecursiveDepthCount);
            System.out.println("potential candidates tried count: " + this.valuesTriedCount);
            System.out.println("solutions found: " + this.solutions);
            System.out.println("Elapsed ms: " + (endTime - startTime));
            //if we've already found the specified maximum solutions set exit flag
            if(this.solutions == this.maximumSolutions) {
                this.endSearch = true;
            }
        }
    }

    public void checkForGeneratedPuzzle() {
        if(this.potentialSolutionCandiates.size() == (CombinationGenerator.MAX_COLS * CombinationGenerator.MAX_ROWS) 
                - 40) {
            
            this.solutions++;
            
            this.endTime = System.currentTimeMillis();
            LOGGER.debug("current solution rows: ");
            this.printSolutionList();
            System.out.println("Starting puzzle:");
            this.writer.writeGrid(givenSolutions, 9, 9);
            System.out.println("Solution:");
            this.solution = this.writer.writeGrid(this.convertSolutionCandidateListToListString(), 9, 9);
            this.currentSolution = this.writer.writeShorthand(this.convertSolutionCandidateListToListString(), 9, 9);
            this.results.addResult(this.currentSolution);
            
            //result current solution for next
            this.currentSolution = new ArrayList<>();
            
            System.out.println("recursive depth count: " + this.recursiveDepthCount);
            System.out.println("potential candidates tried count: " + this.valuesTriedCount);
            System.out.println("solutions found: " + this.solutions);
            System.out.println("Elapsed ms: " + (endTime - startTime));
            //if we've already found the specified maximum solutions set exit flag
            if(this.solutions == this.maximumSolutions) {
                this.endSearch = true;
            }
        }
    }
    
    /**
     * Get next column sequentially from the list.
     * 
     * This is used by the solver, but not the puzzle generator.
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
        }
        return colWithLeastRemaining;
    }

    /**
     * Get next column randomly from remaining colunmns.
     * 
     * This is used by the puzzle generator.
     *
     * @param rootNode
     * @return
     */
    private ConstraintCell getRandomNextColumn(ConstraintCell c) {
        int remainingConstraints = this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode);
        int randomColumn = new Random().nextInt(remainingConstraints);
        int solutionsForColumn = 0;
        
        ConstraintCell nextColumn = null;
        
        ConstraintCell nextRandomColumn = null;
        int currentColumn = 0;
        
        //get next random column where solutionsForColumn must be < 9
        
        //TODO: try getting all rows where remaining Contraints is lowest, and then picking random one of these
        
        for(nextColumn = c.getRight(); nextColumn.getRight() != c && (nextRandomColumn == null || currentColumn <= randomColumn); currentColumn++) {
            solutionsForColumn = this.dancingLinks.countRemainingCandidateSolutionRowsInColumn(nextColumn);
            LOGGER.debug("Candidate rows in this col: " + solutionsForColumn);
            nextColumn = nextColumn.getRight();
            
            //keep track of last column with at least 1 candidate row, so
            //we don't return a column than has 0 candidate rows
            if(solutionsForColumn > 0) {
                nextRandomColumn = nextColumn;
            }
        }
        if(nextRandomColumn == null) {
            System.out.println("uhoh");
        }
        return nextRandomColumn;
    }
    
    public ConstraintCell initiateCandidateMatrix(List<String> givenSolutions) {
        this.rootNode = this.generator.generateConstraintGrid(givenSolutions);
        
        this.removeGivenSolutionsFromSolutionMatrix(this.rootNode, givenSolutions);
        
        return this.rootNode;
    }
    
}
