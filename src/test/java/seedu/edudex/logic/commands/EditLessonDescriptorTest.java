package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.edudex.logic.parser.EditLessonDescriptor;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;

public class EditLessonDescriptorTest {

    @Test
    public void equals() {
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setSubject(new Subject("Mathematics"));
        descriptor.setDay(new Day("Monday"));
        descriptor.setStartTime(new Time("10:00"));
        descriptor.setEndTime(new Time("12:00"));

        // same values -> returns true
        EditLessonDescriptor descriptorCopy = new EditLessonDescriptor(descriptor);
        assertTrue(descriptor.equals(descriptorCopy));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different type -> returns false
        assertFalse(descriptor.equals(5));

        // different subject -> returns false
        EditLessonDescriptor differentDescriptor = new EditLessonDescriptor(descriptor);
        differentDescriptor.setSubject(new Subject("Physics"));
        assertFalse(descriptor.equals(differentDescriptor));

        // different day -> returns false
        differentDescriptor = new EditLessonDescriptor(descriptor);
        differentDescriptor.setDay(new Day("Tuesday"));
        assertFalse(descriptor.equals(differentDescriptor));

        // different start time -> returns false
        differentDescriptor = new EditLessonDescriptor(descriptor);
        differentDescriptor.setStartTime(new Time("09:00"));
        assertFalse(descriptor.equals(differentDescriptor));

        // different end time -> returns false
        differentDescriptor = new EditLessonDescriptor(descriptor);
        differentDescriptor.setEndTime(new Time("11:00"));
        assertFalse(descriptor.equals(differentDescriptor));
    }

    @Test
    public void hashCodeTest() {
        EditLessonDescriptor descriptor1 = new EditLessonDescriptor();
        descriptor1.setSubject(new Subject("Mathematics"));
        descriptor1.setDay(new Day("Monday"));

        EditLessonDescriptor descriptor2 = new EditLessonDescriptor();
        descriptor2.setSubject(new Subject("Mathematics"));
        descriptor2.setDay(new Day("Monday"));

        assertEquals(descriptor1.hashCode(), descriptor2.hashCode());
    }
}
