package seedu.edudex.model.person;

import java.util.function.Predicate;

import seedu.edudex.model.subject.Subject;

/**
 * Represents a predicate that checks if a {@code Person} has at least one {@code Lesson}
 * with a {@code Subject} that matches the given subject name (case-insensitive).
 */
public class SubjectMatchesPredicate implements Predicate<Person> {

    private final String subjectName;

    /**
     * Constructs a {@code SubjectMatchesPredicate} with the specified subject name.
     *
     * @param subjectName The subject name to match against lessons.
     */
    public SubjectMatchesPredicate(String subjectName) {
        this.subjectName = subjectName.trim();
    }

    /**
     * Tests whether the given {@code Person} has any lessons with a subject
     * that matches the specified subject name.
     *
     * @param person The person to test.
     * @return True if the person has at least one matching lesson, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        if (person.getLessons() == null || person.getLessons().isEmpty()) {
            return false;
        }

        Subject targetSubject = new Subject(subjectName);

        // Strictly match only lessons whose Subject is the same (not substring)
        return person.getLessons().stream()
                .anyMatch(lesson -> lesson.getSubject().isSameSubject(targetSubject));
    }

    /**
     * Checks whether this predicate is equal to another object.
     * Two {@code SubjectMatchesPredicate} instances are equal if their subject names match case-insensitively.
     *
     * @param other The object to compare against.
     * @return True if both predicates are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SubjectMatchesPredicate
                && subjectName.equalsIgnoreCase(((SubjectMatchesPredicate) other).subjectName));
    }

    /**
     * Returns the subject keyword used in this predicate.
     *
     * @return The subject name string.
     */
    public String getSubjectKeyword() {
        return subjectName;
    }

    /**
     * Returns a string representation of this predicate for debugging.
     *
     * @return A string showing the subject name used in the predicate.
     */
    @Override
    public String toString() {
        return String.format("SubjectMatchesPredicate{subject=%s}", subjectName);
    }
}
