package seedu.edudex.model.subject.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateSubjectException extends RuntimeException {
    public DuplicateSubjectException() {
        super("Operation would result in duplicate subjects");
    }
}
