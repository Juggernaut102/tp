package seedu.edudex.logic.commands;

import static seedu.edudex.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edudex.model.person.Lesson;

/**
 * Unit tests for AddLessonCommand.
 */
public class AddLessonCommandTest {



    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null, null));
    }

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {}

    // need to test that subjects not added are not allowed

    @Test
    public Lesson
}
