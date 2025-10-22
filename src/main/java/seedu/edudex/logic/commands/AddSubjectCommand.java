package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.subject.Subject;

/**
 * Adds a subject to the list of allowed subjects in the subject.json file in data folder.
 */
public class AddSubjectCommand extends Command {

    public static final String COMMAND_WORD = "addsub";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a subject to the list of allowed subjects.\n"
            + "Parameters: SUBJECT\n"
            + "Example: " + COMMAND_WORD + " English";

    public static final String MESSAGE_ADD_SUBJECT_SUCCESS = "New subject added to subject list: %1$s";
    public static final String MESSAGE_DUPLICATE_SUBJECT = "This subject is already in the subject list";

    private final Subject toAdd;

    /**
     * Creates an AddSubjectCommand to add the specified {@code Subject}
     */
    public AddSubjectCommand(Subject subject) {
        requireNonNull(subject);
        toAdd = subject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSubject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUBJECT);
        }

        model.addSubject(toAdd);
        return new CommandResult(String.format(MESSAGE_ADD_SUBJECT_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddSubjectCommand)) {
            return false;
        }

        AddSubjectCommand otherAddSubjectCommand = (AddSubjectCommand) other;
        return toAdd.equals(otherAddSubjectCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddSubject", toAdd)
                .toString();
    }
}
