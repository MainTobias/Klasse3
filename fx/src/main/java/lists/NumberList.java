package lists;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.Utils.getPath;

public class NumberList extends Application {

    @FXML
    private ToggleGroup selection;
    @FXML
    private ListView<Integer> left;
    @FXML
    private ListView<Integer> right;
    @FXML
    private RadioButton single;
    @FXML
    private RadioButton multi;

    private SelectionMode getSelectionMode() {
        return single.isSelected() ? SelectionMode.SINGLE : SelectionMode.MULTIPLE;
    }


    private void moveMultiple(ListView<Integer> from, ListView<Integer> to) {
        List<Integer> l = new ArrayList<>(from.getSelectionModel().getSelectedItems());
        to.getItems().addAll(l);
        from.getItems().removeAll(l);
        Collections.sort(to.getItems());
    }

    private void moveSingle(ListView<Integer> from, ListView<Integer> to) {
        Integer i = from.getSelectionModel().getSelectedItem();
        if (i < 0) return;
        to.getItems().add(-(Collections.binarySearch(to.getItems(), i)+1), i);
    }

    @FXML
    private void moveLeft() {
        if (getSelectionMode() == SelectionMode.SINGLE) {
            moveSingle(right, left);
        } else {
            moveMultiple(right, left);
        }
    }

    @FXML
    private void moveRight() {
        if (getSelectionMode() == SelectionMode.SINGLE) {
            moveSingle(left, right);
        } else {
            moveMultiple(left, right);
        }
    }

    @FXML
    private void switchSelection() {
        if (single.isSelected()) {
            left.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } else if (multi.isSelected()) {
            left.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
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


    public void initialize() {
        startLogic();
    }

    private void startLogic() {
        left.getItems().addAll(IntStream.rangeClosed(0, 100).boxed().toList());
    }
}
