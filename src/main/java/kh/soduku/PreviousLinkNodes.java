package kh.soduku;

import java.util.HashMap;
import java.util.Map;

public class PreviousLinkNodes {

    private ConstraintCell previousValidConstraintNode;
    private ConstraintCell lastConstraintNode;
    
    /**
     * Map of last satisfied constraint for each column. Used to build links between cells in a column.
     */
    private Map<String, ConstraintCell> previousConstraintNodesInColumns = new HashMap<>();
    
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

    public ConstraintCell getPreviousConstraintNodeInColumn(String constraintName) {
        return this.previousConstraintNodesInColumns.get(constraintName);
    }
    
    public void setPreviousConstraintNodeInColumn(String constraintName, ConstraintCell constraint) {
        this.previousConstraintNodesInColumns.put(constraintName, constraint);
    }
    
    public Map<String, ConstraintCell> getPreviousConstraintNodesInColumns() {
        return previousConstraintNodesInColumns;
    }

    public void setPreviousConstraintNodesInColumns(Map<String, ConstraintCell> previousConstraintNodesInColumns) {
        this.previousConstraintNodesInColumns = previousConstraintNodesInColumns;
    }
    
}
