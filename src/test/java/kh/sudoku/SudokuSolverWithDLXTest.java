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
    String[] cells = {"8:r1:c4", "1:r1:c5", "6:r1:c7", "7:r1:c8"};
    
    @Test
    public void removeGivenSolutions() {
        String[] testSolution1 = {"8:r1:c4"};
        List<String> givenCells = Arrays.asList(testSolution1);
        ConstraintCell rootNode = generator.generateConstraintGrid();
        
        this.solver.removeGivenSolutionsFromSolutionMatrix(rootNode, givenCells);
        
    }
}
