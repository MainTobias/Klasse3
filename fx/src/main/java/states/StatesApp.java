package states;

import com.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StatesApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("states.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("States");
        stage.setScene(scene);
        stage.show();
    }
}
