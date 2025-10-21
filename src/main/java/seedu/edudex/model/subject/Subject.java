package seedu.edudex.model.subject;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.commons.util.AppUtil.checkArgument;

/**
 * Represents a Subject in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubjectName(String)}
 */
public class Subject {
    public static final String MESSAGE_CONSTRAINTS =
            "Subject should only be from the list of subjects previously indicated.";
    public final String subjectName;

    /**
     * Constructs a {@code Subject}.
     *
     * @param name A valid subject.
     */
    public Subject(String name) {
        requireNonNull(name);
        // check validity from Subject.json file for allowed subjects
        checkArgument(isValidSubjectName(name), MESSAGE_CONSTRAINTS);
        this.subjectName = name;
    }

    /**
     * Returns true if a given string is a valid subject from the list of tutor subjects.
     */
    public static boolean isValidSubjectName(String name) {
        // Ryan: to clarify: this method is intended just to ensure the string is valid
        // NOT to check if the input by the user is valid (i.e. entered a subject that matches
        // a subject in the subject list)
        return true;
    }

    @Override
    public String toString() {
        return subjectName;
    }

    /**
     * Returns true if both subjects have the same name.
     * This defines a weaker notion of equality between two subjects.
     */
    public boolean isSameSubject(Subject otherSubject) {
        if (otherSubject == this) {
            return true;
        }

        return otherSubject != null
                && otherSubject.subjectName.equals(subjectName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherSubject = (Subject) other;
        return otherSubject.subjectName.equals(this.subjectName);
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }
}
