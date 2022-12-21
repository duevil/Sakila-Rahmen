package de.softwaretechnik.views.listeners;

import de.softwaretechnik.views.observerelements.ObservableLabel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static de.softwaretechnik.views.MainWindow.getLast;
import static de.softwaretechnik.views.MainWindow.setLast;

/**
 * Creates a Listener to handle a mouse click on a movie and display the respective information by using the Observer
 * Pattern.
 * @author Elisa Johanna Woelk (m30192)
 */
public class ClickListener extends MouseAdapter {
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component. <br><br>
     *
     * Updates the elements displaying title, length, year and description to display information about the clicked
     * movie. <br>
     * If the last clicked element is not <code>null</code>, sets its background color to the default color of movies in
     * the list. <br>
     * Gets the clicked {@link ObservableLabel}, stores it as the last clicked element and
     * {@link ObservableLabel#updateAll() updates} all observing Elements before highlighting the clicked element and
     * {@link Container#revalidate() revalidating} the parent container.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (getLast() != null) getLast().setBackground(new Color(90, 90, 90));
        ObservableLabel c = (ObservableLabel) e.getSource();
        setLast(c);
        c.updateAll();
        c.setBackground(new Color(105, 105, 105));
        c.getParent().revalidate();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Not required for the class to function
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Not required for the class to function
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Not required for the class to function
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Not required for the class to function
    }
}
