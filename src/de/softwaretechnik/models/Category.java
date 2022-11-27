package de.softwaretechnik.models;

import java.util.Map;
import java.util.Optional;

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

    private Category(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    static Query<Category> getQuery() {
        return QUERY;
    }

    int id() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((Category) o).id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
