package de.softwaretechnik.views.observerElements;

import de.softwaretechnik.views.listeners.LabelListener;

import java.awt.*;

public class ObserverLabel extends Label implements LabelListener {
    private Type type;

    public ObserverLabel(String text, Type type) {
        this.type = type;
        setText(text);
    }

    public ObserverLabel() {}

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

    public enum Type {
        TITLE,
        YEAR,
        LENGTH
    }
}
