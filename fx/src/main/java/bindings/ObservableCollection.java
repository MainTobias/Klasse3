package bindings;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;

import static com.Utils.getPath;

public class ObservableCollection extends Application {
    @FXML
    ListView<Integer> listview;

    final ObservableList<Integer> list = FXCollections.observableArrayList();

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

    @FXML
    public void initialize() {
        listview.setItems(list);
        list.addListener((ListChangeListener<Integer>) c -> {
            c.next();
            if (c.wasAdded() || c.wasReplaced()) {
                Collections.sort(list);
                System.out.println("because c was somehow manipulated we sorted the list element");
            } else if (c.wasRemoved()) {
                list.addAll(c.getRemoved());
                list.addAll(c.getRemoved());
                System.out.println("because c was removed we duplicated the removed element");
            } else if (c.wasUpdated() || c.wasPermutated()) {
                list.remove(0);
                System.out.println("because c was updated we removed the first element");
            }
        });
        list.addAll(3, 6, 1, 2);
    }
}
