package de.softwaretechnik.utils;

import java.util.Locale;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public interface WordUtils {
    static String capitalizeFully(final String str) {
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
