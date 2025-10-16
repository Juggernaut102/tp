package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s day matches the given {@code Day} (case-insensitive).
 */
public class DayMatchesPredicate implements Predicate<Person> {
    private final Day day;

    public DayMatchesPredicate(Day day) {
        this.day = day;
    }

    @Override
    public boolean test(Person person) {
        if (person.getSubject() == null) {
            return false;
        }
        // Match day, ignoring case
        return person.getSubject().getDay() != null
                && person.getSubject().getDay().equals(day);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DayMatchesPredicate
                && day.equals(((DayMatchesPredicate) other).day));
    }

    @Override
    public String toString() {
        return String.format("DayMatchesPredicate{day=%s}", day);
    }
}
