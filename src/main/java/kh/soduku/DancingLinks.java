package kh.soduku;

public class DancingLinks {

    
    /**
     * Removes each of the given solutions from the matrix.
     */
    public void removeGivenSolutionsFromSolutionMatrix() {
        //TODO
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
