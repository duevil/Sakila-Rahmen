package de.softwaretechnik.views;

import de.softwaretechnik.models.Category;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.models.Movie;
import de.softwaretechnik.models.MovieQuery;
import de.softwaretechnik.program.Program;
import de.softwaretechnik.views.listeners.ClickListener;
import de.softwaretechnik.views.observerElements.ObservableLabel;
import de.softwaretechnik.views.observerElements.ObserverLabel;
import de.softwaretechnik.views.observerElements.ObserverTextArea;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

import static java.awt.Color.RED;

//TODO: Add way to filter for text search
//		[ ] Add filter methods
//		[X] Add clickable Movies
//		[X] Format Movies
//		[X] Add Movie Info to Details on Click
//		[X] Find out why everything is repeated

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

    public static Label getLast() {
        return last;
    }
    public static void setLast(Label last) {
        MainWindow.last = last;
    }

    private MainWindow() {
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
        ObserverLabel title = new ObserverLabel("", ObserverLabel.Type.TITLE);
        title.setAlignment(Label.CENTER);
        title.setFont(new Font(VERDANA, Font.BOLD, 18));
        title.setBackground(new Color(85, 85, 85));

        int width = (int) ((int) screenWidth * 0.15);

        Panel pane = new BorderPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        Label l1 = new Label("Movie Database", Label.CENTER);
        l1.setForeground(white);
        l1.setFont(new Font(VERDANA, Font.BOLD, 80));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 32;
        c.gridheight = 5;
        c.insets = new Insets(30, 10, 10, 10);
        pane.add(l1, c);

        ScrollPane mContent = new ScrollPane();
        Panel area = new Panel();
        area.setLayout(new BoxLayout(area, BoxLayout.PAGE_AXIS));
        mContent.setPreferredSize(new Dimension(1100, 900));
        area.setFocusable(false);
		area.setFont(new Font(VERDANA, Font.PLAIN, 14));
		area.setForeground(white);
        area.setBackground(new Color(85, 85, 85));
        c.weightx = 0.5;
        c.gridx = 5;
        c.gridy = 6;
        c.gridwidth = 22;
        c.gridheight = 21;
        mContent.add(area);
        pane.add(mContent, c);

        Label searchTitle = createHeader("Search:");
        c.gridx = 0;
        c.gridy = 6;
        c.weighty = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.insets = new Insets(10, 5, 0, 5);
        pane.add(searchTitle, c);

        TextField search = new TextField();
        search.setFont(new Font(VERDANA, Font.PLAIN, 20));
        c.gridy = 7;
        c.insets = new Insets(5, 5, 0, 5);
        pane.add(search, c);

        Label genreTitle = createHeader("Genre:");
        c.gridy = 8;
        c.insets = new Insets(35, 5, 0, 5);
        pane.add(genreTitle, c);

        Choice genres = selectGenre(width);
        c.gridy = 9;
        c.insets = new Insets(5, 5, 0, 5);
        pane.add(genres, c);

        Label durationTitle = createHeader("Duration:");
        c.gridy = 10;
        c.insets = new Insets(35, 5, 0, 5);
        pane.add(durationTitle, c);

        Label minLenLabel = createLabel("Min:");
        c.gridy = 11;
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
        c.gridy = 12;
        c.gridx = 0;
        c.gridwidth = 4;
        c.insets = new Insets(35, 5, 0, 5);
        pane.add(releaseTitle, c);

        Label from = createLabel("From:");
        c.gridy = 13;
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
        c.gridy = 14;
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

        Panel filterPanel = new BorderPanel();
        filterPanel.setBackground(grey);

        filterPanel.setLayout(new GridBagLayout());

        Label filterLabel = createHeader("Active Filters:");
		filterLabel.setPreferredSize(new Dimension(width - 5, 25));
        c.weightx = 0.5;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 1;
        c.insets = new Insets(5, 5, 5, 5);
        filterPanel.add(filterLabel, c);

        TextArea filterField = new TextArea("", 10, 5, TextArea.SCROLLBARS_NONE);
        filterField.setEditable(false);
        filterField.setFocusable(false);
        filterField.setBackground(new Color(60, 60, 60));
        filterField.setFont(new Font(VERDANA, Font.PLAIN, 12));
        filterField.setForeground(white);
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        c.insets = new Insets(0, 5, 5, 5);
        c.weighty = 1.0;
        filterPanel.add(filterField, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridy = 9;
        c.gridx = 0;
        c.gridwidth = 4;
        c.insets = new Insets(20, 5, 10, 5);
        c.weighty = 1.0;
        pane.add(filterPanel, c);

        filterPanel.setBackground(new Color(60, 60, 60));
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 15;
        c.gridx = 0;
        c.gridwidth = 4;
        c.insets = new Insets(20, 5, 10, 5);
        c.weighty = 1.0;

        pane.add(filterPanel, c);

        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 0;
        c.weightx = 0.5;
        c.gridx = 29;
        c.gridy = 6;
        c.gridwidth = 4;
        c.gridheight = 2;
        c.insets = new Insets(10, 5, 0, 5);
        pane.add(title, c);

        ObserverLabel yearLabel = new ObserverLabel("", ObserverLabel.Type.YEAR);
        yearLabel.setFont(new Font(VERDANA, Font.PLAIN, 20));
        yearLabel.setBackground(new Color(85, 85, 85));
        yearLabel.setAlignment(Label.CENTER);

        c.gridy = 8;
        c.gridheight = 1;
        c.ipady = 0;
        c.insets = new Insets(5, 5, 0, 5);
        pane.add(yearLabel, c);

        ObserverLabel lengthLabel = new ObserverLabel("", ObserverLabel.Type.LENGTH);
        lengthLabel.setFont(new Font(VERDANA, Font.ITALIC, 16));
        lengthLabel.setBackground(new Color(85, 85, 85));
        lengthLabel.setAlignment(Label.CENTER);
        c.gridy = 9;
        c.gridheight = 1;
        pane.add(lengthLabel, c);

        ObserverTextArea details = new ObserverTextArea("", 1, 1, TextArea.SCROLLBARS_VERTICAL_ONLY);
        details.setEditable(false);
        details.setFocusable(false);
        details.setFont(new Font(VERDANA, Font.ITALIC, 16));
        details.setBackground(new Color(85, 85, 85));
        c.gridy = 10;
        c.gridheight = 15;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.insets = new Insets(10, 5, 5, 5);
        pane.add(details, c);

        Checkbox showYear = new Checkbox("Show release year", true);
        showYear.setFont(new Font(VERDANA, Font.PLAIN, 11));
        showYear.setForeground(white);
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 25;
        c.gridx = 30;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0;
        pane.add(showYear, c);

        Checkbox showLength = new Checkbox("Show duration", true);
        showLength.setFont(new Font(VERDANA, Font.PLAIN, 11));
        showLength.setForeground(white);
        c.gridx = 31;
        pane.add(showLength, c);

        Label footer = new Label("ERROR");
        footer.setFont(new Font(VERDANA, Font.PLAIN, 11));
        footer.setForeground(RED);
        c.weightx = 1;
        c.gridy = 29;
        c.gridx = 0;
        c.gridwidth = 32;
        c.gridheight = 4;
        pane.add(footer, c);

        HashMap<String, String> selectedFilters = new HashMap<>();
        submit.addActionListener(e -> {
            SelectedFilter selectedFilter = new SelectedFilter();
            MovieQuery query = Model.getInstance().createMovieQuery();
            int genre = genres.getSelectedIndex();
            int minLen = -1;
            int maxLen = -1;
            int fromY = -1;
            int toY = -1;
            if (!search.getText().isBlank()) {
                selectedFilter.setTextSearch(search.getText());
                query = query.filterName(search.getText());
                selectedFilters.put("Containing:", search.getText());
            }
            if (genre != 0) {
                selectedFilter.setGenre(genres.getItem(genre));
                List<Category> categories = Model.getInstance().createCategoryQuery().get();
                Category cat = null;
                for (Category category : categories) {
                    if (category.toString().equals(genres.getItem(genre))) cat = category;
                }
                query = query.filterCategories(cat);
                selectedFilters.put("Genre:", genres.getItem(genre));
            }
            if (!minLength.getText().isBlank()) {
                try {
                    minLen = Integer.parseInt(minLength.getText());
                    selectedFilter.setMinLength(minLen);
                    selectedFilters.put("Min Duration:", String.valueOf(minLen).concat(" minutes"));
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid minimum length");
                }
            }
            if (!maxLength.getText().isBlank()) {
                try {
                    maxLen = Integer.parseInt(minLength.getText());
                    selectedFilter.setMaxLength(maxLen);
                    selectedFilters.put("Max Duration:", String.valueOf(maxLen).concat(" minutes"));
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid maximum length");
                }
            }
            if (!fromYear.getText().isBlank()) {
                try {
                    fromY = Integer.parseInt(fromYear.getText());
                    selectedFilter.setFromYear(fromY);
                    selectedFilters.put("From Year:", String.valueOf(fromY));
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid starting year");
                }
            }
            if (!toYear.getText().isBlank()) {
                try {
                    toY = Integer.parseInt(toYear.getText());
                    selectedFilter.setToYear(toY);
                    selectedFilters.put("To Year:", String.valueOf(toY));
                } catch (NumberFormatException nfe) {
                    errorText.setText("Not a valid ending year");
                }
            }
            if (minLen != -1 && maxLen != -1) {
                query.filterLength(minLen, maxLen);
            }
            if (minLen != -1 && maxLen == -1) {
                query.filterLength(minLen, Integer.MAX_VALUE);
            }
            if (minLen == -1 && maxLen != -1) {
                query.filterLength(Integer.MIN_VALUE, maxLen);
            }
            if (fromY != -1 && toY != -1) {
                query.filterYear(fromY, toY);
            }
            if (fromY != -1 && toY == -1) {
                query.filterYear(fromY, Integer.MAX_VALUE);
            }
            if (fromY == -1 && toY != -1) {
                query.filterYear(Integer.MIN_VALUE, toY);
            }
            filterField.setText(String.valueOf(selectedFilters)
                    .substring(1, String.valueOf(selectedFilters)
                            .length() - 1).replace('=', ' ')
                    .replace(", ", System.lineSeparator())
                    .trim());

        });

        remove.addActionListener(e -> {
            HashMap<String, String> movieMap = new HashMap<>();
            area.removeAll();
            search.setText("");
            genres.select(0);
            minLength.setText("");
            maxLength.setText("");
            fromYear.setText("");
            toYear.setText("");
            filterField.setText("");
            if (showYear.getState() && !showLength.getState()) {
                movieMap = queryYear();
            }
            if (showLength.getState() && !showYear.getState()) {
                movieMap = queryLength();
            }
            if (showYear.getState() && showLength.getState()) {
                movieMap = queryBoth();
            } else {
                movieMap = queryNeither();
            }
            addAll(movieMap, title, yearLabel, lengthLabel, details, area);
        });
        addAll(queryBoth(), title, yearLabel, lengthLabel, details, area);
        return pane;
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
     * Sets up the {@link Choice}-Element for selecting a genre.
     *
     * @param width An {@link Integer}: The width of the parent container
     * @return A {@link Choice} containing all available genres
     */
    private Choice selectGenre(int width) {
        Choice genres = new Choice();
        genres.setPreferredSize(new Dimension(width - 40, 30));
        genres.add("All");
        genres.add("Action");
        genres.add("Animation");
        genres.add("Children");
        genres.add("Classics");
        genres.add("Comedy");
        genres.add("Documentary");
        genres.add("Drama");
        genres.add("Family");
        genres.add("Foreign");
        genres.add("Games");
        genres.add("Horror");
        genres.add("Music");
        genres.add("New");
        genres.add("Sci-Fi");
        genres.add("Sports");
        genres.add("Travel");
        genres.setBackground(grey);
        genres.setForeground(white);
        genres.setFocusable(false);
        return genres;
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
