package de.softwaretechnik.views.observerElements;

import de.softwaretechnik.views.listeners.LabelListener;
import de.softwaretechnik.views.listeners.TextAreaListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ObservableLabel extends Label {
    private List<LabelListener> labelListeners = new ArrayList<>();
    private List<TextAreaListener> textAreaListeners = new ArrayList<>();
    public ObservableLabel(String text) {
        setText(text);
    }

    public void addLabelListener(LabelListener toAdd) {
        labelListeners.add(toAdd);
    }

    public void addTextFieldListener(TextAreaListener toAdd) {
        textAreaListeners.add(toAdd);
    }

    public void updateAll() {
        for (LabelListener ll : labelListeners) {
            ll.updateContent(this.getText());
        }
        for (TextAreaListener tl : textAreaListeners) {
            tl.updateContent(this.getName());
        }
    }
}
