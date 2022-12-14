package de.softwaretechnik.models;

import de.softwaretechnik.utils.WordUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Class for storing and querying author data
 *
 * @author Malte Kasolowsky
 */
public final class Actor {
    private static final String SQL = """
            SELECT first_name, last_name
            FROM actor JOIN film_actor USING (actor_id)
            WHERE film_id = ?""";
    private static final PreparedStatement STATEMENT;
    private static final SQLException INSTANTIATION_EXCEPTION;
    private static final Query<Actor> QUERY = new QueryInternal<>() {
        @Override
        protected Actor buildFromMap(final Map<String, Object> map) {
            return new Actor(
                    WordUtils.capitalizeFully((String) map.get("first_name")),
                    WordUtils.capitalizeFully((String) map.get("last_name"))
            );
        }

        @Override
        protected QueryExecutor createQueryExecutor() {
            return new QueryExecutor(STATEMENT);
        }
    };

    static {
        PreparedStatement statement = null;
        SQLException instantiationExecution = null;
        try {
            statement = DBModel.getInstance().getConnection().prepareStatement(SQL);
        } catch (SQLException e) {
            instantiationExecution = e;
        }
        STATEMENT = statement;
        INSTANTIATION_EXCEPTION = instantiationExecution;
    }

    private final String firstName;
    private final String lastName;

    /**
     * Privat constructor, as an instantiation will only be performed by the underlying {@link Query}
     *
     * @param firstName The first name of the author to be stored
     * @param lastName  The last name of the author to be stored
     */
    private Actor(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets the underlying {@link Query} for querying authors for a movie
     *
     * @param movieID The id of a movie the authors should be queried for
     * @return The updated, underlying query
     */
    static Query<Actor> getQueryForMovieID(final int movieID) {
        if (INSTANTIATION_EXCEPTION != null) {
            throw new IllegalStateException(
                    "a failure occurred previously when preparing the SQL statement",
                    INSTANTIATION_EXCEPTION
            );
        }
        try {
            STATEMENT.setInt(1, movieID);
        } catch (SQLException e) {
            throw new QueryException(e);
        }
        return QUERY;
    }

    /**
     * Creates a String representation of the author
     *
     * @return A String containing the author's information
     */
    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}
