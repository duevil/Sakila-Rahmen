package de.softwaretechnik.views;

import java.awt.*;

public class ObserverLabel extends Label implements MovieListener {
    private Type type;

    public ObserverLabel(String text, Type type) {
        this.type = type;
        setText(text);
    }

    public Type type() {
        return this.type;
    }

    @Override
    public void updateContent(String message) {
        switch (this.type) {
            case TITLE -> {
                if (message.contains("(")) {
                    this.setText(message.substring(0, message.indexOf('(') - 1));
                }
                if (!message.contains("(") && message.contains("[")) {
                    this.setText(message.substring(0, message.indexOf('[') - 1));
                }
            }
            case YEAR -> {
                if (message.contains("(")) {
                    this.setText(message.substring(message.indexOf('(') + 1, message.lastIndexOf(')')));
                }
            }
            case LENGTH -> {
                if (message.contains("[")) {
                    this.setText(message.substring(message.indexOf('[') + 1, message.lastIndexOf(']')).concat(" min"));
                }
            }
        }
        System.out.println(message);
        this.revalidate();
    }

    public enum Type {
        TITLE,
        YEAR,
        LENGTH
    }
}
