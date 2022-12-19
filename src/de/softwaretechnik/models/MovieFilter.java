package de.softwaretechnik.models;

import java.util.Set;
import java.util.StringJoiner;

record MovieFilter(
        boolean getDescription,
        boolean getCategory,
        boolean getYear,
        boolean getLength,
        boolean getActors,
        String search,
        Set<Category> categories,
        int minYear,
        int maxYear,
        int minLength,
        int maxLength
) {
    String toSQL() {
        final var sb = new StringBuilder();
        sb.append("SELECT f.film_id,title");
        if (getDescription) sb.append(",description");
        if (getCategory || !categories.isEmpty()) sb.append(",fc.category_id");
        if (getCategory) sb.append(",name");
        if (getYear) sb.append(",release_year");
        if (getLength) sb.append(",length");
        sb.append(" FROM film f");
        if (getCategory || !categories.isEmpty()) sb.append(" JOIN film_category fc USING(film_id)");
        if (getCategory) sb.append(" JOIN category USING(category_id)");
        sb.append(" WHERE 1=1 ");
        if (search != null) sb.append(" AND title LIKE '%").append(search).append("%'");
        if (!categories.isEmpty()) {
            final var sj = new StringJoiner(" OR ", " AND ", "");
            for (Category c : categories) sj.add("fc.category_id = " + c.id());
            sb.append(sj);
        }
        sb.append(" AND release_year BETWEEN %d AND %d".formatted(minYear, maxYear + 1));
        sb.append(" AND length BETWEEN %d AND %d".formatted(minLength, maxLength));
        return sb.toString();
    }
}
