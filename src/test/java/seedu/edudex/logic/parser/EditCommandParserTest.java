package seedu.edudex.logic.parser;

import static seedu.edudex.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edudex.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.edudex.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.edudex.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.edudex.logic.commands.CommandTestUtil.INVALID_SCHOOL_DESC;
import static seedu.edudex.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.edudex.logic.commands.CommandTestUtil.LESSON_DESC_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.edudex.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_DAY_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_ENDTIME_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_STARTTIME_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edudex.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edudex.logic.parser.EditCommandParser.MESSAGE_CANNOT_EDIT_BOTH;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.EditCommand;
import seedu.edudex.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.School;
import seedu.edudex.model.tag.Tag;
import seedu.edudex.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_SCHOOL_DESC, School.MESSAGE_CONSTRAINTS); // invalid school
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid school
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + SCHOOL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_SCHOOL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + SCHOOL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withSchool(VALID_SCHOOL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + SCHOOL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withSchool(VALID_SCHOOL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // school
        userInput = targetIndex.getOneBased() + SCHOOL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withSchool(VALID_SCHOOL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // lessons
        userInput = targetIndex.getOneBased() + LESSON_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withLessonEdit(1, VALID_SUBJECT_AMY,
                VALID_DAY_AMY, VALID_STARTTIME_AMY, VALID_ENDTIME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_SCHOOL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_SCHOOL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_SCHOOL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_SCHOOL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_lessonEdit_success() {
        // edit 1 lesson field
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = "";
        EditPersonDescriptor descriptor;


        userInput = targetIndex.getOneBased() + " " + PREFIX_LESSON + "1 " + PREFIX_DAY + VALID_DAY_AMY;
        descriptor = new EditPersonDescriptorBuilder()
                .withLessonEdit(0, null, VALID_DAY_AMY, null, null)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // edit all lesson fields
        userInput = targetIndex.getOneBased() + LESSON_DESC_AMY; // lesson index 2 in DESC_AMY is 1 in 0-based index
        descriptor = new EditPersonDescriptorBuilder()
                .withLessonEdit(1, VALID_SUBJECT_AMY, VALID_DAY_AMY, VALID_STARTTIME_AMY, VALID_ENDTIME_AMY)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_lessonEditWithPersonFields_failure() {
        // lesson edit with person fields
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LESSON + "1 "
                + PREFIX_DAY + VALID_DAY_AMY + NAME_DESC_AMY;

        assertParseFailure(parser, userInput, MESSAGE_CANNOT_EDIT_BOTH);
    }

    @Test
    public void parse_lessonEditNoLessonFields_failure() {
        // lesson edit without lesson fields
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LESSON + "1";

        assertParseFailure(parser, userInput, EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_lessonEditMissingLessonIndex_failure() {
        // lesson edit without lesson index
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LESSON + PREFIX_DAY + VALID_DAY_AMY;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_lessonEditInvalidLessonIndex_failure() {
        // lesson edit with invalid lesson index
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LESSON + "a "
                + PREFIX_DAY + VALID_DAY_AMY;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

}
