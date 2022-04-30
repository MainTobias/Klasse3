package bindings;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import static com.Utils.getPath;

public class CustomBinding extends Application {
    @FXML
    Rectangle rect;

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
    public void initialize() throws Exception {
        super.init();
        rect.xProperty().bind(((AnchorPane) rect.getParent()).widthProperty().divide(4));
        rect.yProperty().bind(((AnchorPane) rect.getParent()).heightProperty().divide(4));
        rect.widthProperty().bind(((AnchorPane) rect.getParent()).widthProperty().divide(2));
        rect.heightProperty().bind(((AnchorPane) rect.getParent()).heightProperty().divide(2));
        rect.fillProperty().bind(Bindings.createObjectBinding(() ->
                Color.color(reduce(rect.widthProperty().get()), reduce(rect.widthProperty().multiply(rect.heightProperty()).get()), reduce(rect.heightProperty().get())), rect.widthProperty(), rect.heightProperty()));
    }

    private double reduce(double d) {
        while (d > 1) {
            d = d / 10;
        }
        while (d < 0) {
            d = d * 10;
        }
        return d;
    }
}
