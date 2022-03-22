module com.swapper {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.swapper to javafx.fxml;
    exports com.swapper;
}