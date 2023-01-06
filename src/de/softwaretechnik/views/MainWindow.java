package de.softwaretechnik.views;

import de.softwaretechnik.models.Category;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.models.Movie;
import de.softwaretechnik.models.MovieQuery;
import de.softwaretechnik.program.Program;
import de.softwaretechnik.views.listeners.ClickListener;
import de.softwaretechnik.views.observerelements.ObservableLabel;
import de.softwaretechnik.views.observerelements.ObserverLabel;
import de.softwaretechnik.views.observerelements.ObserverTextArea;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Sets up the GUI with all necessary components and functions
 *
 * @author Elisa Johanna Woelk (m30192)
 */
public class MainWindow extends Frame {

    /**
     * The MainWindow instance used for setting up the GUI according to the Singleton Pattern
     */
    private static final MainWindow window = new MainWindow();

    /**
     * The font family used in the GUI
     */
    private static final String VERDANA = "Verdana";

    /**
     * The {@link Logger} used for this class
     */
    private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getName());

    /**
     * A {@link Label} storing the last clicked label when browsing the list of movies
     */
    private static Label last;

    /**
     * The screen width of the users display used for dynamic layout
     */
    private final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    /**
     * The screen height of the users display used for dynamic layout
     */
    private final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    /**
     * A color used for the GUI's text
     */
    private final Color white = new Color(246, 246, 246);

    /**
     * A {@link List} storing the available {@link Category categories}
     */
    private final List<Category> categories;

    /**
     * The private constructor for this class according to the Singleton Pattern <br>
     * Fills {@link #categories} with all {@link Category categories} and sets up the GUI
     */
    private MainWindow() {
        categories = Model.getInstance().createCategoryQuery().get();
        setBackground(new Color(47, 47, 47));
        setTitle(Program.APP_TITLE + " [" + Program.APP_V + "]");
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        createGUI();
    }

    /**
     * The factory method for getting the {@link #window instance} according to the Singleton Pattern
     *
     * @return A {@link MainWindow} object: The instance of this class
     */
    public static MainWindow getInstance() {
        return window;
    }

    /**
     * A Getter for {@link #last}
     *
     * @return A {@link Label}: {@link #last}
     */
    public static Label getLast() {
        return last;
    }

    /**
     * A Setter for {@link #last}
     */
    public static void setLast(Label last) {
        MainWindow.last = last;
    }

    /**
     * Adds all elements to the GUI
     */
    public void createGUI() {
        add(addItems());
    }

    /**
     * Sets up the GUI via a {@link GridBagLayout} to allow the components to access each other as using several panels
     * makes this very difficult or impossible
     *
     * @return A {@link Panel} with all the elements in the GUI
     */
    private Panel addItems() {
        int marginFive = 5;
        Color grey = new Color(85, 85, 85);

        //The ObserverLabel for the title of the currently selected movie
        ObserverLabel title = new ObserverLabel("", ObserverLabel.Type.TITLE);
        title.setAlignment(Label.CENTER);
        title.setPreferredSize(new Dimension((int) ((int) (screenWidth / 10) * 1.5), 40));
        title.setFont(new Font(VERDANA, Font.BOLD, 20));
        title.setForeground(white);
        title.setBackground(grey);

        //Panel with GridBagLayout
        Panel pane = new Panel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        //Label with the Title of the GUI
        Label l1 = new Label("Movie Database", Label.CENTER);
        l1.setForeground(white);
        l1.setFont(new Font(VERDANA, Font.BOLD, 80));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        c.gridheight = 5;
        c.insets = new Insets(30, 10, 10, 10);
        pane.add(l1, c);

        //Scrollable middle panel with movies according to current filter
        ScrollPane mContent = new ScrollPane();
        Panel area = new Panel();
        area.setLayout(new BoxLayout(area, BoxLayout.PAGE_AXIS));
        mContent.setPreferredSize(new Dimension(1100, (int) (screenHeight - l1.getHeight() - 250)));
        mContent.setMinimumSize(new Dimension(1100, (int) (screenHeight - l1.getHeight() - 250)));
        area.setFocusable(false);
        area.setFont(new Font(VERDANA, Font.PLAIN, 14));
        area.setForeground(white);
        area.setBackground(grey);
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 40;
        mContent.add(area);
        pane.add(mContent, c);

        //Label and TextField for the text search
        Label searchTitle = createHeader("Search:");
        searchTitle.setPreferredSize(new Dimension((int) ((int) (screenWidth / 10) * 1.5), 40));
        c.gridx = 0;
        c.gridy = 5;
        c.weighty = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.insets = new Insets(10, marginFive, 0, marginFive);
        pane.add(searchTitle, c);

        TextField search = new TextField();
        search.setFont(new Font(VERDANA, Font.PLAIN, 20));
        c.gridy = 6;
        c.insets = new Insets(5, marginFive, 0, marginFive);
        pane.add(search, c);

        //Label and TextField for the category filter
        Label genreTitle = createHeader("Genre:");
        c.gridy = 7;
        c.insets = new Insets(20, marginFive, 0, marginFive);
        pane.add(genreTitle, c);

        c.insets = new Insets(10, marginFive, 0, marginFive);
        for (int i = 0; i < categories.size(); i++) {
            c.gridy = 8 + i;
            Checkbox checkbox = createCheckbox(categories.get(i).toString());
            pane.add(checkbox, c);
        }

        //Labels and TextFields for the duration filter
        Label durationTitle = createHeader("Duration:");
        c.gridy = 24;
        c.insets = new Insets(20, marginFive, 0, marginFive);
        pane.add(durationTitle, c);

        Label minLenLabel = createLabel("Min:");
        c.gridy = 25;
        c.gridwidth = 1;
        c.insets = new Insets(marginFive, 30, 0, marginFive);
        pane.add(minLenLabel, c);

        TextField minLength = new TextField();
        c.gridx = 1;
        c.insets = new Insets(marginFive, 0, 0, 10);
        pane.add(minLength, c);

        Label maxLenLabel = createLabel("Max:");
        c.gridx = 2;
        c.insets = new Insets(marginFive, 30, 0, marginFive);
        pane.add(maxLenLabel, c);

        TextField maxLength = new TextField();
        c.gridx = 3;
        c.insets = new Insets(marginFive, 0, 0, 10);
        pane.add(maxLength, c);

        //Labels and TextFields for the release year filter
        Label releaseTitle = createHeader("Release year:");
        c.gridy = 26;
        c.gridx = 0;
        c.gridwidth = 4;
        c.insets = new Insets(20, marginFive, 0, marginFive);
        pane.add(releaseTitle, c);

        Label from = createLabel("From:");
        c.gridy = 27;
        c.gridwidth = 1;
        c.insets = new Insets(marginFive, 30, 0, marginFive);
        pane.add(from, c);

        TextField fromYear = new TextField();
        c.gridx = 1;
        c.insets = new Insets(marginFive, 0, 0, 10);
        pane.add(fromYear, c);

        Label to = createLabel("To:");
        c.gridx = 2;
        c.insets = new Insets(marginFive, 30, 0, marginFive);
        pane.add(to, c);

        TextField toYear = new TextField();
        c.gridx = 3;
        c.insets = new Insets(marginFive, 0, 0, 10);
        pane.add(toYear, c);

        //Button to apply the filters
        Button submit = new Button("Filter");
        submit.setBackground(grey);
        submit.setForeground(white);
        c.ipady = 15;
        c.ipadx = 10;
        c.gridy = 28;
        c.gridx = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(25, 45, 20, 45);
        pane.add(submit, c);

        //Button to remove the filters
        Button remove = new Button("Clear");
        remove.setBackground(grey);
        remove.setForeground(white);
        c.gridx = 2;
        pane.add(remove, c);

        //Adding the Label displaying the title of the currently selected movie
        c.ipady = 0;
        c.ipadx = 0;
        c.weighty = 0;
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(10, marginFive, 0, marginFive);
        pane.add(title, c);

        //Adding the ObserverLabel displaying the release year of the currently selected movie
        ObserverLabel yearLabel = new ObserverLabel("", ObserverLabel.Type.YEAR);
        yearLabel.setForeground(white);
        yearLabel.setFont(new Font(VERDANA, Font.PLAIN, 20));
        yearLabel.setBackground(grey);
        yearLabel.setAlignment(Label.CENTER);
        c.gridy = 6;
        c.insets = new Insets(marginFive, marginFive, 0, marginFive);
        pane.add(yearLabel, c);

        //Adding the ObserverLabel displaying the duration of the currently selected movie
        ObserverLabel lengthLabel = new ObserverLabel("", ObserverLabel.Type.LENGTH);
        lengthLabel.setForeground(white);
        lengthLabel.setFont(new Font(VERDANA, Font.ITALIC, 16));
        lengthLabel.setBackground(grey);
        lengthLabel.setAlignment(Label.CENTER);
        c.gridy = 7;
        pane.add(lengthLabel, c);

        //Adding the ObserverTextArea displaying the description of the currently selected movie
        ObserverTextArea details = new ObserverTextArea("", 1, 1, TextArea.SCROLLBARS_VERTICAL_ONLY);
        details.setEditable(false);
        details.setFocusable(false);
        details.setFont(new Font(VERDANA, Font.ITALIC, 16));
        details.setForeground(white);
        details.setBackground(grey);
        c.gridy = 8;
        c.gridheight = 20;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.insets = new Insets(10, marginFive, marginFive, marginFive);
        pane.add(details, c);

        //Adding the Checkbox for toggling the release year display
        Checkbox showYear = new Checkbox("Show release year", true);
        showYear.setFont(new Font(VERDANA, Font.PLAIN, 11));
        showYear.setForeground(white);
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 28;
        c.gridx = 5;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0;
        pane.add(showYear, c);

        //Adding the Checkbox for toggling the duration display
        Checkbox showLength = new Checkbox("Show duration", true);
        showLength.setFont(new Font(VERDANA, Font.PLAIN, 11));
        showLength.setForeground(white);
        c.gridx = 6;
        pane.add(showLength, c);

        //Adding an ActionListener to the button applying the filters
        submit.addActionListener(e -> {
            MovieQuery query = filter(search, pane, minLength, maxLength, fromYear, toYear);
            HashMap<String, String> movieQuery = new HashMap<>();
            query = getQuery(query, showYear, showLength);
            query.withDescription().get().forEach((Movie m) -> movieQuery.put(m.toString(), m.description()));
            area.removeAll();
            addAll(movieQuery, title, yearLabel, lengthLabel, details, area);
        });

        //Adding an ActionListener to the button removing the filters
        remove.addActionListener(e -> {
            HashMap<String, String> movieMap;
            area.removeAll();
            search.setText("");
            minLength.setText("");
            maxLength.setText("");
            fromYear.setText("");
            toYear.setText("");
            for (int i = 0; i < categories.size(); i++) {
                if (pane.getComponent(5 + i) instanceof Checkbox checkbox) {
                    checkbox.setState(false);
                }
            }
            movieMap = determineQuery(showYear, showLength);
            addAll(movieMap, title, yearLabel, lengthLabel, details, area);
        });

        //Adding a TextListener to the text search bar
        search.addTextListener(e -> {
            if (search.getText().length() >= 4) {
                MovieQuery query = filter(search, pane, minLength, maxLength, fromYear, toYear);
                HashMap<String, String> movieQuery = new HashMap<>();
                query = getQuery(query, showYear, showLength);
                query.withDescription().get().forEach((Movie m) -> movieQuery.put(m.toString(), m.description()));
                area.removeAll();
                addAll(movieQuery, title, yearLabel, lengthLabel, details, area);
            }
        });

        //Adding all movies without filtering them on startup
        addAll(determineQuery(showYear, showLength), title, yearLabel, lengthLabel, details, area);
        return pane;
    }

    /**
     * Filters the movies according to the parameters specified
     *
     * @param search    The {@link TextField} used for text search
     * @param pane      The {@link Panel} containing all filter components
     * @param minLength The {@link TextField} used for setting a minimum duration
     * @param maxLength The {@link TextField} used for setting a maximum duration
     * @param fromYear  The {@link TextField} used for setting a minimum release year
     * @param toYear    The {@link TextField} used for setting a maximum release year
     * @return A {@link MovieQuery} according to the set filters
     */
    private MovieQuery filter(TextField search,
                              Panel pane,
                              TextField minLength,
                              TextField maxLength,
                              TextField fromYear,
                              TextField toYear) {
        MovieQuery query = Model.getInstance().createMovieQuery(); //MovieQuery that is extended according to filters
        List<Category> catFilter = new LinkedList<>(); //List storing categories to be filtered for
        int minLen = -1; //Minimum length
        int maxLen = -1; //Maximum length
        int fromY = -1; //Minimum release year
        int toY = -1; //Maximum release year

        //Get text search content
        if (!search.getText().isBlank()) {
            query = query.filterName(search.getText());
        }
        //Get selected categories
        for (int i = 0; i < categories.size(); i++) {
            if (pane.getComponent(5 + i) instanceof Checkbox checkbox && checkbox.getState()) {
                catFilter.add(categories.get(i));
            }
        }
        //If categories were selected
        if (!catFilter.isEmpty()) {
            //If only one category was selected
            if (catFilter.size() == 1) {
                query = query.filterCategories(catFilter.get(0));
            }
            else {
                query = query.filterCategories(catFilter.toArray(new Category[0]));
            }
        }
        //If a minimum duration was entered, try to parse the input
        if (!minLength.getText().isBlank()) {
            try {
                minLen = Integer.parseInt(minLength.getText());
            }
            catch (NumberFormatException nfe) {
                LOGGER.severe("Not a valid minimum length");
            }
        }
        else {
            minLen = 0;
        }
        //If a maximum duration was entered, try to parse the input
        if (!maxLength.getText().isBlank()) {
            try {
                maxLen = Integer.parseInt(maxLength.getText());
            }
            catch (NumberFormatException nfe) {
                LOGGER.severe("Not a valid maximum length");
            }
        }
        else {
            maxLen = Integer.MAX_VALUE;
        }
        //If either a minimum or maximum duration was entered, add to filters
        if (!(minLen == 0 && maxLen == Integer.MAX_VALUE)) {
            query = query.filterLength(minLen, maxLen);
        }
        //If a minimum release year was entered, try to parse the input
        if (!fromYear.getText().isBlank()) {
            try {
                fromY = Integer.parseInt(fromYear.getText());
            }
            catch (NumberFormatException nfe) {
                LOGGER.severe("Not a valid starting year");
            }
        }
        else {
            fromY = Integer.MIN_VALUE;
        }
        //If a maximum release year was entered, try to parse the input
        if (!toYear.getText().isBlank()) {
            try {
                toY = Integer.parseInt(toYear.getText());
            }
            catch (NumberFormatException nfe) {
                LOGGER.severe("Not a valid ending year");
            }
        }
        else {
            toY = Integer.MAX_VALUE;
        }
        //If either a minimum or maximum release year was entered, add to filters
        if (!(fromY == Integer.MIN_VALUE && toY == Integer.MAX_VALUE)) {
            query = query.filterYear(fromY, toY);
        }
        return query;
    }

    /**
     * Determine, if the movies should be displayed with their duration, release year, both or neither
     *
     * @param query      The current {@link MovieQuery} to be expanded
     * @param showYear   The {@link Checkbox} for toggling the release year display
     * @param showLength The {@link Checkbox} for toggling the duration display
     * @return The expanded {@link MovieQuery}
     */
    private static MovieQuery getQuery(MovieQuery query, Checkbox showYear, Checkbox showLength) {
        if (showYear.getState() && !showLength.getState()) {
            return query.withYear();
        }
        if (showLength.getState() && !showYear.getState()) {
            return query.withLength();
        }
        if (showYear.getState() && showLength.getState()) {
            return query.withYear().withLength();
        }
        else {
            assert !showYear.getState() && !showLength.getState();
            return query;
        }
    }

    /**
     * Creates a query for all available categories and adds the returned movies to a {@link HashMap}
     *
     * @param showYear   The {@link Checkbox} for toggling the release year display
     * @param showLength The {@link Checkbox} for toggling the duration display
     * @return The HashMap containing all {@link Movie}s returned by the query
     */
    private static HashMap<String, String> determineQuery(Checkbox showYear, Checkbox showLength) {
        HashMap<String, String> movieMap = new HashMap<>();
        Model.getInstance().
                createCategoryQuery()
                .get()
                .forEach(cat -> buildQueryForCategory(cat, showYear, showLength)
                        .get().forEach((Movie m) -> movieMap.put(m.toString(), m.description())));
        return movieMap;
    }

    /**
     * Builds the base query for each category according to the display preferences set with the checkboxes to toggle
     * the duration and release year display
     *
     * @param cat        The {@link Category} to create the {@link MovieQuery} for
     * @param showYear   The {@link Checkbox} for toggling the release year display
     * @param showLength The {@link Checkbox} for toggling the duration display
     * @return The {@link MovieQuery} for the passed category
     */
    private static MovieQuery buildQueryForCategory(Category cat, Checkbox showYear, Checkbox showLength) {
        MovieQuery query = Model.getInstance()
                .createMovieQuery()
                .filterCategories(cat)
                .withCategory()
                .withDescription();
        return getQuery(query, showYear, showLength);
    }

    /**
     * Creates the checkboxes used for the category filters
     *
     * @param text The label of the checkbox
     * @return A styled {@link Checkbox} with the specified label
     */
    private Checkbox createCheckbox(String text) {
        Checkbox checkbox = new Checkbox(text);
        checkbox.setFont(new Font(VERDANA, Font.PLAIN, 14));
        checkbox.setForeground(white);

        return checkbox;
    }

    /**
     * Adds all movies from the passed map and connects the elements from the Observer Pattern to them
     *
     * @param movieMap    The {@link HashMap} containing the movies according to the set filters
     * @param title       The {@link ObserverLabel} for the movie's title
     * @param yearLabel   The {@link ObserverLabel} for the movie's release year
     * @param lengthLabel The {@link ObserverLabel} for the movie's duration
     * @param details     The {@link ObserverTextArea} for the movie's description
     * @param area        The {@link Panel} to which the elements will be added to
     */
    private static void addAll(
            HashMap<String, String> movieMap,
            ObserverLabel title,
            ObserverLabel yearLabel,
            ObserverLabel lengthLabel,
            ObserverTextArea details,
            Panel area) {
        ObservableLabel label;
        List<String> keys = movieMap.keySet().stream().toList();
        for (String key : keys) {
            label = new ObservableLabel(key);
            label.addMouseListener(new ClickListener());
            label.setBackground(new Color(90, 90, 90));

            label.addLabelListener(title);
            label.addLabelListener(yearLabel);
            label.addLabelListener(lengthLabel);
            label.setName(movieMap.get(key));
            label.addTextFieldListener(details);

            area.add(label);
            Component spacer = Box.createRigidArea(new Dimension(5, 5));
            area.add(spacer);
        }
        area.revalidate();
    }

    /**
     * Creates a {@link Label} with the font 'Verdana', in plain style, font size 12 and white text.
     *
     * @param text A {@link String}: The text to be displayed by the {@link Label}
     * @return A styled {@link Label} with the specified text
     */
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font(VERDANA, Font.PLAIN, 12));
        label.setForeground(white);
        return label;
    }

    /**
     * Creates a {@link Label} used for headers, with the font 'Verdana', in bold style, font size 16 and white text.
     *
     * @param text A {@link String}: The text to be displayed by the {@link Label}
     * @return A styled header {@link Label} with the specified text
     */
    private Label createHeader(String text) {
        Label label = new Label(text);
        label.setFont(new Font(VERDANA, Font.BOLD, 16));
        label.setForeground(white);
        label.setAlignment(Label.LEFT);
        return label;
    }
}
