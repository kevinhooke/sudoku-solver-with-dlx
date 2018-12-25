package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
            System.out.print("row " + cellCombination.getName() + ": ");
            satisfiedConstraint = cellCombination;
            //for each row
            for(int expectedConstraint = 0; expectedConstraint < 5; expectedConstraint++) {
                satisfiedConstraint = satisfiedConstraint.getRight();
            
                //print out the name of the next node part from the last iteration
                if(expectedConstraint < 4) {
                    System.out.print(satisfiedConstraint.getName() + " ");
                }
                numberOfSatisfiedContrainstsPerRow++;
            }
            System.out.println();
            assertEquals(5, numberOfSatisfiedContrainstsPerRow);
            //last node from calling .getRight() expected to be null
            assertNull(satisfiedConstraint);
        }
        
    }
    
}
