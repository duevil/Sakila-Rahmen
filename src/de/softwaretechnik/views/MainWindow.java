package de.softwaretechnik.views;

import de.softwaretechnik.models.Model;
import de.softwaretechnik.models.Movie;
import de.softwaretechnik.program.Program;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static java.awt.Color.*;


public class MainWindow extends Frame {

	/*
		- Main Window als Singleton
		- Nur UI/GUI spezifische Implementierungen
	 */

	// ------------------------------------------------------------------------------------------------
	// Singleton
	private static final MainWindow window = new MainWindow();
	private final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public static MainWindow getInstance(){
		return window;
	}


	private final Color black = new Color(17, 17, 17);
	private final Color grey = new Color(47, 47, 47);
	private final Color white = new Color(246, 246, 246);
	protected final Color gold = new Color(255, 203, 116);

	private Panel filters = new Panel();
	private Label errorText;
	private static final String VERDANA = "Verdana";
	private Panel moviePanel;
	private List<Movie> selectedMovies;

	private MainWindow() {
		setBackground(grey);
		setTitle(Program.APP_TITLE + " [" + Program.APP_V + "]");
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);

		setLayout(new BorderLayout());

		createGUI();
	}

	public void createGUI(){
		add(filterPane(), BorderLayout.WEST);
		add(topPane(), BorderLayout.NORTH);
		add(detailPane(), BorderLayout.EAST);
		add(mainPane(), BorderLayout.CENTER);
		add(bottomPane(), BorderLayout.SOUTH);
	}

	private Panel filterPane() {
		Panel westPanel = new BorderPanel();
		westPanel.setBackground(grey);
		int width = (int) ((int) screenWidth * 0.15);
		int height = (int) ((int) (screenHeight / 10) * 7.5);
		westPanel.setPreferredSize(new Dimension(width, height));

		westPanel.add(createHeader("Search:", width));
		TextField search = addSearchField(width);
		westPanel.add(search);

		westPanel.add(Box.createRigidArea(new Dimension(width - 5, 20)));

		westPanel.add(createHeader("Genre:", width));
		Choice genres = selectGenre(width);
		westPanel.add(genres);

		westPanel.add(Box.createRigidArea(new Dimension(width - 5, 20)));

		westPanel.add(createHeader("Duration:", width));
		TextField minLength = new TextField(3);
		TextField maxLength = new TextField(3);
		Panel lengthFrame = selectLength(minLength, maxLength);
		westPanel.add(lengthFrame);

		westPanel.add(Box.createRigidArea(new Dimension(width - 5, 20)));

		westPanel.add(createHeader("Release year:", width));
		TextField fromYear = new TextField(3);
		TextField toYear = new TextField(3);
		Panel yearFrame = selectYear(fromYear, toYear);
		westPanel.add(yearFrame);

		westPanel.add(Box.createRigidArea(new Dimension(width - 5, 10)));

		westPanel.add(createHeader("Cast:", width));
		Label castL = new Label("Multiple actors are separated by a comma (,)");
		castL.setFont(new Font(VERDANA, Font.PLAIN, 11));
		castL.setAlignment(Label.LEFT);
		castL.setPreferredSize(new Dimension(width - 5, 25));
		castL.setForeground(new Color(120, 120, 120));
		westPanel.add(castL);
		TextField cast = new TextField();
		cast.setFont(new Font(VERDANA, Font.PLAIN, 20));
		cast.setPreferredSize(new Dimension(width - 20, 25));
		westPanel.add(cast);

		westPanel.add(Box.createRigidArea(new Dimension(width - 5, 20)));

		Button submit = new Button("Filter");
		submit.addActionListener(e -> {
			SelectedFilter selectedFilter = new SelectedFilter();
			int genre = genres.getSelectedIndex();
			int minLen = -1;
			int maxLen = -1;
			int fromY = -1;
			int toY = -1;
			String castQuery = "";
			if (!minLength.getText().isBlank()) {
				try {
					minLen = Integer.parseInt(minLength.getText());
				}
				catch (NumberFormatException nfe) {
					errorText.setText("Not a valid minimum length");
				}
			}
			if (!maxLength.getText().isBlank()) {
				try {
				maxLen = Integer.parseInt(minLength.getText());
				}
				catch (NumberFormatException nfe) {
					errorText.setText("Not a valid maximum length");
				}
			}
			if (!fromYear.getText().isBlank()) {
				try {
				fromY = Integer.parseInt(fromYear.getText());
				}
				catch (NumberFormatException nfe) {
					errorText.setText("Not a valid starting year");
				}
			}
			if (!toYear.getText().isBlank()) {
				try {
				toY = Integer.parseInt(toYear.getText());
				}
				catch (NumberFormatException nfe) {
					errorText.setText("Not a valid ending year");
				}
			}
			if (!cast.getText().isBlank()) {
				castQuery = cast.getText();
			}
			List<String> filterList = new ArrayList<>();
			if (genre != 0) {
				selectedFilter.setGenre(genres.getItem(genre));
				filterList.add("Genre: ".concat(genres.getItem(genre)));

			}
			if (minLen != -1) {
				selectedFilter.setMinLength(minLen);
				filterList.add("Min Duration: ".concat(String.valueOf(minLen)));
			}
			if (maxLen != -1) {
				selectedFilter.setMaxLength(maxLen);
				filterList.add("Max Duration: ".concat(String.valueOf(maxLen)));
			}
			if (fromY != -1) {
				selectedFilter.setFromYear(fromY);
				filterList.add("From Year: ".concat(String.valueOf(fromY)));
			}
			if (toY != -1) {
				selectedFilter.setToYear(toY);
				filterList.add("To Year: ".concat(String.valueOf(toY)));
			}
			if (!castQuery.isBlank()) {
				selectedFilter.setActors(
						Arrays.stream(castQuery.split(","))
								.map(String::trim)
								.toArray(String[]::new));
				filterList.add("With: ".concat(castQuery.trim()));
			}
			addFilters(filterList);

			/*if (!search.getText().isBlank() && search.getText().length() >= 4) {
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
								.filterName(search.getText())
								.get().forEach((Movie m) -> filterRequest(m, selectedFilter)));
			}
			else {
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
								.get().forEach((Movie m) -> filterRequest(m, selectedFilter)));
			}*/

			/*
			How to request?
			 */
		});
		westPanel.add(submit);

		westPanel.add(Box.createRigidArea(new Dimension(width - 5, 7)));

		Panel activeFilters = displayActiveFilters(width);
		westPanel.add(activeFilters);

		return westPanel;
	}

	private void filterRequest(Movie m, SelectedFilter filter) {
		selectedMovies = new LinkedList<>();
		if (
				filter.isYearRange(m) && //if year within range
				filter.isGenre(m.category().toString()) && //if correct genre
				filter.isLengthRange(m) && //if length within range
				filter.hasActors(m)) //if specified actors in cast
		{
			if (!filter.getTextSearch().isBlank()
					&& m.toString().contains(filter.getTextSearch()) //if query is in title, length or year
					|| m.description().contains(filter.getTextSearch()) //if query is in description
					|| m.actors().stream().anyMatch(e -> e.toString().matches(filter.getTextSearch())) //if query is in actors
					|| m.category().toString().contains(filter.getTextSearch())) //if query is in category
			{
				selectedMovies.add(m);
			}
			else {
				selectedMovies.add(m);
			}
		}

	}

	//TODO: Task to update GUI
	private TextField addSearchField(int width) {
		TextField search = new TextField();
		search.setFont(new Font(VERDANA, Font.PLAIN, 20));
		search.setPreferredSize(new Dimension(width - 10, 30));
		search.addTextListener(e -> {
			if (search.getText().length() >= 4) {
				//TODO: Hand over text to task for filtering
				/*Model.getInstance().
						createCategoryQuery()
						.get()
						.forEach(c -> Model.getInstance()
								.createMovieQuery()
								.filterName(search.getText())
								.get().forEach((Movie m) -> {
									if (m.description() != null) System.out.println(m.description());
									if (m.category() != null) System.out.println(m.category());
									if (m.actors() != null) m.actors().forEach(System.out::println);
								}));*/
			}

		});
		return search;
	}

	/**
	 * Sets up the {@link Choice}-Element for selecting a genre.
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
	 * Sets up the {@link Panel} containing the {@link TextField}s to set the minimum and maximum length.
	 * @param minLength The {@link TextField} used to set the minimum length
	 * @param maxLength The {@link TextField} used to set the maximum length
	 * @return The {@link Panel} containing the minimum and maximum length {@link TextField}s
	 */
	private Panel selectLength(TextField minLength, TextField maxLength) {
		Panel lengthFrame = new Panel();
		lengthFrame.setLayout(new GridLayout(0, 5));
		lengthFrame.add(createLabel("Min:"));
		lengthFrame.add(minLength);
		lengthFrame.add(Box.createRigidArea(new Dimension(5, 10)));
		lengthFrame.add(createLabel("Max:"));
		lengthFrame.add(maxLength);
		return lengthFrame;
	}

	/**
	 * Sets up the {@link Panel} containing the {@link TextField}s to set the range for the year of release.
	 * @param fromYear The {@link TextField} used to set the lower limit
	 * @param toYear The {@link TextField} used to set the upper limit
	 * @return The {@link Panel} containing the {@link TextField}s for the range for the year of release
	 */
	private Panel selectYear(TextField fromYear, TextField toYear) {
		Panel yearFrame = new Panel();
		yearFrame.setLayout(new GridLayout(0, 5));
		yearFrame.add(createLabel("From:"));
		yearFrame.add(fromYear);
		yearFrame.add(Box.createRigidArea(new Dimension(5, 10)));
		yearFrame.add(createLabel("To:"));
		yearFrame.add(toYear);
		return yearFrame;
	}

	/**
	 * Sets up the {@link Panel} containing the active filters being used for the movie query
	 * @param width An {@link Integer}: The width of the parent container
	 * @return The {@link Panel} containing the active filters
	 */
	private Panel displayActiveFilters(int width) {
		Panel activeFilters = new BorderPanel();
		activeFilters.setPreferredSize(new Dimension(width - 10, 328));
		activeFilters.add(createHeader("Active filters:", width - 20));
		filters = new Panel();
		filters.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		filters.setPreferredSize(new Dimension(width - 20, 30));
		filters.setMaximumSize(new Dimension(width - 20, 280));
		filters.setMinimumSize(new Dimension(width - 24, 20));
		filters.setLayout(new FlowLayout());

		activeFilters.add(filters);
		return activeFilters;
	}

	private void addFilters(List<String> selectedFilters) {
		if (!selectedFilters.isEmpty()) {
			for (String selectedFilter : selectedFilters) {
				Panel p = new Panel();
				Label l = createLabel(selectedFilter);

				Button remove = new Button("X");
				remove.setPreferredSize(new Dimension(15, 15));

				p.add(remove);
				p.add(l);
				filters.add(p);
				filters.repaint();
			}
		}
	}

	/**
	 * Creates a {@link Label} with the font 'Verdana', in plain style, font size 12 and white text.
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
	 * @param width An {@link Integer}: The width of the parent container
	 * @return A styled header {@link Label} with the specified text
	 */
	private Label createHeader(String text, int width) {
		Label label = new Label(text);
		label.setFont(new Font(VERDANA, Font.BOLD, 16));
		label.setForeground(white);
		label.setAlignment(Label.LEFT);
		label.setPreferredSize(new Dimension(width - 5, 25));
		return label;
	}

	/**
	 * Creates the top {@link Panel} containing the title of the GUI
	 * @return The styled top {@link Panel}
	 */
	private Panel topPane() {
		Panel northPanel = new Panel();

		int width = (int) screenWidth;
		int height = (int) ((int) (screenHeight / 10) * 1.5);
		northPanel.setPreferredSize(new Dimension(width, height));
		northPanel.setBackground(grey);

		Label l1 = new Label("Movie Database", Label.CENTER);
		l1.setForeground(white);
		l1.setFont(new Font(VERDANA, Font.BOLD, 80));

		northPanel.add(l1);

		return northPanel;
	}

	/**
	 * Creates the main {@link Panel} containing {@link Movie movies} according to the currently set filter
	 * @return The styled main {@link Panel}
	 */
	private Panel mainPane() {
		Panel centerPanel = new BorderPanel();
		centerPanel.setBackground(grey);
		ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(1335, 820));

		scrollPane.setBackground(new Color(60, 60, 60));

		moviePanel = new Panel(new GridLayout(0, 3, 3, 0));
		moviePanel.setSize(scrollPane.getViewportSize());
		moviePanel.setMaximumSize(scrollPane.getViewportSize());
		moviePanel.setMaximumSize(scrollPane.getViewportSize());

		Label titleL = new Label("TITLE");
		titleL.setFont(new Font(VERDANA, Font.BOLD, 16));
		titleL.setPreferredSize(new Dimension(434, 30));
		titleL.setForeground(white);
		moviePanel.add(titleL);

		Label genreL = new Label("GENRE");
		genreL.setFont(new Font(VERDANA, Font.BOLD, 16));
		genreL.setForeground(white);
		genreL.setPreferredSize(new Dimension(scrollPane.getViewportSize().width / 3, 30));
		moviePanel.add(genreL);

		Label actorL = new Label("CAST");
		actorL.setFont(new Font(VERDANA, Font.BOLD, 16));
		actorL.setForeground(white);
		actorL.setPreferredSize(new Dimension(scrollPane.getViewportSize().width / 3, 30));
		moviePanel.add(actorL);

		//TODO: Remove placeholder
		Label title1 = new Label("Movietitle 1");
		title1.setFont(new Font(VERDANA, Font.PLAIN, 14));
		title1.setForeground(white);
		moviePanel.add(title1);

		//TODO: Remove placeholder
		Label genre1 = new Label("Testgenre");
		genre1.setFont(new Font(VERDANA, Font.PLAIN, 14));
		genre1.setForeground(white);
		moviePanel.add(genre1);

		//TODO: Remove placeholder
		//TODO: Create method to read actors into choice
		java.awt.Choice castL1 = new java.awt.Choice();
		castL1.setFocusable(false);
		castL1.setBackground(new Color(60, 60, 60));
		castL1.setForeground(white);
		castL1.add("FName LName");
		castL1.add("FName LName");
		castL1.add("FName LName");
		castL1.add("FName LName");
		castL1.setFont(new Font(VERDANA, Font.PLAIN, 14));
		moviePanel.add(castL1);

		//TODO: Remove placeholder
		Label title2 = new Label("Movietitle 2");
		title2.setFont(new Font(VERDANA, Font.PLAIN, 14));
		title2.setForeground(white);
		moviePanel.add(title2);

		//TODO: Remove placeholder
		Label genre2 = new Label("Testgenre");
		genre2.setFont(new Font(VERDANA, Font.PLAIN, 14));
		genre2.setForeground(white);
		moviePanel.add(genre2);

		//TODO: Remove placeholder
		java.awt.Choice cast2 = new java.awt.Choice();
		cast2.setFocusable(false);
		cast2.setBackground(new Color(60, 60, 60));
		cast2.setForeground(white);
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.add("FName LName");
		cast2.setFont(new Font(VERDANA, Font.PLAIN, 14));
		moviePanel.add(cast2);
		Panel n = new Panel();
		n.add(moviePanel);
		scrollPane.add(n);

		centerPanel.add(scrollPane);

		return centerPanel;
	}

	/**
	 * Creates the right {@link Panel} containing details to the currently selected movie
	 * @return The styled right {@link Panel}
	 */
	private Panel detailPane() {
		Panel eastPanel = new BorderPanel();
		int width = (int) ((int) screenWidth * 0.15);
		int height = (int) (screenHeight / 10) * 6;
		eastPanel.setPreferredSize(new Dimension(width, height));
		eastPanel.setBackground(grey);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));


		Panel pane = new BorderPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		Label title = new Label("Title");
		title.setAlignment(Label.CENTER);
		title.setFont(new Font("Verdana", Font.BOLD, 30));
		title.setBackground(new Color(85, 85, 85));
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10,5,0,5);
		pane.add(title, c);

		Label yearLabel = new Label("[2006]");
		yearLabel.setFont(new Font(VERDANA, Font.PLAIN, 20));
		yearLabel.setBackground(new Color(85, 85, 85));
		yearLabel.setAlignment(Label.CENTER);

		c.gridy = 1;
		c.insets = new Insets(5,5,0,5);
		pane.add(yearLabel, c);

		Label lengthLabel = new Label("[120min]");
		lengthLabel.setFont(new Font(VERDANA, Font.ITALIC, 16));
		lengthLabel.setBackground(new Color(85, 85, 85));
		lengthLabel.setAlignment(Label.CENTER);
		c.gridy = 2;
		c.gridheight = 1;
		c.insets = new Insets(5,5,5,5);
		pane.add(lengthLabel, c);

		TextField details = new TextField();
		details.setEditable(false);
		details.setFocusable(false);
		details.setFont(new Font(VERDANA, Font.ITALIC, 16));
		details.setBackground(new Color(85, 85, 85));
		c.gridy = 4;
		c.gridheight = 5;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.insets = new Insets(10,5,5,5);
		pane.add(details, c);

		Checkbox showYear = new Checkbox("Show release year", true);
		showYear.setFont(new Font(VERDANA, Font.PLAIN, 11));
		showYear.setForeground(white);
		c.gridy = 9;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weighty = 0;
		pane.add(showYear, c);
		showYear.addItemListener(e -> yearLabel.setVisible(showYear.getState()));

		Checkbox showLength = new Checkbox("Show duration", true);
		showLength.setFont(new Font(VERDANA, Font.PLAIN, 11));
		showLength.setForeground(white);
		c.gridx = 1;
		pane.add(showLength, c);
		showLength.addItemListener(e -> lengthLabel.setVisible(showLength.getState()));

		eastPanel.add(pane);

		return eastPanel;
	}

	private Panel bottomPane() {
		Panel southPanel = new BorderPanel();

		southPanel.setBackground(grey);
		int width = (int) screenWidth;
		int height = (int) screenHeight / 10;
		southPanel.setPreferredSize(new Dimension(width, height));
		errorText = new Label();
		errorText.setFont(new Font(VERDANA, Font.BOLD, 20));
		errorText.setForeground(RED);

		//TODO: Placeholder - To be removed
		errorText.setText("This could be your error!");

		southPanel.add(errorText);

		return southPanel;
	}
}
