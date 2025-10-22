package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalPersons.CARL;
import static seedu.edudex.testutil.TypicalPersons.ELLE;
import static seedu.edudex.testutil.TypicalPersons.FIONA;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.UserPrefs;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.DayMatchesPredicate;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.NameContainsKeywordsPredicate;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.SubjectComparator;
import seedu.edudex.model.person.SubjectMatchesPredicate;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalEduDex(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEduDex(), new UserPrefs());

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
    public void executeByName_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void executeByName_singleKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Carl");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void executeByName_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void executeByName_caseInsensitiveKeywords_personsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("cArL");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void executeByName_partialMatchKeyword_noPersonFound() {
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

    //     @Test
    //     public void executeByDay_dayNotFound_noPersonsFound() {
    //        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
    //        DayMatchesPredicate predicate = prepareDayPredicate("Sunday");
    //        FindCommand command = new FindCommand(predicate);
    //        expectedModel.updateFilteredPersonList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    //     }

    @Test
    public void executeByDay_invalidDayFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> prepareDayPredicate("mondayy"));
    }

    /**
     * Case-insensitive user input for day should still work.
     */
    @Test
    public void executeByDay_dayCaseInsensitive_personsFound() {
        DayMatchesPredicate predicate = prepareDayPredicate("mOnDaY");
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size()),
                expectedModel);

        // Compare filtered lists
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    // ----------------------------------------------
    // Subject-Based Find tests
    // ----------------------------------------------

    @Test
    public void executeBySubject_subjectMatch_success() {
        // Create student with lessons
        Person student = new PersonBuilder().withName("Student A").build();
        Lesson mathLesson = new Lesson(new Subject("Math"), new Day("Monday"),
                new Time("10:00"), new Time("11:00"));
        Lesson scienceLesson = new Lesson(new Subject("Science"), new Day("Tuesday"),
                new Time("13:00"), new Time("14:00"));
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(scienceLesson);
        lessons.add(mathLesson);
        student.setLessons(lessons);
        model.addPerson(student);
        expectedModel.addPerson(student);

        SubjectMatchesPredicate predicate = new SubjectMatchesPredicate("Math");
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.sortFilteredPersonList(new SubjectComparator());

        assertCommandSuccess(command, model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size()),
                expectedModel);

        List<Lesson> sorted = expectedModel.getFilteredPersonList().get(0).getLessons();
        assertEquals("Monday", sorted.get(0).getDay().toString());
    }

    @Test
    public void executeBySubject_subjectNotFound_noPersonsFound() {
        SubjectMatchesPredicate predicate = new SubjectMatchesPredicate("Art");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }


    // ----------------------------------------------
    // Utility / Misc. Tests
    // ----------------------------------------------
    //    @Test
    //    public void toStringMethod() {
    //        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
    //        FindCommand findCommand = new FindCommand(predicate);
    //        String expected = FindCommand.class.getCanonicalName() + "{findByDay=false, predicate=" + predicate + "}";
    //        assertEquals(expected, findCommand.toString());
    //    }
    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName()
                + "{searchType=NAME, predicate=" + predicate + "}";
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
