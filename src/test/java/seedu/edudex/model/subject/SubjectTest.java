package seedu.edudex.model.subject;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;

public class SubjectTest {

    @Test
    public void validateExistsIn_subjectNotInModel_throwsCommandException() {
        Model model = new ModelManager(); // empty model
        Subject subject = new Subject("Art");

        assertThrows(CommandException.class, () -> subject.validateExistsIn(model));
    }

    @Test
    public void validateExistsIn_subjectInModel_doesNotThrow() throws CommandException {
        Model model = new ModelManager();
        Subject subject = new Subject("Math");
        model.addSubject(subject);

        // Should not throw
        subject.validateExistsIn(model);
    }
}
