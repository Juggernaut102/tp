package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.edudex.logic.commands.AddSubjectCommand;
import seedu.edudex.logic.parser.exceptions.ParseException;
import seedu.edudex.model.subject.Subject;

/**
 * Parses input arguments and creates a new AddSubjectCommand object
 */
public class AddSubjectCommandParser implements Parser<AddSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSubjectCommand
     * and returns a AddSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubjectCommand parse(String args) throws ParseException {
        try {
            Subject subject = ParserUtil.parseSubjectName(args);
            return new AddSubjectCommand(subject);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubjectCommand.MESSAGE_USAGE), pe);
        }
    }

}
