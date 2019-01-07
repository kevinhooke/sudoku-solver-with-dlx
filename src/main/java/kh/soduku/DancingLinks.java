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
        ConstraintCell lastCell = null;
        System.out.println("first constraint: " + constraint.getName());
        //iterate through linked nodes until we end up back at the root node (it's a circularly linked list)
        while((constraint = constraint.getRight()) != rootNode) {
            lastCell = constraint;
            remainingUnsatisfiedConstraints++;
        }
        System.out.println("last constraint: " + lastCell.getName());
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
    public ConstraintCell findCandidateSolutionRowByName(String candidateName, ConstraintCell rootCell) {
        ConstraintCell cell = rootCell;
        while(!(cell = cell.getDown()).getName().equals(candidateName)) {
            // continue
        }
        //check we found the right column
        if(!cell.getName().equals(candidateName)) {
            throw new ConstraintColumnNotFoundException();
        }
        return cell;        
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
                    //System.out.print("Candidate row: " + cellToUnlink.getName() + " ");
                }
                //System.out.println("Unlinking: " + cellToUnlink.getName());                
                cellToUnlink.getUp().setDown(cellToUnlink.getDown());
                cellToUnlink.getDown().setUp(cellToUnlink.getUp());
            }
            //unlink the starting column cell
            //System.out.println("Unlinking: " + columnCell.getName());
            //test
            //columnCell.getUp().setDown(columnCell.getDown());
        }
    }
    
    //called in solve
    public void uncoverColumn(ConstraintCell startingCell) {
        //link column header
        //System.out.println("linking: " + startingCell.getName());
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
                    //System.out.print("Candidate row: " + cellToLink.getName() + " ");
                }
                //System.out.println("linking: " + cellToLink.getName());
                //test - is this not needed?
                //cellToLink.getLeft().setRight(cellToLink);
                //cellToLink.getRight().setLeft(cellToLink);
                
                //relink down to back up
                cellToLink.getDown().setUp(cellToLink);
                //test
                cellToLink.getUp().setDown(cellToLink);
            }
            //link column
            //System.out.println("linking: " + columnToLink.getName());
            
            //relink down links
            //test
            //columnToLink.getDown().setUp(columnToLink);
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

    /**
     * Covers a candidate row and it's columns from the matrix. Used to remove a given
     * solution from the matrix.
     * 
     * @param row
     */
    public void coverCandidateRow(ConstraintCell startingRowCell) {
        System.out.println("Covering candidate row: " + startingRowCell.getName());
        ConstraintCell columnCell = null;
        ConstraintCell rowCell = startingRowCell;
        //unlink cells for this row
        while((rowCell = rowCell.getRight()) != startingRowCell) {

            System.out.println("Unlinking for given solution: " + rowCell.getName());                

            columnCell = rowCell;
            while((columnCell = columnCell.getDown()) != rowCell) {
                System.out.println("Unlinking for given solution: " + columnCell.getName());
//                if(columnCell.getType() == NodeType.ConstraintColumnHeader) {
//                    //System.out.println("at column header node");
//                    //also remove left and right links for column header
//                    columnCell.getLeft().setRight(columnCell.getRight());
//                    columnCell.getRight().setLeft(columnCell.getLeft());
//                }
                columnCell.getLeft().setRight(columnCell.getRight());
                columnCell.getRight().setLeft(columnCell.getLeft());
                columnCell.getUp().setDown(columnCell.getDown());
                columnCell.getDown().setUp(columnCell.getUp());
            }
            rowCell.getUp().setDown(rowCell.getDown());
            rowCell.getDown().setUp(rowCell.getUp());
        }
        startingRowCell.getUp().setDown(startingRowCell.getDown());
        startingRowCell.getDown().setUp(startingRowCell.getUp());
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

    
    
    public ConstraintCell getColumnHeaderForCell(ConstraintCell c) {
        if(c.getType() == NodeType.ConstraintColumnHeader) {
            System.out.println("... cell already is column header");
            return c;
        }
        ConstraintCell header = c;
        //iterate through linked nodes until we end up back at the column header node (it's a circularly linked list)
        while((header = header.getUp()).getType() != NodeType.ConstraintColumnHeader) {
            System.out.println("...looking for header");
        }
        if(header.getType() !=  NodeType.ConstraintColumnHeader) {
            throw new ContraintColumnHeaderNotFoundException();
        }
        return header;
    }
    
}
