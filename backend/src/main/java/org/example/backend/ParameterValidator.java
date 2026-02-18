package org.example.backend;

import org.example.backend.exception.InvalidParameterException;

public class ParameterValidator {
    private static final String ID_NOT_NEGATIVE = "[FAILED] To-do List's ID must not be negative!";

    public static void validateID(long id) {
        if (id < 0) throw new InvalidParameterException(ID_NOT_NEGATIVE);
    }
}
