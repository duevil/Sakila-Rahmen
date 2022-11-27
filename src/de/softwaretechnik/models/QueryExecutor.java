package de.softwaretechnik.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class QueryExecutor {
    private final Statement statement;
    private final String sql;

    QueryExecutor(final PreparedStatement statement) {
        this(statement, null);
    }

    QueryExecutor(final String sql) {
        this(null, sql);
    }

    private QueryExecutor(final Statement statement, final String sql) {
        this.statement = statement;
        this.sql = sql;
    }

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

    private List<Map<String, Object>> getMapList(final Statement statement) throws SQLException {
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

    private List<Map<String, Object>> getMapList() throws SQLException {
        if (statement != null) {
            List<Map<String, Object>> mapList = getMapList(statement);
            if (!(statement instanceof PreparedStatement)) statement.close();
            return mapList;
        } else try (Statement stmt = DBModel.getInstance().getConnection().createStatement()) {
            return getMapList(stmt);
        }
    }
}
