package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edudex.testutil.TypicalSubjects.getTypicalEduDex;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.Messages;
import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.UserPrefs;
import seedu.edudex.model.subject.Subject;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteSubjectCommandTest {

    private Model model = new ModelManager(getTypicalEduDex(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() {
        Subject subjectToDelete = model.getSubjectList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteSubjectCommand deleteSubjectCommand = new DeleteSubjectCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteSubjectCommand.MESSAGE_DELETE_SUBJECT_SUCCESS,
                Messages.format(subjectToDelete));

        ModelManager expectedModel = new ModelManager(model.getEduDex(), new UserPrefs());
        expectedModel.deleteSubject(subjectToDelete);

        assertCommandSuccess(deleteSubjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSubjectList().size() + 1);
        DeleteSubjectCommand deleteSubjectCommand = new DeleteSubjectCommand(outOfBoundIndex);

        assertCommandFailure(deleteSubjectCommand, model, Messages.MESSAGE_INVALID_SUBJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteSubjectCommand deleteFirstSubjectCommand = new DeleteSubjectCommand(INDEX_FIRST_PERSON);
        DeleteSubjectCommand deleteSecondSubjectCommand = new DeleteSubjectCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstSubjectCommand.equals(deleteFirstSubjectCommand));

        // same values -> returns true
        DeleteSubjectCommand deleteFirstSubjectCommandCopy = new DeleteSubjectCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstSubjectCommand.equals(deleteFirstSubjectCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstSubjectCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstSubjectCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstSubjectCommand.equals(deleteSecondSubjectCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteSubjectCommand deleteSubjectCommand = new DeleteSubjectCommand(targetIndex);
        String expected = DeleteSubjectCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteSubjectCommand.toString());
    }
}
