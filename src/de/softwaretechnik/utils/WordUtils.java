package de.softwaretechnik.utils;

import java.util.StringJoiner;
import java.util.StringTokenizer;

public interface WordUtils {
    static String capitalizeFully(final String str) {
        final StringJoiner sj = new StringJoiner(" ");
        final StringTokenizer st = new StringTokenizer(str, " ");
        while (st.hasMoreTokens()) {
            final String s = st.nextToken();
            final StringBuilder sb = new StringBuilder(s.length() + 1);
            sb.append(Character.toUpperCase(s.charAt(0)));
            sb.append(s.substring(1).toLowerCase());
            sj.add(sb);
        }
        return sj.toString();
    }
}
