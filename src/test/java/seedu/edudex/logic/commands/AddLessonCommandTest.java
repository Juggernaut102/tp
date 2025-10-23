package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.edudex.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.testutil.LessonBuilder;

/**
 * Unit tests for AddLessonCommand.
 */
public class AddLessonCommandTest {

    private static final Index DEFAULT_INDEX = Index.fromOneBased(1);

    @Test
    public void constructor_nullStudentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddLessonCommand(null, new LessonBuilder().build()));
    }

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddLessonCommand(DEFAULT_INDEX, null));
    }

    @Test
    public void equals() {
        Lesson lessonA = new LessonBuilder().withSubject("Math").build();
        Lesson lessonB = new LessonBuilder().withSubject("Physics").build();

        AddLessonCommand commandA1 = new AddLessonCommand(DEFAULT_INDEX, lessonA);
        AddLessonCommand commandA2 = new AddLessonCommand(DEFAULT_INDEX, lessonA);
        AddLessonCommand commandB = new AddLessonCommand(DEFAULT_INDEX, lessonB);

        // same object -> true
        assertEquals(commandA1, commandA1);

        // same values -> true
        assertEquals(commandA1, commandA2);

        // different lesson -> false
        assertNotEquals(commandA1, commandB);

        // different types -> false
        assertNotEquals("string", commandA1);

        // null -> false
        assertNotEquals(null, commandA1);
        assertNotEquals(null, commandA2);
        assertNotEquals(null, commandB);
    }

    // need to test that subjects not added are not allowed
}
