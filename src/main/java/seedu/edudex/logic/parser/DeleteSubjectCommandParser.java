package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.DeleteSubjectCommand;
import seedu.edudex.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSubjectCommand object
 */
public class DeleteSubjectCommandParser implements Parser<DeleteSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSubjectCommand
     * and returns a DeleteSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSubjectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteSubjectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSubjectCommand.MESSAGE_USAGE), pe);
        }
    }

}
