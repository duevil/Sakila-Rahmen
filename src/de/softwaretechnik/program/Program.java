package de.softwaretechnik.program;

import de.softwaretechnik.controller.MainWindowController;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.models.Movie;
import de.softwaretechnik.views.MainWindow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Program {
    public static final String APP_TITLE = "Sakila Viewer";
    public static final float APP_V = 0.8F;
    public static final String DB_URL = "jdbc:mysql://localhost/sakila";

    public static void main(String[] args) {
        // lose Kopplung von GUI und Datenmodel
        Model model = Model.getInstance();
        MainWindow mw = MainWindow.getInstance();

        MainWindowController mc = new MainWindowController(mw, model);
        mc.startProgram();
    }

    // TODO: method is only an example and needs to be removed
    private static void getShin() {
        Model.getInstance().
                createCategoryQuery()
                .get()
                .forEach(c -> Model.getInstance()
                        .createMovieQuery()
                        .withActors()
                        .filterCategories(c)
                        .withCategory()
                        .withLength()
                        .withDescription()
                        .withYear()
                        .filterName("shin")
                        .filterYear(1900, 2020)
                        .filterLength(120, Integer.MAX_VALUE)
                        .get());
    }
}
