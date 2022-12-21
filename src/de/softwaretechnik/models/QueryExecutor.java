package de.softwaretechnik.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class for executing a {@link Query}
 *
 * @author Malte Kasolowsky
 */
public final class QueryExecutor {
    private final Statement statement;
    private final String sql;

    /**
     * Constructs an executor based on a {@link PreparedStatement}
     *
     * @param statement The prepared statement to execute a query for
     */
    QueryExecutor(final PreparedStatement statement) {
        this(statement, null);
    }

    /**
     * Constructs an executor based on a plain SQL statement string
     *
     * @param sql The SQL statement to execute a query for
     */
    QueryExecutor(final String sql) {
        this(null, sql);
    }

    /**
     * Private constructor for storing a {@link Statement} or a SQL statement string;
     * if the statement is left empty, a new statement will be created on execution,
     * if the statement is not a {@link PreparedStatement}, an exception might be thrown during execution
     *
     * @param statement The statement to query for
     * @param sql       The SQL statement to execute
     */
    private QueryExecutor(final Statement statement, final String sql) {
        this.statement = statement;
        this.sql = sql;
    }

    /**
     * Executes a {@link QueryInternal}
     *
     * @param query The query to execute
     * @param <T>   The type of the queries elements
     * @return A {@link List} with the queried elements
     * @throws QueryException If a {@link SQLException} is thrown during execution
     */
    static <T> List<T> execute(final QueryInternal<T> query) throws QueryException {
        try {
            return query.createQueryExecutor()
                    .getMapList()
                    .stream()
                    .map(query::buildFromMap)
                    .toList();
        } catch (SQLException e) {
            throw new QueryException(e);
        }
    }

    /**
     * Executes a SQL query for the given {@link Statement} and reads the resulting data into a {@link Map}
     * pairing the values with their column name
     *
     * @param statement The statement to execute
     * @param sql       The sql string to execute a normal statement for;
     *                  can be null if the statement is a {@link PreparedStatement}
     * @return A map with the values paired with their name
     * @throws SQLException The exception thrown during execution if one was thrown
     */
    private static List<Map<String, Object>> getMapList(final Statement statement, final String sql) throws SQLException {
        final var resultSet = statement instanceof PreparedStatement preparedStmt
                ? preparedStmt.executeQuery()
                : statement.executeQuery(sql);
        final var resultSetMetaData = resultSet.getMetaData();
        final var mapList = new LinkedList<Map<String, Object>>();
        final int columnCount = resultSetMetaData.getColumnCount();

        while (resultSet.next()) {
            final var map = new HashMap<String, Object>();
            mapList.add(map);
            for (int i = 1; i <= columnCount; i++) {
                map.put(
                        resultSetMetaData.getColumnLabel(i),
                        resultSet.getObject(i, statement.getConnection().getTypeMap())
                );
            }
        }

        return mapList;
    }

    /**
     * Checks whether a non-null {@link Statement} was passed initially;
     * calls {@link QueryExecutor#getMapList(Statement, String)} for the statement
     * or a newly created statement;
     * if the statement is not a {@link PreparedStatement} or was newly created,
     * it will be {@link AutoCloseable#close() closed}
     *
     * @return The resulting {@link Map} with the queried values paired with the column names
     * @throws SQLException The exception thrown during the execution or the creation of new statement
     *                      if one was thrown
     */
    private List<Map<String, Object>> getMapList() throws SQLException {
        if (statement != null) {
            List<Map<String, Object>> mapList = getMapList(statement, sql);
            if (!(statement instanceof PreparedStatement)) statement.close();
            return mapList;
        } else try (Statement stmt = DBModel.getInstance().getConnection().createStatement()) {
            return getMapList(stmt, sql);
        }
    }
}
