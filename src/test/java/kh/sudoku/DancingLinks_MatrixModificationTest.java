package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class DancingLinks_MatrixModificationTest {
    
    private static final int INITIAL_CANDIDATE_SOLUTION_ROWS = 9*9*9;
    
    //9 rows * 9 columns * 4 constraints
    private static final int INITIAL_UNSATISIFIED_CONSTRAINTS = 9 * 9 * 4;
    private CombinationGenerator generator = new CombinationGenerator();
    DancingLinks dancingLinks = new DancingLinks();
    
    @Test
    public void testRemoveGivenSolution_1r1c1(){
        ConstraintCell rootNode = generator.generateConstraintGrid(new ArrayList<String>());
        
        //get first row node
        ConstraintCell firstRowNode = rootNode.getCandidateRowFirstNodes().get("1:r1:c1");
        assertNotNull(firstRowNode);
        
        //cover columns for each satisfied constraint for this row

        
        int remainingConstraints = this.dancingLinks.countRemainingUnsatisfiedConstraints(rootNode);
        assertEquals(INITIAL_UNSATISIFIED_CONSTRAINTS - 4, remainingConstraints);
    }
}
