package kh.sudoku;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.DancingLinks;

public class DancingLinksTest {

    private CombinationGenerator generator = new CombinationGenerator();
    DancingLinks links = new DancingLinks();
    
    /**
     * Tests in the initial constraint matrix there are 9 * 9 * 9 * 4 = 
     */
    @Test
    public void countRemainingUnsatisfiedConstraints() {
        ConstraintCell rootNode = generator.generateConstraintGrid();
        int result = links.countRemainingUnsatisfiedConstraints(rootNode);
        
        //9 rows * 9 columns * 4 constraints
        assertEquals((9 * 9 * 4), result);
    }    

    
}
