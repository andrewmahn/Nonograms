package org.nonograms.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private List<Clues> clues;
  private int index;
  private Board board;
  private List<ModelObserver> observers;

  public ModelImpl(List<Clues> clues) {
    this.clues = clues;
    this.index = 0;
    this.board = new BoardImpl(clues.get(index).getWidth(), clues.get(index).getHeight());
    this.observers = new ArrayList<>();
    if (clues == null) {
      throw new IllegalArgumentException("Clues is null!");
    }
  }

  @Override
  public int getPuzzleCount() {
    return clues.size();
  }

  @Override
  public int getPuzzleIndex() {
    return index;
  }

  @Override
  public void setPuzzleIndex(int index) {
    if (index < 0 || index >= clues.size()) {
      throw new IllegalArgumentException("Invalid puzzle index!");
    }
    this.index = index;
    this.board = new BoardImpl(clues.get(index).getWidth(), clues.get(index).getHeight());
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Cannot add null observer!");
    }
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Cannot remove null observer!");
    }
    observers.remove(observer);
  }

  @Override
  public boolean isSolved() {
    // Initializes new 2D arrays for actual row and col clues
    int[][] actualRowClues =
        new int[clues.get(index).getHeight()][clues.get(index).getRowCluesLength()];
    int[][] actualColClues =
        new int[clues.get(index).getWidth()][clues.get(index).getColCluesLength()];

    // Initializes 2D arrays representing expected row and col clues based on current puzzle clues
    int[][] expectedRowClues =
        new int[clues.get(index).getHeight()][clues.get(index).getRowCluesLength()];
    for (int i = 0; i < clues.get(index).getHeight(); i++) {
      expectedRowClues[i] = clues.get(index).getRowClues(i);
    }
    int[][] expectedColClues =
        new int[clues.get(index).getWidth()][clues.get(index).getColCluesLength()];
    for (int i = 0; i < clues.get(index).getWidth(); i++) {
      expectedColClues[i] = clues.get(index).getColClues(i);
    }

    // GENERATES ROW CLUES FROM ACTUAL BOARD STATE
    // Iterates through each row
    for (int i = 0; i < actualRowClues.length; i++) {
      int inARow = 0;
      int clue = 0;
      // Iterates through each column
      for (int j = 0; j < clues.get(index).getWidth(); j++) {
        // Keeps track of how many shaded cells are in a row
        if (isShaded(i, j)) {
          inARow++;
        } else {
          // If a space is reached after a series of shaded cells, adds number to clue
          if (inARow != 0) {
            actualRowClues[i][clue] = inARow;
            inARow = 0;
            clue++;
          }
        }
        // Edge case for when the last cell of a row is shaded
        if (inARow != 0 && j == clues.get(index).getWidth() - 1) {
          actualRowClues[i][clue] = inARow;
          inARow = 0;
          clue++;
        }
      }
      // Once end of row is reached, fills rest of clue with zeroes if necessary
      int shift = clues.get(index).getRowCluesLength() - clue;
      if (clue < clues.get(index).getRowCluesLength()) {
        // Shifts numbers to the right
        for (int k = 0; k < shift; k++) {
          for (int l = clues.get(index).getRowCluesLength() - 1; l > 0; l--) {
            actualRowClues[i][l] = actualRowClues[i][l - 1];
          }
        }
        // Fills preceding numbers with 0s
        for (int k = 0; k < shift; k++) {
          actualRowClues[i][k] = 0;
        }
      }
    }

    // GENERATES COL CLUES FROM ACTUAL BOARD STATE
    // Iterates through each column
    for (int i = 0; i < actualColClues.length; i++) {
      int inARow = 0;
      int clue = 0;
      // Iterates through each row
      for (int j = 0; j < clues.get(index).getHeight(); j++) {
        // Keeps track of how many shaded cells are in a row
        if (isShaded(j, i)) {
          inARow++;
        } else {
          // If a space is reached after a series of shaded cells, adds number to clue
          if (inARow != 0) {
            actualColClues[i][clue] = inARow;
            inARow = 0;
            clue++;
          }
        }
        // Edge case for when the last cell of a column is shaded
        if (inARow != 0 && j == clues.get(index).getHeight() - 1) {
          actualColClues[i][clue] = inARow;
          inARow = 0;
          clue++;
        }
      }
      // Once end of column is reached, fills rest of clue with zeroes if necessary
      int shift = clues.get(index).getColCluesLength() - clue;
      if (clue < clues.get(index).getColCluesLength()) {
        // Shifts numbers to the right
        for (int k = 0; k < shift; k++) {
          for (int l = clues.get(index).getColCluesLength() - 1; l > 0; l--) {
            actualColClues[i][l] = actualColClues[i][l - 1];
          }
        }
        // Fills preceding numbers with 0s
        for (int k = 0; k < shift; k++) {
          actualColClues[i][k] = 0;
        }
      }
    }
    // Returns true if expected and actual row and col clues are equal and false if otherwise
    for (int i = 0; i < actualRowClues.length; i++) {
      for (int j = 0; j < actualRowClues[i].length; j++) {
        if (actualRowClues[i][j] != expectedRowClues[i][j]) {
          return false;
        }
      }
    }
    for (int i = 0; i < actualColClues.length; i++) {
      for (int j = 0; j < actualColClues[i].length; j++) {
        if (actualColClues[i][j] != expectedColClues[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isShaded(int row, int col) {
    if (row < 0
        || col < 0
        || row > clues.get(index).getHeight()
        || col > clues.get(index).getWidth()) {
      throw new RuntimeException("Invalid coordinate!");
    }
    return board.isShaded(row, col);
  }

  @Override
  public boolean isEliminated(int row, int col) {
    if (row < 0
        || col < 0
        || row > clues.get(index).getHeight()
        || col > clues.get(index).getWidth()) {
      throw new RuntimeException("Invalid coordinate!");
    }
    return board.isEliminated(row, col);
  }

  @Override
  public boolean isSpace(int row, int col) {
    if (row < 0
        || col < 0
        || row > clues.get(index).getHeight()
        || col > clues.get(index).getWidth()) {
      throw new RuntimeException("Invalid coordinate!");
    }
    return board.isSpace(row, col);
  }

  @Override
  public void toggleCellShaded(int row, int col) {
    if (row < 0
        || col < 0
        || row > clues.get(index).getHeight()
        || col > clues.get(index).getWidth()) {
      throw new RuntimeException("Invalid coordinate!");
    }
    board.toggleCellShaded(row, col);
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }

  @Override
  public void toggleCellEliminated(int row, int col) {
    if (row < 0
        || col < 0
        || row > clues.get(index).getHeight()
        || col > clues.get(index).getWidth()) {
      throw new RuntimeException("Invalid coordinate!");
    }
    board.toggleCellEliminated(row, col);
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }

  @Override
  public void clear() {
    board.clear();
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }

  @Override
  public int getWidth() {
    return clues.get(index).getWidth();
  }

  @Override
  public int getHeight() {
    return clues.get(index).getHeight();
  }

  @Override
  public int[] getRowClues(int index) {
    return clues.get(this.index).getRowClues(index);
  }

  @Override
  public int[] getColClues(int index) {
    return clues.get(this.index).getColClues(index);
  }

  @Override
  public int getRowCluesLength() {
    return clues.get(index).getRowCluesLength();
  }

  @Override
  public int getColCluesLength() {
    return clues.get(index).getColCluesLength();
  }
}
