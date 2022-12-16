package de.softwaretechnik.views;


import de.softwaretechnik.models.Movie;

import java.util.Set;

public class SelectedFilter {
    private String textSearch = "";
    private String genre = "";
    private int minLength = -1;
    private int maxLength = -1;
    private int fromYear = -1;
    private int toYear = -1;
    private String[] actors;


    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isGenre(String movieGenre) {
        if (genre.isBlank()) return true;
        return movieGenre.equals(genre);
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isLengthRange(Movie m) {
        if (minLength == -1 && maxLength == -1) return true;
        int movieLength = Integer.parseInt(
                m.toString()
                        .substring(
                                m.toString().indexOf("[") + 1,
                                m.toString().lastIndexOf("]")));
        if (minLength == -1) return movieLength >= maxLength;
        if (maxLength == -1) return movieLength <= minLength;
        return movieLength >= minLength && movieLength <= maxLength;
    }

    public int getFromYear() {
        return fromYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public int getToYear() {
        return toYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    /**
     * Checks, whether the movie was released in the specified range by extracting the release year from the movie and
     * testing, whether it's above the lower limit and under the upper limit.
     * @param m The {@link Movie} being checked
     * @return True, if the Movie was released within the specified range and false, if the movies release year falls
     * outside the specified range.
     */
    public boolean isYearRange(Movie m) {
        if (fromYear == -1 && toYear == -1) return true;
        int movieYear = Integer.parseInt(
                m.toString()
                        .substring(
                                m.toString().indexOf("(") + 1,
                                m.toString().lastIndexOf(")")));
        if (fromYear == -1) return movieYear >= toYear;
        if (toYear == -1) return movieYear <= fromYear;
        return movieYear >= fromYear && movieYear <= toYear;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String... actors) {
        this.actors = actors;
    }

    public boolean hasActors(Movie m) {
        if (actors.length == 0) return true;
        return m.actors().stream().anyMatch(e -> Set.of(actors).contains(e.toString()));
    }
}
