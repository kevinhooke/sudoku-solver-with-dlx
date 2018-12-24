package kh.soduku;

public class ConstraintCell {


    private String name;
    private int columnCount;
    
    public ConstraintCell(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int incrementColumnCount() {
        this.columnCount++;
        return this.columnCount;
    }
    
    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
    
    
    
}
