package org.nonograms.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.nonograms.controller.Controller;

public class Puzzle implements FXComponent {

  private final Controller controller;

  public Puzzle(Controller controller) {
    this.controller = controller;
    if (controller == null) {
      throw new IllegalArgumentException("Controller is null!");
    }
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(2.5);
    grid.setVgap(2.5);
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setStyle("-fx-background-color: #D3D3D3;");

    generateBoard(grid);

    return grid;
  }

  private void generateBoard(GridPane grid) {
    for (int i = 1; i < controller.getClues().getHeight() + 1; i++) {
      HBox rowClue = new HBox();
      rowClue.getChildren().add(new Label(rowClueToString(i - 1)));
      rowClue.setAlignment(Pos.CENTER_RIGHT);
      grid.add(rowClue, 0, i);
    }
    for (int j = 1; j < controller.getClues().getWidth() + 1; j++) {
      VBox colClue = new VBox();
      colClue.getChildren().add(new Label(colClueToString(j - 1)));
      colClue.setAlignment(Pos.BOTTOM_CENTER);
      grid.add(colClue, j, 0);
    }
    for (int i = 1; i < controller.getClues().getHeight() + 1; i++) {
      for (int j = 1; j < controller.getClues().getWidth() + 1; j++) {
        final int row = i - 1;
        final int col = j - 1;
        StackPane cell = new StackPane();
        cell.setPrefHeight(30);
        cell.setPrefWidth(30);
        cell.setStyle("-fx-background-color: #FFFFFF;");
        cell.setStyle("-fx-background-color: #FFFFFF;");
        if (controller.isShaded(row, col)) {
          cell.setStyle("-fx-background-color: #000000;");
        }
        if (controller.isEliminated(row, col)) {
          Label x = new Label("X");
          cell.getChildren().add(x);
          x.setAlignment(Pos.CENTER);
        }
        cell.setOnMouseClicked(
            event -> {
              if (event.getButton() == MouseButton.PRIMARY) {
                controller.toggleShaded(row, col);
              }
              if (event.getButton() == MouseButton.SECONDARY) {
                controller.toggleEliminated(row, col);
              }
            });
        grid.add(cell, j, i);
      }
    }
  }

  private String rowClueToString(int row) {
    int numCount = 0;
    int zeroCount = 0;
    for (int i = 0; i < controller.getClues().getRowCluesLength(); i++) {
      if (controller.getClues().getRowClues(row)[i] != 0) {
        numCount++;
      } else {
        zeroCount++;
      }
    }
    if (numCount == 0) {
      return "0";
    }
    StringBuilder result = new StringBuilder();
    for (int i = zeroCount; i < controller.getClues().getRowCluesLength(); i++) {
      result.append(" ").append(controller.getClues().getRowClues(row)[i]);
    }
    return result.toString();
  }

  private String colClueToString(int col) {
    int numCount = 0;
    int zeroCount = 0;
    for (int i = 0; i < controller.getClues().getColCluesLength(); i++) {
      if (controller.getClues().getColClues(col)[i] != 0) {
        numCount++;
      } else {
        zeroCount++;
      }
    }
    if (numCount == 0) {
      return "0";
    }
    StringBuilder result = new StringBuilder();
    for (int i = zeroCount; i < controller.getClues().getColCluesLength(); i++) {
      result.append("\n").append(controller.getClues().getColClues(col)[i]);
    }
    return result.toString();
  }
}
