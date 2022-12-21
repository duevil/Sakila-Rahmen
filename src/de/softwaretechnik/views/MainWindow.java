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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//TODO: Add way to filter for text search
//		[ ] Add filter methods
//		[X] Add clickable Movies
//		[X] Format Movies
//		[X] Add Movie Info to Details on Click
//		[X] Find out why everything is repeated

/**
 *
 * @author Elisa Johanna Woelk (m30192)
 */
public class MainWindow extends Frame {

	/*
		- Main Window als Singleton
		- Nur UI/GUI spezifische Implementierungen
	 */

    // ------------------------------------------------------------------------------------------------
    // Singleton
    private static final MainWindow window = new MainWindow();
    private final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    public static MainWindow getInstance() {
        return window;
    }

    private final Color grey = new Color(47, 47, 47);
    private final Color white = new Color(246, 246, 246);
    protected final Color gold = new Color(255, 203, 116);
    private Label errorText = new Label();
    private static final String VERDANA = "Verdana";

    private static Label last;

    private static List<Category> categories;

    public static Label getLast() {
        return last;
    }
    public static void setLast(Label last) {
        MainWindow.last = last;
    }

    private MainWindow() {
        categories = Model.getInstance().createCategoryQuery().get();
        setBackground(grey);
        setTitle(Program.APP_TITLE + " [" + Program.APP_V + "]");
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        createGUI();
    }

    public void createGUI() {
        add(addItems());
    }

    private Panel addItems() {
        // Label for movie title
        ObserverLabel title = new ObserverLabel("", ObserverLabel.Type.TITLE);
        title.setAlignment(Label.CENTER);
        title.setPreferredSize(new Dimension((int) ((int) (screenWidth / 10) * 1.5), 40));
        title.setFont(new Font(VERDANA, Font.BOLD, 20));
        title.setForeground(white);
        title.setBackground(new Color(85, 85, 85));

        Panel pane = new Panel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Label l1 = new Label("Movie Database", Label.CENTER);
        l1.setForeground(white);
        l1.setFont(new Font(VERDANA, Font.BOLD, 80));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        c.gridheight = 5;
        c.insets = new Insets(30, 10, 10, 10);
        pane.add(l1, c);

        ScrollPane mContent = new ScrollPane();
        Panel area = new Panel();
        area.setLayout(new BoxLayout(area, BoxLayout.PAGE_AXIS));
        mContent.setPreferredSize(new Dimension(1100, 1000));
        mContent.setMinimumSize(new Dimension(1100, 1000));
        area.setFocusable(false);
		area.setFont(new Font(VERDANA, Font.PLAIN, 14));
		area.setForeground(white);
        area.setBackground(new Color(85, 85, 85));
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 40;
        mContent.add(area);
        pane.add(mContent, c);

        Label searchTitle = createHeader("Search:");
        searchTitle.setPreferredSize(new Dimension((int) ((int) (screenWidth / 10) * 1.5), 40));
        c.gridx = 0;
        c.gridy = 5;
        c.weighty = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.insets = new Insets(10, 5, 0, 5);
        pane.add(searchTitle, c);

        TextField search = new TextField();
        search.setFont(new Font(VERDANA, Font.PLAIN, 20));
        c.gridy = 6;
        c.insets = new Insets(5, 5, 0, 5);
        pane.add(search, c);

        Label genreTitle = createHeader("Genre:");
        c.gridy = 7;
        c.insets = new Insets(10, 5, 0, 5);
        pane.add(genreTitle, c);

        c.insets = new Insets(10, 5, 0, 5);
        for (int i = 0; i < categories.size(); i++) {
            c.gridy = 8 + i;
            Checkbox checkbox = createCheckbox(categories.get(i).toString());
            pane.add(checkbox, c);
        }

        Label durationTitle = createHeader("Duration:");
        c.gridy = 24;
        c.insets = new Insets(35, 5, 0, 5);
        pane.add(durationTitle, c);

        Label minLenLabel = createLabel("Min:");
        c.gridy = 25;
        c.gridx = 0;
        c.gridwidth = 1;
        c.insets = new Insets(5, 30, 0, 5);
        pane.add(minLenLabel, c);

        TextField minLength = new TextField();
        c.gridx = 1;
        c.insets = new Insets(5, 0, 0, 10);
        pane.add(minLength, c);

        Label maxLenLabel = createLabel("Max:");
        c.gridx = 2;
        c.insets = new Insets(5, 30, 0, 5);
        pane.add(maxLenLabel, c);

        TextField maxLength = new TextField();
        c.gridx = 3;
        c.insets = new Insets(5, 0, 0, 10);
        pane.add(maxLength, c);

        Label releaseTitle = createHeader("Release year:");
        c.gridy = 26;
        c.gridx = 0;
        c.gridwidth = 4;
        c.insets = new Insets(35, 5, 0, 5);
        pane.add(releaseTitle, c);

        Label from = createLabel("From:");
        c.gridy = 27;
        c.gridx = 0;
        c.gridwidth = 1;
        c.insets = new Insets(5, 30, 0, 5);
        pane.add(from, c);

        TextField fromYear = new TextField();
        c.gridx = 1;
        c.insets = new Insets(5, 0, 0, 10);
        pane.add(fromYear, c);

        Label to = createLabel("To:");
        c.gridx = 2;
        c.insets = new Insets(5, 30, 0, 5);
        pane.add(to, c);

        TextField toYear = new TextField();
        c.gridx = 3;
        c.insets = new Insets(5, 0, 0, 10);
        pane.add(toYear, c);

        Button submit = new Button("Filter");
        submit.setBackground(new Color(85, 85, 85));
        submit.setForeground(white);
        c.ipady = 15;
        c.ipadx = 10;
        c.gridy = 28;
        c.gridx = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(35, 45, 20, 45);
        pane.add(submit, c);

        Button remove = new Button("Clear");
        remove.setBackground(new Color(85, 85, 85));
        remove.setForeground(white);
        c.gridx = 2;
        c.gridwidth = 2;
        c.insets = new Insets(35, 45, 20, 45);
        pane.add(remove, c);

        c.ipady = 0;
        c.ipadx = 0;
        c.weighty = 0;
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(10, 5, 0, 5);
        pane.add(title, c);

        ObserverLabel yearLabel = new ObserverLabel("", ObserverLabel.Type.YEAR);
        yearLabel.setForeground(white);
        yearLabel.setFont(new Font(VERDANA, Font.PLAIN, 20));
        yearLabel.setBackground(new Color(85, 85, 85));
        yearLabel.setAlignment(Label.CENTER);

        c.gridy = 6;
        c.insets = new Insets(5, 5, 0, 5);
        pane.add(yearLabel, c);

        ObserverLabel lengthLabel = new ObserverLabel("", ObserverLabel.Type.LENGTH);
        lengthLabel.setForeground(white);
        lengthLabel.setFont(new Font(VERDANA, Font.ITALIC, 16));
        lengthLabel.setBackground(new Color(85, 85, 85));
        lengthLabel.setAlignment(Label.CENTER);
        c.gridy = 7;
        pane.add(lengthLabel, c);

        ObserverTextArea details = new ObserverTextArea("", 1, 1, TextArea.SCROLLBARS_VERTICAL_ONLY);
        details.setEditable(false);
        details.setFocusable(false);
        details.setFont(new Font(VERDANA, Font.ITALIC, 16));
        details.setForeground(white);
        details.setBackground(new Color(85, 85, 85));
        c.gridy = 8;
        c.gridheight = 20;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.insets = new Insets(10, 5, 5, 5);
        pane.add(details, c);

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

        Checkbox showLength = new Checkbox("Show duration", true);
        showLength.setFont(new Font(VERDANA, Font.PLAIN, 11));
        showLength.setForeground(white);
        c.gridx = 6;
        pane.add(showLength, c);

        submit.addActionListener(e -> {
            MovieQuery query = Model.getInstance().createMovieQuery();
            int minLen = -1;
            int maxLen = -1;
            int fromY = -1;
            int toY = -1;

            if (!search.getText().isBlank()) {
                query = query.filterName(search.getText());
            }
            List<Category> catFilter = new LinkedList<>();
            for (int i = 0; i < categories.size(); i++) {
                if (pane.getComponent(5 + i) instanceof Checkbox checkbox && checkbox.getState()) {
                    catFilter.add(categories.get(i));
                }
            }
            if (!catFilter.isEmpty()) {
                if (catFilter.size() == 1) {
                    query = query.filterCategories(catFilter.get(0));
                }
                else {
                    System.out.println(catFilter);
                    Category[] catArr = new Category[catFilter.size()];
                    for (int i = 0; i < catFilter.size(); i++) {
                        catArr[i] = catFilter.get(i);
                    }
                    System.out.println(Arrays.toString(catArr));
                    query = query.filterCategories(catArr);
                }
            }
            if (!minLength.getText().isBlank()) {
                try {
                    minLen = Integer.parseInt(minLength.getText());
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid minimum length");
                }
            }
            else {
                minLen = 0;
            }
            if (!maxLength.getText().isBlank()) {
                try {
                    maxLen = Integer.parseInt(maxLength.getText());
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid maximum length");
                }
            }
            else {
                maxLen = Integer.MAX_VALUE;
            }
            if (!(minLen == 0 && maxLen == Integer.MAX_VALUE)) {
                query = query.filterLength(minLen, maxLen);
            }

            if (!fromYear.getText().isBlank()) {
                try {
                    fromY = Integer.parseInt(fromYear.getText());
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid starting year");
                }
            }
            else {
                fromY = Integer.MIN_VALUE;
            }
            if (!toYear.getText().isBlank()) {
                try {
                    toY = Integer.parseInt(toYear.getText());
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid ending year");
                }
            }
            else {
                toY = Integer.MAX_VALUE;
            }
            if (!(fromY == Integer.MIN_VALUE && toY == Integer.MAX_VALUE)) {
                query = query.filterYear(fromY, toY);
            }
            List<Movie> movieQuery = query.get();
            System.out.println(movieQuery);
            //TODO: Execute Query
        });

        remove.addActionListener(e -> {
            HashMap<String, String> movieMap = new HashMap<>();
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
            if (showYear.getState() && !showLength.getState()) {
                movieMap = queryYear();
            }
            if (showLength.getState() && !showYear.getState()) {
                movieMap = queryLength();
            }
            if (showYear.getState() && showLength.getState()) {
                movieMap = queryBoth();
            }
            if (!showYear.getState() && !showLength.getState()) {
                movieMap = queryNeither();
            }
            addAll(movieMap, title, yearLabel, lengthLabel, details, area);
        });
        addAll(queryBoth(), title, yearLabel, lengthLabel, details, area);
        return pane;
    }

    private Checkbox createCheckbox(String text) {
        Checkbox checkbox = new Checkbox(text);
        checkbox.setFont(new Font(VERDANA, Font.PLAIN, 14));
        checkbox.setForeground(white);

        return checkbox;
    }

    private void addAll(
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

    private HashMap<String, String> queryNeither() {
        HashMap<String, String> movieMap = new HashMap<>();
        Model.getInstance().
                createCategoryQuery()
                .get()
                .forEach(cat -> Model.getInstance()
                        .createMovieQuery()
                        .filterCategories(cat)
                        .withCategory()
                        .withDescription()
                        .get().forEach((Movie m) -> movieMap.put(m.toString(), m.description())));
        return movieMap;
    }

    private HashMap<String, String> queryYear() {
        HashMap<String, String> movieMap = new HashMap<>();
        Model.getInstance().
                createCategoryQuery()
                .get()
                .forEach(cat -> Model.getInstance()
                        .createMovieQuery()
                        .filterCategories(cat)
                        .withCategory()
                        .withYear()
                        .withDescription()
                        .get().forEach((Movie m) -> movieMap.put(m.toString(), m.description())));
        return movieMap;
    }

    private HashMap<String, String> queryLength() {
        HashMap<String, String> movieMap = new HashMap<>();
        Model.getInstance().
                createCategoryQuery()
                .get()
                .forEach(cat -> Model.getInstance()
                        .createMovieQuery()
                        .filterCategories(cat)
                        .withCategory()
                        .withLength()
                        .withDescription()
                        .get().forEach((Movie m) -> movieMap.put(m.toString(), m.description())));
        return movieMap;
    }

    private HashMap<String, String> queryBoth() {
        HashMap<String, String> movieMap = new HashMap<>();
        Model.getInstance().
                createCategoryQuery()
                .get()
                .forEach(cat -> Model.getInstance()
                        .createMovieQuery()
                        .filterCategories(cat)
                        .withCategory()
                        .withLength()
                        .withYear()
                        .withDescription()
                        .get().forEach((Movie m) -> movieMap.put(m.toString(), m.description())));
        return movieMap;
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
