package ru.bogdanov.view.gui;

import javax.swing.*;

public class IntegerLabel extends JLabel {

    public Integer getValue() {
        String text = getText();
        return Integer.parseInt(text);
    }

    public void setValue(Integer value) {
        setText(String.valueOf(value));
    }

    public void increaseValue() {
        Integer value = getValue() + 1;
        setValue(value);
    }

}
