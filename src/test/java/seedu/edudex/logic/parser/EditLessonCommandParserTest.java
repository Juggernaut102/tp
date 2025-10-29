package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.EditLessonCommand;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;

/**
 * Contains unit tests for EditLessonCommandParser.
 */
public class EditLessonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLessonCommand.MESSAGE_USAGE);

    private EditLessonCommandParser parser = new EditLessonCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no indices specified
        assertParseFailure(parser, " " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);

        // only person index specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1 1", EditLessonCommand.MESSAGE_NOT_EDITED);

        // no indices and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative person index
        assertParseFailure(parser, "-5 1 " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);

        // zero person index
        assertParseFailure(parser, "0 1 " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);

        // negative lesson index
        assertParseFailure(parser, "1 -2 " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);

        // zero lesson index
        assertParseFailure(parser, "1 0 " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // too many indices
        assertParseFailure(parser, "1 2 3 " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);

        // non-numeric person index
        assertParseFailure(parser, "a 1 " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);

        // non-numeric lesson index
        assertParseFailure(parser, "1 b " + PREFIX_DAY + "Monday", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid day
        assertParseFailure(parser, "1 1 " + PREFIX_DAY + "Funday", Day.MESSAGE_CONSTRAINTS);

        // invalid time format for start time
        assertParseFailure(parser, "1 1 " + PREFIX_START + "25:00", Time.MESSAGE_CONSTRAINTS);

        // invalid time format for end time
        assertParseFailure(parser, "1 1 " + PREFIX_END + "invalid", Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index lessonIndex = INDEX_SECOND_PERSON;
        String userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_SUBJECT + "Mathematics"
                + " " + PREFIX_DAY + "Monday"
                + " " + PREFIX_START + "10:00"
                + " " + PREFIX_END + "12:00";

        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setSubject(new Subject("Mathematics"));
        descriptor.setDay(new Day("Monday"));
        descriptor.setStartTime(new Time("10:00"));
        descriptor.setEndTime(new Time("12:00"));

        EditLessonCommand expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index lessonIndex = INDEX_FIRST_PERSON;

        // only day
        String userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_DAY + "Wednesday";
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Wednesday"));
        EditLessonCommand expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // only subject
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_SUBJECT + "Physics";
        descriptor = new EditLessonDescriptor();
        descriptor.setSubject(new Subject("Physics"));
        expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // day and start time
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_DAY + "Tuesday"
                + " " + PREFIX_START + "14:00";
        descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Tuesday"));
        descriptor.setStartTime(new Time("14:00"));
        expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start and end time
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_START + "09:00"
                + " " + PREFIX_END + "11:00";
        descriptor = new EditLessonDescriptor();
        descriptor.setStartTime(new Time("09:00"));
        descriptor.setEndTime(new Time("11:00"));
        expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index lessonIndex = INDEX_FIRST_PERSON;

        // subject
        String userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_SUBJECT + "Mathematics";
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setSubject(new Subject("Mathematics"));
        EditLessonCommand expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // day
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_DAY + "Friday";
        descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Friday"));
        expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_START + "08:00";
        descriptor = new EditLessonDescriptor();
        descriptor.setStartTime(new Time("08:00"));
        expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end time
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_END + "17:00";
        descriptor = new EditLessonDescriptor();
        descriptor.setEndTime(new Time("17:00"));
        expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index lessonIndex = INDEX_FIRST_PERSON;

        // duplicate subject
        String userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_SUBJECT + "Mathematics"
                + " " + PREFIX_SUBJECT + "Physics";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBJECT));

        // duplicate day
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_DAY + "Monday"
                + " " + PREFIX_DAY + "Tuesday";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // duplicate start time
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_START + "10:00"
                + " " + PREFIX_START + "11:00";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START));

        // duplicate end time
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_END + "12:00"
                + " " + PREFIX_END + "13:00";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END));

        // multiple duplicates
        userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_DAY + "Monday"
                + " " + PREFIX_START + "10:00"
                + " " + PREFIX_DAY + "Tuesday"
                + " " + PREFIX_START + "11:00";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY, PREFIX_START));
    }

    @Test
    public void parse_fieldsInDifferentOrder_success() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index lessonIndex = INDEX_SECOND_PERSON;

        // end before start, subject last
        String userInput = personIndex.getOneBased() + " " + lessonIndex.getOneBased()
                + " " + PREFIX_END + "16:00"
                + " " + PREFIX_DAY + "Thursday"
                + " " + PREFIX_START + "14:00"
                + " " + PREFIX_SUBJECT + "Chemistry";

        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setSubject(new Subject("Chemistry"));
        descriptor.setDay(new Day("Thursday"));
        descriptor.setStartTime(new Time("14:00"));
        descriptor.setEndTime(new Time("16:00"));

        EditLessonCommand expectedCommand = new EditLessonCommand(personIndex, lessonIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
