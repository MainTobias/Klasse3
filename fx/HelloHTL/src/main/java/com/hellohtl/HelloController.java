package com.hellohtl;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private StackPane root;

    @FXML
    private void rotate() {
        RotateTransition r = new RotateTransition();
        r.setByAngle(180);
        r.setDuration(Duration.seconds(2));
        r.setAutoReverse(true);
        r.setCycleCount(2);
        r.setNode(root);
        r.play();
    }
}