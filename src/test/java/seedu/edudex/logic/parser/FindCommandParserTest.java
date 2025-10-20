package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.edudex.logic.commands.FindCommand;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.DayMatchesPredicate;
import seedu.edudex.model.person.NameContainsKeywordsPredicate;
import seedu.edudex.model.person.SubjectMatchesPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    // ----------------------------------------------
    // Basic validation
    // ----------------------------------------------
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    // ----------------------------------------------
    // Name-based parsing
    // ----------------------------------------------
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    // ----------------------------------------------
    // Day-based parsing
    // ----------------------------------------------
    @Test
    public void parse_validDayPrefix_returnsFindCommand() {
        FindCommand expectedCommand = new FindCommand(new DayMatchesPredicate(new Day("Monday")));
        assertParseSuccess(parser, " d/Monday", expectedCommand);
    }

    @Test
    public void parse_invalidDay_throwsParseException() {
        assertParseFailure(parser, " d/Notaday", Day.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyDayPrefix_throwsParseException() {
        assertParseFailure(parser, " d/",
                String.format(Day.MESSAGE_CONSTRAINTS));
    }

    // ----------------------------------------------
    // Subject-based parsing
    // ----------------------------------------------
    @Test
    public void parse_validSubjectPrefix_returnsFindCommand() {
        FindCommand expectedCommand = new FindCommand(new SubjectMatchesPredicate("Math"));
        assertParseSuccess(parser, " s/Math", expectedCommand);
    }

    @Test
    public void parse_emptySubjectPrefix_throwsParseException() {
        assertParseFailure(parser, " s/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_subjectWithSpaces_trimsAndParsesCorrectly() {
        FindCommand expectedCommand = new FindCommand(new SubjectMatchesPredicate("Science"));
        assertParseSuccess(parser, " s/   Science   ", expectedCommand);
    }
}
