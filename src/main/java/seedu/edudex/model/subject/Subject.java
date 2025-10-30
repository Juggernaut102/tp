package seedu.edudex.model.subject;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.commons.util.AppUtil.checkArgument;

import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;

/**
 * Represents a Subject in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubjectName(String)}
 */
public class Subject {
    public static final String MESSAGE_CONSTRAINTS =
            "Subject should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the subject must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String subjectName;

    /**
     * Constructs a {@code Subject}.
     *
     * @param name A valid subject.
     */
    public Subject(String name) {
        requireNonNull(name);
        checkArgument(isValidSubjectName(name), MESSAGE_CONSTRAINTS);
        this.subjectName = name.toLowerCase();
    }

    /**
     * Returns true if a given string is alphanumeric.
     */
    public static boolean isValidSubjectName(String name) {
        requireNonNull(name);
        return name.matches(VALIDATION_REGEX);
    }

    /**
     * Capitalises each word in the given string.
     */
    public static String capitalizeWords(String str) {
        requireNonNull(str);

        String[] words = str.trim().toLowerCase().split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return capitalized.toString().trim();
    }

    @Override
    public String toString() {
        return capitalizeWords(subjectName);
    }

    /**
     * Returns the string representing this Subject.
     */
    public String getSubjectAsString() {
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
                && otherSubject.subjectName.equalsIgnoreCase(subjectName);
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

    /**
     * Makes a copy of this Subject, and return a new Subject object with the same attributes.
     */
    public Subject getCopyOfSubject() {
        return new Subject(subjectName);
    }

    /**
     * Validates that this subject exists in the given model.
     * Throws a CommandException if it does not.
     *
     * @param model The model to check against.
     * @throws CommandException if the subject is not registered in the model.
     */
    public void ExistsIn(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasSubject(this)) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBJECT);
        }
    }

}
