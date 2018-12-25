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
     * Generates the matrix of candiate cell conbinations against set of all possible constraints.
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
                    
                    
                    //TODO:links between nodes in columns
                    
                    previousLinkNodes = this.generateNumberInACellConbinations(candidateNode, row, col);
                    previousLinkNodes = this.generateNumberInARowCombinations(previousLinkNodes, row, num);
                    previousLinkNodes = this.generateNumberInAColumnCombinations(previousLinkNodes, col, num);
                    numberOfCombinations++;
                    previousNode = candidateNode;
                    System.out.println();
                }
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
        return rootNode;
    }

    public PreviousLinkNodes generateNumberInACellConbinations(ConstraintCell previousNode, int currentRow, int currentCol) {
        PreviousLinkNodes previousLinkNodes = new PreviousLinkNodes();
        
        ConstraintCell constraintNode = null;
        
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
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
        previousLinkNodes.setLastConstraintNode(constraintNode);
        return previousLinkNodes;
    }

    public PreviousLinkNodes generateNumberInARowCombinations(PreviousLinkNodes previousLinkNodes, int currentRow, int currentNum) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":r" + row;
                constraintNode = new ConstraintCell(nodeName);
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
        return previousLinkNodes;
    }

    public PreviousLinkNodes generateNumberInAColumnCombinations(PreviousLinkNodes previousLinkNodes, int currentCol, int currentNum) {
        ConstraintCell constraintNode = null;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
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

        return previousLinkNodes;
    }
    
    public PreviousLinkNodes generateNumberInASquareCombinations(PreviousLinkNodes previousLinkNodes, int currentCol, int currentNum) {
        int numberOfCombinations = 0;
        for (int square = 1; square <= NUM_SQUARES; square++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                System.out.println(num + ":s" + square);
                numberOfCombinations++;
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
        return previousLinkNodes;
    }
    
    // following methods used only to write a visual representation of the candidate x constraint matrix
    
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
