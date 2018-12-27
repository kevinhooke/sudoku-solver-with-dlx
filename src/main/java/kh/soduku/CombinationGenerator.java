package kh.soduku;

public class CombinationGenerator {

    private static final int MAX_NUM = 9;
    private static final int MAX_ROWS = 9;
    private static final int MAX_COLS = 9;
    private static final int NUM_SQUARES = 9;
    
    private ConstraintCell rootNode = null;
    
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
        int numberOfCombinations = 0;
        this.rootNode = new ConstraintCell("root");
        this.rootNode.setType(NodeType.RootNode);
        ConstraintCell previousNode = rootNode;
        ConstraintCell previousCandidateNode = previousNode;
        PreviousLinkNodes previousLinkNodes = new PreviousLinkNodes();
        previousLinkNodes.setLastConstraintNode(previousNode);
            
        //generate first row of headers
        //test removing outer loop
        //for (int col = 1; col <= MAX_COLS; col++) {
        //    for (int num = 1; num <= MAX_NUM; num++) {
                previousLinkNodes = this.generateNumberInACellConbinations(previousLinkNodes, 1, col, true);
                previousLinkNodes = this.generateNumberInARowCombinations(previousLinkNodes, 1, col, num, true);
                previousLinkNodes = this.generateNumberInAColumnCombinations(previousLinkNodes, col, num, true);
                previousLinkNodes = this.generateNumberInASquareCombinations(previousLinkNodes, 1, col, num, true);
                previousNode = previousLinkNodes.getLastConstraintNode();
        //    }
        //}
        
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
        System.out.println("Combinations: " + numberOfCombinations);
        return rootNode;
    }

    //
    //
    // TODO: take header loops out to new methods that don't take row and col params because they're not needed
    // other non-header parts are ok. header code was looping unnessarily and overwriting header nodes
    //
    
    public PreviousLinkNodes generateNumberInACellConbinations(PreviousLinkNodes previousLinkNodes, 
            int currentRow, int currentCol, boolean headerNode) {
        
        ConstraintCell constraintNode = null;
        
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
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
                    //System.out.print(nodeName + " ");
                    if(currentRow == row && currentCol == col) {
                        constraintNode.setConstraintSatisfied(1);
                        constraintNode.setType(NodeType.SatisfiedConstraint);
                        //assign links to/from this node and previous constraint node in current row
                        constraintNode.setLeft(previousLinkNodes.getLastConstraintNode());
                        previousLinkNodes.getLastConstraintNode().setRight(constraintNode);
                        previousLinkNodes.setPreviousValidConstraintNode(constraintNode);

                        ConstraintCell previousNodeInColumn = previousLinkNodes.getPreviousConstraintNodesInColumns().get(constraintNode.getName()); 
                        //assign links to previous satisfied constraint in same column
                        constraintNode.setUp(previousNodeInColumn);
                        previousNodeInColumn.setDown(constraintNode);
                        
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

    public PreviousLinkNodes generateNumberInARowCombinations(PreviousLinkNodes previousLinkNodes, 
            int currentRow, int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":r" + row;
                constraintNode = new ConstraintCell(nodeName);
                
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
                    if(currentRow == row && currentNum == num) {
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

    public PreviousLinkNodes generateNumberInAColumnCombinations(PreviousLinkNodes previousLinkNodes, 
            int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                
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
    
    public PreviousLinkNodes generateNumberInASquareCombinations(PreviousLinkNodes previousLinkNodes, 
            int currentRow, int currentCol, int currentNum, boolean headerNode) {
        ConstraintCell constraintNode = null;
        
        for (int square = 1; square <= NUM_SQUARES; square++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":s" + square;
            
                constraintNode = new ConstraintCell(nodeName);
                
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
                    //do the current number, row and column satisfy the current square constraint (e.g. 1:s1)
                    if(currentNum == num && this.rowAndColInSquare(currentCol, currentRow, square)) {
                        constraintNode.setConstraintSatisfied(1);
                        constraintNode.setType(NodeType.SatisfiedConstraint);
                        
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
