package states;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class StateCell extends Cell<State> {
    @FXML
    private Label flaeche;

    @FXML
    private ImageView wappen;

    @FXML
    private Label hauptstadt;

    @FXML
    private Label einwohner;

    @FXML
    private Label name;

    private boolean init;

    @Override
    protected void updateItem(State item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (!init) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("state-cell.fxml"));
                try {
                    Parent p = fxmlLoader.load();
                    setGraphic(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            setText(item.toString());
        }
    }
}
