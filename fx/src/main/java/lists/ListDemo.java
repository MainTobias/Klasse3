package lists;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static com.Utils.getPath;

import java.io.IOException;
import java.util.Collections;

public class ListDemo extends Application {

    @FXML
    private TextField input;
    @FXML
    private ListView<String> items;

    @FXML
    private void add() {
        String s = input.getText().strip();
        if (s.length() == 0) return;
        items.getItems().add(s);
        input.clear();
        input.requestFocus();
    }

    @FXML
    private void clear() {
        items.getItems().clear();
    }

    @FXML
    private void delete() {
        int selected = items.getSelectionModel().getSelectedIndex();
        if (selected < 0) return;
        items.getItems().remove(selected);
    }

    @FXML
    private void shuffle() {
        Collections.shuffle(items.getItems());
    }

    @FXML
    private void sort() {
        Collections.sort(items.getItems());
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getPath(this.getClass()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(scene);
        stage.show();
    }
}
