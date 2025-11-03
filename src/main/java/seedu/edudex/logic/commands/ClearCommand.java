package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.edudex.model.EduDex;
import seedu.edudex.model.Model;

/**
 * Clears EduDex.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "EduDex has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEduDex(new EduDex());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
