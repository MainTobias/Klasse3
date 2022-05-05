module fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens chess to javafx.fxml;
    exports chess;

    opens hellohtl to javafx.fxml;
    exports hellohtl;

    opens swapper to javafx.fxml;
    exports swapper;

    opens textcombiner to javafx.fxml;
    exports textcombiner;

    opens lists to javafx.fxml;
    exports lists;

    opens bindings to javafx.fxml;
    exports bindings;

    opens counter.withoutModel to javafx.fxml;
    exports counter.withoutModel;

    opens counter.withModel to javafx.fxml;
    exports counter.withModel;

    opens com to javafx.fxml;
    exports com;
}