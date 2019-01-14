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
        //System.out.println("first constraint: " + constraint.getName());
        //iterate through linked nodes until we end up back at the root node (it's a circularly linked list)
        while((constraint = constraint.getRight()) != rootNode) {
            lastCell = constraint;
            remainingUnsatisfiedConstraints++;
        }
        //System.out.println("last constraint: " + lastCell.getName());
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
        while((candidateSolution = candidateSolution.getUp()) != rootNode) {
            remainingCandidateSolutionRows++;
        }
        
        return remainingCandidateSolutionRows;
    }
    
    
    public int countRemainingCandidateSolutionRowsInColumn(ConstraintCell column) {
        int remainingCandidateSolutionRows = 0;
        ConstraintCell candidateSolution = column;
        //iterate through linked nodes until we end up back at the root node (it's a circularly linked list)
        while((candidateSolution = candidateSolution.getUp()) != column) {
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
        System.out.println("Covering column: " + startingCell.getName());
        //unlink column header
        startingCell.getRight().setLeft(startingCell.getLeft());
        startingCell.getLeft().setRight(startingCell.getRight());
        
        //iterate and unlink all rows for this column    
        for(ConstraintCell row = startingCell.getDown(); row != startingCell; row = row.getDown()) {
            //iterate and unlink the cells for this row
            ConstraintCell rowCell = null;
            for(rowCell = row.getRight(); rowCell != row; rowCell = rowCell.getRight()) {
            //while((rowCell = rowCell.getLeft()) != columnCell) {
                System.out.println("... unlinking: " + rowCell.toString() + " " + rowCell.getName());                
                rowCell.getUp().setDown(rowCell.getDown());
                rowCell.getDown().setUp(rowCell.getUp());
            }
            //test
            System.out.println("... unlinking: " + rowCell.toString() + " " + rowCell.getName());                
            rowCell.getUp().setDown(rowCell.getDown());
            rowCell.getDown().setUp(rowCell.getUp());
        }
    }
    
    //called in solve
    public void uncoverColumn(ConstraintCell startingCell) {
        
        //iterate and link all rows for this column
        for(ConstraintCell row = startingCell.getUp(); row != startingCell; row = row.getUp()) {
        //while((columnCell = columnCell.getUp()) != startingCell) {

            //iterate and link the cells for this row
            ConstraintCell rowCell = null;
            for(rowCell = row.getLeft(); rowCell != row; rowCell = rowCell.getLeft()) {
            //while((rowCell = rowCell.getLeft()) != columnCell) {
                //relink down to back up
                System.out.println("... linking: " + rowCell.toString() + " " + rowCell.getName());  
                rowCell.getDown().setUp(rowCell);
                rowCell.getUp().setDown(rowCell);
            }
            System.out.println("... linking: " + rowCell.toString() + " " + rowCell.getName());  
            rowCell.getDown().setUp(rowCell);
            rowCell.getUp().setDown(rowCell);
        }
        
        //link column header
        //System.out.println("linking: " + startingCell.getName());
        startingCell.getRight().setLeft(startingCell);
        startingCell.getLeft().setRight(startingCell);
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
