package states;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

public class Controller implements Initializable {
    @FXML
    private ListView<State> states;

    private Runnable onInitialize;
    public void setOnInitialize(Runnable onInitialize) {
        this.onInitialize = onInitialize;
    }

    public void setStates(List<State> s) {
        states.setItems(s instanceof ObservableList<State> ? (ObservableList<State>) s : FXCollections.observableList(s));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onInitialize.run();
        states.setCellFactory((a) -> new StateCell());
    }
}
