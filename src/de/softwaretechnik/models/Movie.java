package de.softwaretechnik.models;

import de.softwaretechnik.utils.WordUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused") // TODO: remove suppression
public final class Movie {
    private static final Movie NULL_MOVIE =
            new Movie(-1, null, null, null, null, null, null);
    private final int id;
    private final String title;
    private final String description;
    private final Category category;
    private final Integer releaseYear;
    private final Integer length;
    private final List<Actor> actors;

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

    static Query<Movie> getMovieQueryForFilter(final MovieFilter filter) {
        return new QueryInternal<>() {
            @Override
            protected Movie buildFromMap(final Map<String, Object> map) {
                return Optional.ofNullable((Integer) map.get("film_id"))
                        .map(movieId -> new Movie(
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
                        ))
                        .orElse(NULL_MOVIE);
            }

            @Override
            protected QueryExecutor createQueryExecutor() {
                return new QueryExecutor(filter.toSQL());
            }
        };
    }

    public int id() {
        return id;
    }

    public String description() {
        return description;
    }

    public Category category() {
        return category;
    }

    public List<Actor> actors() {
        return actors;
    }

    @Override
    public String toString() {
        String s = String.valueOf(title);
        if (releaseYear != null) s += " (" + releaseYear + ')';
        if (length != null) s += " [" + length + ']';
        return s;
    }
}
