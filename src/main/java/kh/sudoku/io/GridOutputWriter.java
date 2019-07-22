package kh.sudoku.io;

import java.util.ArrayList;
import java.util.List;

public class GridOutputWriter {

    
    
    public String writeGrid(List<String> results, int rows, int cols) {
        StringBuilder builder = new StringBuilder();
        int[][] displayGrid = new int[rows + 1][cols + 1]; //ignoring 0 indexes
        
        for(String cellEntry : results) {
            GridValue value = this.parseGridValueFromResultString(cellEntry);
            displayGrid[value.getRow()][value.getCol()] = value.getValue();
        }
        
        builder.append("+-------+-------+-------+\n");
        
        for(int row = 1; row < 9+1; row++) {
            
            builder.append("| ");
            
            for(int col = 1; col < 9+1; col++) {
                if(displayGrid[row][col] == 0) {
                    builder.append(".");
                }
                else {
                    builder.append(displayGrid[row][col]);
                }
                
                if(col % 3 == 0) {
                    builder.append(" | ");
                }
                else {
                    builder.append(" ");    
                }
            }
            builder.append("\n");
            if(row % 3 == 0) {
                builder.append("+-------+-------+-------+\n");
            }
        }
        
        System.out.println(builder.toString());
        return builder.toString();
    }

    private GridValue parseGridValueFromResultString(String cellEntry) {
        GridValue value = new GridValue();
        
        String[] valueParts = cellEntry.split(":");
        value.setValue(Integer.parseInt(valueParts[0]));
        value.setRow(Integer.parseInt(valueParts[1].replace("r", "")));
        value.setCol(Integer.parseInt(valueParts[2].replace("c", "")));
        return value;
    }

    public List<String> writeShorthand(List<String> results, int rows, int cols) {
        List<String> resultsFormatted = new ArrayList<>();
        int[][] displayGrid = new int[rows + 1][cols + 1]; //ignoring 0 indexes
        
        for(String cellEntry : results) {
            GridValue value = this.parseGridValueFromResultString(cellEntry);
            displayGrid[value.getRow()][value.getCol()] = value.getValue();
        }
        
        for(int row = 1; row < 9+1; row++) {
            StringBuilder builder = new StringBuilder();         
            for(int col = 1; col < 9+1; col++) {
                if(displayGrid[row][col] == 0) {
                    builder.append(".");
                }
                else {
                    builder.append(displayGrid[row][col]);
                }                
            }
            resultsFormatted.add(builder.toString());
        }
        
        return resultsFormatted;
    }
    
}
