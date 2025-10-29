package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.model.person.Lesson.MESSAGE_CONFLICTING_LESSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.parser.EditLessonDescriptor;
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

public class EditLessonCommandTest {
    @Test
    public void execute_editLessonInPersonSuccess() {
        // Setup: Person with a lesson
        Person personWithLesson = new PersonBuilder().withName("Bob").build();
        Lesson originalLesson = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        personWithLesson.setLessons(new ArrayList<>(List.of(originalLesson)));

        Model model = new ModelManager(new EduDex(), new UserPrefs());
        model.addPerson(personWithLesson);
        model.addSubject(new Subject("Mathematics"));
        model.addSubject(new Subject("Physics"));

        // Edit all lesson fields
        EditLessonDescriptor descriptor = new EditLessonDescriptor();

        descriptor.setSubject(new Subject("Physics"));
        descriptor.setDay(new Day("Tuesday"));
        descriptor.setStartTime(new Time("14:00"));
        descriptor.setEndTime(new Time("16:00"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);

        // Create expected model
        Model expectedModel = new ModelManager(new EduDex(), new UserPrefs());
        Person expectedPerson = new PersonBuilder().withName("Bob").build();
        Lesson editedLesson = new Lesson(
                new Subject("Physics"),
                new Day("Tuesday"),
                new Time("14:00"),
                new Time("16:00")
        );
        expectedPerson.setLessons(new ArrayList<>(List.of(editedLesson)));
        expectedModel.addPerson(expectedPerson);
        expectedModel.addSubject(new Subject("Mathematics"));
        expectedModel.addSubject(new Subject("Physics"));

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                expectedPerson.getName(), editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        // Setup: Person with a lesson
        Person personWithLesson = new PersonBuilder().withName("Alice").build();
        Lesson originalLesson = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        personWithLesson.setLessons(new ArrayList<>(List.of(originalLesson)));

        Model model = new ModelManager(new EduDex(), new UserPrefs());
        model.addPerson(personWithLesson);
        model.addSubject(new Subject("Mathematics"));

        // Edit only day and start time
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Wednesday"));
        descriptor.setStartTime(new Time("09:00"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);

        // Create expected model
        Model expectedModel = new ModelManager(new EduDex(), new UserPrefs());
        Person expectedPerson = new PersonBuilder().withName("Alice").build();
        Lesson editedLesson = new Lesson(
                new Subject("Mathematics"),
                new Day("Wednesday"),
                new Time("09:00"),
                new Time("12:00")
        );
        expectedPerson.setLessons(new ArrayList<>(List.of(editedLesson)));
        expectedModel.addPerson(expectedPerson);
        expectedModel.addSubject(new Subject("Mathematics"));

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                expectedPerson.getName(), editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Model model = new ModelManager(new EduDex(), new UserPrefs());
        Person person = new PersonBuilder().withName("Bob").build();
        Lesson lesson = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        person.setLessons(new ArrayList<>(List.of(lesson)));
        model.addPerson(person);
        model.addSubject(new Subject("Mathematics"));

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Tuesday"));

        EditLessonCommand editCommand = new EditLessonCommand(outOfBoundIndex, INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Model model = new ModelManager(new EduDex(), new UserPrefs());
        Person person = new PersonBuilder().withName("Bob").build();
        Lesson lesson = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        person.setLessons(new ArrayList<>(List.of(lesson)));
        model.addPerson(person);
        model.addSubject(new Subject("Mathematics"));

        Index outOfBoundLessonIndex = Index.fromOneBased(2);
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Tuesday"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_FIRST_PERSON, outOfBoundLessonIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LESSON_INDEX);
    }

    @Test
    public void execute_noLessons_failure() {
        Model model = new ModelManager(new EduDex(), new UserPrefs());
        Person person = new PersonBuilder().withName("Bob").build();
        model.addPerson(person);

        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Tuesday"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_NO_LESSONS);
    }

    @Test
    public void execute_startTimeAfterEndTime_failure() {
        Model model = new ModelManager(new EduDex(), new UserPrefs());
        Person person = new PersonBuilder().withName("Nina").build();
        Lesson lesson = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        person.setLessons(new ArrayList<>(List.of(lesson)));
        model.addPerson(person);
        model.addSubject(new Subject("Mathematics"));

        // Try to edit start time to 13:00, making it 13:00-12:00 (invalid)
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setStartTime(new Time("13:00"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, Lesson.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_subjectNotTaught_failure() {
        Model model = new ModelManager(new EduDex(), new UserPrefs());
        Person person = new PersonBuilder().withName("Bob").build();
        Lesson lesson = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        person.setLessons(new ArrayList<>(List.of(lesson)));
        model.addPerson(person);
        model.addSubject(new Subject("Mathematics"));

        // Try to edit to a subject not in the model
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setSubject(new Subject("Physics"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model,
                "You tried to edit a lesson with a subject not taught.\nEither use \"addsub SUBJECT\" "
                        + "to add a subject first."
                        + "\nOr use \"dellesson STUDENT_INDEX LESSON_INDEX\" to delete lesson of old subject");
    }

    @Test
    public void execute_conflictingLessonWithSameStudent_failure() {
        Model model = new ModelManager(new EduDex(), new UserPrefs());
        Person person = new PersonBuilder().withName("Bob").build();
        Lesson lesson1 = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        Lesson lesson2 = new Lesson(
                new Subject("Physics"),
                new Day("Monday"),
                new Time("14:00"),
                new Time("16:00")
        );
        person.setLessons(new ArrayList<>(List.of(lesson1, lesson2)));
        model.addPerson(person);
        model.addSubject(new Subject("Mathematics"));
        model.addSubject(new Subject("Physics"));

        // Try to edit lesson 1 to conflict with lesson 2
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setStartTime(new Time("14:30"));
        descriptor.setEndTime(new Time("15:30"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = MESSAGE_CONFLICTING_LESSON + "\nConflicts with an existing lesson the student has: "
                + lesson2;

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_conflictingLessonWithDifferentStudent_failure() {
        Model model = new ModelManager(new EduDex(), new UserPrefs());

        Person person1 = new PersonBuilder().withName("Alice").build();
        Lesson lesson1 = new Lesson(
                new Subject("Mathematics"),
                new Day("Monday"),
                new Time("10:00"),
                new Time("12:00")
        );
        person1.setLessons(new ArrayList<>(List.of(lesson1)));

        Person person2 = new PersonBuilder().withName("Bob").build();
        Lesson lesson2 = new Lesson(
                new Subject("Physics"),
                new Day("Monday"),
                new Time("14:00"),
                new Time("16:00")
        );
        person2.setLessons(new ArrayList<>(List.of(lesson2)));

        model.addPerson(person1);
        model.addPerson(person2);
        model.addSubject(new Subject("Mathematics"));
        model.addSubject(new Subject("Physics"));

        // Try to edit person2's lesson to conflict with person1's lesson
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setStartTime(new Time("10:30"));
        descriptor.setEndTime(new Time("11:30"));

        EditLessonCommand editCommand = new EditLessonCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = MESSAGE_CONFLICTING_LESSON + "\nConflicts with lesson of: " + person1.getName();
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        EditLessonDescriptor descriptor1 = new EditLessonDescriptor();
        descriptor1.setDay(new Day("Monday"));

        EditLessonDescriptor descriptor2 = new EditLessonDescriptor();
        descriptor2.setDay(new Day("Tuesday"));

        EditLessonCommand command1 = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor1);
        EditLessonCommand command2 = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor1);
        EditLessonCommand command3 = new EditLessonCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON, descriptor1);
        EditLessonCommand command4 = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, descriptor1);
        EditLessonCommand command5 = new EditLessonCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, descriptor2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different type -> returns false
        assertFalse(command1.equals(new ClearCommand()));

        // different person index -> returns false
        assertFalse(command1.equals(command3));

        // different lesson index -> returns false
        assertFalse(command1.equals(command4));

        // different descriptor -> returns false
        assertFalse(command1.equals(command5));
    }

    @Test
    public void toStringMethod() {
        Index personIndex = Index.fromOneBased(1);
        Index lessonIndex = Index.fromOneBased(1);
        EditLessonDescriptor descriptor = new EditLessonDescriptor();
        descriptor.setDay(new Day("Monday"));

        EditLessonCommand command = new EditLessonCommand(personIndex, lessonIndex, descriptor);
        String expected = EditLessonCommand.class.getCanonicalName()
                + "{personIndex=" + personIndex
                + ", lessonIndex=" + lessonIndex
                + ", editLessonDescriptor=" + descriptor + "}";
        assertEquals(expected, command.toString());
    }
}
