package seedu.edudex.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edudex.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.edudex.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.exceptions.IllegalValueException;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;

public class JsonAdaptedLessonTest {
    private static final String INVALID_SUBJECT = "Maths123";
    private static final String INVALID_DAY = "Funday";
    private static final String INVALID_STARTTIME = "25:00";
    private static final String INVALID_ENDTIME = "ab:cd";

    private static final String VALID_SUBJECT = "Math";
    private static final String VALID_DAY = "Monday";
    private static final String VALID_STARTTIME = "09:00";
    private static final String VALID_ENDTIME = "11:00";

    // A valid lesson object to be used in tests
    private static final Lesson VALID_LESSON = new Lesson(new Subject(VALID_SUBJECT),
            new Day(VALID_DAY),
            new Time(VALID_STARTTIME),
            new Time(VALID_ENDTIME)
    );

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_LESSON);
        assertEquals(VALID_LESSON, lesson.toModelType());
    }

    // Once we have implemented Subject.json validation, we can uncomment these tests
    //    @Test
    //    public void toModelType_invalidSubject_throwsIllegalValueException() {
    //        JsonAdaptedLesson lesson =
    //                new JsonAdaptedLesson(INVALID_SUBJECT, VALID_DAY, VALID_STARTTIME, VALID_ENDTIME);
    //        String expectedMessage = Subject.MESSAGE_CONSTRAINTS;
    //        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    //    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, VALID_DAY, VALID_STARTTIME, VALID_ENDTIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Subject");
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_SUBJECT, INVALID_DAY, VALID_STARTTIME, VALID_ENDTIME);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, null, VALID_STARTTIME, VALID_ENDTIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Day");
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_SUBJECT, VALID_DAY, INVALID_STARTTIME, INVALID_ENDTIME);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_DAY, null, VALID_ENDTIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start Time");
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_SUBJECT, VALID_DAY, VALID_STARTTIME, INVALID_ENDTIME);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_SUBJECT, VALID_DAY, VALID_STARTTIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "End Time");
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

}
