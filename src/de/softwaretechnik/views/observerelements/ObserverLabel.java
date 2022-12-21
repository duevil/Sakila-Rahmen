package de.softwaretechnik.views.observerelements;

import de.softwaretechnik.views.listeners.LabelListener;

import java.awt.*;

/**
 * Creates an observing {@link java.awt.Label Label} according to the observer pattern.
 * @author Elisa Johanna Woelk (m30192)
 */
public class ObserverLabel extends Label implements LabelListener {

    /**
     * Determines, of which {@link Type type} the label is
     */
    private final Type type;

    /**
     * The constructor setting the text of the label and it's {@link Type type}
     * @param text The text content of the label
     * @param type The {@link Type type} of the label
     */
    public ObserverLabel(String text, Type type) {
        this.type = type;
        setText(text);
    }

    /**
     * Updates the label according to its type with the respective information. <br>
     * If the label is of the type {@link Type#TITLE Title}, it is updated to the title of the movie.<br>
     * If the label is of the type {@link Type#YEAR Year}, it is updated to the release year of the movie.<br>
     * If the label is of the type {@link Type#LENGTH Length}, it is updated to the length of the movie.<br>
     * All of these attributes will be extracted from the text content of the clicked label if necessary. <br>
     * The parent container is then {@link Container#revalidate() revalidated}.
     * @param movie The selected movie
     */
    @Override
    public void updateContent(String movie) {
        switch (this.type) {
            case TITLE -> {
                if (movie.contains("(")) {
                    this.setText(movie.substring(0, movie.indexOf('(') - 1));
                }
                if (!movie.contains("(") && movie.contains("[")) {
                    this.setText(movie.substring(0, movie.indexOf('[') - 1));
                }
                else {
                    this.setText(movie);
                }
            }
            case YEAR -> {
                if (movie.contains("(")) {
                    this.setText(movie.substring(movie.indexOf('(') + 1, movie.lastIndexOf(')')));
                }
            }
            case LENGTH -> {
                if (movie.contains("[")) {
                    this.setText(movie.substring(movie.indexOf('[') + 1, movie.lastIndexOf(']')).concat(" min"));
                }
            }
        }
        this.revalidate();
    }

    /**
     * An enum used to determine the function of the label.
     */
    public enum Type {
        /**
         * The label displays the title of the movie
         */
        TITLE,

        /**
         * The label displays the release year of the movie
         */
        YEAR,

        /**
         * The label displays the length of the movie
         */
        LENGTH
    }
}
