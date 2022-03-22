/*
 * Copyright (c) 2022 by MainTobias
 */

package com.textcombiner;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private RadioButton t1;
    @FXML
    private RadioButton t2;
    @FXML
    private ToggleGroup text;
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private TextField result;

    @FXML
    private void clear() {
        text1.clear();
        text2.clear();
        result.clear();
        t1.setSelected(true);
    }

    @FXML
    private void apply() {
        RadioButton t = (RadioButton) text.getSelectedToggle();
        if (t == null) {
            result.setText("Select a RadioButton");
            return;
        }
        String s1 = text1.getText();
        String s2 = text2.getText();
        if (s1.isEmpty() || s2.isEmpty()) {
            result.setText("Both TextFields must be filled.");
            return;
        }
        if (t.equals(t1)) {
            result.setText(s1 + s2);
        }
        if (t.equals(t2)) {
            result.setText(s1 + s1);
        }
    }

}