package de.softwaretechnik.models;

import de.softwaretechnik.utils.WordUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Class for storing and querying movie data
 *
 * @author Malte Kasolosky
 */
public final class Movie {
    private static final Movie NULL_MOVIE
            = new Movie(-1, null, null, null, null, null, null);
    private static final BiFunction<MovieFilter, Map<String, Object>, Movie> MAPPER
            = (filter, map) -> Optional.ofNullable((Integer) map.get("film_id")).map(
            movieId -> new Movie(
                    movieId,
                    WordUtils.capitalizeFully((String) map.get("title")),
                    (String) map.get("description"),
                    ((QueryInternal<Category>) Category.getQuery()).buildFromMap(map),
                    filter.getYear()
                            ? ((java.sql.Date) map.get("release_year")).toLocalDate().getYear()
                            : null,
                    (Integer) map.get("length"),
                    filter.getActors()
                            ? Actor.getQueryForMovieID(movieId).get()
                            : null
            )).orElse(NULL_MOVIE);
    private final int id;
    private final String title;
    private final String description;
    private final Category category;
    private final Integer releaseYear;
    private final Integer length;
    private final List<Actor> actors;

    /**
     * Privat constructor, as an instantiation will only be performed within an underlying mapper function
     *
     * @param id          The movie id to be stored
     * @param title       The movie title to be stored
     * @param description The movie description to be stored
     * @param category    The movie category to be stored
     * @param releaseYear The movie release year to be stored
     * @param length      The movie length to be stored
     * @param actors      The movie actors to be stored
     */
    private Movie(
            final int id,
            final String title,
            final String description,
            final Category category,
            final Integer releaseYear,
            final Integer length,
            final List<Actor> actors
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.releaseYear = releaseYear;
        this.length = length;
        this.actors = actors;
    }

    /**
     * Used in {@link MovieQuery} to create a {@link Query} for a {@link MovieFilter}
     *
     * @param filter The filter the query should be created for
     * @return A new Implementation of {@link QueryInternal}
     */
    static Query<Movie> createMovieQueryForFilter(final MovieFilter filter) {
        return new QueryInternal<>() {
            @Override
            protected Movie buildFromMap(final Map<String, Object> map) {
                return MAPPER.apply(filter, map);
            }

            @Override
            protected QueryExecutor createQueryExecutor() {
                return new QueryExecutor(filter.toSQL());
            }
        };
    }

    /**
     * Getter for the movie id
     *
     * @return The movie id
     */
    public int id() {
        return id;
    }

    /**
     * Getter for the movie description
     *
     * @return The movie description
     */
    public String description() {
        return description;
    }

    /**
     * Getter for the movie category
     *
     * @return The movie category
     */
    public Category category() {
        return category;
    }

    /**
     * Getter for the movie actors
     *
     * @return A {@link List} containing the movie actors
     */
    public List<Actor> actors() {
        return actors;
    }

    /**
     * Creates a String representation of the movie
     *
     * @return A String containing the movie name and, if either is not null, the release year and length
     */
    @Override
    public String toString() {
        String s = String.valueOf(title);
        if (releaseYear != null) s += " (" + releaseYear + ')';
        if (length != null) s += " [" + length + ']';
        return s;
    }
}
