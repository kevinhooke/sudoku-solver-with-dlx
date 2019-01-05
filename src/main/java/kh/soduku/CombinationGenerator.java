package kh.soduku;

import java.util.ArrayList;
import java.util.List;

public class CombinationGenerator {

    private static final int MAX_NUM = 9;
    private static final int MAX_ROWS = 9;
    private static final int MAX_COLS = 9;
    private static final int NUM_SQUARES = 9;
    
    private ConstraintCell rootNode = null;
    
    public static void main(String[] args) {
        CombinationGenerator generator = new CombinationGenerator();
        List<String> solutions = new ArrayList<>();
        generator.generateConstraintGrid(solutions);
        //generator.generateNumberInACellConbinations();
        //generator.generateNumberInARowCombinations();
        //generator.generateNumberInAColumnCombinations();
        //generator.generateNumberInASquareCombinations();
    }

    /**
     * Generates the matrix of candidate cell combinations against set of all possible constraints.
     * 
     * @param givenSolutions TODO: test this approach
     * 
     * @return the root cell of the linked list that represents the matrix
     */
    public ConstraintCell generateConstraintGrid(List<String> givenSolutions) {
        int numberOfCombinations = 0;
        this.rootNode = new ConstraintCell("root");
        this.rootNode.setType(NodeType.RootNode);
        ConstraintCell previousNode = rootNode;
        ConstraintCell previousCandidateNode = previousNode;
        PreviousLinkNodes previousLinkNodes = new PreviousLinkNodes();
        previousLinkNodes.setLastConstraintNode(previousNode);
            
        //generate first row of column header nodes
        previousLinkNodes = this.generateNumberInACellConbinationsHeaders(previousLinkNodes);
        previousLinkNodes = this.generateNumberInARowCombinationsHeaders(previousLinkNodes);
        previousLinkNodes = this.generateNumberInAColumnCombinationsHeaders(previousLinkNodes);
        previousLinkNodes = this.generateNumberInASquareCombinationsHeaders(previousLinkNodes);
        previousNode = previousLinkNodes.getLastConstraintNode();
        
        //link last column node back to root to establish the circularly linked list
        previousNode.setRight(rootNode);
        rootNode.setLeft(previousNode);
        
        System.out.println();
        
        //reset back to working from the root
        previousNode = rootNode;
        previousCandidateNode = previousNode;
        
        
        //TODO during solve, 1:c1:c1 is missing in matrix
        
        
        //generate sparse matrix of candidate rows with linked matching constraint nodes in columns
        //where constraints are met
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                for (int num = 1; num <= MAX_NUM; num++) {
                    //the first cell in a row is a number:row:cell candidate solution node
                    String nodeName = num + ":r" + row + ":c" + col;
                    System.out.print(nodeName + " ");
                    ConstraintCell candidateNode = new ConstraintCell(nodeName);
                    candidateNode.setType(NodeType.Candidate);
                    
                    // set link between new candidate row node and previous node (nodes on left of the matrix)
                    previousCandidateNode.setDown(candidateNode);
                    candidateNode.setUp(previousCandidateNode);
                    previousCandidateNode = candidateNode;
                    previousLinkNodes.setLastConstraintNode(candidateNode);
                    
                    previousLinkNodes = this.generateNumberInACellConbinations(previousLinkNodes, row, col, false);
                    previousLinkNodes = this.generateNumberInARowCombinations(previousLinkNodes, row, col, num, false);
                    previousLinkNodes = this.generateNumberInAColumnCombinations(previousLinkNodes, col, num, false);
                    previousLinkNodes = this.generateNumberInASquareCombinations(previousLinkNodes, row, col, num, false);
                    numberOfCombinations++;
                    previousNode = candidateNode;
                    System.out.println();
                }
            }
        }
        
        //make circular link from last node in each column back to column header node
        this.linkLastNodeInEachColumnBackToColumnHeaderNode();
        
        //TODO: make circular link from last node in each row back to the row header node
        this.linkLastNodeInEachRowBackToRowHeaderNode();
        
        System.out.println("Combinations prior to removing given solutions: " + numberOfCombinations);

        
        return rootNode;
    }
    
    /**
     * TODO working, add javadoc
     */
    private void linkLastNodeInEachRowBackToRowHeaderNode() {
        ConstraintCell rowHeader = this.rootNode;
        while((rowHeader = rowHeader.getDown()) != this.rootNode) {
            ConstraintCell cell = rowHeader;
            ConstraintCell lastCell = null;
            while((cell = cell.getRight()) != null) {
                if(cell != null) {
                    lastCell = cell;
                }
            }
            if(lastCell != null) {
                //reached last cell, link it back to the row header
                lastCell.setRight(rowHeader);
                rowHeader.setLeft(lastCell);
            }
        }
    }


    /**
     * Sets circular links for each last node in a column back to the column headers
     */
    private void linkLastNodeInEachColumnBackToColumnHeaderNode() {
        
        //1. iterate and set the row headers first
        // this is causing the npe, not doing this step
        ConstraintCell columnHeader = this.rootNode.getDown();
        ConstraintCell columnCell;
        columnCell = columnHeader;
        ConstraintCell lastCell = null;
        while((columnCell = columnCell.getDown()) != null) {
            if(columnCell != null) {
                lastCell = columnCell;
            }
        }
        lastCell.setDown(this.rootNode);
        this.rootNode.setUp(lastCell);
        
        //2. iterate each column, for each column iterate to last node and set link back to header
        columnHeader = this.rootNode;
        lastCell = null;
        columnCell = null;
        while((columnHeader = columnHeader.getRight()) != this.rootNode) {
            columnCell = columnHeader;
            while((columnCell = columnCell.getDown()) != null) {
                if(columnCell != null) {
                    lastCell = columnCell;
                }
            }
            if(lastCell != null) {
                //reached last cell, link it back to the row header
                lastCell.setDown(columnHeader);
                columnHeader.setUp(lastCell);
            }
        }
    }

    public PreviousLinkNodes generateNumberInACellConbinationsHeaders(PreviousLinkNodes previousLinkNodes) {
        
        ConstraintCell constraintNode = null;
        
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                    constraintNode.setType(NodeType.ConstraintColumnHeader);
                    //header nodes are linked together across the row
                    constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                    previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setLastConstraintNode(constraintNode);
                    
                    //add header node to map of last nodes in columns - used later for adding satisfied constraint links in each column
                    previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                    
                    System.out.print(constraintNode.getName() + " ");
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }
    
    public PreviousLinkNodes generateNumberInACellConbinations(PreviousLinkNodes previousLinkNodes, int currentRow,
            int currentCol, boolean headerNode) {

        ConstraintCell constraintNode = null;

        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);

                if (currentRow == row && currentCol == col) {
                    constraintNode.setConstraintSatisfied(1);
                    constraintNode.setType(NodeType.SatisfiedConstraint);
                    // assign links to/from this node and previous constraint
                    // node in current row
                    constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                    previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);

                    ConstraintCell previousNodeInColumn = previousLinkNodes.getPreviousConstraintNodesInColumns()
                            .get(constraintNode.getName());
                    // assign links to previous satisfied constraint in same column
                    constraintNode.setUp(previousNodeInColumn);
                    previousNodeInColumn.setDown(constraintNode);

                    // add this satisfied constraint as the last for this column
                    // for building column links
                    previousLinkNodes.setPreviousConstraintNodeInColumn(constraintNode.getName(), constraintNode);
                } else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }

    public PreviousLinkNodes generateNumberInARowCombinationsHeaders(PreviousLinkNodes previousLinkNodes) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":r" + row;
                constraintNode = new ConstraintCell(nodeName);
                
                constraintNode.setType(NodeType.ConstraintColumnHeader);
                //header nodes are linked together across the row
                constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                previousLinkNodes.setLastConstraintNode(constraintNode);
                
                //add header node to map of last nodes in columns - used later for adding satisfied constraint links in each column
                previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                
                System.out.print(constraintNode.getName() + " ");
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }

    
    public PreviousLinkNodes generateNumberInARowCombinations(PreviousLinkNodes previousLinkNodes, int currentRow,
            int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":r" + row;
                constraintNode = new ConstraintCell(nodeName);
                if (currentRow == row && currentNum == num) {
                    constraintNode.setConstraintSatisfied(1);
                    constraintNode.setType(NodeType.SatisfiedConstraint);

                    // assign links to/from this node and previous constraint node
                    constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                    previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);

                    // assign links to previous satisfied constraint in same column
                    constraintNode.setUp(
                            previousLinkNodes.getPreviousConstraintNodesInColumns().get(constraintNode.getName()));
                    previousLinkNodes.getPreviousConstraintNodesInColumns().get(constraintNode.getName())
                            .setDown(constraintNode);

                    // add this satisfied constraint as the last for this column
                    // for building column links
                    previousLinkNodes.setPreviousConstraintNodeInColumn(constraintNode.getName(), constraintNode);
                } else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }

    public PreviousLinkNodes generateNumberInAColumnCombinationsHeaders(PreviousLinkNodes previousLinkNodes) {
        ConstraintCell constraintNode = null;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                
                constraintNode.setType(NodeType.ConstraintColumnHeader);
                //header nodes are linked together across the row
                constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                previousLinkNodes.setLastConstraintNode(constraintNode);
                
                //add header node to map of last nodes in columns - used later for adding satisfied constraint links in each column
                previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                
                System.out.print(constraintNode.getName() + " ");
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }

    
    public PreviousLinkNodes generateNumberInAColumnCombinations(PreviousLinkNodes previousLinkNodes, 
            int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                //TODO can this if block be removed?
                if(headerNode) {
                    constraintNode.setType(NodeType.ConstraintColumnHeader);
                    //header nodes are linked together across the row
                    constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                    previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setLastConstraintNode(constraintNode);
                    
                    //add header node to map of last nodes in columns - used later for adding satisfied constraint links in each column
                    previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                    
                    System.out.print(constraintNode.getName() + " ");
                }
                else {
                    if(currentCol == col && currentNum == num) {
                        constraintNode.setConstraintSatisfied(1);
                        constraintNode.setType(NodeType.SatisfiedConstraint);
                        
                        //assign links to/from this node and previous constraint node
                        constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                        previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                        previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                        
                        //assign links to previous satisfied constraint in same column
                        constraintNode.setUp(previousLinkNodes.getPreviousConstraintNodesInColumns().get(constraintNode.getName()));
                        previousLinkNodes.getPreviousConstraintNodesInColumns().get(constraintNode.getName()).setDown(constraintNode);
                        
                        //add this satisfied constraint as the last for this column for building column links
                        previousLinkNodes.setPreviousConstraintNodeInColumn(constraintNode.getName(), constraintNode);
                    }
                    else {
                        constraintNode.setConstraintSatisfied(0);
                    }
                    System.out.print(constraintNode.getConstraintSatisfied());
                }
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }

    public PreviousLinkNodes generateNumberInASquareCombinationsHeaders(PreviousLinkNodes previousLinkNodes) {
        ConstraintCell constraintNode = null;
        
        for (int square = 1; square <= NUM_SQUARES; square++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":s" + square;
            
                constraintNode = new ConstraintCell(nodeName);
                
                constraintNode.setType(NodeType.ConstraintColumnHeader);
                //header nodes are linked together across the row
                constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                previousLinkNodes.setLastConstraintNode(constraintNode);
                
                //add header node to map of last nodes in columns - used later for adding satisfied constraint links in each column
                previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                
                System.out.print(constraintNode.getName() + " ");
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }

    
    public PreviousLinkNodes generateNumberInASquareCombinations(PreviousLinkNodes previousLinkNodes, int currentRow,
            int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;

        for (int square = 1; square <= NUM_SQUARES; square++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":s" + square;

                constraintNode = new ConstraintCell(nodeName);

                // do the current number, row and column satisfy the current
                // square constraint (e.g. 1:s1)
                if (currentNum == num && this.rowAndColInSquare(currentCol, currentRow, square)) {
                    constraintNode.setConstraintSatisfied(1);
                    constraintNode.setType(NodeType.SatisfiedConstraint);

                    // assign links to/from this node and previous constraint node
                    constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                    previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);

                    // assign links to previous satisfied constraint in same column
                    constraintNode.setUp(
                            previousLinkNodes.getPreviousConstraintNodesInColumns().get(constraintNode.getName()));
                    previousLinkNodes.getPreviousConstraintNodesInColumns().get(constraintNode.getName())
                            .setDown(constraintNode);

                    // add this satisfied constraint as the last for this column
                    // for building column links
                    previousLinkNodes.setPreviousConstraintNodeInColumn(constraintNode.getName(), constraintNode);
                } else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }
    
    //row 1-3 col 1-3 = s1
    //row 1-3 col 4-6 = s2
    //row 1-3 col 7-9 = s3
    //row 4-6 col 1-3 = s4
    //row 4-6 col 4-6 = s5
    //row 4-6 col 7-9 = s6
    //row 7-9 col 1-3 = s7
    //row 7-9 col 4-6 = s8
    //row 7-9 col 7-9 = s9
    //TODO: there's probably a more elegant way of doing this - potential for refactor later
    public boolean rowAndColInSquare(int currentCol, int currentRow, int square) {
        boolean result = false;
        
        //check inputs
        if(square < 1 || square > 9 || currentRow < 1 || currentRow > MAX_ROWS 
                || currentCol < 1 || currentCol > MAX_COLS) {
            throw new IllegalArgumentException();
        }
        
        if(square == 1 && (currentRow >= 1 && currentRow <= 3) && (currentCol >= 1 && currentCol <= 3)) {
            result = true;
        }
        else if(square == 2 && (currentRow >= 1 && currentRow <= 3) && (currentCol >= 4 && currentCol <= 6)) {
            result = true;
        }
        else if(square == 3 && (currentRow >= 1 && currentRow <= 3) && (currentCol >= 7 && currentCol <= 9)) {
            result = true;
        }
        else if(square == 4 && (currentRow >= 4 && currentRow <= 6) && (currentCol >= 1 && currentCol <= 3)) {
            result = true;
        }
        else if(square == 5 && (currentRow >= 4 && currentRow <= 6) && (currentCol >= 4 && currentCol <= 6)) {
            result = true;
        }
        else if(square == 6 && (currentRow >= 4 && currentRow <= 6) && (currentCol >= 7 && currentCol <= 9)) {
            result = true;
        }
        else if(square == 7 && (currentRow >= 7 && currentRow <= 9) && (currentCol >= 1 && currentCol <= 3)) {
            result = true;
        }
        else if(square == 8 && (currentRow >= 7 && currentRow <= 9) && (currentCol >= 4 && currentCol <= 6)) {
            result = true;
        }
        else if(square == 9 && (currentRow >= 7 && currentRow <= 9) && (currentCol >= 7 && currentCol <= 9)) {
            result = true;
        }

        return result;
    }

    // -------------------------------------------------------------------------------------------------
    // following methods used only to write a visual representation of the candidate x constraint matrix
    // -------------------------------------------------------------------------------------------------
    public void generateNumberInACellConbinations() {
        int numberOfCombinations = 0;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                System.out.print(nodeName + " ");
                numberOfCombinations++;
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
    }
    
    public void generateNumberInARowCombinations() {
        int numberOfCombinations = 0;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                System.out.println(num + ":r" + row);
                numberOfCombinations++;
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
    }
    
    public void generateNumberInAColumnCombinations() {
        int numberOfCombinations = 0;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                System.out.println(num + ":c" + col);
                numberOfCombinations++;
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
        
    }

    public void generateNumberInASquareCombinations() {
        int numberOfCombinations = 0;
        for (int square = 1; square <= NUM_SQUARES; square++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                System.out.println(num + ":s" + square);
                numberOfCombinations++;
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
        
    }


}
