package com.swapper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private Text message;
    @FXML
    private VBox messageBox;

    private void toast(String toast) {
        message.setText(toast);
        messageBox.setVisible(true);
    }

    @FXML
    private void swap() {
        String s1 = text1.getText();
        String s2 = text2.getText();

        if (s1.isEmpty() || s2.isEmpty()) {
            toast("Both TextFields must be filled.");
            return;
        }

        clear();
        text1.setText(s2);
        text2.setText(s1);
    }

    @FXML
    private void clear() {
        messageBox.setVisible(false);
        text1.setText("");
        text2.setText("");
    }
}