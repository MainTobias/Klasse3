package chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static int SIZE = 60;

    private static Color getColor(int column, int row) {
        if (row % 2 == 0) {
            if (column % 2 == 0) {
                return Color.WHITE;
            } else {
                return Color.BLACK;
            }
        } else {
            if (column % 2 == 0) {
                return Color.BLACK;
            } else {
                return Color.WHITE;
            }
        }
    }

    @Override
    public void start(Stage stage) {

        GridPane root = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle();
                r.setWidth(SIZE);
                r.setHeight(SIZE);
                r.setFill(getColor(i, j));
                root.add(r, i, j);
            }
        }

        Scene scene = new Scene(root);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getTarget() instanceof Rectangle r) {
                if (r.getFill().equals(Color.RED)) {
                    r.setFill(getColor((int) Math.floor(e.getY() / SIZE), (int) Math.floor(e.getX() / SIZE)));
                } else {
                    r.setFill(Color.RED);
                }
            }
        });
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}