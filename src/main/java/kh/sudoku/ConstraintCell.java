package kh.sudoku;

import java.util.HashMap;
import java.util.Map;

public class ConstraintCell {

    private String name;
    private ConstraintCell up;
    private ConstraintCell down;
    private ConstraintCell left;
    private ConstraintCell right;
    private NodeType type;

    /**
     * During matrix initialization, only the cells satisfying a constraint are linked
     * into the matrix (constraintSatisfied = 1), so this property is irrelevant.
     */
    private int constraintSatisfied; // 0 = no, 1 = yes

    /**
     * Map of the first node in each row, keyed by candidate solution name. This
     * is only populated for the root node, and allows a quick way to remove the given solution rows.
     */
    private Map<String, ConstraintCell> candidateRowFirstNodes;
    
    public ConstraintCell(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConstraintCell getUp() {
        return this.up;
    }

    public void setUp(ConstraintCell up) {
        this.up = up;
    }

    public ConstraintCell getDown() {
        return this.down;
    }

    public void setDown(ConstraintCell down) {
        this.down = down;
    }

    public ConstraintCell getLeft() {
        return this.left;
    }

    public void setLeft(ConstraintCell left) {
        this.left = left;
    }

    public ConstraintCell getRight() {
        return this.right;
    }

    public void setRight(ConstraintCell right) {
        this.right = right;
    }

    public int getConstraintSatisfied() {
        return this.constraintSatisfied;
    }

    public void setConstraintSatisfied(int constraintSatisfied) {
        this.constraintSatisfied = constraintSatisfied;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
    
    public String toString() {
        return this.getName();
    }

    public Map<String, ConstraintCell> getCandidateRowFirstNodes() {
        
        if(this.candidateRowFirstNodes == null){
            this.candidateRowFirstNodes = new HashMap<>();
        }
        return this.candidateRowFirstNodes;
    }

    public void setCandidateRowFirstNodes(Map<String, ConstraintCell> candidateRowFirstNodes) {
        this.candidateRowFirstNodes = candidateRowFirstNodes;
    }
    
}
