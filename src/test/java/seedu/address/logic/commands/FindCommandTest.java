package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Day;
import seedu.address.model.person.DayMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // ----------------------------------------------
    // Equality tests
    // ----------------------------------------------
    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        DayMatchesPredicate firstDayPredicate = new DayMatchesPredicate(new Day("Monday"));
        DayMatchesPredicate secondDayPredicate = new DayMatchesPredicate(new Day("Tuesday"));

        FindCommand findByNameFirst = new FindCommand(firstPredicate);
        FindCommand findByNameSecond = new FindCommand(secondPredicate);
        FindCommand findByDayFirst = new FindCommand(firstDayPredicate);
        FindCommand findByDaySecond = new FindCommand(secondDayPredicate);

        // same object -> returns true
        assertTrue(findByNameFirst.equals(findByNameFirst));

        // same values -> returns true
        FindCommand findByNameCopy = new FindCommand(firstPredicate);
        assertTrue(findByNameFirst.equals(findByNameCopy));

        // different types -> returns false
        assertFalse(findByNameFirst.equals(1));

        // null -> returns false
        assertFalse(findByNameFirst.equals(null));

        // different person -> returns false
        assertFalse(findByNameFirst.equals(findByNameSecond));

        // different mode (name vs day) -> returns false
        assertFalse(findByNameCopy.equals(findByDayFirst));

        // same day predicate -> returns true
        FindCommand findByDayCopy = new FindCommand(firstDayPredicate);
        assertTrue(findByDayFirst.equals(findByDayCopy));

        // different day predicate -> returns false
        assertFalse(findByDayFirst.equals(findByDaySecond));
    }

    // ----------------------------------------------
    // Name-based Find tests
    // ----------------------------------------------

    @Test
    public void execute_namePredicate_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_namePredicate_singleKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Carl");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_namePredicate_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_caseInsensitiveKeywords_personsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("cArL");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialMatchKeyword_noPersonFound() {
        // Ensures that names are matched by an entire word (separated by whitespace)
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Car");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }
    // ----------------------------------------------
    // Day-Based Find tests
    // ----------------------------------------------

    //    @Test
    //    public void execute_validDay_personsFound() {
    //        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
    //        DayMatchesPredicate predicate = new DayMatchesPredicate(new Day("Monday"));
    //        FindCommand command = new FindCommand(predicate);
    //        expectedModel.updateFilteredPersonList(predicate);
    //
    //        // BENSON has day = "Monday" in TypicalPersons
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    //    }

    @Test
    public void execute_dayNotFound_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DayMatchesPredicate predicate = new DayMatchesPredicate(new Day("Sunday"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{findByDay=false, predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }



    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code DayMatchesPredicate}.
     */
    private DayMatchesPredicate prepareDayPredicate(String userInput) {
        return new DayMatchesPredicate(new Day(userInput));
    }
}
