package kh.soduku;

public class CombinationGenerator {

    private static final int MAX_NUM = 9;
    private static final int MAX_ROWS = 9;
    private static final int MAX_COLS = 9;
    private static final int NUM_SQUARES = 9;
    
    private ConstraintCell rootNode;
    
    public static void main(String[] args) {
        CombinationGenerator generator = new CombinationGenerator();
        generator.generateConstraintGrid();
        //generator.generateNumberInACellConbinations();
        //generator.generateNumberInARowCombinations();
        //generator.generateNumberInAColumnCombinations();
        //generator.generateNumberInASquareCombinations();
    }

    public void generateConstraintGrid() {
        int numberOfCombinations = 0;
        this.rootNode = new ConstraintCell("root");
        ConstraintCell previousNode = rootNode;
        
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                for (int num = 1; num <= MAX_NUM; num++) {
                    String nodeName = num + ":c" + col + ":r" + row;
                    System.out.print(nodeName + " ");
                    ConstraintCell candidateNode = new ConstraintCell(nodeName);
                    
                    //link to previous node
                    previousNode.setRight(candidateNode);
                    candidateNode.setLeft(previousNode);
                    
                    previousNode = this.generateNumberInACellConbinations(candidateNode, row, col);
                    previousNode = this.generateNumberInARowCombinations(previousNode, row, num);
                    previousNode = this.generateNumberInAColumnCombinations(previousNode, col, num);
                    numberOfCombinations++;
                    previousNode = candidateNode;
                    System.out.println();
                }
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
    }

    public ConstraintCell generateNumberInACellConbinations(ConstraintCell previousNode, int currentRow, int currentCol) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                String nodeName = "n:r" + row + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                //System.out.print(nodeName + " ");
                if(currentRow == row && currentCol == col) {
                    constraintNode.setConstraintSatisfied(1);
                }
                else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
                //link to previous node
                previousNode.setRight(constraintNode);
                constraintNode.setLeft(previousNode);
                previousNode = constraintNode;
            }
        }
        return constraintNode;
    }
    
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

    public ConstraintCell generateNumberInARowCombinations(ConstraintCell previousNode, int currentRow, int currentNum) {
        ConstraintCell constraintNode = null;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":r" + row;
                constraintNode = new ConstraintCell(nodeName);
                if(currentRow == row && currentNum == num) {
                    constraintNode.setConstraintSatisfied(1);
                }
                else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
                //link to previous node
                previousNode.setRight(constraintNode);
                constraintNode.setLeft(previousNode);
                previousNode = constraintNode;
            }
        }
        return constraintNode;
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

    public ConstraintCell generateNumberInAColumnCombinations(ConstraintCell previousNode, int currentCol, int currentNum) {
        ConstraintCell constraintNode = null;
        for (int col = 1; col <= MAX_COLS; col++) {
            for (int num = 1; num <= MAX_NUM; num++) {
                String nodeName = num + ":c" + col;
                constraintNode = new ConstraintCell(nodeName);
                if(currentCol == col && currentNum == num) {
                    constraintNode.setConstraintSatisfied(1);
                }
                else {
                    constraintNode.setConstraintSatisfied(0);
                }
                System.out.print(constraintNode.getConstraintSatisfied());
                //link to previous node
                previousNode.setRight(constraintNode);
                constraintNode.setLeft(previousNode);
                previousNode = constraintNode;
            }
        }

        return constraintNode;
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
