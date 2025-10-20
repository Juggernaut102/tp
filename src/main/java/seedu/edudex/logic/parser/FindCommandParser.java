package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Arrays;

import seedu.edudex.logic.commands.FindCommand;
import seedu.edudex.logic.parser.exceptions.ParseException;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.DayMatchesPredicate;
import seedu.edudex.model.person.NameContainsKeywordsPredicate;
import seedu.edudex.model.person.SubjectMatchesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_SUBJECT);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            // ensures that user did not use find with no keywords
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // find by day (e.g. "find d/Monday")
        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            String dayValue = argMultimap.getValue(PREFIX_DAY).get().trim();
            if (dayValue.isEmpty() || !Day.isValidDay(dayValue)) {
                throw new ParseException(String.format(Day.MESSAGE_CONSTRAINTS));
            }
            return new FindCommand(new DayMatchesPredicate(new Day(dayValue)));
        }

        // find by subject (e.g. "find s/Math")
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            String subjectName = argMultimap.getValue(PREFIX_SUBJECT).get().trim();
            if (subjectName.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            return new FindCommand(new SubjectMatchesPredicate(subjectName));
        }

        // If d/ or s/ prefix is not provided, the default is to treat the arguments as name keywords
        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
