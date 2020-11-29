package org.nonograms.model;

public class BoardImpl implements Board {

  enum state {
    SPACE,
    SHADED,
    ELIMINATED
  }

  private state[][] board;

  public BoardImpl(int width, int height) {
    this.board = new state[height][width];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = state.SPACE;
      }
    }
    if (width == 0) {
      throw new IllegalArgumentException("Invalid width!");
    }
    if (height == 0) {
      throw new IllegalArgumentException("Invalid height!");
    }
  }

  @Override
  public boolean isShaded(int row, int col) {
    if (board[row][col].equals(state.SHADED)) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isEliminated(int row, int col) {
    if (board[row][col].equals(state.ELIMINATED)) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isSpace(int row, int col) {
    if (board[row][col].equals(state.SPACE)) {
      return true;
    }
    return false;
  }

  @Override
  public void toggleCellShaded(int row, int col) {
    if (board[row][col].equals(state.SHADED)) {
      board[row][col] = state.SPACE;
      return;
    }
    board[row][col] = state.SHADED;
  }

  @Override
  public void toggleCellEliminated(int row, int col) {
    if (board[row][col].equals(state.ELIMINATED)) {
      board[row][col] = state.SPACE;
      return;
    }
    board[row][col] = state.ELIMINATED;
  }

  @Override
  public void clear() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = state.SPACE;
      }
    }
  }
}