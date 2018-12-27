package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;

public class CombinationGeneratorTest {

    /**
     * Tests number of generated cell combinations is 9 rows * 9 cells * 9 values (1..9)
     */
    @Test
    public void testCellCombinationsInGeneratedMatrix() {
        CombinationGenerator generator = new CombinationGenerator();
        ConstraintCell rootCell = generator.generateConstraintGrid();
        
        int numberOfCellCombinations = 0;
        ConstraintCell nextCell = rootCell;
        
        while((nextCell = nextCell.getDown()) != null) {
            numberOfCellCombinations++;
        }

        assertEquals(9*9*9, numberOfCellCombinations);
    }
    
    @Test
    public void testSatifiedConstraintsInEachRowInGeneratedMatrix() {
        CombinationGenerator generator = new CombinationGenerator();
    
        ConstraintCell rootCell = generator.generateConstraintGrid();
        
        int numberOfSatisfiedContrainstsPerRow = 0;
        ConstraintCell cellCombination = rootCell;
        ConstraintCell satisfiedConstraint = null;

        
        while((cellCombination = cellCombination.getDown()) != null) {
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
            //last node from calling .getRight() expected to be null
            assertNull(satisfiedConstraint.getRight());
        }
        
    }
    
    
    @Test
    public void testrowAndColInSquare_square1() {
        CombinationGenerator generator = new CombinationGenerator();
        assertTrue(generator.rowAndColInSquare(1, 1, 1));
        assertTrue(generator.rowAndColInSquare(1, 3, 1));
        assertTrue(generator.rowAndColInSquare(3, 1, 1));
        assertTrue(generator.rowAndColInSquare(3, 3, 1));
        
        //negative test
        assertFalse(generator.rowAndColInSquare(4, 1, 1));
    }

    @Test
    public void testrowAndColInSquare_square9() {
        CombinationGenerator generator = new CombinationGenerator();
        assertTrue(generator.rowAndColInSquare(7, 7, 9));
        assertTrue(generator.rowAndColInSquare(7, 9, 9));
        assertTrue(generator.rowAndColInSquare(9, 7, 9));
        assertTrue(generator.rowAndColInSquare(9, 9, 9));
        
        //negative test
        assertFalse(generator.rowAndColInSquare(6, 9, 9));
    }

    /**
     * Column 1 constraint is n:c1:r1.
     * Expected result: there's 9 valid candidates to satisfy this constraint
     */
    @Test
    public void checkLinkedConstraintsForColumn1() {
        CombinationGenerator generator = new CombinationGenerator();
        
        ConstraintCell rootCell = generator.generateConstraintGrid();
        ConstraintCell columnCell = rootCell.getRight();
        int linkedInColumnCount = 0;
        while((columnCell = columnCell.getDown()) != null ) {
            linkedInColumnCount++;
            
            //TODO should assert here for the candidate names that match this constraint
            
        }
        System.out.println("column count: " + linkedInColumnCount);
        //9 matching constrains for n:c1:r1
        assertEquals(9, linkedInColumnCount);
    }
    
    //TODO need to test removing/covering rows and counting remaining candidates
}
