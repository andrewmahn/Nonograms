package org.nonograms.model;

public class CluesImpl implements Clues {

  private int width;
  private int height;
  private int[][] rowClues;
  private int[][] colClues;

  public CluesImpl(int[][] rowClues, int[][] colClues) {
    this.rowClues = rowClues;
    this.colClues = colClues;
    this.width = colClues.length;
    this.height = rowClues.length;
    if (rowClues == null) {
      throw new IllegalArgumentException("Row Clues is Null!");
    }
    if (colClues == null) {
      throw new IllegalArgumentException("Col Clues is Null!");
    }
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int[] getRowClues(int index) {
    return rowClues[index];
  }

  @Override
  public int[] getColClues(int index) {
    return colClues[index];
  }

  @Override
  public int getRowCluesLength() {
    return rowClues[0].length;
  }

  @Override
  public int getColCluesLength() {
    return colClues[0].length;
  }
}
