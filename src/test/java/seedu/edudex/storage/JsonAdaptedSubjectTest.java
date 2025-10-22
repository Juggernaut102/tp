package seedu.edudex.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edudex.storage.JsonAdaptedSubject.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalSubjects.MATH;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.exceptions.IllegalValueException;
import seedu.edudex.model.person.Name;

public class JsonAdaptedSubjectTest {
    private static final String VALID_SUBJECT_MATH = MATH.subjectName;

    @Test
    public void toModelType_validSubject_returnsSubject() throws Exception {
        JsonAdaptedSubject subject = new JsonAdaptedSubject(VALID_SUBJECT_MATH);
        assertEquals(MATH, subject.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() throws Exception {
        JsonAdaptedSubject subject = new JsonAdaptedSubject((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, subject::toModelType);
    }
}
