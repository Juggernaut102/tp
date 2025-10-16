package seedu.edudex.logic.commands;

import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edudex.logic.Messages;
import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.UserPrefs;
import seedu.edudex.model.person.Person;
import seedu.edudex.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEduDex(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getEduDex(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getEduDex().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
