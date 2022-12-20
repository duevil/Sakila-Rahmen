package de.softwaretechnik.views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ObservableLabel extends Label {
    private List<MovieListener> listeners = new ArrayList<>();
    public ObservableLabel(String text) {
        setText(text);
    }

    public void addListener(MovieListener toAdd) {
        listeners.add(toAdd);
    }

    public void updateAll() {
        for (MovieListener hl : listeners) {
            hl.updateContent(this.getText());
        }
    }
}
