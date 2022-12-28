package de.softwaretechnik.program;

import de.softwaretechnik.controller.MainWindowController;
import de.softwaretechnik.models.Category;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.views.MainWindow;

import java.util.List;


public class Program {
    public static final String APP_TITLE = "Sakila Viewer";
    public static final float APP_V = 0.11F;
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
       List<Category> categories = Model.getInstance().createCategoryQuery().get();

        /*Model.getInstance()
                .createMovieQuery()
                .withActors()
                .filterCategories(new Category[]{categories.get(1), categories.get(7)})
                .withCategory()
                .withLength()
                .withDescription()
                .filterName("Shin")
                .withYear()
                .get().forEach(movie -> {
            System.out.println();
            System.out.println(movie.toString());
            System.out.println(movie.category());
        });*/
        /*Model.getInstance().
                createCategoryQuery()
                .get()
                .forEach(c -> Model.getInstance()
                        .createMovieQuery()
                        .withActors()
                        .filterCategories(new Category[]{categories.get(1), categories.get(7)})
                        .withCategory()
                        .withLength()
                        .withDescription()
                        .filterName("Shin")
                        .withYear()
                        .get().forEach(movie -> {
                            System.out.println();
                            System.out.println(movie.toString());
                            System.out.println(movie.category());
                        }));
*/
    }
}
