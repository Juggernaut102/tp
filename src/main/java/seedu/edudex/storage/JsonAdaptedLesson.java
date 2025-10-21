package seedu.edudex.storage;

import seedu.edudex.commons.exceptions.IllegalValueException;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private final String subject;
    private final String day;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    public JsonAdaptedLesson(String subject, String day, String startTime, String endTime) {
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        subject = source.getSubject().toString();
        day = source.getDay().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Subject"));
        }
        if (!Subject.isValidSubjectName(subject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }

        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Day"));
        }

        if (!Day.isValidDay(day)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start Time"));
        }

        if (!Time.isValidTime(startTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "End Time"));
        }

        if (!Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }

        Subject modelSubject = new Subject(subject);
        Day modelDay = new Day(day);
        Time modelStartTime = new Time(startTime);
        Time modelEndTime = new Time(endTime);
        return new Lesson(modelSubject, modelDay, modelStartTime, modelEndTime);
    }
}
