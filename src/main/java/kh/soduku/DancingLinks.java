package kh.soduku;

public class DancingLinks {

    
    /**
     * Removes each of the given solutions from the matrix.
     */
    public void removeGivenSolutionsFromSolutionMatrix() {
        //TODO
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
     * Removes a column from the matrix, when that constraint has been satisfied.
     * 
     * @return
     */
    public void removeColumn(ConstraintCell column) {
        this.coverCellInMatrix(column);
    }
    
}
