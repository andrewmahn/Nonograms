package org.nonograms;

import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.nonograms.controller.Controller;
import org.nonograms.controller.ControllerImpl;
import org.nonograms.model.Clues;
import org.nonograms.model.Model;
import org.nonograms.model.ModelImpl;
import org.nonograms.view.View;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Model
        List<Clues> clues = PuzzleLibrary.create();
        Model model = new ModelImpl(clues);

        // Controller
        Controller controller = new ControllerImpl(model);

        // View
        View view = new View(controller);

        // Makes scene
        stage.setScene(new Scene(view.render()));

        // Refreshes view when model changes
        model.addObserver(
            (Model m) -> {
                stage.setScene(new Scene(view.render()));
            });

        // Shows the stage
        stage.setTitle("Andy Mahndy's Handy Dandy Nonograms!");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}