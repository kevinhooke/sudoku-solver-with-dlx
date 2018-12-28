package kh.soduku;

import java.util.List;

public class DancingLinks {

    /**
     * Counts the remaining, or unsatisfied constraints remaining in the matrix. If unsatisfied
     * constraints remain, the puzzle is not yet solved.
     * 
     * @param rootNode
     * @return
     */
    public int countRemainingUnsatisfiedConstraints(ConstraintCell rootNode) {
        int remainingUnsatisfiedConstraints = 0;
        ConstraintCell constraint = rootNode;
        while((constraint = constraint.getRight()) != null) {
            remainingUnsatisfiedConstraints++;
        }
        
        return remainingUnsatisfiedConstraints;
    }
    
    /**
     * Get a combination row using name in format e.g. 3:c2:r1 = 3 in col 2 row 1
     * @param combination
     * @return
     */
    public ConstraintCell getCombinationRowUsingName(String combination) {
        ConstraintCell result = null; 
        
        //TODO
        
        return result;
    }
    
    /**
     * Remove a cell from the candidate matrix by linking it's left and right neighbor
     * cells, which removes itself from the linked list of cells for that row.
     * @param column
     */
    public void coverCellInMatrix(ConstraintCell cell) {
        
        cell.getLeft().setRight(cell.getRight());
        cell.getRight().setLeft(cell.getLeft());
        
    }
    
    public void uncoverCellInMatrix(ConstraintCell cell) {
        
        //TODO
    }
    
    /**
     * Removes a candidate row from the matrix without adding it to the backtrack list, because this is
     * a given solution and we don't need to backtrack for these.
     * @param row
     */
    public void removeCandidateRow(ConstraintCell row) {
        row.getUp().setDown(row.getDown());
        row.getDown().setUp(row.getUp());
    }
    
    /**
     * Removes a column from the matrix, when that constraint has been satisfied.
     * 
     * @return
     */
    public void removeColumn(ConstraintCell column) {
        this.coverCellInMatrix(column);
    }
    
}
