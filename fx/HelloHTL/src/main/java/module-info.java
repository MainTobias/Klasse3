module com.hellohtl {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.hellohtl to javafx.fxml;
    exports com.hellohtl;
}