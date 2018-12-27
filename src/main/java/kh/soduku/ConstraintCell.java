package kh.soduku;

public class ConstraintCell {


    private String name;
    private int columnCount;
    private int constraintSatisfied; // 0 = no, 1 = yes
    private ConstraintCell up;
    private ConstraintCell down;
    private ConstraintCell left;
    private ConstraintCell right;
    private NodeType type;
    
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

    public ConstraintCell getUp() {
        return up;
    }

    public void setUp(ConstraintCell up) {
        this.up = up;
    }

    public ConstraintCell getDown() {
        return down;
    }

    public void setDown(ConstraintCell down) {
        this.down = down;
    }

    public ConstraintCell getLeft() {
        return left;
    }

    public void setLeft(ConstraintCell left) {
        this.left = left;
    }

    public ConstraintCell getRight() {
        return right;
    }

    public void setRight(ConstraintCell right) {
        this.right = right;
    }

    public int getConstraintSatisfied() {
        return constraintSatisfied;
    }

    public void setConstraintSatisfied(int constraintSatisfied) {
        this.constraintSatisfied = constraintSatisfied;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
    
    
    
}
