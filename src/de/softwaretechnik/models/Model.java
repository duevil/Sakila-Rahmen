package de.softwaretechnik.models;

/**
 * Singleton class providing functionality to {@link Query} {@link Movie movies},
 * {@link Actor actors} and {@link Category categories}
 *
 * @author Malte Kasolowsky
 */
public final class Model {
    private static final Model MODEL = new Model();

    /**
     * Private constructor, as only one static instantiation for the Singleton will be performed
     */
    private Model() {
    }

    /**
     * Singleton getter
     *
     * @return The model Singleton
     */
    public static Model getInstance() {
        return MODEL;
    }

    /**
     * Factory method for creating a new {@link Query} for querying {@link Movie movies}
     *
     * @return A new {@link MovieQuery}
     */
    public MovieQuery createMovieQuery() {
        return new MovieQuery();
    }

    /**
     * Factory method for creating a new {@link Query} for querying {@link Category categories}
     *
     * @return {@link Category#getQuery()}
     */
    public Query<Category> createCategoryQuery() {
        return Category.getQuery();
    }

    /**
     * Factory method for creating a new {@link Query} for querying {@link Actor actors}
     *
     * @param movie The movie which the authors should be queried for
     * @return {@link Actor#getQueryForMovieID(int)}
     */
    public Query<Actor> createActorQueryForMovieID(Movie movie) {
        return Actor.getQueryForMovieID(movie.id());
    }
}
