package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.DancingLinks;

public class CombinationGeneratorTest {

    private CombinationGenerator generator = new CombinationGenerator();
    private ConstraintCell rootCell = generator.generateConstraintGrid();
    
    /**
     * Tests number of generated cell combinations is 9 rows * 9 cells * 9 values (1..9)
     */
    @Test
    public void testCellCombinationsInGeneratedMatrix() {
        
        int numberOfCellCombinations = 0;
        ConstraintCell nextCell = this.rootCell;
        
        while((nextCell = nextCell.getDown()) != this.rootCell) {
            numberOfCellCombinations++;
        }

        assertEquals(9*9*9, numberOfCellCombinations);
    }
    
    @Test
    public void testSatifiedConstraintsInEachRowInGeneratedMatrix() {
        
        int numberOfSatisfiedContrainstsPerRow = 0;
        ConstraintCell cellCombination = this.rootCell;
        ConstraintCell satisfiedConstraint = null;

        
        while((cellCombination = cellCombination.getDown()) != this.rootCell) {
            numberOfSatisfiedContrainstsPerRow = 0;
            System.out.print("combination " + cellCombination.getName() + ": ");
            satisfiedConstraint = cellCombination;
            //for each row
            for(int expectedConstraint = 0; expectedConstraint < 4; expectedConstraint++) {
                satisfiedConstraint = satisfiedConstraint.getRight();
            
                //print out the name of the next node part from the last iteration
                System.out.print(satisfiedConstraint.getName() + " ");
                numberOfSatisfiedContrainstsPerRow++;
            }
            System.out.println();
            assertEquals(4, numberOfSatisfiedContrainstsPerRow);
        }
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
    
    //TODO need to test removing/covering rows and counting remaining candidates
}
