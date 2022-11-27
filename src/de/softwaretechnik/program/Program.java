package de.softwaretechnik.program;

import de.softwaretechnik.controller.MainWindowController;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.models.Movie;
import de.softwaretechnik.views.MainWindow;

public class Program {
    public static final String APP_TITLE = "Sakila Viewer";
    public static final float APP_V = 0.2F;
    public static final String DB_URL = "jdbc:mysql://localhost/sakila";

    public static void main(String[] args) {

        // lose Kopplung von GUI und Datenmodel
        Model model = Model.getInstance();
        MainWindow mw = MainWindow.getInstance();

        MainWindowController mc = new MainWindowController(mw, model);
        mc.startProgram();
    }

    @SuppressWarnings("unused") // TODO: method is only an example and needs to be removed
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
                        .get().forEach((Movie m) -> {
                            System.out.println(m);
                            if (m.description() != null) System.out.println(m.description());
                            if (m.category() != null) System.out.println(m.category());
                            if (m.actors() != null) m.actors().forEach(System.out::println);
                            System.out.println();
                        }));
    }
}
