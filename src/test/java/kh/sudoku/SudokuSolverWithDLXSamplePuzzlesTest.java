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
    
    private String easySolution2 = 
            "+-------+-------+-------+\n" + 
            "| 2 4 6 | 9 8 1 | 5 7 3 | \n" + 
            "| 5 3 7 | 4 6 2 | 1 9 8 | \n" + 
            "| 1 9 8 | 3 5 7 | 4 6 2 | \n" +  
            "+-------+-------+-------+\n" + 
            "| 8 6 9 | 7 1 3 | 2 4 5 | \n" + 
            "| 4 5 1 | 2 9 6 | 8 3 7 | \n" + 
            "| 7 2 3 | 8 4 5 | 9 1 6 | \n" +  
            "+-------+-------+-------+\n" + 
            "| 9 8 2 | 6 3 4 | 7 5 1 | \n" + 
            "| 6 1 4 | 5 7 8 | 3 2 9 | \n" + 
            "| 3 7 5 | 1 2 9 | 6 8 4 | \n" + 
            "+-------+-------+-------+\n"; 
    
    private String easySolution3 = 
    "+-------+-------+-------+\n" + 
    "| 2 4 6 | 8 9 1 | 5 7 3 | \n" + 
    "| 5 9 7 | 3 6 4 | 1 2 8 | \n" + 
    "| 1 3 8 | 5 2 7 | 6 9 4 | \n" + 
    "+-------+-------+-------+\n" + 
    "| 8 5 1 | 7 4 3 | 2 6 9 | \n" + 
    "| 4 6 9 | 2 1 5 | 3 8 7 | \n" + 
    "| 7 2 3 | 9 8 6 | 4 1 5 | \n" + 
    "+-------+-------+-------+\n" + 
    "| 9 8 4 | 6 3 2 | 7 5 1 | \n" + 
    "| 6 1 5 | 4 7 8 | 9 3 2 | \n" + 
    "| 3 7 2 | 1 5 9 | 8 4 6 | \n" + 
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
    
    /**
     * givens: 80 (1 removed)
     * recursive depth count: 1
     * potential candidates tried count: 1
     * solutions found: 1
     */
    @Test
    public void test1Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5174962.8");
        givenSolutionsShorthand.add("268357194");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("972538461");
        givenSolutionsShorthand.add("651274389");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 79 (2 removed)
     * recursive depth count: 2
     * potential candidates tried count: 2
     * solutions found: 1
     */
    @Test
    public void test2Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5174962.8");
        givenSolutionsShorthand.add("268357194");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("972538461");
        givenSolutionsShorthand.add("6.1274389");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 78 (3 removed)
     * recursive depth count: 3
     * potential candidates tried count: 3
     * solutions found: 1
     */
    @Test
    public void test3Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5.74962.8");
        givenSolutionsShorthand.add("268357194");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("972538461");
        givenSolutionsShorthand.add("6.1274389");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 77 (4 removed)
     * recursive depth count: 4
     * potential candidates tried count: 4
     * solutions found: 1
     */
    @Test
    public void test4Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5.74962.8");
        givenSolutionsShorthand.add("268357194");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("972538461");
        givenSolutionsShorthand.add("6.12743.9");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 76 (5 removed)
     * recursive depth count: 5
     * potential candidates tried count: 5
     * solutions found: 1
     */
    @Test
    public void test5Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5.74962.8");
        givenSolutionsShorthand.add("2.8357194");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("972538461");
        givenSolutionsShorthand.add("6.12743.9");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }

    /**
     * givens: 75 (6 removed)
     * recursive depth count: 6
     * potential candidates tried count: 6
     * solutions found: 1
     */
    @Test
    public void test6Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5.74962.8");
        givenSolutionsShorthand.add("2.83571.4");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("972538461");
        givenSolutionsShorthand.add("6.12743.9");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 74 (7 removed)
     * recursive depth count: 7
     * potential candidates tried count: 7
     * solutions found: 1
     */
    @Test
    public void test7Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5.74962.8");
        givenSolutionsShorthand.add("2.83571.4");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("9.2538461");
        givenSolutionsShorthand.add("6.12743.9");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 73 (8 removed)
     * recursive depth count: 8
     * potential candidates tried count: 8
     * solutions found: 1
     */
    @Test
    public void test8Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("349812675");
        givenSolutionsShorthand.add("5.74962.8");
        givenSolutionsShorthand.add("2.83571.4");
        givenSolutionsShorthand.add("185723946");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add("726945813");
        givenSolutionsShorthand.add("9.25384.1");
        givenSolutionsShorthand.add("6.12743.9");
        givenSolutionsShorthand.add("834169752");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 61 (20 removed)
     * recursive depth count: 20
     * potential candidates tried count: 20
     * solutions found: 1
     */
    @Test
    public void test20Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".4.812.7.");
        givenSolutionsShorthand.add("5.74962.8");
        givenSolutionsShorthand.add("2.83571.4");
        givenSolutionsShorthand.add(".8572394.");
        givenSolutionsShorthand.add("493681527");
        givenSolutionsShorthand.add(".2694581.");
        givenSolutionsShorthand.add("9.25384.1");
        givenSolutionsShorthand.add("6.12743.9");
        givenSolutionsShorthand.add(".3.169.5.");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 41 (40 removed)
     * recursive depth count: 40
     * potential candidates tried count: 40
     * solutions found: 1
     */
    @Test
    public void test40Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".4.8.2.7.");
        givenSolutionsShorthand.add("5..4.6..8");
        givenSolutionsShorthand.add("2..357..4");
        givenSolutionsShorthand.add(".8.723.4.");
        givenSolutionsShorthand.add("49.....27");
        givenSolutionsShorthand.add(".2.945.1.");
        givenSolutionsShorthand.add("9.2.3.4.1");
        givenSolutionsShorthand.add("6.1.7.3.9");
        givenSolutionsShorthand.add(".3.1.9.5.");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution, result);
    }
    
    /**
     * givens: 21 (60 removed)
     * recursive depth count: 60
     * potential candidates tried count: 60
     * solutions found: 1
     * Elapsed ms: 4
     * 
     * This started with the same grid as the others above, but the single solution
     * is different than the others.
     */
    @Test
    public void test60Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".4.....7.");
        givenSolutionsShorthand.add("5.......8");
        givenSolutionsShorthand.add("...3.7...");
        givenSolutionsShorthand.add("...7.3...");
        givenSolutionsShorthand.add("4.......7");
        givenSolutionsShorthand.add(".2..4..1.");
        givenSolutionsShorthand.add("9...3...1");
        givenSolutionsShorthand.add("6...7...9");
        givenSolutionsShorthand.add("...1.9...");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution2, result);
    }
    
    /**
     * givens: 17 (64 removed)
     * recursive depth count: 64
     * potential candidates tried count: 64
     * solutions found: 1
     * Elapsed ms: 6
     * 
     * This started with the same grid as the others above, but the single solution
     * is different than the others.
     */
    @Test
    public void test64Removed() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".4.....7.");
        givenSolutionsShorthand.add("5.......8");
        givenSolutionsShorthand.add(".....7...");
        givenSolutionsShorthand.add("...7.3...");
        givenSolutionsShorthand.add("4.......7");
        givenSolutionsShorthand.add(".2.....1.");
        givenSolutionsShorthand.add("9...3...1");
        givenSolutionsShorthand.add("....7....");
        givenSolutionsShorthand.add("...1.9...");
        String result = solver.runWithFormattedOutput(givenSolutionsShorthand, 1);
        assertEquals(easySolution3, result);
    }
    
    /**
     * Puzzle generated from https://www.websudoku.com
     * 
     * Difficulty: easy
     * recursive depth count: 46
     * potential candidates tried count: 46
     * solutions found: 1
     * Elapsed ms: 3
     */
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
    
    /**
     * Puzzle generated from https://www.websudoku.com
     * 
     * Difficulty: medium
     * 
     * given: 31 (removed 50)
     * Depth: 50
     * Candidates tried: 50
     * Time: 3ms
     */
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

    /**
     * Puzzle generated from https://www.websudoku.com
     * 
     * Difficulty: hard
     * Depth: 55
     * Candidates tried: 55
     * Time: 3ms
     */
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

    /**
     * Test for an invalid puzzle as there's not a single solution.
     * 
     * recursive depth count: 56
     * potential candidates tried count: 145
     * solutions found: 4
     * Elapsed ms: 31
     */
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
    
    /**
     * Puzzle generated from https://www.websudoku.com
     * 
     * Difficulty: evil
     * 
     * givens: 24 (removed 57)
     * recursive depth count: 57
     * potential candidates tried count: 57
     * solutions found: 1
     * Elapsed ms: 4
     */
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
     * 
     * Difficulty: evil
     * 
     * givens: 17 (64 removed)
     * recursive depth count: 64
     * potential candidates tried count: 64
     * solutions found: 1
     * Elapsed ms: 5ms (2008 Mac Pro 2x 2.8GHz Xeon)
     * Elapsed ms: 4ms (2015 MBP i7)
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
    
    /**
     * Sudokuwiki unsolvable problem #28
     * From: http://www.sudokuwiki.org/Weekly_Sudoku.asp?puz=28
     * 
     * Difficult: unsolvable 
     * 
     * givens: 23 (removed 58)
     * Depth: 59
     * Candidates tried: 29671
     * Time: 159ms
     */
    @Test
    public void testUnsolvableSudokuWiki28() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("6....894.");
        givenSolutionsShorthand.add("9....61..");
        givenSolutionsShorthand.add(".7..4....");
        givenSolutionsShorthand.add("2..61....");
        givenSolutionsShorthand.add("......2..");
        givenSolutionsShorthand.add(".89..2...");
        givenSolutionsShorthand.add("....6...5");
        givenSolutionsShorthand.add(".......3.");
        givenSolutionsShorthand.add("8....16..");
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertTrue(results.isValidPuzzle());
        assertEquals(1, results.getResults().size());
    }
    
    /**
     * Sudokuwiki unsolvable problem #49
     * From: http://www.sudokuwiki.org/Weekly_Sudoku.asp?puz=49
     * 
     * Difficulty: unsolvable
     * givens: 22 (removed 59)
     * Depth: 59
     * Candidates tried: 23407
     * Time: 165ms
     */
    @Test
    public void testUnsolvableSudokuWiki49() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add("..28.....");
        givenSolutionsShorthand.add(".3..6...7");
        givenSolutionsShorthand.add("1......4.");
        givenSolutionsShorthand.add("6...9....");
        givenSolutionsShorthand.add(".5.6....9");
        givenSolutionsShorthand.add("....57.6.");
        givenSolutionsShorthand.add("...3..1..");
        givenSolutionsShorthand.add(".7...6..8");
        givenSolutionsShorthand.add("4......2.");
        PuzzleResults results = solver.run(givenSolutionsShorthand, 10);
        assertTrue(results.isValidPuzzle());
        assertEquals(1, results.getResults().size());
    }
    
    /**
     * Invalid puzzle with only 1 given. Tests generating a completed grid from a
     * seeded grid of minimal givens.
     * 
     * Candidates tried: 162
     */
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
