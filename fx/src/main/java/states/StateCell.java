package states;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class StateCell extends ListCell<State> {
    private Label flaeche;
    private ImageView wappen;
    private Label hauptstadt;
    private Label einwohner;
    private Label name;
    private boolean init;
    private GridPane root;

    @Override
    protected void updateItem(State s, boolean empty) {
        super.updateItem(s, empty);
        if (empty || s == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (!init) {
                root = new GridPane();
                root.setOnMouseClicked(m -> createDialog(s).ifPresent(s::setHauptstadt));
                ColumnConstraints cc = new ColumnConstraints();
                cc.setHgrow(Priority.SOMETIMES);
                root.getColumnConstraints().addAll(cc, cc, cc, cc, cc);
                flaeche = new Label(String.valueOf(s.getFlaeche()));
                hauptstadt = new Label();
                hauptstadt.textProperty().bind(s.hauptstadtProperty());
                einwohner = new Label(String.valueOf(s.getEinwohner()));
                name = new Label(s.getName());
                wappen = new ImageView(new Image(new File(Objects.requireNonNull(this.getClass().getResource("assets/" + s.getWappen())).getFile()).getAbsolutePath()));
                root.add(name, 0, 0);
                root.add(wappen, 1, 0);
                root.add(hauptstadt, 2, 0);
                root.add(einwohner, 3, 0);
                root.add(flaeche, 4, 0);
                init = true;
                setGraphic(root);
            }
            //setText(item.toString());
        }
    }

    private Optional<String> createDialog(State s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ButtonType t = new ButtonType("Ok", ButtonType.OK.getButtonData());
        alert.getButtonTypes().addAll();
        alert.setTitle(s.getName());
        GridPane content = new GridPane();
        content.add((new ImageView(new Image(new File(Objects.requireNonNull(this.getClass().getResource("assets/" + s.getWappen())).getFile()).getAbsolutePath()))), 0, 0);
        var x = new Label("Landeshauptmann/frau: ");
        x.setStyle("-fx-text-fill: blue");
        content.add(x,1, 0);
        x = new Label("Hauptstadt: ");
        x.setStyle("-fx-text-fill: blue");
        content.add(x,1, 1);
        x = new Label("Sitzverteilung: ");
        x.setStyle("-fx-text-fill: blue");
        content.add(x,1, 2);
        content.add(new Label(s.getLandeshauptmann()), 2, 0);
        TextField tf = new TextField(s.getHauptstadt());
        content.add(tf, 2, 1);
        content.add(new Label(s.getParteien().toString()), 2, 2);
        alert.setGraphic(content);
        return alert.showAndWait().isPresent() ? Optional.of(tf.getText()) : Optional.empty();
    }
}
