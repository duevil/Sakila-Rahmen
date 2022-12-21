package de.softwaretechnik.models;

import java.util.Map;
import java.util.Optional;

/**
 * Class for storing and querying category data
 *
 * @author Malte Kasolowsky
 */
public final class Category {
    private static final String SQL = "SELECT category_id, name FROM category";
    private static final Query<Category> QUERY = new QueryInternal<>() {
        @Override
        protected Category buildFromMap(final Map<String, Object> map) {
            return Optional.ofNullable((Integer) map.get("category_id"))
                    .map(cID -> new Category(cID, (String) map.get("name")))
                    .orElse(null);
        }

        @Override
        protected QueryExecutor createQueryExecutor() {
            return new QueryExecutor(SQL);
        }
    };
    private final int id;
    private final String name;

    /**
     * Private constructor, as an instantiation will only be performed by the underlying {@link Query}
     *
     * @param id   The id of the category to be stored
     * @param name The name of the category to be stored
     */
    private Category(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the underlying {@link Query} for Categories
     *
     * @return The underlying query
     */
    static Query<Category> getQuery() {
        return QUERY;
    }

    /**
     * Getter for category id
     *
     * @return The id of the category
     */
    int id() {
        return id;
    }

    /**
     * Creates a String representation of the category
     *
     * @return The category name or "null" if the name is null
     */
    @Override
    public String toString() {
        return String.valueOf(name);
    }

    /**
     * Implements {@link Object#equals(Object)} to check for equal category id,
     * if the passed object is also a category
     *
     * @param o The object to compare against
     * @return true, if the object is a category and both ids are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((Category) o).id;
    }

    /**
     * Implements {@link Object#hashCode()} as the id
     *
     * @return The category id
     */
    @Override
    public int hashCode() {
        return id;
    }
}
