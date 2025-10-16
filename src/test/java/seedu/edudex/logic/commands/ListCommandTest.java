package seedu.edudex.logic.commands;

import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEduDex(), new UserPrefs());
        expectedModel = new ModelManager(model.getEduDex(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
