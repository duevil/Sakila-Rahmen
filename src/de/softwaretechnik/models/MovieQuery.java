package de.softwaretechnik.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for building a {@link MovieFilter}; implements a {@link Query} for {@link Movie movies}
 *
 * @author Malte Kasolowsky
 */
public final class MovieQuery implements Query<Movie> {
    private static final int MAX_SEARCH_LENGTH = 4;
    private final Set<Category> categories = new HashSet<>();
    private boolean getDescription;
    private boolean getCategory;
    private boolean getYear;
    private boolean getLength;
    private boolean getActors;
    private String search;
    private int minYear = 1960;
    private int maxYear = 2020;
    private int minLength = 10;
    private int maxLength = 240;

    /**
     * Package-private default constructor
     */
    MovieQuery() {
    }

    /**
     * Sets whether the movie description should be queried
     *
     * @return this
     */
    public MovieQuery withDescription() {
        this.getDescription = true;
        return this;
    }

    /**
     * Sets whether the movie category should be queried
     *
     * @return this
     */
    public MovieQuery withCategory() {
        this.getCategory = true;
        return this;
    }

    /**
     * Sets whether the movie release year should be queried
     *
     * @return this
     */
    public MovieQuery withYear() {
        this.getYear = true;
        return this;
    }

    /**
     * Sets whether the movie length should be queried
     *
     * @return this
     */
    public MovieQuery withLength() {
        this.getLength = true;
        return this;
    }

    /**
     * Sets whether the movie actors should be queried
     *
     * @return this
     */
    public MovieQuery withActors() {
        this.getActors = true;
        return this;
    }

    /**
     * Sets the search string for searching the movie titles
     *
     * @param search The string to search; must be at least 4 characters long
     * @return this
     * @throws IllegalArgumentException If the search string is null or too short
     */
    public MovieQuery filterName(String search) throws IllegalArgumentException {
        if (search == null) throw new IllegalArgumentException("search parameter must not be null");
        if (search.length() < MAX_SEARCH_LENGTH) {
            throw new IllegalArgumentException(
                    "search parameter has to be at least "
                            + MAX_SEARCH_LENGTH
                            + " characters long"
            );
        }
        this.search = search;
        return this;
    }

    /**
     * Adds a {@link Category} to the underlying {@link Set} of categories
     *
     * @param category The category movies should be queried for
     * @return this
     * @throws IllegalArgumentException If the category is null
     */
    public MovieQuery filterCategories(Category category) throws IllegalArgumentException {
        if (category == null) throw new IllegalArgumentException("category must not be null");
        this.categories.add(category);
        return this;
    }

    /**
     * Adds a array of {@link Category categories} to the underlying {@link Set} of categories
     *
     * @param categories The categories movies should be queried for
     * @return this
     * @throws IllegalArgumentException When the array is null or contains null elements
     */
    public MovieQuery filterCategories(Category[] categories) throws IllegalArgumentException {
        if (categories == null) throw new IllegalArgumentException("category array must not be null");
        for (Category category : categories) filterCategories(category);
        return this;
    }

    /**
     * Sets the range for the movie release year to be filtered for
     *
     * @param minYear The earliest year
     * @param maxYear The latest year
     * @return this
     * @throws IllegalArgumentException If minYear is negativ or greater than maxYear
     */
    public MovieQuery filterYear(int minYear, int maxYear) throws IllegalArgumentException {
        if (minYear < 0 || minYear > maxYear) {
            throw new IllegalArgumentException("min year must not be negativ or greater than max year");
        }
        this.minYear = minYear;
        this.maxYear = maxYear;
        return this;
    }

    /**
     * Sets the range for the movie length to be filtered for
     *
     * @param minLength The least length
     * @param maxLength The greatest length
     * @return this
     * @throws IllegalArgumentException If minLength is negativ or greater than maxLength
     */
    public MovieQuery filterLength(int minLength, int maxLength) throws IllegalArgumentException {
        if (minLength < 0 || minLength > maxLength) {
            throw new IllegalArgumentException("min length must not be negativ or greater than max length");
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
        return this;
    }

    /**
     * Creates a new {@link MovieFilter} from the stored filter values
     * and executes the {@link Query} returned from {@link Movie#createMovieQueryForFilter(MovieFilter)}
     * for this filter
     *
     * @return The resulting {@link List} of {@link Movie movies} of the query execution
     */
    @Override
    public List<Movie> get() {
        final MovieFilter filter = new MovieFilter(
                getDescription,
                getCategory,
                getYear,
                getLength,
                getActors,
                search,
                categories,
                minYear,
                maxYear,
                minLength,
                maxLength
        );
        return Movie.createMovieQueryForFilter(filter).get();
    }
}
