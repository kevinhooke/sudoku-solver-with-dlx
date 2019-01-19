package kh.sudoku;

import java.util.ArrayList;
import java.util.List;

public class CombinationGenerator {

    //2*2 grid does not satisfy all constraints (1 square is not valid)
    
//    public static final int MAX_NUM = 9;
//    public static final int MAX_ROWS = 3;
//    public static final int MAX_COLS = 3;
//    public static final int NUM_SQUARES = 1;

    public static final int MAX_NUM = 9;
    public static final int MAX_ROWS = 9;
    public static final int MAX_COLS = 9;
    public static final int NUM_SQUARES = 9;
    
    private int solutionsAddedToMatrix = 0;
    private ConstraintCell rootNode = null;
    
    public static void main(String[] args) {
        CombinationGenerator generator = new CombinationGenerator();
        List<String> solutions = new ArrayList<>();
        generator.generateConstraintGrid(solutions);
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
        
        //link last column node back to root to establish the circularly linked list of headers
        previousNode.setRight(rootNode);
        rootNode.setLeft(previousNode);
        
        System.out.println();
        
        //reset back to working from the root
        previousNode = rootNode;
        previousCandidateNode = previousNode;
        
        
        //generate sparse matrix of candidate rows with linked matching constraint nodes in columns
        //where constraints are met
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                for (int num = 1; num <= MAX_NUM; num++) {
                    String solutionName = num + ":r" + row + ":c" + col;
                    System.out.print(solutionName + " ");
                    
                    //TODO removal of given solutions approach is not currently working
                    //need to fo a cover for a the column for each of the givens
                    
                    previousLinkNodes = this.generateNumberInACellCombinations(solutionName, previousLinkNodes, givenSolutions, row, col, false);
                    previousLinkNodes = this.generateNumberInARowCombinations(solutionName, previousLinkNodes, givenSolutions, row, col, num, false);
                    previousLinkNodes = this.generateNumberInAColumnCombinations(solutionName, previousLinkNodes, givenSolutions, col, num, false);
                    previousLinkNodes = this.generateNumberInASquareCombinations(solutionName, previousLinkNodes, givenSolutions, row, col, num, false);
                    numberOfCombinations++;
                    System.out.println();                    
                    this.solutionsAddedToMatrix++;
                    //assign circular row links
                    previousLinkNodes.getFirstNodeInRow().setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                    previousLinkNodes.getPreviousValidConstraintNode().setRight(previousLinkNodes.getFirstNodeInRow());
                }
                
            }

        }
        
        //make circular link from last node in each column back to column header node
        this.linkLastNodeInEachColumnBackToColumnHeaderNode();

        System.out.println("Combinations: " + numberOfCombinations);
        
        return rootNode;
    }

    /**
     * Sets circular links for each last node in a column back to the column headers
     */
    private void linkLastNodeInEachColumnBackToColumnHeaderNode() {
        
        ConstraintCell columnHeader = this.rootNode.getDown();
        ConstraintCell lastColumnHeader = null;
        ConstraintCell columnCell;
        ConstraintCell lastCell = null;
        columnHeader = this.rootNode;
        lastCell = null;
        columnCell = null;
        while((columnHeader = columnHeader.getRight()) != this.rootNode) {
            System.out.println("linkLastNodeInEachColumnBackToColumnHeaderNode : " + columnHeader.getName());
            columnCell = columnHeader;
            while(columnCell != null && (columnCell = columnCell.getDown()) != null && columnCell != columnHeader) {
                if(columnCell != null) {
                    lastCell = columnCell;
                }
            }
            if(lastCell != null) {
                //reached last cell, link it back to the column header
                lastCell.setDown(columnHeader);
                columnHeader.setUp(lastCell);
            }
            lastColumnHeader = columnHeader;
        }

        lastColumnHeader.setRight(this.rootNode);
        this.rootNode.setLeft(lastColumnHeader);
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
    
    public PreviousLinkNodes generateNumberInACellCombinations(String solutionName, PreviousLinkNodes previousLinkNodes, List<String> givenSolutions,
            int currentRow, int currentCol, boolean headerNode) {

        ConstraintCell constraintNode = null;
        boolean firstConstraintForThisRow = true;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                constraintNode = new ConstraintCell(solutionName);

                //if current row and column match then this is a satisfied constraint, and if this solution name is not in
                //in the list of given solutions, add it to the sparse matrix, otherwise skip it
                //&& !givenSolutions.contains(solutionName)
                if (currentRow == row && currentCol == col ) {
                    constraintNode.setConstraintSatisfied(1);
                    constraintNode.setType(NodeType.SatisfiedConstraint);
                    
                    if(firstConstraintForThisRow) {
                        previousLinkNodes.setFirstNodeInRow(constraintNode);
                        firstConstraintForThisRow = false;
                        
                        //keep track of first node in each row to help with given solution removal
                        this.rootNode.getCandidateRowFirstNodes().put(solutionName, constraintNode);
                    }
                    // assign links to/from this node and previous constraint
                    // node in current row
                    else {
                        constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                        previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                        previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                    }
                    ConstraintCell previousNodeInColumn = previousLinkNodes.getPreviousConstraintNodesInColumns()
                            .get(nodeName);
                    // assign links to previous satisfied constraint in same column
                    constraintNode.setUp(previousNodeInColumn);
                    previousNodeInColumn.setDown(constraintNode);

                    // add this satisfied constraint as the last for this column
                    // for building column links
                    previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                    if(firstConstraintForThisRow) {
                        firstConstraintForThisRow = false;
                    }
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

    
    public PreviousLinkNodes generateNumberInARowCombinations(String solutionName, PreviousLinkNodes previousLinkNodes, List<String> givenSolutions,
            int currentRow, int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":r" + row;
                constraintNode = new ConstraintCell(solutionName);
                //&& !givenSolutions.contains(solutionName)
                if (currentRow == row && currentNum == num ) {
                    constraintNode.setConstraintSatisfied(1);
                    constraintNode.setType(NodeType.SatisfiedConstraint);

                    // assign links to/from this node and previous constraint node
                    constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                    previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                    // assign links to previous satisfied constraint in same column
                    constraintNode.setUp(
                            previousLinkNodes.getPreviousConstraintNodesInColumns().get(nodeName));
                    previousLinkNodes.getPreviousConstraintNodesInColumns().get(nodeName)
                            .setDown(constraintNode);

                    // add this satisfied constraint as the last for this column
                    // for building column links
                    previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
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

    
    public PreviousLinkNodes generateNumberInAColumnCombinations(String solutionName, PreviousLinkNodes previousLinkNodes, List<String> givenSolutions,
            int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":c" + col;
                constraintNode = new ConstraintCell(solutionName);

                //&& !givenSolutions.contains(solutionName)
                if(currentCol == col && currentNum == num ) {
                    constraintNode.setConstraintSatisfied(1);
                    constraintNode.setType(NodeType.SatisfiedConstraint);
                    constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                    previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                    
                    //assign links to previous satisfied constraint in same column
                    constraintNode.setUp(previousLinkNodes.getPreviousConstraintNodesInColumns().get(nodeName));
                    previousLinkNodes.getPreviousConstraintNodesInColumns().get(nodeName).setDown(constraintNode);
                    
                    //add this satisfied constraint as the last for this column for building column links
                    previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                }
                else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
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

    
    public PreviousLinkNodes generateNumberInASquareCombinations(String solutionName, PreviousLinkNodes previousLinkNodes, List<String> givenSolutions,
            int currentRow, int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;

        for (int square = 1; square <= NUM_SQUARES; square++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":s" + square;

                constraintNode = new ConstraintCell(solutionName);

                // do the current number, row and column satisfy the current
                // square constraint (e.g. 1:s1)
                //&& !givenSolutions.contains(solutionName)
                if (currentNum == num && this.rowAndColInSquare(currentCol, currentRow, square)) {
                    constraintNode.setConstraintSatisfied(1);
                    constraintNode.setType(NodeType.SatisfiedConstraint);
                    //track last node for circular link
                    previousLinkNodes.setLastConstraintNode(constraintNode);
                    // assign links to/from this node and previous constraint node
                    constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                    previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                    // assign links to previous satisfied constraint in same column
                    constraintNode.setUp(
                            previousLinkNodes.getPreviousConstraintNodesInColumns().get(nodeName));
                    previousLinkNodes.getPreviousConstraintNodesInColumns().get(nodeName)
                            .setDown(constraintNode);

                    // add this satisfied constraint as the last for this column
                    // for building column links
                    previousLinkNodes.setPreviousConstraintNodeInColumn(nodeName, constraintNode);
                    previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
                } else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
            }
        }
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }
    
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
    public void generateNumberInACellCombinations() {
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

    public int getSolutionsAddedToMatrix() {
        return solutionsAddedToMatrix;
    }

    public void setSolutionsAddedToMatrix(int solutionsAddedToMatrix) {
        this.solutionsAddedToMatrix = solutionsAddedToMatrix;
    }


}
