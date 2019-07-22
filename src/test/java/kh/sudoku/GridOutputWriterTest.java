package kh.sudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kh.sudoku.io.GridOutputWriter;

public class GridOutputWriterTest {

    private GridOutputWriter writer = new GridOutputWriter();
    
    private static final String expectedResultCol1 = 
            "+-------+-------+-------+\n" + 
            "| 1 . . | . . . | . . . | \n" + 
            "| 2 . . | . . . | . . . | \n" + 
            "| 3 . . | . . . | . . . | \n" + 
            "+-------+-------+-------+\n" + 
            "| . . . | . . . | . . . | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "+-------+-------+-------+\n" + 
            "| 7 . . | . . . | . . . | \n" + 
            "| 8 . . | . . . | . . . | \n" + 
            "| 9 . . | . . . | . . . | \n" + 
            "+-------+-------+-------+\n";
    
    private static final String expectedResultRow1 = 
            "+-------+-------+-------+\n" + 
            "| 1 2 3 | . . . | 7 8 9 | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "+-------+-------+-------+\n" + 
            "| . . . | . . . | . . . | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "+-------+-------+-------+\n" + 
            "| . . . | . . . | . . . | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "| . . . | . . . | . . . | \n" + 
            "+-------+-------+-------+\n";
    
    private static final List<String> expectedResultShorthandRow1 = new ArrayList<>();
    {
        expectedResultShorthandRow1.add("123...789");
        expectedResultShorthandRow1.add(".........");
        expectedResultShorthandRow1.add(".........");
        expectedResultShorthandRow1.add(".........");
        expectedResultShorthandRow1.add(".........");
        expectedResultShorthandRow1.add(".........");
        expectedResultShorthandRow1.add(".........");
        expectedResultShorthandRow1.add(".........");
        expectedResultShorthandRow1.add(".........");
    }
    
    @Test
    public void writeGridTest_row1() {
        
        List<String> cellValues = new ArrayList<>();
        cellValues.add("1:r1:c1");
        cellValues.add("2:r1:c2");
        cellValues.add("3:r1:c3");
        cellValues.add("7:r1:c7");
        cellValues.add("8:r1:c8");
        cellValues.add("9:r1:c9");
        String result = writer.writeGrid(cellValues, 9, 9);
        assertEquals(expectedResultRow1, result);
    }

    @Test
    public void writeGridTest_col1() {
        
        List<String> cellValues = new ArrayList<>();
        cellValues.add("1:r1:c1");
        cellValues.add("2:r2:c1");
        cellValues.add("3:r3:c1");
        cellValues.add("7:r7:c1");
        cellValues.add("8:r8:c1");
        cellValues.add("9:r9:c1");
        String result = writer.writeGrid(cellValues, 9, 9);    
        assertEquals(expectedResultCol1, result);
    }

    @Test
    public void writeShorthandTest_row1() {
        
        List<String> cellValues = new ArrayList<>();
        cellValues.add("1:r1:c1");
        cellValues.add("2:r1:c2");
        cellValues.add("3:r1:c3");
        cellValues.add("7:r1:c7");
        cellValues.add("8:r1:c8");
        cellValues.add("9:r1:c9");
        List<String> result = writer.writeShorthand(cellValues, 9, 9);
        for(String row : result) {
            System.out.println(row);
        }
        assertEquals(expectedResultShorthandRow1, result);
    }

}
