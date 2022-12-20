package de.softwaretechnik.views.listeners;

import de.softwaretechnik.views.observerElements.ObservableLabel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static de.softwaretechnik.views.MainWindow.getLast;
import static de.softwaretechnik.views.MainWindow.setLast;


public class ClickListener extends MouseAdapter {
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (getLast() != null) getLast().setBackground(new Color(95, 95, 95));
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

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
