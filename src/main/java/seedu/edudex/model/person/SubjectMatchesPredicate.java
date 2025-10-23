package seedu.edudex.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person} has at least one {@code Lesson} with the given {@code Subject} name (case-insensitive).
 */
public class SubjectMatchesPredicate implements Predicate<Person> {

    private final String subjectName;

    public SubjectMatchesPredicate(String subjectName) {
        this.subjectName = subjectName.trim();
    }

    @Override
    public boolean test(Person person) {
        if (person.getLessons() == null || person.getLessons().isEmpty()) {
            return false;
        }

        return person.getLessons().stream()
                .anyMatch(lesson ->
                        lesson.getSubject().toString().equalsIgnoreCase(subjectName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SubjectMatchesPredicate
                && subjectName.equalsIgnoreCase(((SubjectMatchesPredicate) other).subjectName));
    }

    @Override
    public String toString() {
        return String.format("SubjectMatchesPredicate{subject=%s}", subjectName);
    }
}
