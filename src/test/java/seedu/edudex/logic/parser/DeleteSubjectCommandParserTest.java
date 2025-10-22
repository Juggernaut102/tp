package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.edudex.logic.commands.DeleteSubjectCommand;

public class DeleteSubjectCommandParserTest {

    private DeleteSubjectCommandParser parser = new DeleteSubjectCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteSubjectCommand() {
        assertParseSuccess(parser, "1", new DeleteSubjectCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSubjectCommand.MESSAGE_USAGE));
    }
}
