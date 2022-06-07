package states;

import com.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class StatesApp extends Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("states.fxml"));
        Controller c = new Controller();
        fxmlLoader.setController(c);
        c.setOnInitialize(() -> {
            try {
                c.setStates(State.readFile(this.getClass().getResourceAsStream("bundesländer.csv")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("States");
        stage.setScene(scene);
        stage.show();
    }
}
