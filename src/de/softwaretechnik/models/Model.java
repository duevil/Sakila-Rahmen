package de.softwaretechnik.models;

public final class Model {
    private static final Model MODEL = new Model();

    private Model() {
    }

    public static Model getInstance() {
        return MODEL;
    }

    public MovieQuery createMovieQuery() {
        return new MovieQuery();
    }

    public Query<Category> createCategoryQuery() {
        return Category.getQuery();
    }

    public Query<Actor> createActorQueryForMovieID(Movie movie) {
        return Actor.getQueryForMovieID(movie.id());
    }
}
