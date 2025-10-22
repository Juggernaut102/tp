package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.subject.Subject;


/**
 * Deletes a subject from the list of allowed subjects.
 */
public class DeleteSubjectCommand extends Command {

    public static final String COMMAND_WORD = "delsub";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a subject from the list of allowed subjects.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SUBJECT_SUCCESS = "Deleted Subject: %1$s";

    private final Index targetIndex;

    public DeleteSubjectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Subject> lastShownList = model.getSubjectList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBJECT_DISPLAYED_INDEX);
        }

        Subject subjectToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteSubject(subjectToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SUBJECT_SUCCESS, Messages.format(subjectToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteSubjectCommand)) {
            return false;
        }

        DeleteSubjectCommand otherDeleteSubjectCommand = (DeleteSubjectCommand) other;
        return targetIndex.equals(otherDeleteSubjectCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
