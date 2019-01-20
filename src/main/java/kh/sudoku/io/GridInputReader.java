package kh.sudoku.io;

import java.util.ArrayList;
import java.util.List;

public class GridInputReader {

    
    /**
     * Reads a List of Strings that represent the starting matrix with given solutions.
     * Each String represents 1 row, and for a 9x9 grid there should be 9 rows, each with 9
     * entries.
     * 
     * Given solutions are numbers in their position, and cells with blanks are a '.'
     * 
     * e.g "..1.3...9" represents a row with 1 in c3, 3 in c5, 9 in c9.
     * 
     * @param givenSolutionShorthand
     * @return
     */
    public List<String> readGivenSolutions(List<String> givenSolutionShorthand){
        List<String> givenSolutions = new ArrayList<>();
        int row = 0;
        int col = 0;
        for(String currentRow : givenSolutionShorthand) {
            row++;
            char[] cellValues = currentRow.toCharArray();
            
            col = 0;
            for(char value : cellValues) {
                col++;
                if(value == '.') {
                    //skip to next value
                    continue;
                }
                else {
                    String givenSolution = value + ":r" + row + ":c" + col;
                    givenSolutions.add(givenSolution);
                }
            }
        }
        
        return givenSolutions;
    }
    
}
