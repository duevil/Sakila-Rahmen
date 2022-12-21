package de.softwaretechnik.views.listeners;

/**
 * Creates a Listener specifically for the {@link java.awt.Label Label}s displaying the movie's title, release year
 * and length.
 * @author Elisa Johanna Woelk (m30192)
 */
public interface LabelListener {
    /**
     * Method to update the content of the {@link java.awt.Label Label}s to the movie's movie's title, release year
     * and length.
     * @param movie The selected movie
     */
    void updateContent(String movie);
}
