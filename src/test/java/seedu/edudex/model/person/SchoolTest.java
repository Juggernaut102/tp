package seedu.edudex.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SchoolTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new School(null));
    }

    @Test
    public void constructor_invalidSchool_throwsIllegalArgumentException() {
        String invalidSchool = "";
        assertThrows(IllegalArgumentException.class, () -> new School(invalidSchool));
    }

    @Test
    public void isValidSchool() {
        // null school
        assertThrows(NullPointerException.class, () -> School.isValidSchool(null));

        // invalid schooles
        assertFalse(School.isValidSchool("")); // empty string
        assertFalse(School.isValidSchool(" ")); // spaces only

        // valid schooles
        assertTrue(School.isValidSchool("NUS Primary School"));
        assertTrue(School.isValidSchool("-")); // one character
        assertTrue(School.isValidSchool("Schoooooooooooooooooooooooooooooooooooooooooool")); // long school
    }

    @Test
    public void equals() {
        School school = new School("Valid School");

        // same values -> returns true
        assertTrue(school.equals(new School("Valid School")));

        // same object -> returns true
        assertTrue(school.equals(school));

        // null -> returns false
        assertFalse(school.equals(null));

        // different types -> returns false
        assertFalse(school.equals(5.0f));

        // different values -> returns false
        assertFalse(school.equals(new School("Other Valid School")));
    }
}

