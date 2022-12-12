package de.softwaretechnik.program;

import de.softwaretechnik.controller.MainWindowController;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.models.Movie;
import de.softwaretechnik.views.MainWindow;

public class Program {
    public static final String APP_TITLE = "Sakila Viewer";
    public static final float APP_V = 0.4F;
    public static final String DB_URL = "jdbc:mysql://localhost/sakila";

    /*TODO:
    Exception in thread "AWT-EventQueue-0" java.lang.IllegalStateException: a failure occurred previously when creating the DBModel instance
	at de.softwaretechnik.models.DBModel.getInstance(DBModel.java:33)
	at de.softwaretechnik.models.QueryExecutor.getMapList(QueryExecutor.java:67)
	at de.softwaretechnik.models.QueryExecutor.execute(QueryExecutor.java:31)
	at de.softwaretechnik.models.QueryInternal.get(QueryInternal.java:13)
	at de.softwaretechnik.views.MainWindow.lambda$filterPane$2(MainWindow.java:103)
    Caused by: java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost/sakila
	at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:706)
	at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:229)
	at de.softwaretechnik.models.DBModel.<init>(DBModel.java:28)
	at de.softwaretechnik.models.DBModel.<clinit>(DBModel.java:17)
    */
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
