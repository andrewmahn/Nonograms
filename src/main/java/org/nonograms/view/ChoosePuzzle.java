package org.nonograms.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.nonograms.controller.Controller;

// UI Component:
// Red Banner at the top of the window that contains controls for choosing a puzzle.
public class ChoosePuzzle implements FXComponent {

  private final Controller controller;

  public ChoosePuzzle(Controller controller) {
    this.controller = controller;
    if (controller == null) {
      throw new IllegalArgumentException("Controller is null!");
    }
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.setPadding(new Insets(15, 12, 15, 12));
    layout.setSpacing(10);
    layout.setStyle("-fx-background-color: #ff0000;");

    Button prevPuzzle = new Button("<");
    prevPuzzle.setPrefSize(20, 20);
    prevPuzzle.setOnMouseClicked(
        (MouseEvent event) -> {
          controller.prevPuzzle();
        });

    Button puzzle = new Button("Puzzle " + (controller.getPuzzleIndex() + 1));
    puzzle.setPrefSize(100, 20);

    Button nextPuzzle = new Button(">");
    nextPuzzle.setPrefSize(20, 20);
    nextPuzzle.setOnMouseClicked(
        (MouseEvent event) -> {
          controller.nextPuzzle();
        });

    Button randPuzzle = new Button("Random Puzzle");
    randPuzzle.setPrefSize(150, 20);
    randPuzzle.setOnMouseClicked(
        (MouseEvent event) -> {
          controller.randPuzzle();
        });

    Button clear = new Button("Clear");
    clear.setPrefSize(100, 20);
    clear.setOnMouseClicked(
        (MouseEvent event) -> {
          controller.clearBoard();
        });

    layout.getChildren().addAll(prevPuzzle, puzzle, nextPuzzle, randPuzzle, clear);

    return layout;
  }
}
