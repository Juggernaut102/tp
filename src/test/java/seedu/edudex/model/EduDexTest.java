package seedu.edudex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalPersons.ALICE;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;
import static seedu.edudex.testutil.TypicalSubjects.MATH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.exceptions.DuplicatePersonException;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.PersonBuilder;

public class EduDexTest {

    private final EduDex eduDex = new EduDex();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eduDex.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduDex.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEduDex_replacesData() {
        EduDex newData = getTypicalEduDex();
        eduDex.resetData(newData);
        assertEquals(newData, eduDex);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        EduDexStub newData = new EduDexStub(newPersons, new ArrayList<>());

        assertThrows(DuplicatePersonException.class, () -> eduDex.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduDex.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInEduDex_returnsFalse() {
        assertFalse(eduDex.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInEduDex_returnsTrue() {
        eduDex.addPerson(ALICE);
        assertTrue(eduDex.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInEduDex_returnsTrue() {
        eduDex.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(eduDex.hasPerson(editedAlice));
    }

    @Test
    public void hasSubject_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduDex.hasSubject(null));
    }

    @Test
    public void hasSubject_subjectNotInEduDex_returnsFalse() {
        assertFalse(eduDex.hasSubject(MATH));
    }

    @Test
    public void hasSubject_subjectInEduDex_returnsTrue() {
        eduDex.addSubject(MATH);
        assertTrue(eduDex.hasSubject(MATH));
    }

    @Test
    public void hasSubject_subjectWithSameNameInEduDex_returnsTrue() {
        eduDex.addSubject(MATH);
        Subject editedMath = new Subject("Math");
        assertTrue(eduDex.hasSubject(editedMath));
    }

    @Test
    public void getSubjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eduDex.getSubjectList().remove(0));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eduDex.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = EduDex.class.getCanonicalName() + "{persons=" + eduDex.getPersonList() + "}";
        assertEquals(expected, eduDex.toString());
    }

    /**
     * A stub ReadOnlyEduDex whose persons list can violate interface constraints.
     */
    private static class EduDexStub implements ReadOnlyEduDex {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Subject> subjects = FXCollections.observableArrayList();

        EduDexStub(Collection<Person> persons, Collection<Subject> subjects) {
            this.persons.setAll(persons);
            this.subjects.setAll(subjects);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Subject> getSubjectList() {
            return subjects;
        }
    }
}
