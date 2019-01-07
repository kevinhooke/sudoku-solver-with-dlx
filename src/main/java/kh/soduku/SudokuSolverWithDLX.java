package kh.soduku;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SudokuSolverWithDLX {


    

    
    private ConstraintCell rootNode;
    private CombinationGenerator generator = new CombinationGenerator();
    private DancingLinks dancingLinks = new DancingLinks();
    
    private int recursiveDepthCount;
    private boolean endSearch = false;;
    
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
        List<String> givenSolutions = new ArrayList<>();
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
        this.initiateCandidateMatrix(givenSolutions);
        try {
            this.solve();
            System.out.println("... search ended, nodes in solution list: " + this.potentialSolutionCandiates.size());
            this.printSolutionList();
        }
        catch(Error e) {
            System.out.println("candidate solution rows so far: " + this.potentialSolutionCandiates.size());
        }
    }
    
    private void printSolutionList() {
        for(ConstraintCell c : this.potentialSolutionCandiates) {
            System.out.println(this.getCandiateSolutionNameFromCell(c));
        }
        
    }

    private String getCandiateSolutionNameFromCell(ConstraintCell c) {
        ConstraintCell candiateSolutionCell = c;
        while((candiateSolutionCell = candiateSolutionCell.getLeft()).getType() != NodeType.Candidate) {
            //no op
        }
        return candiateSolutionCell.getName();
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
            this.dancingLinks.coverCandidateRow(nodeToRemove);
        }
    }
    
    public void solve() {
        recursiveDepthCount++;
        
        if(!endSearch) {
            //Knuth DLX: if R[h] = h print solution and return
            //interpretation 1: if node on right of root node is the root node, there are no columns left
            //interpretation 2: if constraint matrix still has columns that have not yet been satisfied, continue, else end
            if(this.rootNode.getRight() == this.rootNode || 
                    this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) == 0 ||
                    this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) <= 20){
                //TODO: print solution
                System.out.println("test exit at 20!");
                System.out.println("end: solution found");
                this.printSolutionList();
                endSearch = true;
            }
            else {
                System.out.println("columns remaining: " 
                        + this.dancingLinks.countRemainingUnsatisfiedConstraints(this.rootNode) + " (depth: " + this.recursiveDepthCount + ")");
                //Knuth DLX: Otherwise chose a column object c
                //interpretation: select a constraint column from matrix
                // - non-deterministic approach: chose next available column
                // - deterministic approach: chose column with least number of 1s
                //TODO: get next unsolved column
                ConstraintCell c = this.getNextColumn(this.rootNode);
                if(c == null) {
                    System.out.println("*** c is null!");
                    return;
                }
                System.out.println("next column constraint: " + c.getName());
                this.dancingLinks.coverColumn(c);
                
                ConstraintCell r = c;
                
                //Knuth DLX: for each r <- D[c], D[D[c]], ..., while r != c
                //interpretation: for each row in the current column, moving down
                while((r = r.getDown()) != c) {
                //for(ConstraintCell r = c; r != c; r = r.getDown()) {
                    //Knuth DLX: set Ok <- r
                    //interpretation: add current row to solution
                    potentialSolutionCandiates.add(r);
                    
                    //Knuth DLX: for each j <- R[r], R[R[r]], ..., while j != r
                    //interpretation: for each cell to the right in the current row
                    //TODO: this part I don't think is working as expected
                    ConstraintCell j = r;
                    while((j = j.getRight()) != r) {
                        //Knuth DLX: cover column C[j]
                        //TODO test this, if candidate cell, skip?
                        if(j.getType() != NodeType.Candidate) {
                            this.dancingLinks.coverColumn(this.dancingLinks.getColumnHeaderForCell(j));
                        }
                    }
                    this.dancingLinks.coverColumn(this.dancingLinks.getColumnHeaderForCell(r));
                    
                    //Knuth DLX: search(k+1)
                    this.solve();
                    
                    //Knuth DLX: set r <- Ok and c <- C[r]
                    //TODO is this in the right place? this seems to remove everything
                    //r = this.potentialSolutionCandiates.removeLast();
    
                    //TODO: this part I don't think is working
                    ConstraintCell jj = r;
                    //Knuth DLX: for each j <- L[r],L[L[r]], ..., while j != r
                    while((jj = jj.getLeft()) != r ) {
                        //Knuth: uncover column C[j]
                        //TODO test this
                        if(jj.getType() != NodeType.Candidate) {
                            this.dancingLinks.uncoverColumn(this.dancingLinks.getColumnHeaderForCell(jj));
                        }
                    }
                    //TODO: one too many uncovers?
                    //this.dancingLinks.uncoverColumn(this.dancingLinks.getColumnHeaderForCell(r));
                    
                    //moved here
                    //r = this.potentialSolutionCandiates.removeLast();
                    this.potentialSolutionCandiates.removeLast();
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
     * Note: the suggested better alterantive is to get the next column with the 
     * least number of satisfied rows.
     * 
     * //TODO: add getNextColumnWithLeastRows() later.
     * 
     * @param rootNode
     * @return
     */
    private ConstraintCell getNextColumn(ConstraintCell c) {
        if(c.getRight() != c) {
            return c.getRight();
        }
        else {
            return null;
        }
    }

    public ConstraintCell initiateCandidateMatrix(List<String> givenSolutions) {
        this.rootNode = this.generator.generateConstraintGrid(givenSolutions);
        
        for(String givenSolutionName : givenSolutions) {
            ConstraintCell givenSolutionRow = this.dancingLinks.findCandidateSolutionRowByName(givenSolutionName, this.rootNode);
            this.dancingLinks.coverCandidateRow(givenSolutionRow);
        }
        return this.rootNode;
    }
    
}
