package kh.sudoku;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.DancingLinks;
import kh.soduku.SudokuSolverWithDLX;

public class SudokuSolverWithDLXTest {

    private SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
    private DancingLinks links = new DancingLinks();

    
    //tests
    String[] cells = {"8:r1:c4", "1:r1:c5", "6:r1:c7", "7:r1:c8"};
    
    @Test
    public void testRemoveGivenSolution_1s() {
        String[] testSolution1 = {"8:r1:c4"};
        List<String> givenSolutions = Arrays.asList(testSolution1);
        
        ConstraintCell rootNode = this.solver.initiateCandidateMatrix(givenSolutions);

        //TODO: now row headers are removed we need another way of counting progress
        //int solutionRows = this.links.countRemainingCandidateSolutionRows(rootNode);
        //assertEquals( (9 * 9 * 9) - 1,solutionRows);
        
//        int remainingUnsatisfiedConstraints = this.links.countRemainingUnsatisfiedConstraints(rootNode);
//        assertEquals((9 * 9 * 4) - 4, remainingUnsatisfiedConstraints);
    }
}
