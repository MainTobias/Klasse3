/*
 * Copyright (c) 2022 by MainTobias
 */

package textcombiner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends com.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("text-combiner.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Text Combiner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}