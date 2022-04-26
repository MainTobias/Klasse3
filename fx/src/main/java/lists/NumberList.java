package lists;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.stream.IntStream;

import static com.Utils.getPath;

public class NumberList extends Application {

    @FXML
    private ListView<Integer> left;
    @FXML
    private ListView<Integer> right;

    @FXML
    private ToggleGroup selection;

    private void move(ListView<Integer> from, ListView<Integer> to) {
        for (Iterator<Integer> it = from.getSelectionModel().getSelectedItems().iterator(); it.hasNext(); ) {
            Integer i = it.next();
            to.getItems().add(i);
            from.getItems().remove(i);
        }
    }

    @FXML
    private void moveLeft() {
        System.out.println(left);
        move(right, left);
    }

    @FXML
    private void moveRight() {
        move(left, right);
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
        stage.setOnShown((e) -> System.out.println(left));
        stage.show();
    }

    private void startLogic(Stage stage) {
        left.getItems().addAll(IntStream.rangeClosed(0, 100).boxed().toList());
    }
}
