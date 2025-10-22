package seedu.edudex.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edudex.model.subject.Subject;

public class LessonTest {
    // Reusable test data
    private final Subject subjectMath = new Subject("Math");
    private final Subject subjectScience = new Subject("Science");
    private final Day dayMonday = new Day("Monday");
    private final Day dayTuesday = new Day("Tuesday");
    private final Time time0900 = new Time("09:00");
    private final Time time1000 = new Time("10:00");
    private final Time time1100 = new Time("11:00");

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Test null subject
        assertThrows(NullPointerException.class, () -> new Lesson(null, dayMonday, time0900, time1000));
        // Test null day
        assertThrows(NullPointerException.class, () -> new Lesson(subjectMath, null, time0900, time1000));
        // Test null startTime
        assertThrows(NullPointerException.class, () -> new Lesson(subjectMath, dayMonday, null, time1000));
        // Test null endTime
        assertThrows(NullPointerException.class, () -> new Lesson(subjectMath, dayMonday, time0900, null));
    }

    @Test
    public void constructor_invalidStartEndTime_throwsIllegalArgumentException() {
        // End time is before start time
        assertThrows(IllegalArgumentException.class, () -> new Lesson(subjectMath, dayMonday, time1000, time0900));

        // Start time is the same as end time
        assertThrows(IllegalArgumentException.class, () -> new Lesson(subjectMath, dayMonday, time1000, time1000));
    }

    @Test
    public void isValidStartEndTime() {
        // Valid: start time is before end time
        assertTrue(Lesson.isValidStartEndTime(new Time("08:00"), new Time("09:00")));

        // Invalid: start time is after end time
        assertFalse(Lesson.isValidStartEndTime(new Time("10:00"), new Time("09:00")));

        // Invalid: start time is the same as end time
        assertFalse(Lesson.isValidStartEndTime(new Time("09:00"), new Time("09:00")));
    }

    @Test
    public void equals() {
        Lesson lesson = new Lesson(subjectMath, dayMonday, time0900, time1000);

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // same values -> returns true
        Lesson lessonCopy = new Lesson(subjectMath, dayMonday, time0900, time1000);
        assertTrue(lesson.equals(lessonCopy));

        // different type -> returns false
        assertFalse(lesson.equals(1));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different subject -> returns false
        Lesson differentSubjectLesson = new Lesson(subjectScience, dayMonday, time0900, time1000);
        assertFalse(lesson.equals(differentSubjectLesson));

        // different day -> returns false
        Lesson differentDayLesson = new Lesson(subjectMath, dayTuesday, time0900, time1000);
        assertFalse(lesson.equals(differentDayLesson));

        // different startTime -> returns false
        Lesson differentStartTimeLesson = new Lesson(subjectMath, dayMonday, time1000, time1100);
        assertFalse(lesson.equals(differentStartTimeLesson));

        // different endTime -> returns false
        Lesson differentEndTimeLesson = new Lesson(subjectMath, dayMonday, time0900, time1100);
        assertFalse(lesson.equals(differentEndTimeLesson));
    }

    @Test
    public void hashCode_consistency() {
        Lesson lesson1 = new Lesson(subjectMath, dayMonday, time0900, time1000);
        Lesson lesson2 = new Lesson(subjectMath, dayMonday, time0900, time1000);

        // Lessons that are equal must have the same hash code
        assertEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void toString_correctFormat() {
        Lesson lesson = new Lesson(subjectMath, dayMonday, time0900, time1000);
        String expectedString = "[Name: Math, Day: Monday, startTime: 09:00, endTime: 10:00]";
        assertEquals(expectedString, lesson.toString());
    }
}
