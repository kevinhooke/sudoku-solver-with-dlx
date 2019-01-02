package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.ConstraintColumnNotFoundException;
import kh.soduku.DancingLinks;

public class DancingLinks_MatrixModificationTest {
    
    private static final int INITIAL_CANDIDATE_SOLUTION_ROWS = 9*9*9;
    
    //9 rows * 9 columns * 4 constraints
    private static final int INITIAL_NUMBER_OF_CONSTRAINTS = 9 * 9 * 4;
    private CombinationGenerator generator = new CombinationGenerator();
    ConstraintCell rootNode = generator.generateConstraintGrid();
    DancingLinks links = new DancingLinks();
    
    /**
     * Tests in the initial constraint matrix there are 9 * 9 * 9 * 4 = 
     */
    @Test
    public void countRemainingUnsatisfiedConstraints() {

        int result = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        
        assertEquals(INITIAL_NUMBER_OF_CONSTRAINTS, result);
    }    

    @Test
    public void testCoverAndUncoverColumn_nr1c1() {
        
        //initial state
        int initialUnsatisfiedRows = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        assertEquals(INITIAL_NUMBER_OF_CONSTRAINTS, initialUnsatisfiedRows);
        int initialCandidateRows = this.links.countRemainingCandidateSolutionRows(this.rootNode);
        assertEquals(INITIAL_CANDIDATE_SOLUTION_ROWS, initialCandidateRows);
        
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r1:c1");
        assertTrue(cell.getName().equals("n:r1:c1"));
        
        this.links.coverColumn(cell);
        int unsatisfiedConstraints = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        assertEquals(INITIAL_NUMBER_OF_CONSTRAINTS - 1, unsatisfiedConstraints);
        int remainingCandidateRows = this.links.countRemainingCandidateSolutionRows(this.rootNode);
        assertEquals(INITIAL_CANDIDATE_SOLUTION_ROWS - 9, remainingCandidateRows);
        
        //now uncover to reverse
        this.links.uncoverColumn(cell);
        unsatisfiedConstraints = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        assertEquals(INITIAL_NUMBER_OF_CONSTRAINTS, unsatisfiedConstraints);
        remainingCandidateRows = this.links.countRemainingCandidateSolutionRows(this.rootNode);
        assertEquals(INITIAL_CANDIDATE_SOLUTION_ROWS, remainingCandidateRows);
        
    }
}
