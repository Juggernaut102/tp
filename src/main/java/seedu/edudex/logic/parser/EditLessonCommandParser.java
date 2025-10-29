package seedu.edudex.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.EditLessonCommand;
import seedu.edudex.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLessonCommand object
 */
public class EditLessonCommandParser implements Parser<EditLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditLessonCommand
     * and returns an EditLessonCommand object for execution.
     *
     * @param args The user input arguments to parse.
     * @return An EditLessonCommand object with the parsed parameters.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public EditLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_DAY, PREFIX_START, PREFIX_END);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT, PREFIX_DAY, PREFIX_START, PREFIX_END);

        String preamble = argMultimap.getPreamble().trim();
        String[] indices = preamble.split("\\s+");

        // Expect exactly 2 indices: PERSON_INDEX LESSON_INDEX
        if (indices.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditLessonCommand.MESSAGE_USAGE));
        }

        Index personIndex;
        Index lessonIndex;

        try {
            personIndex = ParserUtil.parseIndex(indices[0]);
            lessonIndex = ParserUtil.parseIndex(indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditLessonCommand.MESSAGE_USAGE), pe);
        }

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
            editLessonDescriptor.setStartTime(
                    ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get()));
        }
        if (argMultimap.getValue(PREFIX_END).isPresent()) {
            editLessonDescriptor.setEndTime(
                    ParserUtil.parseTime(argMultimap.getValue(PREFIX_END).get()));
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLessonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLessonCommand(personIndex, lessonIndex, editLessonDescriptor);
    }
}
