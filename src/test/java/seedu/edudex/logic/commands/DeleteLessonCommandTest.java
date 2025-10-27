package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edudex.testutil.TypicalLessons.MATH;
import static seedu.edudex.testutil.TypicalLessons.SCIENCE;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.Messages;
import seedu.edudex.model.EduDex;
import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.UserPrefs;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.PersonBuilder;
import seedu.edudex.testutil.TypicalLessons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {

    private Model model = new ModelManager(getTypicalEduDex(), new UserPrefs());

    @Test
    public void execute_validIndexLesson_success() {
        List<Lesson> lessons = TypicalLessons.getTypicalLessons();
        Person student = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()))
                .withLessons(lessons)
                .build();
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), student);

        // Delete the first lesson
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                MATH.getSubject(), student.getName());

        Model expectedModel = new ModelManager(new EduDex(model.getEduDex()), new UserPrefs());
        Person expectedStudent = new PersonBuilder(student).build();
        expectedStudent.setLessons(List.of(SCIENCE)); // expected remaining lesson
        expectedModel.setPerson(student, expectedStudent);

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        Person student = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        Lesson mathLesson = new Lesson(new Subject("Math"), new Day("Monday"),
                new Time("10:00"), new Time("11:00"));
        student.setLessons(List.of(mathLesson));

        // commit changes to model
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), student);

        // There’s only 1 lesson, so index 2 is invalid
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_INDEX);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex, Index.fromOneBased(1));

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLessonCommand deleteFirstLesson = new DeleteLessonCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(1));
        DeleteLessonCommand deleteSecondLesson = new DeleteLessonCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(2));
        DeleteLessonCommand deleteDifferentStudent = new DeleteLessonCommand(INDEX_SECOND_PERSON,
                Index.fromOneBased(1));

        // same object -> true
        assertTrue(deleteFirstLesson.equals(deleteFirstLesson));

        // same values -> true
        DeleteLessonCommand deleteFirstLessonCopy = new DeleteLessonCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        assertTrue(deleteFirstLesson.equals(deleteFirstLessonCopy));

        // different lesson index -> false
        assertFalse(deleteFirstLesson.equals(deleteSecondLesson));

        // different student index -> false
        assertFalse(deleteFirstLesson.equals(deleteDifferentStudent));

        // null -> false
        assertFalse(deleteFirstLesson.equals(null));

        // different type -> false
        assertFalse(deleteFirstLesson.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        Index studentIndex = Index.fromOneBased(1);
        Index lessonIndex = Index.fromOneBased(2);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(studentIndex, lessonIndex);

        String expected = DeleteLessonCommand.class.getCanonicalName()
                + "{studentIndex=" + studentIndex
                + ", lessonIndex=" + lessonIndex + "}";
        assertTrue(deleteLessonCommand.toString().contains(expected));
    }
}
