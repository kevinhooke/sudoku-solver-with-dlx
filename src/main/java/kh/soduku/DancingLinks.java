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
        //iterate through linked nodes until we end up back at the root node (it's a circularly linked list)
        while((constraint = constraint.getRight()) != rootNode) {
            remainingUnsatisfiedConstraints++;
        }
        
        return remainingUnsatisfiedConstraints;
    }
    
    /**
     * Counts remaining candidate solution combination rows.
     * @param rootNode
     * @return
     */
    public int countRemainingCandidateSolutionRows(ConstraintCell rootNode) {
        int remainingCandidateSolutionRows = 0;
        ConstraintCell candidateSolution = rootNode;
        //iterate through linked nodes until we end up back at the root node (it's a circularly linked list)
        //test: count up not down, to follow links not removed
        while((candidateSolution = candidateSolution.getUp()) != rootNode) {
            remainingCandidateSolutionRows++;
        }
        
        return remainingCandidateSolutionRows;
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
    
    //called in solve
    public void coverColumn(ConstraintCell startingCell) {
        //unlink column header
        startingCell.getLeft().setRight(startingCell.getRight());
        startingCell.getRight().setLeft(startingCell.getLeft());
        
        //iterate and unlink all rows for this column
        ConstraintCell columnCell = startingCell;
        ConstraintCell rowCell = null;
        ConstraintCell cellToUnlink = null;
        ConstraintCell columnToUnlink = null;
        while((columnCell = columnCell.getDown()) != startingCell) {
            columnToUnlink = columnCell;
            //iterate and unlink the cells for this row
            rowCell = columnCell;
            while((rowCell = rowCell.getLeft()) != columnCell) {
                cellToUnlink = rowCell;
                if(cellToUnlink.getType() == NodeType.Candidate) {
                    System.out.print("Candidate row: " + cellToUnlink.getName() + " ");
                }
                System.out.println("Unlinking: " + cellToUnlink.getName());                
                cellToUnlink.getUp().setDown(cellToUnlink.getDown());
                cellToUnlink.getDown().setUp(cellToUnlink.getUp());
            }
            //unlink the starting column cell
            System.out.println("Unlinking: " + columnCell.getName());
            columnCell.getUp().setDown(columnCell.getDown());
        }
    }
    
    //called in solve
    public void uncoverColumn(ConstraintCell startingCell) {
        //link column header
        System.out.println("linking: " + startingCell.getName());
        startingCell.getLeft().setRight(startingCell);
        startingCell.getRight().setLeft(startingCell);
        
        //iterate and link all rows for this column
        ConstraintCell columnCell = startingCell;
        ConstraintCell rowCell = null;
        ConstraintCell cellToLink = null;
        ConstraintCell columnToLink = null;
        //navigate backwards through links
        while((columnCell = columnCell.getUp()) != startingCell) {
            columnToLink = columnCell;
            //iterate and link the cells for this row
            rowCell = columnCell;
            while((rowCell = rowCell.getLeft()) != columnCell) {
                cellToLink = rowCell;
                if(cellToLink.getType() == NodeType.Candidate) {
                    System.out.print("Candidate row: " + cellToLink.getName() + " ");
                }
                System.out.println("linking: " + cellToLink.getName());
                //test - is this not needed?
                //cellToLink.getLeft().setRight(cellToLink);
                //cellToLink.getRight().setLeft(cellToLink);
                
                //relink down to back up
                cellToLink.getDown().setUp(cellToLink);
            }
            //link column
            System.out.println("linking: " + columnToLink.getName());
            
            //relink down links
            columnToLink.getDown().setUp(columnToLink);
        }
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

    /**
     * Finds a constraint column by constraint name, e.g. "5:r3:c6"
     * @param rootCell
     * @param string
     * @return
     */
    public ConstraintCell findColumnByConstraintName(ConstraintCell rootCell, String constraintName) {
        ConstraintCell cell = rootCell;
        while((cell = cell.getRight()) != rootCell && !cell.getName().equals(constraintName)) {
            // do nothing
        }
        //check we found the right column
        if(!cell.getName().equals(constraintName)) {
            throw new ConstraintColumnNotFoundException();
        }
        return cell;
    }
    
}
