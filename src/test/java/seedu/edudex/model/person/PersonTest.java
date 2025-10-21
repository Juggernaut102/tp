package seedu.edudex.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalPersons.ALICE;
import static seedu.edudex.testutil.TypicalPersons.BOB;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.PersonBuilder;

public class PersonTest {
    // Reusable lesson data for tests
    private final Lesson lessonMathMonday = new Lesson(new Subject("Math"), new Day("Monday"),
            new Time("09:00"), new Time("10:00"));
    private final Lesson lessonScienceTuesday = new Lesson(new Subject("Science"), new Day("Tuesday"),
            new Time("10:00"), new Time("11:00"));
    private final Lesson lessonMathWednesday = new Lesson(new Subject("Math"), new Day("Wednesday"),
            new Time("11:00"), new Time("12:00"));

    @Test
    public void addLesson() {
        Person person = new PersonBuilder().build(); // build person with empty lesson list
        person.addLesson(lessonMathMonday);
        assertTrue(person.getLessons().contains(lessonMathMonday));
        assertEquals(1, person.getLessons().size());
    }

    @Test
    public void getLessonsAsString() {
        // No lessons
        Person person = new PersonBuilder().build();
        assertEquals("No lessons scheduled.", person.getLessonsAsString());

        // One lesson
        person.addLesson(lessonMathMonday);
        assertEquals(lessonMathMonday.toString(), person.getLessonsAsString());

        // Multiple lessons
        person.addLesson(lessonScienceTuesday);
        String expected = lessonMathMonday.toString() + "\n" + lessonScienceTuesday.toString();
        assertEquals(expected, person.getLessonsAsString());
    }

    @Test
    public void getAllSubjects() {
        Person person = new PersonBuilder().build();

        // No lessons -> empty list
        assertTrue(person.getAllSubjects().isEmpty());

        // One lesson
        person.addLesson(lessonMathMonday);
        assertEquals(List.of(new Subject("Math")), person.getAllSubjects());

        // Multiple lessons, one unique subject
        person.addLesson(lessonMathWednesday);
        assertEquals(List.of(new Subject("Math")), person.getAllSubjects());

        // Multiple lessons, multiple unique subjects
        person.addLesson(lessonScienceTuesday);
        List<Subject> expectedSubjects = List.of(new Subject("Math"), new Subject("Science"));
        // Using containsAll and checking size to avoid order dependency in the test
        assertTrue(person.getAllSubjects().containsAll(expectedSubjects));
        assertEquals(2, person.getAllSubjects().size());
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different lessons -> returns false
        editedAlice = new PersonBuilder(ALICE).withLessons(List.of(lessonScienceTuesday)).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void hashCode_consistency() {
        // Two persons that are equal must have the same hash code
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone="
                + ALICE.getPhone() + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags()
                + ", lessons=" + ALICE.getLessons() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
