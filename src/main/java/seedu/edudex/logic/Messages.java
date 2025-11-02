package seedu.edudex.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.edudex.logic.parser.Prefix;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_SUBJECT_DISPLAYED_INDEX = "The subject index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_LESSON_INDEX =
            "The lesson index provided is invalid.";
    public static final String MESSAGE_NO_LESSONS = "This student has no lessons. "
            + "Add lessons using the addlesson command.";
    public static final String MESSAGE_EMPTY_SUBJECT =
            "Subject cannot be empty. Please enter one of the subjects listed (e.g., find sub/Math).";
    public static final String MESSAGE_INVALID_SUBJECT =
            "This subject is not in the subject list.\n"
            + "Please enter one of the subjects listed (e.g., find sub/Math), "
            + "or add a new subject (e.g. addsub SUBJECT)";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; School: ")
                .append(person.getSchool())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Lessons: ")
                .append("\n")
                .append(person.getLessonsAsString());

        Set<Tag> tags = person.getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        return builder.toString();
    }

    /**
     * Formats the {@code subject} for display to the user.
     */
    public static String format(Subject subject) {
        return subject.toString();
    }
}
