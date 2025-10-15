package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Day;
import seedu.address.model.person.DayMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY);

        // find by day (e.g. "find d/Monday")
        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            String dayValue = argMultimap.getValue(PREFIX_DAY).get().trim();
            if (dayValue.isEmpty() || !Day.isValidDay(dayValue)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindCommand.MESSAGE_USAGE + "\n\n" + Day.MESSAGE_CONSTRAINTS));
            }
            return new FindCommand(new DayMatchesPredicate(new Day(dayValue)));
        }

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            // ensures that user did not use find with no keywords
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // If d/ prefix is not provided, the default is to treat the arguments as name keywords
        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
