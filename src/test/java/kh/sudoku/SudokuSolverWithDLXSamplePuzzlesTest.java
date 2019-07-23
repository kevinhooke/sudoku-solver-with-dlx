package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
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
    
    private static final List<String> easySolutionShorthand = new ArrayList<>();
    
    @BeforeClass
    public static void init(){
        easySolutionShorthand.add("349812675");
        easySolutionShorthand.add("517496238");
        easySolutionShorthand.add("268357194");
        easySolutionShorthand.add("185723946");
        easySolutionShorthand.add("493681527");
        easySolutionShorthand.add("726945813");
        easySolutionShorthand.add("972538461");
        easySolutionShorthand.add("651274389");
        easySolutionShorthand.add("834169752");
    }
    
    @Test
    public void testEasy() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
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
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }

    @Test
    public void testEasy_returnShorthand() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
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
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertTrue(results.isValidPuzzle());
        assertEquals(1, results.getResults().size());
        assertEquals(easySolutionShorthand, results.getResults().get(0));
    }
    
    @Test
    public void testMedium() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
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
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertTrue(results.isValidPuzzle());
        assertEquals(1, results.getResults().size());
        
        //TODO: need assert against expected result
    }

    @Test
    public void testHard() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
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
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertTrue(results.isValidPuzzle());
        assertEquals(1, results.getResults().size());
        
        //TODO: need assert against expected result
    }

    @Test
    public void testHard_modified1GivenRemoved() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".....1.78");
        givenSolutionsShorthand.add(".3......9");
        givenSolutionsShorthand.add(".5.6..4..");
        givenSolutionsShorthand.add("...2.7...");
        givenSolutionsShorthand.add(".83...79.");
        givenSolutionsShorthand.add("...5.8...");
        givenSolutionsShorthand.add("..5..6.2.");
        givenSolutionsShorthand.add("2......4.");
        givenSolutionsShorthand.add("81.7....3.");
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertFalse(results.isValidPuzzle());
        assertEquals(4, results.getResults().size());

    }
    
    @Test
    public void testEvil() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
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
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertTrue(results.isValidPuzzle());
        assertEquals(1, results.getResults().size());
        
        //TODO: need assert against expected result
    }
    
    /**
     * Puzzle from: https://www.free-sudoku.com/sudoku.php?dchoix=evil
     */
    @Test
    public void test17Givens_1() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".4.3.....");
        givenSolutionsShorthand.add("......79.");
        givenSolutionsShorthand.add("...6.....");
        givenSolutionsShorthand.add("...14.5..");
        givenSolutionsShorthand.add("9......1.");
        givenSolutionsShorthand.add("2.......6");
        givenSolutionsShorthand.add("....92...");
        givenSolutionsShorthand.add(".5....8..");
        givenSolutionsShorthand.add("....7....");
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertTrue(results.isValidPuzzle());
        assertEquals(1, results.getResults().size());
    }
    
    @Test
    public void testOneGiven() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
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
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertFalse(results.isValidPuzzle());
        assertEquals(10, results.getResults().size());
    }

}
