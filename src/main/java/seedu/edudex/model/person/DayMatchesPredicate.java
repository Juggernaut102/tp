package seedu.edudex.model.person;

import java.util.function.Predicate;

/**
 * Represents a predicate that checks if a {@code Person} has at least one {@code Lesson}
 * scheduled on the specified {@code Day}, using case-insensitive matching.
 */
public class DayMatchesPredicate implements Predicate<Person> {
    private final Day day;

    /**
     * Constructs a {@code DayMatchesPredicate} with the specified day.
     *
     * @param day The day to match against a person's lessons.
     */
    public DayMatchesPredicate(Day day) {
        this.day = day;
    }

    /**
     * Tests whether the given {@code Person} has any lessons scheduled on the specified day.
     *
     * @param person The person to test.
     * @return True if the person has at least one matching lesson, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        if (person.getLessons() == null || person.getLessons().isEmpty()) {
            return false;
        }

        return person.getLessons().stream()
                .anyMatch(lesson -> lesson.getDay().toString().equalsIgnoreCase(day.toString()));
    }

    /**
     * Checks whether this predicate is equal to another object.
     * Two {@code DayMatchesPredicate} instances are equal if their day values match.
     *
     * @param other The object to compare against.
     * @return True if both predicates are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DayMatchesPredicate
                && day.equals(((DayMatchesPredicate) other).day));
    }

    /**
     * Returns a string representation of this predicate for debugging.
     *
     * @return A string showing the day used in the predicate.
     */
    @Override
    public String toString() {
        return String.format("DayMatchesPredicate{day=%s}", day);
    }
}
