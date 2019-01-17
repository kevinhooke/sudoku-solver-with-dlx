package kh.sudoku;

import java.util.List;

public class GridOutputWriter {

    class GridValue{
        int row;
        int col;
        int value;
        
        public GridValue() {
        }
        
        public GridValue(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
        public int getRow() {
            return row;
        }
        public void setRow(int row) {
            this.row = row;
        }
        public int getCol() {
            return col;
        }
        public void setCol(int col) {
            this.col = col;
        }
        public int getValue() {
            return value;
        }
        public void setValue(int value) {
            this.value = value;
        }
    }
    
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
    
}
