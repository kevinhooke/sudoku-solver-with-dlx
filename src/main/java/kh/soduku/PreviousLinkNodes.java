package kh.soduku;

import java.util.HashMap;
import java.util.Map;

public class PreviousLinkNodes {

    private ConstraintCell previousValidConstraintNode;
    private ConstraintCell lastConstraintNode;
    private Map<Integer, ConstraintCell> previousConstraintNodesInColumns = new HashMap<>();
    
    public ConstraintCell getPreviousValidConstraintNode() {
        return previousValidConstraintNode;
    }
    
    public void setPreviousValidConstraintNode(ConstraintCell previousValidConstraintNode) {
        this.previousValidConstraintNode = previousValidConstraintNode;
    }
    
    public ConstraintCell getLastConstraintNode() {
        return lastConstraintNode;
    }
    
    public void setLastConstraintNode(ConstraintCell lastConstraintNode) {
        this.lastConstraintNode = lastConstraintNode;
    }

    public ConstraintCell getPreviousConstraintNodeInColumn(int columnIndex) {
        return this.previousConstraintNodesInColumns.get(columnIndex);
    }
    
    public void setPreviousConstraintNodeInColumn(int columnIndex, ConstraintCell constraint) {
        this.previousConstraintNodesInColumns.put(columnIndex, constraint);
    }
    
    public Map<Integer, ConstraintCell> getPreviousConstraintNodesInColumns() {
        return previousConstraintNodesInColumns;
    }

    public void setPreviousConstraintNodesInColumns(Map<Integer, ConstraintCell> previousConstraintNodesInColumns) {
        this.previousConstraintNodesInColumns = previousConstraintNodesInColumns;
    }
    
}
