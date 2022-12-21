package de.softwaretechnik.views.observerelements;

import de.softwaretechnik.views.listeners.LabelListener;
import de.softwaretechnik.views.listeners.TextAreaListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an observable {@link java.awt.Label Label} according to the observer pattern.
 * @author Elisa Johanna Woelk (m30192)
 */
public class ObservableLabel extends Label {
    /**
     * The {@link ArrayList} containing all observing {@link LabelListener}s
     */
    private final List<LabelListener> labelListeners = new ArrayList<>();

    /**
     * The {@link ArrayList} containing all observing {@link TextAreaListener}s
     */
    private final List<TextAreaListener> textAreaListeners = new ArrayList<>();

    /**
     * The constructor with the option to set the text of the label
     */
    public ObservableLabel(String text) {
        setText(text);
    }

    /**
     * The standard constructor
     */
    public ObservableLabel() {}

    /**
     * Adds a {@link LabelListener} to this element by adding it to the {@link #labelListeners list}
     * @param toAdd The newly observing {@link LabelListener}
     */
    public void addLabelListener(LabelListener toAdd) {
        labelListeners.add(toAdd);
    }

    /**
     * Adds a {@link TextAreaListener} to this element by adding it to the {@link #textAreaListeners list}
     * @param toAdd The newly observing {@link TextAreaListener}
     */
    public void addTextFieldListener(TextAreaListener toAdd) {
        textAreaListeners.add(toAdd);
    }

    /**
     * Updates all observing {@link LabelListener}s with the text content of the label and all observing
     * {@link TextAreaListener}s with the name of the label
     */
    public void updateAll() {
        for (LabelListener ll : labelListeners) {
            ll.updateContent(this.getText());
        }
        for (TextAreaListener tl : textAreaListeners) {
            tl.updateContent(this.getName());
        }
    }
}
