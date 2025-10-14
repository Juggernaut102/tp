package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Day in the address book.
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
        this.day = day;
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
}
