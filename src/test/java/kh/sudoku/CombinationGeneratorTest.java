package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.DancingLinks;

public class CombinationGeneratorTest {

    private CombinationGenerator generator = new CombinationGenerator();
    private ConstraintCell rootCell = generator.generateConstraintGrid(new ArrayList<String>());
    
    @Test
    public void testCellCombinationsInGeneratedMatrix() {
        assertEquals(9*9*9, this.generator.getSolutionsAddedToMatrix());
    }

    //TODO: this is not working
//    @Test
    public void testSatisfiedConstraintsPerRow() {
        
        int numberOfSatisfiedContrainstsPerRow = 0;
        ConstraintCell columnCell = this.rootCell.getRight();
        ConstraintCell rowCell = null;
        
        while((columnCell = columnCell.getDown()) != this.rootCell.getRight()) {
            numberOfSatisfiedContrainstsPerRow = 0;
            rowCell = columnCell;
            try {
            //TODO: left and right links are missing
            while((rowCell = rowCell.getRight()) != columnCell) {
                numberOfSatisfiedContrainstsPerRow++;
            }
            }
            catch(Exception e) {
                System.out.println("exception here");
            }
            System.out.println("constraints in row: " + numberOfSatisfiedContrainstsPerRow );
        }
        
    }
    
    @Test
    public void testForDuplicatesInContraints() {
        Set<ConstraintCell> constraints = new HashSet<>();
        ConstraintCell constraint = this.rootCell;
        ConstraintCell lastCell = null;
        int numberChecked = 0;
        System.out.println("first constraint: " + this.rootCell.getRight().getName());
        //iterate through linked nodes until we end up back at the root node (it's a circularly linked list)
        while((constraint = constraint.getRight()) != this.rootCell) {
            lastCell = constraint;
            numberChecked++;
            System.out.println("constraint col name: " + constraint.getName() + ", checked: " + numberChecked);
            if(!constraints.add(constraint)) {
                fail();
            }
        }
        System.out.println("number of columns checked: " + numberChecked);
        assertEquals(9*9*4, numberChecked);
    }
    
    
    @Test
    public void testRowAndColInSquare_square1() {
        assertTrue(this.generator.rowAndColInSquare(1, 1, 1));
        assertTrue(this.generator.rowAndColInSquare(1, 3, 1));
        assertTrue(this.generator.rowAndColInSquare(3, 1, 1));
        assertTrue(this.generator.rowAndColInSquare(3, 3, 1));
        
        //negative test
        assertFalse(this.generator.rowAndColInSquare(4, 1, 1));
    }

    @Test
    public void testRowAndColInSquare_square9() {
        assertTrue(this.generator.rowAndColInSquare(7, 7, 9));
        assertTrue(this.generator.rowAndColInSquare(7, 9, 9));
        assertTrue(this.generator.rowAndColInSquare(9, 7, 9));
        assertTrue(this.generator.rowAndColInSquare(9, 9, 9));
        
        //negative test
        assertFalse(this.generator.rowAndColInSquare(6, 9, 9));
    }

    /**
     * Column 1 constraint is n:c1:r1.
     * Expected result: there's 9 valid candidates to satisfy this constraint
     */
    @Test
    public void checkLinkedConstraintsForColumn1() {
        ConstraintCell columnCell = this.rootCell.getRight();
        ConstraintCell columnHeader = columnCell;
        int linkedInColumnCount = 0;
        while((columnCell = columnCell.getDown()) != columnHeader ) {
            linkedInColumnCount++;
        }
        System.out.println("column count: " + linkedInColumnCount);
        //9 matching constrains for n:c1:r1
        assertEquals(9, linkedInColumnCount);
    }

    /**
     * Test constraint column n:r1:c1.
     * Expected result: there's 9 valid candidates to satisfy this constraint
     */
    @Test
    public void checkLinkedConstraintsForColumn_nr1c1() {
        
        DancingLinks dancing = new DancingLinks();
        
        ConstraintCell columnHeader = dancing.findColumnByConstraintName(this.rootCell, "n:r1:c1");
        
        ConstraintCell columnCell = columnHeader;
        int linkedInColumnCount = 0;
        while((columnCell = columnCell.getDown()) != columnHeader ) {
            linkedInColumnCount++;
        }
        System.out.println("column count: " + linkedInColumnCount);
        //9 matching constrains for n:c1:r1
        assertEquals(9, linkedInColumnCount);
    }

    /**
     * Test constraint column 1:r2
     * Expected result: there's 9 valid candidates to satisfy this constraint
     */
    @Test
    public void checkLinkedConstraintsForColumn_1r2() {
        
        DancingLinks dancing = new DancingLinks();
        
        ConstraintCell columnHeader = dancing.findColumnByConstraintName(this.rootCell, "1:r2");
        
        ConstraintCell columnCell = columnHeader;
        int linkedInColumnCount = 0;
        while((columnCell = columnCell.getDown()) != columnHeader ) {
            linkedInColumnCount++;
        }
        System.out.println("column count: " + linkedInColumnCount);
        //9 matching constrains for 1:r2
        assertEquals(9, linkedInColumnCount);
    }
    
    /**
     * Test constraint column 2:c4
     * Expected result: there's 9 valid candidates to satisfy this constraint
     */
    @Test
    public void checkLinkedConstraintsForColumn_2c4() {
        
        DancingLinks dancing = new DancingLinks();
        
        ConstraintCell columnHeader = dancing.findColumnByConstraintName(this.rootCell, "2:c4");
        
        ConstraintCell columnCell = columnHeader;
        int linkedInColumnCount = 0;
        while((columnCell = columnCell.getDown()) != columnHeader ) {
            linkedInColumnCount++;
        }
        System.out.println("column count: " + linkedInColumnCount);
        //9 matching constrains for 2:c4
        assertEquals(9, linkedInColumnCount);
    }
    
    /**
     * Test constraint column 1:s1.
     * TODO: 1:s1 in column header linking doesn't currently have any linked nodes in column
     * something not working in the x:s constraints
     */
    @Test
    public void checkLinkedConstraintsForColumn_1s1() {
        DancingLinks dancing = new DancingLinks();
        
        ConstraintCell columnHeader = dancing.findColumnByConstraintName(this.rootCell, "1:s1");
        
        ConstraintCell columnCell = columnHeader;
        int linkedInColumnCount = 0;
        while((columnCell = columnCell.getDown()) != columnHeader ) {
            linkedInColumnCount++;
        }
        System.out.println("column count: " + linkedInColumnCount);
        //9 matching constrains for 1:s1
        assertEquals(9, linkedInColumnCount);
    }
}
