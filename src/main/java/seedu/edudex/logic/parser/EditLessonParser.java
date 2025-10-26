package seedu.edudex.logic.parser;

import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.edudex.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an EditLessonDescriptor object
 */
public class EditLessonParser {

    /**
     * Parses the given {@code ArgumentMultimap} and returns an EditLessonDescriptor object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLessonDescriptor parse(ArgumentMultimap argMultimap) throws ParseException {
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();

        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editLessonDescriptor.setSubject(
                    ParserUtil.parseSubjectName(argMultimap.getValue(PREFIX_SUBJECT).get()));
        }
        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            editLessonDescriptor.setDay(
                    ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get()));
        }
        if (argMultimap.getValue(PREFIX_START).isPresent()) {
            editLessonDescriptor.setStartTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get()));
        }
        if (argMultimap.getValue(PREFIX_END).isPresent()) {
            editLessonDescriptor.setEndTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_END).get()));
        }

        return editLessonDescriptor;
    }
}
