package de.softwaretechnik.views.observerelements;

import de.softwaretechnik.views.listeners.LabelListener;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * All of these attributes will be extracted from the text content of the clicked label if present. <br>
     * The parent container is then {@link Container#revalidate() revalidated}.
     * @param movie The selected movie
     */
    @Override
    public void updateContent(String movie) {
        Pattern pattern;

       switch (this.type) {
            case TITLE -> {
                pattern = Pattern.compile("^([A-Za-z\\s]+\\b)");
                Matcher title = pattern.matcher(movie);
                if (title.find()) this.setText(title.group(1));
            }
            case YEAR -> {
                pattern = Pattern.compile("(\\([\\d]{4}\\))");
                Matcher year = pattern.matcher(movie);
                if (year.find()) this.setText(year.group(1).replace("(", "").replace(")", ""));
            }
            case LENGTH -> {
                pattern = Pattern.compile("(\\[[\\d]+])");
                Matcher length = pattern.matcher(movie);
                if (length.find()) this.setText(length.group(1).concat(" mins").replace("[", "").replace("]", ""));
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
