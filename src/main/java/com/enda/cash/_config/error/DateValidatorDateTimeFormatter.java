package com.enda.cash._config.error;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateValidatorDateTimeFormatter {
    public static boolean isValid(final String date) {

        boolean valid = false;

        try {

            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            .withResolverStyle(ResolverStyle.STRICT)
            );

            valid = true;

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            valid = false;
        }

        return valid;
    }
}
