package rectangle;

import com.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.Utils.getPath;

public class RectangleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getPath(this.getClass()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rectangle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}