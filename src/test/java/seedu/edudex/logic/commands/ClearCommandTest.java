package seedu.edudex.logic.commands;

import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import org.junit.jupiter.api.Test;

import seedu.edudex.model.EduDex;
import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyEduDex_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEduDex_success() {
        Model model = new ModelManager(getTypicalEduDex(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEduDex(), new UserPrefs());
        expectedModel.setEduDex(new EduDex());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
