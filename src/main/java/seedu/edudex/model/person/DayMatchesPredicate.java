package seedu.edudex.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s day matches the given {@code Day} (case-insensitive).
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

    @Override
    public boolean test(Person person) {
        if (person.getLessons() == null || person.getLessons().isEmpty()) {
            return false;
        }

        return person.getLessons().stream()
                .anyMatch(lesson -> lesson.getDay().toString().equalsIgnoreCase(day.toString()));
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
