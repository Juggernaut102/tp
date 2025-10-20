package seedu.edudex.model.person;

import static seedu.edudex.commons.util.AppUtil.checkArgument;

/**
 * Represents a Day in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}
 */
public class Day {
    public static final String MESSAGE_CONSTRAINTS =
            "Days should only be one of the following: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday";
    public static final String[] VALID_DAYS =
        {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };
    private final String day;

    /**
     * Constructs a {@code Day}.
     *
     * @param day A valid day.
     */
    public Day(String day) {
        checkArgument(isValidDay(day), MESSAGE_CONSTRAINTS);
        this.day = normalizeDayString(day);
    }

    /**
     * Returns true if a given string is a valid day.
     */
    public static boolean isValidDay(String day) {
        for (String validDay : VALID_DAYS) {
            if (validDay.equalsIgnoreCase(day)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns numeric value of a valid day for comparison
     */
    public int getNumericValue() {
        switch (day.toLowerCase()) {
        case "monday": return 1;
        case "tuesday": return 2;
        case "wednesday": return 3;
        case "thursday": return 4;
        case "friday": return 5;
        case "saturday": return 6;
        case "sunday": return 7;
        default: return 8; // for invalid or unrecognized values
        }
    }

    public String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return day;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && day.equals(((Day) other).day)); // state check
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }

    /**
     * Given an input string, capitalizes the first letter
     */
    private static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * @param input case-insensitive string representing a day of the week
     * @return formatted string representing a day of the week
     */
    private static String normalizeDayString(String input) {
        return capitalize(input.toLowerCase());
    }
}
