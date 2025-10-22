package seedu.edudex.logic.parser;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.AddLessonCommand;
import seedu.edudex.logic.parser.exceptions.ParseException;
import seedu.edudex.model.person.Lesson;

/**
 * Unit tests for AddLessonCommandParser.
 */
public class AddLessonCommandParserTest {

    private final AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_validArgs_returnsAddLessonCommand() {
        String input = "1 sub/math d/MoNday end/13:00 start/12:00";
        try {
            Lesson lesson = ParserUtil.parseLesson("math", "Monday", "12:00", "13:00");
            AddLessonCommand expected = new AddLessonCommand(Index.fromOneBased(1), lesson);
            assertParseSuccess(parser, input, expected);
        } catch (ParseException pe) {
            fail("Execution threw an unexpected exception: " + pe.getMessage());
        }
    }

    @Test
    public void parse_missingFields_throwsParseException() {
        String inputMissingSubject = "1 d/Monday start/12:00 end/13:00";
        assertParseFailure(parser, inputMissingSubject,
                String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        AddLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String input = "a sub/math d/Monday start/12:00 end/13:00";
        assertParseFailure(parser, input,
                String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        AddLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String input = "1 sub/physics sub/math start/12:00 end/13:00 d/Monday";
        assertParseFailure(parser, input, getErrorMessageForDuplicatePrefixes(PREFIX_SUBJECT));
    }
}
