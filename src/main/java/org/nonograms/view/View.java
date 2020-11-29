package org.nonograms.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import org.nonograms.controller.Controller;

public class View implements FXComponent {

  private final Controller controller;

  public View(Controller controller) {
    this.controller = controller;
    if (controller == null) {
      throw new IllegalArgumentException("Controller is null!");
    }
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();
    layout.setAlignment(Pos.CENTER);

    // Controls view
    ChoosePuzzle choosePuzzle = new ChoosePuzzle(controller);
    layout.getChildren().add(choosePuzzle.render());

    if (!controller.isSolved()) {
      Puzzle puzzle = new Puzzle(controller);
      layout.getChildren().add(puzzle.render());
    } else {
      VBox winScreen = new VBox(10);
      winScreen.setAlignment(Pos.CENTER);
      winScreen.setPrefSize(controller.getClues().getColCluesLength() * 10
          + controller.getClues().getColClues(controller.getPuzzleIndex()).length * 30,
          controller.getClues().getRowCluesLength() * 10
              + controller.getClues().getRowClues(controller.getPuzzleIndex()).length * 30);
      winScreen.setStyle("-fx-background-color: #90EE90;");

      Label message = new Label("Congrats! You won!");
      message.setAlignment(Pos.CENTER);

      Button retry = new Button("Retry Puzzle");
      retry.setAlignment(Pos.CENTER);
      retry.setOnMouseClicked(
          event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
              controller.clearBoard();
            }
          });
      retry.setPrefSize(100, 20);
      winScreen.getChildren().addAll(message, retry);

      layout.getChildren().add(winScreen);
    }
    return layout;
  }
}
