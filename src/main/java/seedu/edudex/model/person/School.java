package seedu.edudex.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's school in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchool(String)}
 */
public class School {
    public static final String MESSAGE_CONSTRAINTS =
            "Schools should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the school name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code School}.
     *
     * @param school A valid address.
     */
    public School(String school) {
        requireNonNull(school);
        checkArgument(isValidSchool(school), MESSAGE_CONSTRAINTS);
        value = school;
    }

    /**
     * Returns true if a given string is a valid school.
     */
    public static boolean isValidSchool(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof School)) {
            return false;
        }

        School otherSchool = (School) other;
        return value.equals(otherSchool.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
