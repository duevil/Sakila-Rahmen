package de.softwaretechnik.views.observerElements;

import de.softwaretechnik.views.listeners.TextAreaListener;

import java.awt.*;

public class ObserverTextArea extends TextArea implements TextAreaListener {

    public ObserverTextArea() {}

    public ObserverTextArea(String text, int rows, int cols, int scrollbarPolicy) {
        super(text, rows, cols, scrollbarPolicy);
    }
    @Override
    public void updateContent(String description) {
        setText(description);
    }
}
