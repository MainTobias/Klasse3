module com.textcombiner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.textcombiner to javafx.fxml;
    exports com.textcombiner;
}