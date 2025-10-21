package seedu.edudex.model.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalSubjects.MATH;
import static seedu.edudex.testutil.TypicalSubjects.SCIENCE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edudex.model.subject.exceptions.DuplicateSubjectException;
import seedu.edudex.model.subject.exceptions.SubjectNotFoundException;

public class UniqueSubjectListTest {

    private final UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();

    @Test
    public void contains_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSubjectList.contains(null));
    }

    @Test
    public void contains_subjectNotInList_returnsFalse() {
        assertFalse(uniqueSubjectList.contains(SCIENCE));
    }

    @Test
    public void contains_subjectInList_returnsTrue() {
        uniqueSubjectList.add(SCIENCE);
        assertTrue(uniqueSubjectList.contains(SCIENCE));
    }

    @Test
    public void contains_subjectWithSameNameInList_returnsTrue() {
        uniqueSubjectList.add(SCIENCE);
        Subject editedScience = new Subject("Science");
        assertTrue(uniqueSubjectList.contains(editedScience));
    }

    @Test
    public void add_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSubjectList.add(null));
    }

    @Test
    public void add_duplicateSubject_throwsDuplicateSubjectException() {
        uniqueSubjectList.add(SCIENCE);
        assertThrows(DuplicateSubjectException.class, () -> uniqueSubjectList.add(SCIENCE));
    }

    @Test
    public void setSubject_nullTargetSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSubjectList.setSubject(null, SCIENCE));
    }

    @Test
    public void setSubject_nullEditedSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSubjectList.setSubject(SCIENCE, null));
    }

    @Test
    public void setSubject_targetSubjectNotInList_throwsSubjectNotFoundException() {
        assertThrows(SubjectNotFoundException.class, () -> uniqueSubjectList.setSubject(SCIENCE, SCIENCE));
    }

    @Test
    public void setSubject_editedSubjectIsSameSubject_success() {
        uniqueSubjectList.add(SCIENCE);
        uniqueSubjectList.setSubject(SCIENCE, SCIENCE);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(SCIENCE);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubject_editedSubjectHasDifferentName_success() {
        uniqueSubjectList.add(SCIENCE);
        uniqueSubjectList.setSubject(SCIENCE, MATH);
        UniqueSubjectList expectedUniquePersonList = new UniqueSubjectList();
        expectedUniquePersonList.add(MATH);
        assertEquals(expectedUniquePersonList, uniqueSubjectList);
    }

    @Test
    public void setSubject_editedSubjectHasNonUniqueName_throwsDuplicateSubjectException() {
        uniqueSubjectList.add(SCIENCE);
        uniqueSubjectList.add(MATH);
        assertThrows(DuplicateSubjectException.class, () -> uniqueSubjectList.setSubject(SCIENCE, MATH));
    }

    @Test
    public void remove_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSubjectList.remove(null));
    }

    @Test
    public void remove_subjectDoesNotExist_throwsSubjectNotFoundException() {
        assertThrows(SubjectNotFoundException.class, () -> uniqueSubjectList.remove(SCIENCE));
    }

    @Test
    public void remove_existingSubject_removesSubject() {
        uniqueSubjectList.add(SCIENCE);
        uniqueSubjectList.remove(SCIENCE);
        UniqueSubjectList expectedUniquePersonList = new UniqueSubjectList();
        assertEquals(expectedUniquePersonList, uniqueSubjectList);
    }

    @Test
    public void setSubjects_nullUniqueSubjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSubjectList.setSubjects((UniqueSubjectList) null));
    }

    @Test
    public void setSubjects_uniqueSubjectsList_replacesOwnListWithProvidedUniqueSubjectList() {
        uniqueSubjectList.add(SCIENCE);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(MATH);
        uniqueSubjectList.setSubjects(expectedUniqueSubjectList);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSubjectList.setSubjects((List<Subject>) null));
    }

    @Test
    public void setSubjects_list_replacesOwnListWithProvidedList() {
        uniqueSubjectList.add(SCIENCE);
        List<Subject> subjectList = Collections.singletonList(MATH);
        uniqueSubjectList.setSubjects(subjectList);
        UniqueSubjectList expectedUniqueSubjectList = new UniqueSubjectList();
        expectedUniqueSubjectList.add(MATH);
        assertEquals(expectedUniqueSubjectList, uniqueSubjectList);
    }

    @Test
    public void setSubjects_listWithDuplicateSubject_throwsDuplicateSubjectException() {
        List<Subject> listWithDuplicatePersons = Arrays.asList(MATH, MATH);
        assertThrows(DuplicateSubjectException.class, () -> uniqueSubjectList.setSubjects(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueSubjectList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueSubjectList.asUnmodifiableObservableList().toString(), uniqueSubjectList.toString());
    }
}
