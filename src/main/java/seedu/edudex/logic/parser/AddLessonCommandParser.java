package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.AddLessonCommand;
import seedu.edudex.logic.parser.exceptions.ParseException;
import seedu.edudex.model.person.Lesson;

public class AddLessonCommandParser implements Parser<AddLessonCommand> {
    @Override
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_DAY, PREFIX_START, PREFIX_END);

        // Ensure all required prefixes and the student index (preamble) are present
        if (!argMultimap.arePrefixesPresent(PREFIX_SUBJECT, PREFIX_DAY, PREFIX_START, PREFIX_END)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT, PREFIX_DAY, PREFIX_START, PREFIX_END);

        // Parse student index (preamble before prefixes)
        Index studentIndex;
        try {
            studentIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE),
                    pe);
        }

        // Parse lesson
        String subjectStr = argMultimap.getValue(PREFIX_SUBJECT).get();
        String dayStr = argMultimap.getValue(PREFIX_DAY).get();
        String startStr = argMultimap.getValue(PREFIX_START).get();
        String endStr = argMultimap.getValue(PREFIX_END).get();

        Lesson lesson = ParserUtil.parseLesson(subjectStr, dayStr, startStr, endStr);
        return new AddLessonCommand(studentIndex, lesson);
    }

}
