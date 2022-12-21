package de.softwaretechnik.utils;

import java.util.Locale;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * Utility class for word formatting
 *
 * @author Malte Kasolowsky
 */
public final class WordUtils {
    /**
     * Private constructor as instantiation is not needed for utility class
     */
    private WordUtils() {
    }

    /**
     * Capitalizes each word of the String;
     * every first letter of a word changed to upper case, all following to lower case;
     * a word is delimited with a simple white space character (' ')
     *
     * @param str The String to capitalize
     * @return The capitalized String
     */
    public static String capitalizeFully(final String str) {
        final var sj = new StringJoiner(" ");
        final var st = new StringTokenizer(str, " ");
        while (st.hasMoreTokens()) {
            final String s = st.nextToken();
            final var sb = new StringBuilder(s.length() + 1);
            sb.append(Character.toUpperCase(s.charAt(0)));
            sb.append(s.substring(1).toLowerCase(Locale.ROOT));
            sj.add(sb);
        }
        return sj.toString();
    }
}
