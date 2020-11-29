module org.andrewmahn {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.nonograms to javafx.fxml;
    exports org.nonograms;
}