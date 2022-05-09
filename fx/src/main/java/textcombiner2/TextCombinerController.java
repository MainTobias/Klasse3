package textcombiner2;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TextCombinerController implements Initializable {
    @FXML
    private RadioButton text1text2;

    @FXML
    private ToggleGroup text;

    @FXML
    private RadioButton text2text1;

    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    @FXML
    private TextField combined;

    @FXML
    private Button clear;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combined.textProperty().bind(Bindings.createStringBinding(() -> {
            if (text1.getText().isBlank() || text2.getText().isBlank()) {
                return "Both TextFields must be filled.";
            }
            if (text1text2.isSelected()) {
                return text1.getText() + text2.getText();
            } else {
                return text2.getText() + text1.getText();
            }
        }, text1text2.selectedProperty(), text1.textProperty(), text2.textProperty()));
        clear.setOnAction(e -> {
            text1.setText("");
            text2.setText("");
        });
    }
}