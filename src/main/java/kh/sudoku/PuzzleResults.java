package kh.sudoku;

import java.util.ArrayList;
import java.util.List;

public class PuzzleResults {

    private boolean validPuzzle;
    
    /**
     * List of results. For a valid puzzle, the list is populated with a single
     * result, otherwise populated with multiple populated results.
     */
    private List<List<String>> results = new ArrayList<>();

    public List<List<String>> getResults() {
        return results;
    }
    
    public void addResult(List<String> result) {
        this.results.add(result);
    }

    public boolean isValidPuzzle() {
        return validPuzzle;
    }

    public void setValidPuzzle(boolean validPuzzle) {
        this.validPuzzle = validPuzzle;
    }
}
