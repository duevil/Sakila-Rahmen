package de.softwaretechnik.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused") // TODO: remove suppression
public final class MovieQuery implements Query<Movie> {
    private final Set<Category> categories = new HashSet<>();
    private boolean getDescription = false;
    private boolean getCategory = false;
    private boolean getYear = false;
    private boolean getLength = false;
    private boolean getActors = false;
    private String search = null;

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
        if (search.length() < 4) {
            throw new IllegalArgumentException("search parameter has to be at least 4 characters long");
        }
        this.search = search;
        return this;
    }

    public MovieQuery filterCategories(Category category, Category... categories) {
        return filterCategories(new Category[]{category}).filterCategories(categories);
    }

    public MovieQuery filterCategories(Category[] categories) {
        if (categories == null) throw new IllegalArgumentException("category array must not be null");
        for (Category category : categories) {
            if (category == null) throw new IllegalArgumentException("category must not be null");
            this.categories.add(category);
        }
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
                categories
        );
        return Movie.getMovieQueryForFilter(filter).get();
    }
}