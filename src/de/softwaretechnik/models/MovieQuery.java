package de.softwaretechnik.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    MovieQuery() {
    }

    public MovieQuery withDescription() {
        this.getDescription = true;
        return this;
    }

    public MovieQuery withCategory() {
        this.getCategory = true;
        return this;
    }

    public MovieQuery withYear() {
        this.getYear = true;
        return this;
    }

    public MovieQuery withLength() {
        this.getLength = true;
        return this;
    }

    public MovieQuery withActors() {
        this.getActors = true;
        return this;
    }

    public MovieQuery filterName(String search) {
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

    public MovieQuery filterCategories(Category category) {
        if (category == null) throw new IllegalArgumentException("category must not be null");
        this.categories.add(category);
        return this;
    }

    public MovieQuery filterCategories(Category[] categories) {
        if (categories == null) throw new IllegalArgumentException("category array must not be null");
        for (Category category : categories) filterCategories(category);
        return this;
    }

    public MovieQuery filterYear(int minYear, int maxYear) {
        if (minYear < 0 || minYear > maxYear) {
            throw new IllegalArgumentException("min year must not be negativ or greater than max year");
        }
        this.minYear = minYear;
        this.maxYear = maxYear;
        return this;
    }

    public MovieQuery filterLength(int minLength, int maxLength) {
        if (minLength < 0 || minLength > maxLength) {
            throw new IllegalArgumentException("min length must not be negativ or greater than max length");
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
        return this;
    }

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
        return Movie.getMovieQueryForFilter(filter).get();
    }
}
