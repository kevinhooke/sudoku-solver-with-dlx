package kh.sudoku;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Sample puzzles generated from https://www.websudoku.com
 * 
 * @author kevinhooke
 *
 */
public class SudokuSolverWithDLXSamplePuzzlesTest {

    private String easySolution = 
            "+-------+-------+-------+\n" + 
            "| 3 4 9 | 8 1 2 | 6 7 5 | \n" + 
            "| 5 1 7 | 4 9 6 | 2 3 8 | \n" + 
            "| 2 6 8 | 3 5 7 | 1 9 4 | \n" + 
            "+-------+-------+-------+\n" + 
            "| 1 8 5 | 7 2 3 | 9 4 6 | \n" + 
            "| 4 9 3 | 6 8 1 | 5 2 7 | \n" + 
            "| 7 2 6 | 9 4 5 | 8 1 3 | \n" + 
            "+-------+-------+-------+\n" + 
            "| 9 7 2 | 5 3 8 | 4 6 1 | \n" + 
            "| 6 5 1 | 2 7 4 | 3 8 9 | \n" + 
            "| 8 3 4 | 1 6 9 | 7 5 2 | \n" + 
            "+-------+-------+-------+\n";
    
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
        String result = this.solver.run(givenSolutionsShorthand);
        assertEquals(easySolution, result);
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
        givenSolutionsShorthand.add("...4.9...");
        givenSolutionsShorthand.add("1.6..8...");
        givenSolutionsShorthand.add(".39.....7");
        givenSolutionsShorthand.add("8..5...4.");
        givenSolutionsShorthand.add("....2.3..");
        this.solver.run(givenSolutionsShorthand);
    }
    
    @Test
    public void testOneGiven() {
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("..5......");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        this.solver.run(givenSolutionsShorthand);
    }

}
