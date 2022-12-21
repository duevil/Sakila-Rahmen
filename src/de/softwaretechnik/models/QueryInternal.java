package de.softwaretechnik.models;

import java.util.List;
import java.util.Map;

/**
 * Abstract class for implementing the internal functionality of a {@link Query}
 *
 * @param <T> The type of the elements to query
 * @author Malte Kasolowsky
 */
abstract class QueryInternal<T> implements Query<T> {
    /**
     * Mapper function to create a query result element from a {@link Map},
     * which should contain the values needed for creating the element
     * paired with their column name of the sql query result
     *
     * @param map The map containing the values for creating a return element
     * @return A element with values taken from the map
     */
    protected abstract T buildFromMap(final Map<String, Object> map);

    /**
     * Factory method to create the {@link QueryExecutor} for this query
     *
     * @return The created executor
     */
    protected abstract QueryExecutor createQueryExecutor();

    /**
     * Executes this query using {@link QueryExecutor#execute(QueryInternal)}
     *
     * @return A {@link List} containing the result of the query
     * @throws QueryException If the execution throws this exception
     */
    public final List<T> get() throws QueryException {
        return QueryExecutor.execute(this);
    }
}
