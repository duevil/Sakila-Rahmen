package de.softwaretechnik.models;

import java.util.List;

public interface Query<T> {
    List<T> get() throws QueryException;
}
