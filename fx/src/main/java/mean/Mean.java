package mean;

import com.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static com.Utils.getPath;

public class Mean extends Application implements Initializable {
    @FXML
    private TextField numberInput;
    @FXML
    private Slider precision;
    @FXML
    private ListView<Double> numbers;
    @FXML
    private CheckBox arithmetic;
    @FXML
    private CheckBox geometric;
    @FXML
    private CheckBox harmonic;
    @FXML
    private Label arithmeticLabel;
    @FXML
    private Label geometricLabel;
    @FXML
    private Label harmonicLabel;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getPath(this.getClass()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Logic
        numberInput.setOnAction(e -> add());
        StringBinding formatString = Bindings.createStringBinding(() -> "%." + ((int) precision.valueProperty().get()) + "f", precision.valueProperty());

        // Should only be visible
        arithmeticLabel.visibleProperty().bind(arithmetic.selectedProperty());
        geometricLabel.visibleProperty().bind(geometric.selectedProperty());
        harmonicLabel.visibleProperty().bind(harmonic.selectedProperty());

        // Calculate the means
        arithmeticLabel.textProperty().bind(new SimpleStringProperty("Arithmetisches Mittel:\t").concat(Bindings.createStringBinding(() -> String.format(formatString.get(), numbers.getItems().stream().mapToDouble(x -> x).average().orElse(Double.NaN)), numbers.getItems(), formatString)));
        geometricLabel.textProperty().bind(new SimpleStringProperty("Geometrisches Mittel:\t").concat(Bindings.createStringBinding(() -> numbers.getItems().stream().anyMatch(x -> x <= 0) ? "Liste darf nur positive Werte enthalten" : String.format(formatString.get(), Math.pow(numbers.getItems().stream().mapToDouble(x -> x).reduce(1, (a, b) -> a * b), 1d / numbers.getItems().size())), numbers.getItems(), formatString)));
        harmonicLabel.textProperty().bind(new SimpleStringProperty("Harmonisches Mittel:\t").concat(Bindings.createStringBinding(() -> String.format(formatString.get(), numbers.getItems().contains(0d) ? 0 : numbers.getItems().size() / numbers.getItems().stream().mapToDouble(x -> 1 / x).sum()), numbers.getItems(), formatString)));


    }

    @FXML
    private void add() {
        String s = numberInput.getText();
        boolean isNumber = true;
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException numberFormatException) {
            isNumber = false;
        }
        if (isNumber) {
            numbers.getItems().add(Double.parseDouble(s));
            Collections.sort(numbers.getItems());
            numbers.refresh();
            numberInput.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegaler Wert");
            alert.setHeaderText("Fehler");
            alert.setContentText("Liste kann nur Zahlen enhalten.");
            alert.showAndWait();
        }
    }

    @FXML
    private void remove() {
        if (numbers.getSelectionModel().getSelectedIndex() >= 0)
            numbers.getItems().remove(numbers.getSelectionModel().getSelectedIndex());
        numbers.refresh();
    }

    @FXML
    private void clear() {
        numbers.getItems().clear();
        numbers.refresh();
    }

}
