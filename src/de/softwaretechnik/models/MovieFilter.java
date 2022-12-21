package de.softwaretechnik.models;

import java.util.Set;
import java.util.StringJoiner;

/**
 * Record for filtering a {@link MovieQuery}
 *
 * @param getDescription Whether the movie description should be queried or not
 * @param getCategory    Whether the movie category should be queried or not
 * @param getYear        Whether the movie release year should be queried or not
 * @param getLength      Whether the movie length should be queried or not
 * @param getActors      Whether the movie actors should be queried or not
 * @param search         A string to search the movie titles
 * @param categories     The categories for which movies should only be queried
 * @param minYear        The year a movies release year must be later or equal to
 * @param maxYear        The year a movies release year must be earlier or equal to
 * @param minLength      The length a movies length must be greater or equal to
 * @param maxLength      The length a movies length must be smaller or equal to
 * @author Malte Kasolowsky
 */
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
    /**
     * Creates a SQL statement String from the filter's values
     *
     * @return A String for querying movies by this filter
     */
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
