package de.softwaretechnik.views.observerelements;

import de.softwaretechnik.views.listeners.TextAreaListener;

import java.awt.*;

/**
 * Creates an observing {@link java.awt.TextArea TextArea} according to the observer pattern.
 * @author Elisa Johanna Woelk (m30192)
 */
public class ObserverTextArea extends TextArea implements TextAreaListener {

    /**
     * The standard constructor
     */
    public ObserverTextArea() {}

    /**
     * The constructor allowing for the specification of the text content, amount of rows, columns and the scrollbar
     * policy
     * @param text The text content of the TextArea
     * @param rows The amount of rows of the TextArea
     * @param cols The amount of columns of the TextArea
     * @param scrollbarPolicy The scrollbar policy of the TextArea
     */
    public ObserverTextArea(String text, int rows, int cols, int scrollbarPolicy) {
        super(text, rows, cols, scrollbarPolicy);
    }

    /**
     * Updates the TextArea by setting its text content to the description of the movie, passed by the
     * {@link ObservableLabel}
     * @param description The movie's description
     */
    @Override
    public void updateContent(String description) {
        setText(description);
    }
}
