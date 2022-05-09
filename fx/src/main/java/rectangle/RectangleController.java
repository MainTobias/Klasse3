package rectangle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RectangleController implements Initializable {
    @FXML
    private Text area;

    @FXML
    private ChoiceBox<Color> color;

    @FXML
    private Slider height;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Slider width;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        color.getItems().addAll(List.of(new Color[]{Color.valueOf("#9cce2b"), Color.valueOf("#987654"), Color.valueOf("#123456")}));
        rectangle.widthProperty().bind(width.valueProperty());
        rectangle.heightProperty().bind(height.valueProperty());
        area.textProperty().bind(Bindings.format("%.0fE²", width.valueProperty().multiply(height.valueProperty())));
        rectangle.fillProperty().bind(color.valueProperty());
    }
}