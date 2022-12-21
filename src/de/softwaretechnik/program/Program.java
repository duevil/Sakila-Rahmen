package de.softwaretechnik.program;

import de.softwaretechnik.controller.MainWindowController;
import de.softwaretechnik.models.Category;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.views.MainWindow;

import java.util.List;


public class Program {
    public static final String APP_TITLE = "Sakila Viewer";
    public static final float APP_V = 0.10F;
    public static final String DB_URL = "jdbc:mysql://localhost/sakila";

    public static void main(String[] args) {
        getShin();

        // lose Kopplung von GUI und Datenmodel
        /*Model model = Model.getInstance();
        MainWindow mw = MainWindow.getInstance();

        MainWindowController mc = new MainWindowController(mw, model);
        mc.startProgram();*/
    }

    // TODO: method is only an example and needs to be removed
    private static void getShin() {
       List<Category> categories = Model.getInstance().createCategoryQuery().get();
         /*System.out.println("Categories:");
        System.out.println(categories);
        System.out.println("\n/----------------------------------------------------------------/");
        System.out.println("Ohne Filter (mit CategoryQuery): \n");
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
                        *//*.filterYear(1900, 2020)
                        .filterLength(120, Integer.MAX_VALUE)*//*
                        .get().forEach(System.out::println));

        System.out.println("/----------------------------------------------------------------/");
        System.out.println("Mit einem Filter \"Children\" (ohne CategoryQuery): \n");
        Model.getInstance()
                        .createMovieQuery()
                        .withActors()
                        .filterCategories(categories.get(2))
                        .withCategory()
                        .withLength()
                        .withDescription()
                        .withYear()
                        .filterName("shin")
                        *//*.filterYear(1900, 2020)
                        .filterLength(120, Integer.MAX_VALUE)*//*
                        .get().forEach(System.out::println);*/

        System.out.println("/----------------------------------------------------------------/");
        System.out.println("Mit zwei Filtern \"Children\" & \"Comedy\" (ohne CategoryQuery): \n");
        Category[] cats = new Category[] {categories.get(2), categories.get(4)};
        Model.getInstance()
                .createMovieQuery()
                .withActors()
                .filterCategories(cats)
                .withCategory()
                .withLength()
                .withDescription()
                .withYear()
                .filterName("shin")
                /*.filterYear(1900, 2020)
                .filterLength(120, Integer.MAX_VALUE)*/
                .get().forEach(System.out::println);

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
