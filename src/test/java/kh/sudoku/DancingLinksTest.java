package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.ConstraintColumnNotFoundException;
import kh.soduku.DancingLinks;

public class DancingLinksTest {

    private CombinationGenerator generator = new CombinationGenerator();
    ConstraintCell rootNode = generator.generateConstraintGrid();
    DancingLinks links = new DancingLinks();
    
    /**
     * Tests in the initial constraint matrix there are 9 * 9 * 9 * 4 = 
     */
    @Test
    public void countRemainingUnsatisfiedConstraints() {

        int result = links.countRemainingUnsatisfiedConstraints(this.rootNode);
        
        //9 rows * 9 columns * 4 constraints
        assertEquals((9 * 9 * 4), result);
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
        assertTrue(cell.getName().equals("n:r1:c1"));
    }
}
