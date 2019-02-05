package kh.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Sample puzzles generated from https://www.websudoku.com
 * 
 * @author kevinhooke
 *
 */
public class SudokuSolverWithDLXSamplePuzzlesTest {

    private SudokuSolverWithDLX solver = new SudokuSolverWithDLX();

    
    @Test
    public void testEasy() {
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("...81.67."); 
        givenSolutionsShorthand.add("..749.2.8");
        givenSolutionsShorthand.add(".6..5.1.4");
        givenSolutionsShorthand.add("1....39..");
        givenSolutionsShorthand.add("4...8...7");
        givenSolutionsShorthand.add("..69....3");
        givenSolutionsShorthand.add("9.2.3..6.");
        givenSolutionsShorthand.add("6.1.743..");
        givenSolutionsShorthand.add(".34.69...");
        this.solver.run(givenSolutionsShorthand);
    }

    @Test
    public void testMedium() {
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("5689.....");
        givenSolutionsShorthand.add("......4..");
        givenSolutionsShorthand.add(".43.758..");
        givenSolutionsShorthand.add(".89.42.7.");
        givenSolutionsShorthand.add("....9....");
        givenSolutionsShorthand.add(".7.86.29.");
        givenSolutionsShorthand.add("..472.93.");
        givenSolutionsShorthand.add("..6......");
        givenSolutionsShorthand.add(".....8145");
        this.solver.run(givenSolutionsShorthand);
    }

    @Test
    public void testHard() {
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".9...1.78");
        givenSolutionsShorthand.add(".3......9");
        givenSolutionsShorthand.add(".5.6..4..");
        givenSolutionsShorthand.add("...2.7...");
        givenSolutionsShorthand.add(".83...79.");
        givenSolutionsShorthand.add("...5.8...");
        givenSolutionsShorthand.add("..5..6.2.");
        givenSolutionsShorthand.add("2......4.");
        givenSolutionsShorthand.add("81.7....3.");
        this.solver.run(givenSolutionsShorthand);
    }

    @Test
    public void testEvil() {
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("..5.9....");
        givenSolutionsShorthand.add(".1...7..3");
        givenSolutionsShorthand.add("2.....89.");
        givenSolutionsShorthand.add("...2..1.8");
        givenSolutionsShorthand.add("...419...");
        givenSolutionsShorthand.add("1.6..8...");
        givenSolutionsShorthand.add(".39.....7");
        givenSolutionsShorthand.add("8..5...4.");
        givenSolutionsShorthand.add("....2.3..");
        this.solver.run(givenSolutionsShorthand);
    }

}
