package lists;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.Utils.getPath;

public class Logger extends Application {
    @FXML
    private ListView<LogEntry> logs;
    @FXML
    private ToggleGroup option;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getPath(this.getClass()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(scene);
        stage.setMinHeight(300);
        stage.setMinWidth(500);
        stage.show();
    }

    public void initialize() {
        option.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            logs.getItems().add(new LogEntry(((RadioButton) newToggle).getText() + " selected", LocalDateTime.now()));
        });
    }

    @FXML
    private void somethingOnActionHandler(final ActionEvent event) {
        logs.getItems().add(new LogEntry(event.getSource() instanceof Button ? "Button pressed" : ((CheckBox) event.getSource()).isSelected() ? "CheckBox checked" : "CheckBox unchecked", LocalDateTime.now()));
    }
}
