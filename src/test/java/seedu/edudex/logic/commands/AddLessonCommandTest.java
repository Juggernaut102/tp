package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static javafx.collections.FXCollections.observableArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.edudex.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.edudex.logic.commands.AddLessonCommand.MESSAGE_SUBJECT_NOT_TAUGHT;
import static seedu.edudex.model.person.Lesson.MESSAGE_CONFLICTING_LESSON;
import static seedu.edudex.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.ReadOnlyUserPrefs;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.LessonBuilder;
import seedu.edudex.testutil.PersonBuilder;

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
        assertNotEquals(commandA2, commandB);

        // different types -> false
        assertNotEquals("string", commandA1);

        // null -> false
        assertNotEquals(null, commandA1);
        assertNotEquals(null, commandA2);
        assertNotEquals(null, commandB);
    }

    @Test
    public void toStringMethod() {
        Lesson lesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand =
                new AddLessonCommand(Index.fromOneBased(1), lesson);
        String expected = AddLessonCommand.class.getCanonicalName()
                + "{studentIndex=" + 1
                + ", lessonToAdd=" + lesson
                + "}";
        assertEquals(expected, addLessonCommand.toString());
    }

    @Test
    public void execute_studentIndexOutOfBounds_throwsCommandException() {
        Person person = new PersonBuilder().build();
        Lesson lesson = new LessonBuilder().build();
        ModelStub modelStub = new ModelStubWithOnePerson(person); // contains only 1 person

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromOneBased(2), lesson);
        assertThrows(CommandException.class,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> addLessonCommand.execute(modelStub));
    }

    @Test
    public void execute_subjectOfLessonNotInSubjectsList_throwsCommandException() {
        Person person = new PersonBuilder().build();
        Lesson lesson = new LessonBuilder().build();
        ModelStub modelStub = new ModelStubWithNoSubjects(person);

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromOneBased(1), lesson);
        assertThrows(CommandException.class,
                MESSAGE_SUBJECT_NOT_TAUGHT, () -> addLessonCommand.execute(modelStub));
    }

    @Test
    public void execute_validArguments_success() {
        Person person = new PersonBuilder().build(); // a student with no lessons
        Lesson lesson = new LessonBuilder().withSubject("Math").build(); // a valid lesson
        ModelStubWithOnePersonAndSubjects modelStub = new ModelStubWithOnePersonAndSubjects(person);

        AddLessonCommand addLessonCommand = new AddLessonCommand(Index.fromOneBased(1), lesson);
        try {
            CommandResult result = addLessonCommand.execute(modelStub);
            String expectedMessage = String.format(AddLessonCommand.MESSAGE_ADD_LESSON_SUCCESS,
                    lesson, person.getName());
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException e) {
            fail("Should not throw an exception.");
        }

        // Check that the lesson was added to the student in the model
        Person updatedPerson = modelStub.getPerson();
        assertEquals(1, updatedPerson.getLessons().size());
        assertEquals(lesson, updatedPerson.getLessons().get(0));
    }

    @Test
    public void execute_conflictingLessonWithinSameStudent_throwsCommandException() {
        Lesson existingLesson = new LessonBuilder()
                .withSubject("Math")
                .withDay("Monday")
                .withStartTime("10:00")
                .withEndTime("12:00")
                .build();

        Lesson conflictingLesson1 = new LessonBuilder()
                .withSubject("Math")
                .withDay("Monday")
                .withStartTime("10:30")
                .withEndTime("11:30")
                .build();

        Lesson conflictingLesson2 = new LessonBuilder()
                .withSubject("Math")
                .withDay("Monday")
                .withStartTime("11:30")
                .withEndTime("12:30")
                .build();

        Lesson conflictingLesson3 = new LessonBuilder()
                .withSubject("Math")
                .withDay("Monday")
                .withStartTime("09:30")
                .withEndTime("10:30")
                .build();

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(existingLesson);
        Person person = new PersonBuilder()
                .withLessons(lessons)
                .build();

        ModelStub modelStub = new ModelStubWithOnePersonAndSubjects(person);
        AddLessonCommand addLessonCommand1 =
                new AddLessonCommand(Index.fromOneBased(1), conflictingLesson1);
        AddLessonCommand addLessonCommand2 =
                new AddLessonCommand(Index.fromOneBased(1), conflictingLesson2);
        AddLessonCommand addLessonCommand3 =
                new AddLessonCommand(Index.fromOneBased(1), conflictingLesson3);

        String expectedMessage = MESSAGE_CONFLICTING_LESSON
                + "\nConflicts with an existing lesson the student has: " + existingLesson;
        assertThrows(CommandException.class, expectedMessage, () -> addLessonCommand1.execute(modelStub));
        assertThrows(CommandException.class, expectedMessage, () -> addLessonCommand2.execute(modelStub));
        assertThrows(CommandException.class, expectedMessage, () -> addLessonCommand3.execute(modelStub));
    }



    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getEduDexFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEduDexFilePath(Path eduDexFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEduDex(ReadOnlyEduDex newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEduDex getEduDex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSubject(Subject subject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSubject(Subject subject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSubject(Subject target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSubject(Subject target, Subject editedSubject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Subject> getSubjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getSortedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortLessonsForEachPerson() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateSubjectList(Predicate<Subject> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person findPersonWithLessonConflict(Lesson editedLesson, Person personToEdit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Person> sortLessonsForEachPersonBySubject(String subjectKeyword) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithOnePerson extends ModelStub {
        private final Person person;

        ModelStubWithOnePerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return observableArrayList(person);
        }
    }

    /**
     * A Model stub that contains a single person, and no subjects.
     */
    private class ModelStubWithNoSubjects extends ModelStub {
        private final Person person;

        ModelStubWithNoSubjects(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return observableArrayList(person);
        }

        @Override
        public boolean hasSubject(Subject subject) {
            return false;
        }
    }

    /**
     * A Model stub that contains a single person and has the required subjects.
     */
    private class ModelStubWithOnePersonAndSubjects extends ModelStub {
        protected final ObservableList<Subject> subjects = observableArrayList();
        private Person person;

        ModelStubWithOnePersonAndSubjects(Person person) {
            requireNonNull(person);
            this.person = person;
            subjects.add(new Subject("Math"));
        }

        @Override
        public boolean hasPerson(Person person) {
            return this.person.isSamePerson(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return observableArrayList(person);
        }

        @Override
        public boolean hasSubject(Subject subject) {
            return subjects.stream().anyMatch(s -> s.isSameSubject(subject));
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            // store the updated person
            this.person = editedPerson;
        }

        public Person getPerson() {
            return this.person;
        }

        @Override
        public Person findPersonWithLessonConflict(Lesson editedLesson, Person personToEdit) {
            return null; // no conflicts in this stub
        }
    }

}
