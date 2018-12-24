package kh.soduku;

public class CombinationGenerator {

    private static final int MAX_NUM = 9;
    private static final int MAX_ROWS = 9;
    private static final int MAX_COLS = 9;
    private static final int NUM_SQUARES = 9;
    
    public static void main(String[] args) {
        CombinationGenerator generator = new CombinationGenerator();
        generator.generateCombination();
        generator.generateNumberInACellConbinations();
        generator.generateNumberInARowCombinations();
        generator.generateNumberInAColumnCombinations();
        generator.generateNumberInASquareCombinations();
    }

    public void generateCombination() {
        int numberOfCombinations = 0;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                for (int num = 1; num <= MAX_NUM; num++) {
                    System.out.println(num + ":c" + col + ":r" + row);
                    numberOfCombinations++;
                }
            }
        }
        System.out.println("Combinations: " + numberOfCombinations);
    }

    public void generateNumberInACellConbinations() {
        int numberOfCombinations = 0;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int col = 1; col <= MAX_COLS; col++) {
                System.out.println("n:r" + row + ":c" + col);
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
