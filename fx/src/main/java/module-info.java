module fx {
    requires javafx.controls;
    requires javafx.fxml;


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


}