package seedu.edudex.model.person;

import static seedu.edudex.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;

/**
 * Represents a Time in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should be in the format HH:MM (24-hour format) and should be a valid time.";
    private final LocalTime time;
    private final String timeString;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time);
        this.timeString = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Time)) {
            return false;
        }
        Time otherTime = (Time) other;
        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    /**
     * Makes a copy of this Time, and returns a new Time object with the same attributes.
     */
    public Time getCopyOfTime() {
        return new Time(timeString);
    }
}
