package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.DeleteLessonCommand;

public class DeleteLessonCommandParserTest {

    private final DeleteLessonCommandParser parser = new DeleteLessonCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteLessonCommand() {
        DeleteLessonCommand expectedCommand =
                new DeleteLessonCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        assertParseSuccess(parser, "1 2", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_notEnoughArguments_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_tooManyArguments_throwsParseException() {
        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_nonNumeric_throwsParseException() {
        assertParseFailure(parser, "a b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }
}
