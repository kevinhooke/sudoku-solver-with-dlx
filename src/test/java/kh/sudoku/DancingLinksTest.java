package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class DancingLinksTest {
    
    private static final int INITIAL_CANDIDATE_SOLUTION_ROWS = 9*9*9;
    
    //9 rows * 9 columns * 4 constraints
    private static final int INITIAL_UNSATISIFIED_CONSTRAINTS = 9 * 9 * 4;
    private CombinationGenerator generator = new CombinationGenerator();
    ConstraintCell rootNode = generator.generateConstraintGrid(new ArrayList<String>());
    DancingLinks links = new DancingLinks();
    
    /**
     * Tests in the initial constraint matrix there are 9 * 9 * 9 * 4 = 
     */
    @Test
    public void testCountRemainingUnsatisfiedConstraints() {

        int result = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        assertEquals(INITIAL_UNSATISIFIED_CONSTRAINTS, result);
    }    
    
    /**
     * Tests in the initial constraint matrix there are 9 * 9 * 4 = 324 constraint columns. 
     */
    @Test
    public void countRemainingUnsatisfiedConstraints() {

        int result = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        
        assertEquals(INITIAL_UNSATISIFIED_CONSTRAINTS, result);
    }    

    
    
    @Test
    public void testCoverAndUncoverColumn_nr1c1() {
        
        //initial state
        int initialUnsatisfiedRows = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        assertEquals(INITIAL_UNSATISIFIED_CONSTRAINTS, initialUnsatisfiedRows);
        //TODO: with removal of row headers, there's no easy way to get this count
//        int initialCandidateRows = this.links.countRemainingCandidateSolutionRows(this.rootNode);
//        assertEquals(INITIAL_CANDIDATE_SOLUTION_ROWS, initialCandidateRows);
        
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r1:c1");
        assertTrue(cell.getName().equals("n:r1:c1"));
        
        this.links.coverColumn(cell);
        int unsatisfiedConstraints = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        assertEquals(INITIAL_UNSATISIFIED_CONSTRAINTS - 1, unsatisfiedConstraints);
//        int remainingCandidateRows = this.links.countRemainingCandidateSolutionRows(this.rootNode);
//        assertEquals(INITIAL_CANDIDATE_SOLUTION_ROWS - 9, remainingCandidateRows);
        
        //now uncover to reverse
        this.links.uncoverColumn(cell);
        unsatisfiedConstraints = this.links.countRemainingUnsatisfiedConstraints(this.rootNode);
        assertEquals(INITIAL_UNSATISIFIED_CONSTRAINTS, unsatisfiedConstraints);
//        remainingCandidateRows = this.links.countRemainingCandidateSolutionRows(this.rootNode);
//        assertEquals(INITIAL_CANDIDATE_SOLUTION_ROWS, remainingCandidateRows);
        
    }
    
    @Test
    public void testFindColumnByConstraintName_nr1c1_andCheckLinks() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r1:c1");
        
        ConstraintCell rowCell = cell.getDown();
        assertNotNull(rowCell.getLeft());
        //navigate through 4 links to circle back to starting node
        assertEquals(rowCell, rowCell.getLeft().getLeft().getLeft().getLeft());
        
        assertNotNull(rowCell.getRight());
        //navigate through 4 links to circle back to starting node
        assertEquals(rowCell, rowCell.getRight().getRight().getRight().getRight());
    }

    @Test
    public void testFindColumnByConstraintName_nr1c2_andCheckLinks() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r1:c2");
        
        ConstraintCell rowCell = cell.getDown();
        assertNotNull(rowCell.getLeft());
        //navigate through 4 links to circle back to starting node
        assertEquals(rowCell, rowCell.getLeft().getLeft().getLeft().getLeft());
        
        assertNotNull(rowCell.getRight());
        //TODO: 4th link broken
        //navigate through 4 links to circle back to starting node
        assertEquals(rowCell, rowCell.getRight().getRight().getRight().getRight());
    }
    
    @Test
    public void testFindColumnByConstraintName_nr2c2_andCheckLinks() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r2:c2");
        
        ConstraintCell rowCell = cell.getDown();
        assertNotNull(rowCell.getLeft());
        assertNotNull(rowCell.getRight());
        
        //navigate through 4 links to circle back to starting node
        assertEquals(rowCell, rowCell.getLeft().getLeft().getLeft().getLeft());
        
        //TODO: the 4th satisfied constraint is linked round to the first in the next row, instead of back to first in the row
        //navigate through 4 links to circle back to starting node
        assertEquals(rowCell, rowCell.getRight().getRight().getRight().getRight());
    }
    
    @Test
    public void testFindColumnByConstraintName_nr1c1() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r1:c1");
        assertTrue(cell.getName().equals("n:r1:c1"));
    }

    @Test
    public void testFindColumnByConstraintName_1r1() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "1:r1");
        assertTrue(cell.getName().equals("1:r1"));
    }

    @Test
    public void testFindColumnByConstraintName_1c1() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "1:c1");
        assertTrue(cell.getName().equals("1:c1"));
    }
    
    @Test
    public void testFindColumnByConstraintName_1s1() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "1:s1");
        assertTrue(cell.getName().equals("1:s1"));
    }
    
    @Test(expected = ConstraintColumnNotFoundException.class)
    public void testFindColumnByConstraintName_invalidName() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "invalid");
    }
}
