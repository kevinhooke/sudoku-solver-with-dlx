package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.DancingLinks;

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
    
    @Test
    public void testFindColumnByConstraintName_nr1c1_andCheckLinks() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r1:c1");
        
        ConstraintCell rowCell = cell.getDown();
        assertNotNull(rowCell.getLeft());
        //TODO: right link is null
        assertNotNull(rowCell.getRight());
    }

    @Test
    public void testFindColumnByConstraintName_nr1c2_andCheckLinks() {
        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "n:r1:c2");
        
        ConstraintCell rowCell = cell.getDown();
        assertNotNull(rowCell.getLeft());
        //TODO: right link is null
        assertNotNull(rowCell.getRight());
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
    
//    @Test(expected = ConstraintColumnNotFoundException.class)
//    public void testFindColumnByConstraintName_invalidName() {
//        ConstraintCell cell = this.links.findColumnByConstraintName(this.rootNode, "invalid");
//        assertTrue(cell.getName().equals("n:r1:c1"));
//    }
//    
//    @Test
//    public void testFindCandidateSolutionRowByName_1r1c1() {
//        ConstraintCell cell = this.links.findCandidateSolutionRowByName("1:r1:c1", this.rootNode);
//        assertTrue(cell.getName().equals("1:r1:c1"));
//    }
//
//    @Test
//    public void testFindCandidateSolutionRowByName_2r1c1() {
//        ConstraintCell cell = this.links.findCandidateSolutionRowByName("2:r1:c1", this.rootNode);
//        assertTrue(cell.getName().equals("2:r1:c1"));
//    }
//
//    @Test
//    public void testFindCandidateSolutionRowByName_9r1c9() {
//        ConstraintCell cell = this.links.findCandidateSolutionRowByName("9:r1:c9", this.rootNode);
//        assertTrue(cell.getName().equals("9:r1:c9"));
//    }
//
//    @Test
//    public void testFindCandidateSolutionRowByName_1r2c1() {
//        ConstraintCell cell = this.links.findCandidateSolutionRowByName("1:r2:c1", this.rootNode);
//        assertTrue(cell.getName().equals("1:r2:c1"));
//    }
//    
//    @Test
//    public void testFindCandidateSolutionRowByName_9r9c9() {
//        ConstraintCell cell = this.links.findCandidateSolutionRowByName("9:r9:c9", this.rootNode);
//        assertTrue(cell.getName().equals("9:r9:c9"));
//    }
}
