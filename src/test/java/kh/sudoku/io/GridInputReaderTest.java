package kh.sudoku.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GridInputReaderTest {

    private GridInputReader reader = new GridInputReader();
    
    
    @Test
    public void testOneRow() {
        List<String> shorthandSolutions = new ArrayList<>();
        shorthandSolutions.add(".1.2.3.4.");
        
        List<String> solutions = reader.readGivenSolutions(shorthandSolutions);
        
        assertEquals(4, solutions.size());
        assertTrue(solutions.contains("1:r1:c2"));
        assertTrue(solutions.contains("2:r1:c4"));
        assertTrue(solutions.contains("3:r1:c6"));
        assertTrue(solutions.contains("4:r1:c8"));
    }

    @Test
    public void testNineRows_c1() {
        List<String> shorthandSolutions = new ArrayList<>();
        shorthandSolutions.add("1........");
        shorthandSolutions.add("2........");
        shorthandSolutions.add("3........");
        shorthandSolutions.add("4........");
        shorthandSolutions.add("5........");
        shorthandSolutions.add("6........");
        shorthandSolutions.add("7........");
        shorthandSolutions.add("8........");
        shorthandSolutions.add("9........");
        
        List<String> solutions = reader.readGivenSolutions(shorthandSolutions);
        
        assertEquals(9, solutions.size());
        assertTrue(solutions.contains("1:r1:c1"));
        assertTrue(solutions.contains("2:r2:c1"));
        assertTrue(solutions.contains("3:r3:c1"));
        assertTrue(solutions.contains("4:r4:c1"));
        assertTrue(solutions.contains("5:r5:c1"));
        assertTrue(solutions.contains("6:r6:c1"));
        assertTrue(solutions.contains("7:r7:c1"));
        assertTrue(solutions.contains("8:r8:c1"));
        assertTrue(solutions.contains("9:r9:c1"));
    }

    @Test
    public void testNineRows_c9() {
        List<String> shorthandSolutions = new ArrayList<>();
        shorthandSolutions.add("........1");
        shorthandSolutions.add("........2");
        shorthandSolutions.add("........3");
        shorthandSolutions.add("........4");
        shorthandSolutions.add("........5");
        shorthandSolutions.add("........6");
        shorthandSolutions.add("........7");
        shorthandSolutions.add("........8");
        shorthandSolutions.add("........9");
        
        List<String> solutions = reader.readGivenSolutions(shorthandSolutions);
        
        assertEquals(9, solutions.size());
        assertTrue(solutions.contains("1:r1:c9"));
        assertTrue(solutions.contains("2:r2:c9"));
        assertTrue(solutions.contains("3:r3:c9"));
        assertTrue(solutions.contains("4:r4:c9"));
        assertTrue(solutions.contains("5:r5:c9"));
        assertTrue(solutions.contains("6:r6:c9"));
        assertTrue(solutions.contains("7:r7:c9"));
        assertTrue(solutions.contains("8:r8:c9"));
        assertTrue(solutions.contains("9:r9:c9"));
    }
}
