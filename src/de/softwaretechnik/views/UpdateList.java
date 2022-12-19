package de.softwaretechnik.views;

import de.softwaretechnik.models.Movie;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class UpdateList extends SwingWorker<Boolean, Integer> {
    List<Movie> movieList;
    public UpdateList(List<Movie> movies) {
        movieList = movies;
    }
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * <p>
     * Note that this method is executed only once.
     *
     * <p>
     * Note: this method is executed in a background thread.
     *
     * @return the computed result
     */
    @Override
    protected Boolean doInBackground() {
        for (Movie movie : movieList) {
            newProgress(movie.toString());
        }
        return true;
    }

    public void newProgress(String movie) {
        firePropertyChange("movies", 0, movie);
    }
}
