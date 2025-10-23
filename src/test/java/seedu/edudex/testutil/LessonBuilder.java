package seedu.edudex.testutil;

import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_SUBJECT = "mathematics";
    public static final String DEFAULT_DAY = "Monday";
    public static final String DEFAULT_STARTTIME = "12:00";
    public static final String DEFAULT_ENDTIME = "13:00";

    private Subject subject;
    private Day day;
    private Time startTime;
    private Time endTime;

    /**
     * Creates a {@code LessonBuilder} with the default attributes.
     */
    public LessonBuilder() {
        subject = new Subject(DEFAULT_SUBJECT);
        day = new Day(DEFAULT_DAY);
        startTime = new Time(DEFAULT_STARTTIME);
        endTime = new Time(DEFAULT_ENDTIME);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        subject = lessonToCopy.getSubject().getCopyOfSubject();
        day = lessonToCopy.getDay().getCopyOfDay();
        startTime = lessonToCopy.getStartTime().getCopyOfTime();
        endTime = lessonToCopy.getEndTime().getCopyOfTime();
    }

    /**
     * Builds the Lesson object.
     */
    public Lesson build() {
        Lesson lesson = new Lesson(subject, day, startTime, endTime);
        return lesson;
    }

}
