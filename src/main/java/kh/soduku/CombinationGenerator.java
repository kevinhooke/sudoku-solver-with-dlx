package kh.soduku;

public class CombinationGenerator {

    private static final int MAX_NUM = 9;
    private static final int MAX_ROWS = 9;
    private static final int MAX_COLS = 9;
    private static final int NUM_SQUARES = 9;
    
    
    public static void main(String[] args) {
        CombinationGenerator generator = new CombinationGenerator();
        generator.generateConstraintGrid();
        //generator.generateNumberInACellConbinations();
        //generator.generateNumberInARowCombinations();
        //generator.generateNumberInAColumnCombinations();
        //generator.generateNumberInASquareCombinations();
    }

    /**
     * Generates the matrix of candidate cell combinations against set of all possible constraints.
     * 
     * @return the root cell of the linked list that represents the matrix
     */
    public ConstraintCell generateConstraintGrid() {
        ConstraintCell rootNode = null;
        int numberOfCombinations = 0;
        rootNode = new ConstraintCell("root");
        ConstraintCell previousNode = rootNode;
        ConstraintCell previousCandidateNode = previousNode;
        PreviousLinkNodes previousLinkNodes = new PreviousLinkNodes();
        
        //generate first row of headers
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                previousLinkNodes = this.generateNumberInACellConbinations(previousNode, 1, col, true);
                previousLinkNodes = this.generateNumberInARowCombinations(previousLinkNodes, 1, num, true);
                previousLinkNodes = this.generateNumberInAColumnCombinations(previousLinkNodes, col, num, true);
                previousLinkNodes = this.generateNumberInASquareCombinations(previousLinkNodes, 1, col, num, true);
                previousNode = previousLinkNodes.getLastConstraintNode();
            }
        }
        
        System.out.println();
        
        //reset back to working from the root
        previousNode = rootNode;
        previousCandidateNode = previousNode;
        
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                for (int num = 1; num <= MAX_NUM; num++) {
                    //the first cell in a row is a number:cell:row candidate
                    String nodeName = num + ":c" + col + ":r" + row;
                    System.out.print(nodeName + " ");
                    ConstraintCell candidateNode = new ConstraintCell(nodeName);
                    
                    // set link between new candidate row node and previous node (nodes on left of the matrix)
                    previousCandidateNode.setDown(candidateNode);
                    candidateNode.setUp(previousCandidateNode);
                    previousCandidateNode = candidateNode;
                    
                    
                    //TODO:links between nodes in columns: as matching constraint nodes are added, need to
                    //link to the previous node in same column, need to track in previousLinkNodes
                    
                    previousLinkNodes = this.generateNumberInACellConbinations(candidateNode, row, col, false);
                    previousLinkNodes = this.generateNumberInARowCombinations(previousLinkNodes, row, num, false);
                    previousLinkNodes = this.generateNumberInAColumnCombinations(previousLinkNodes, col, num, false);
                    previousLinkNodes = this.generateNumberInASquareCombinations(previousLinkNodes, row, col, num, false);
                    numberOfCombinations++;
                    previousNode = candidateNode;
                    System.out.println();
                }
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
        return rootNode;
    }

    public PreviousLinkNodes generateNumberInACellConbinations(ConstraintCell previousNode, 
            int currentRow, int currentCol, boolean headerNode) {
        PreviousLinkNodes previousLinkNodes = new PreviousLinkNodes();
        
        ConstraintCell constraintNode = null;
        
        if(headerNode) {
            previousLinkNodes.setLastConstraintNode(previousNode);
        }
        
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                if(headerNode) {
                    //header nodes are linked together across the row
                    constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                    previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setLastConstraintNode(constraintNode);
                    System.out.print(constraintNode.getName() + " ");
                }
                else {
                    //System.out.print(nodeName + " ");
                    if(currentRow == row && currentCol == col) {
                        constraintNode.setConstraintSatisfied(1);
                        
                        //assign links to/from this node and previous constraint node
                        constraintNode.setLeft(previousNode);
                        previousNode.setRight(constraintNode);
                        previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
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

    public PreviousLinkNodes generateNumberInARowCombinations(PreviousLinkNodes previousLinkNodes, 
            int currentRow, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":r" + row;
                constraintNode = new ConstraintCell(nodeName);
                
                if(headerNode) {
                    //header nodes are linked together across the row
                    constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                    previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setLastConstraintNode(constraintNode);
                    System.out.print(constraintNode.getName() + " ");
                }
                else {
                    if(currentRow == row && currentNum == num) {
                        constraintNode.setConstraintSatisfied(1);
                        
                        //assign links to/from this node and previous constraint node
                        constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                        previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                        previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
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

    public PreviousLinkNodes generateNumberInAColumnCombinations(PreviousLinkNodes previousLinkNodes, 
            int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                
                if(headerNode) {
                    //header nodes are linked together across the row
                    constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                    previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setLastConstraintNode(constraintNode);
                    System.out.print(constraintNode.getName() + " ");
                }
                else {
                    if(currentCol == col && currentNum == num) {
                        constraintNode.setConstraintSatisfied(1);
                        
                        //assign links to/from this node and previous constraint node
                        constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                        previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                        previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
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
    
    public PreviousLinkNodes generateNumberInASquareCombinations(PreviousLinkNodes previousLinkNodes, 
            int currentRow, int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        
        for (int square = 1; square <= NUM_SQUARES; square++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":s" + square;
            
                constraintNode = new ConstraintCell(nodeName);
                
                if(headerNode) {
                    //header nodes are linked together across the row
                    constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                    previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                    previousLinkNodes.setLastConstraintNode(constraintNode);
                    System.out.print(constraintNode.getName() + " ");
                }
                else {
                    //do the current number, row and colum satisfy the current square constraint (e.g. 1:s1)
                    if(currentNum == num && this.rowAndColInSquare(currentCol, currentRow, square)) {
                        constraintNode.setConstraintSatisfied(1);
                        
                        //assign links to/from this node and previous constraint node
                        constraintNode.setLeft(previousLinkNodes.getPreviousValidConstraintNode());
                        previousLinkNodes.getPreviousValidConstraintNode().setRight(constraintNode);
                        previousLinkNodes.setPreviousValidConstraintNode(constraintNode);
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
    
    //row 1-3 col 1-3 = s1
    //row 1-3 col 4-6 = s2
    //row 1-3 col 7-9 = s3
    //row 4-6 col 1-3 = s4
    //row 4-6 col 4-6 = s5
    //row 4-6 col 7-9 = s6
    //row 7-9 col 1-3 = s7
    //row 7-9 col 4-6 = s8
    //row 7-9 col 7-9 = s9
    //TODO: there's probably a more elagant way of doing this - potential for refactor later
    public boolean rowAndColInSquare(int currentCol, int currentRow, int square) {
        boolean result = false;
        
        //check inputs
        //TODO calc square range based on max rows and max cols
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
