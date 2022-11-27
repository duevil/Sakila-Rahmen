package de.softwaretechnik.models;

import java.util.List;
import java.util.Map;

abstract class QueryInternal<T> implements Query<T> {

    protected abstract T buildFromMap(final Map<String, Object> map);

    protected abstract QueryExecutor createQueryExecutor();

    public final List<T> get() {
        return QueryExecutor.execute(this);
    }
}
