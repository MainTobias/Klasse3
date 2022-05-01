package counter.withoutModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @FXML
    Label counter;

    IntegerProperty count = new SimpleIntegerProperty();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/counter/counter-view.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        counter.textProperty().bind(count.asString());
        counter.getParent().setOnMouseClicked((EventHandler<Event>) event -> count.set(count.get()+1));
    }
}
