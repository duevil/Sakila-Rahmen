package de.softwaretechnik.views.listeners;

/**
 * Creates a Listener specifically for the {@link java.awt.TextArea TextArea} displaying the movie's description.
 * @author Elisa Johanna Woelk (m30192)
 */
public interface TextAreaListener {
    /**
     * Method to update the content of the {@link java.awt.TextArea TextArea} to the movie's description
     * @param description The movie's description
     */
    void updateContent(String description);
}
