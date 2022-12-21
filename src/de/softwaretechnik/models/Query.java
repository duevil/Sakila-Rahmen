package de.softwaretechnik.models;

import java.util.List;

/**
 * Interface for querying elements
 *
 * @param <T> The type of the elements to query
 * @author Malte Kasolowsky
 */
public interface Query<T> {
    /**
     * Executes the query
     *
     * @return A {@link List} containing the queried elements
     * @throws QueryException If a query failure occurs
     */
    List<T> get() throws QueryException;
}
