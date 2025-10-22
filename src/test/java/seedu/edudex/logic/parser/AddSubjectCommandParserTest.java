package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edudex.testutil.TypicalSubjects.MATH;

import org.junit.jupiter.api.Test;

import seedu.edudex.logic.commands.AddSubjectCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the AddSubjectCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the AddSubjectCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class AddSubjectCommandParserTest {

    private AddSubjectCommandParser parser = new AddSubjectCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "Math", new AddSubjectCommand(MATH));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubjectCommand.MESSAGE_USAGE));
    }
}
