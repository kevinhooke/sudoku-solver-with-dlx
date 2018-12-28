package kh.sudoku;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import kh.soduku.CombinationGenerator;
import kh.soduku.ConstraintCell;
import kh.soduku.DancingLinks;
import kh.soduku.SudokuSolverWithDLX;

public class SudokuSolverWithDLXTest {

    private CombinationGenerator generator = new CombinationGenerator();
    private SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
    private DancingLinks links = new DancingLinks();

    
    //tests
    String[] cells = {"8:c4:r1", "1:c5:r1", "6:c7:r1", "7:c8:r1"};
    
    @Test
    public void removeGivenSolutions() {
        String[] testSolution1 = {"8:c4:r1"};
        List<String> givenCells = Arrays.asList(testSolution1);
        ConstraintCell rootNode = generator.generateConstraintGrid();
        
        this.solver.removeGivenSolutionsFromSolutionMatrix(rootNode, givenCells);
        
    }
}
