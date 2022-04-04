module com.chess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.chess to javafx.fxml;
    exports com.chess;
}