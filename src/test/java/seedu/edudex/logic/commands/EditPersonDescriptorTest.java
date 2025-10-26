package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_DAY_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_STARTTIME_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.edudex.logic.parser.EditLessonDescriptor;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different school -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withSchool(VALID_SCHOOL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different lesson index -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withLessonIndex(1).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different day -> returns false
        EditLessonDescriptor lessonDescriptor = new EditLessonDescriptor();
        lessonDescriptor.setDay(new Day(VALID_DAY_BOB));
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEditLessonDescriptor(lessonDescriptor).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different start time -> returns false
        lessonDescriptor = new EditLessonDescriptor();
        lessonDescriptor.setStartTime(new Time(VALID_STARTTIME_BOB));
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEditLessonDescriptor(lessonDescriptor).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getName().orElse(null) + ", phone="
                + editPersonDescriptor.getPhone().orElse(null) + ", school="
                + editPersonDescriptor.getSchool().orElse(null) + ", address="
                + editPersonDescriptor.getAddress().orElse(null) + ", tags="
                + editPersonDescriptor.getTags().orElse(null) + ", lessonIndex="
                + editPersonDescriptor.getLessonIndex().orElse(null) + ", editLessonDescriptor="
                + editPersonDescriptor.getEditLessonDescriptor().orElse(null)
                + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }

    @Test
    public void getLessonIndex_success() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withLessonIndex(0)
                .build();
        assertTrue(descriptor.getLessonIndex().isPresent());
        assertEquals(Index.fromZeroBased(0), descriptor.getLessonIndex().get());
    }

    @Test
    public void getEditLessonDescriptor_success() {
        EditLessonDescriptor lessonDescriptor = new EditLessonDescriptor();
        lessonDescriptor.setDay(new Day("Monday"));

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withEditLessonDescriptor(lessonDescriptor)
                .build();

        assertTrue(descriptor.getEditLessonDescriptor().isPresent());
        assertEquals(lessonDescriptor, descriptor.getEditLessonDescriptor().get());
    }

    @Test
    public void withLessonEdit_allFieldsSpecified_success() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withLessonEdit(0, "Mathematics", "Monday", "10:00", "12:00")
                .build();

        assertTrue(descriptor.getLessonIndex().isPresent());
        assertTrue(descriptor.getEditLessonDescriptor().isPresent());

        EditLessonDescriptor lessonDesc = descriptor.getEditLessonDescriptor().get();
        assertEquals(new Subject("Mathematics"), lessonDesc.getSubject().get());
        assertEquals(new Day("Monday"), lessonDesc.getDay().get());
        assertEquals(new Time("10:00"), lessonDesc.getStartTime().get());
        assertEquals(new Time("12:00"), lessonDesc.getEndTime().get());
    }

    @Test
    public void withLessonEdit_someFieldsNull_success() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withLessonEdit(0, null, "Tuesday", null, null)
                .build();

        assertTrue(descriptor.getLessonIndex().isPresent());
        assertTrue(descriptor.getEditLessonDescriptor().isPresent());

        EditLessonDescriptor lessonDesc = descriptor.getEditLessonDescriptor().get();
        assertFalse(lessonDesc.getSubject().isPresent());
        assertTrue(lessonDesc.getDay().isPresent());
        assertFalse(lessonDesc.getStartTime().isPresent());
        assertFalse(lessonDesc.getEndTime().isPresent());
    }

}
