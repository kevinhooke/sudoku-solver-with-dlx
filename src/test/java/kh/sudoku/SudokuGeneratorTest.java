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
 * 
 * TODO: instead of picking candidates with lowest counts first, change to randomized
 * 
 * @author kevinhooke
 *
 */
public class SudokuGeneratorTest {
    
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
    
    /**
     * Starts with an empty grid
     */
    @Test
    public void testGenerate1() {
        SudokuSolverWithDLX solver = new SudokuSolverWithDLX();
        List<String> givenSolutionsShorthand = new ArrayList<>();
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        givenSolutionsShorthand.add(".........");
        PuzzleResults results = solver.run(givenSolutionsShorthand, 1);
        //assertFalse(results.isValidPuzzle());
        
        //
        // TODO: this logic here if iterated and checked at each step should be the key
        // for the generator logic
        //
        
        // int givens to remove (up to 64, leaving min of 17)
        
        //int row = nextRandomPos();
        //int col = nextRandomPos();
        // is there a number still in this post?
        // if(true) remove 
        // else generate next random
        
        //check the puzzle is value
        //if still valid, continue
        
        //replace 1 candidate, pass back to solver
        List<String> result1 = results.getResults().get(0);
        String row1 = result1.get(0);
        row1 = row1.substring(1);
        row1 = "." + row1;
        result1.set(0, row1);
        results.getResults().set(0, result1);
        
        solver = new SudokuSolverWithDLX();
        PuzzleResults results2 = solver.run(result1, 1);
        assertTrue(results2.isValidPuzzle());
        
    }

}
