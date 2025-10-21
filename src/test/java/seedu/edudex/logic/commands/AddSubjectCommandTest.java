package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalSubjects.MATH;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.EduDex;
import seedu.edudex.model.Model;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.ReadOnlyUserPrefs;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.SubjectBuilder;

public class AddSubjectCommandTest {

    @Test
    public void constructor_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSubjectCommand(null));
    }

    @Test
    public void execute_subjectAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSubjectAdded modelStub = new ModelStubAcceptingSubjectAdded();
        Subject validSubject = new SubjectBuilder().build();

        CommandResult commandResult = new AddSubjectCommand(validSubject).execute(modelStub);

        assertEquals(String.format(AddSubjectCommand.MESSAGE_ADD_SUBJECT_SUCCESS, Messages.format(validSubject)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSubject), modelStub.subjectsAdded);
    }

    @Test
    public void execute_duplicateSubject_throwsCommandException() {
        Subject validSubject = new SubjectBuilder().build();
        AddSubjectCommand addSubjectCommand = new AddSubjectCommand(validSubject);
        ModelStub modelStub = new ModelStubWithSubject(validSubject);

        assertThrows(CommandException.class,
                AddSubjectCommand.MESSAGE_DUPLICATE_SUBJECT, () -> addSubjectCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Subject math = new SubjectBuilder().withName("Math").build();
        Subject science = new SubjectBuilder().withName("Science").build();
        AddSubjectCommand addMathCommand = new AddSubjectCommand(math);
        AddSubjectCommand addScienceCommand = new AddSubjectCommand(science);

        // same object -> returns true
        assertTrue(addMathCommand.equals(addMathCommand));

        // same values -> returns true
        AddSubjectCommand addMathCommandCopy = new AddSubjectCommand(math);
        assertTrue(addMathCommand.equals(addMathCommandCopy));

        // different types -> returns false
        assertFalse(addMathCommand.equals(1));

        // null -> returns false
        assertFalse(addMathCommand.equals(null));

        // different subject -> returns false
        assertFalse(addMathCommand.equals(addScienceCommand));
    }

    @Test
    public void toStringMethod() {
        AddSubjectCommand addSubjectCommand = new AddSubjectCommand(MATH);
        String expected = AddSubjectCommand.class.getCanonicalName() + "{toAddSubject=" + MATH + "}";
        assertEquals(expected, addSubjectCommand.toString());
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
        public void updateSubjectList(Predicate<Subject> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single subject.
     */
    private class ModelStubWithSubject extends ModelStub {
        private final Subject subject;

        ModelStubWithSubject(Subject subject) {
            requireNonNull(subject);
            this.subject = subject;
        }

        @Override
        public boolean hasSubject(Subject subject) {
            requireNonNull(subject);
            return this.subject.isSameSubject(subject);
        }
    }

    /**
     * A Model stub that always accept the subject being added.
     */
    private class ModelStubAcceptingSubjectAdded extends ModelStub {
        final ArrayList<Subject> subjectsAdded = new ArrayList<>();

        @Override
        public boolean hasSubject(Subject subject) {
            requireNonNull(subject);
            return subjectsAdded.stream().anyMatch(subject::isSameSubject);
        }

        @Override
        public void addSubject(Subject subject) {
            requireNonNull(subject);
            subjectsAdded.add(subject);
        }

        @Override
        public ReadOnlyEduDex getEduDex() {
            return new EduDex();
        }
    }

}
