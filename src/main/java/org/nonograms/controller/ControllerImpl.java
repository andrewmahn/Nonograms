package org.nonograms.controller;

import java.util.Random;
import org.nonograms.model.Clues;
import org.nonograms.model.Model;


public class ControllerImpl implements Controller {

  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
    if (model == null) {
      throw new IllegalArgumentException("Model is null!");
    }
  }

  @Override
  public Clues getClues() {
    return model;
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public boolean isShaded(int row, int col) {
    return model.isShaded(row, col);
  }

  @Override
  public boolean isEliminated(int row, int col) {
    return model.isEliminated(row, col);
  }

  @Override
  public void toggleShaded(int row, int col) {
    model.toggleCellShaded(row, col);
  }

  @Override
  public void toggleEliminated(int row, int col) {
    model.toggleCellEliminated(row, col);
  }

  @Override
  public void nextPuzzle() {
    if (model.getPuzzleIndex() + 1 == model.getPuzzleCount()) {
      return;
    }
    model.setPuzzleIndex(model.getPuzzleIndex() + 1);
  }

  @Override
  public void prevPuzzle() {
    if (model.getPuzzleIndex() - 1 < 0) {
      return;
    }
    model.setPuzzleIndex(model.getPuzzleIndex() - 1);
  }

  @Override
  public void randPuzzle() {
    Random rand = new Random();
    model.setPuzzleIndex(rand.nextInt(model.getPuzzleCount() - 1));
  }

  @Override
  public void clearBoard() {
    model.clear();
  }

  @Override
  public int getPuzzleIndex() {
    return model.getPuzzleIndex();
  }

  @Override
  public int getPuzzleCount() {
    return model.getPuzzleCount();
  }
}
