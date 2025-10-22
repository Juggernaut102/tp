package seedu.edudex.model.person;

import static seedu.edudex.commons.util.AppUtil.checkArgument;

import seedu.edudex.model.subject.Subject;

/**
 * Represents a Lesson in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartEndTime(Time, Time)}
 */
public class Lesson {
    public static final String MESSAGE_CONSTRAINTS =
            "Start time should be before end time.";
    private final Subject subject;
    private final Day day;
    private final Time startTime;
    private final Time endTime;
    private final String value;

    /**
     * Constructs a {@code Subject}.
     *
     * @param day A valid day of the week.
     * @param startTime A valid start time.
     * @param endTime A valid end time.
     */
    public Lesson(Subject subject, Day day, Time startTime, Time endTime) {
        this.subject = subject;
        this.day = day;
        checkArgument(isValidStartEndTime(startTime, endTime), MESSAGE_CONSTRAINTS);
        this.startTime = startTime;
        this.endTime = endTime;

        value = this.toString();
    }

    public Subject getSubject() {
        return subject;
    }

    public Day getDay() {
        return day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    /**
     * Returns true if startTime is before endTime.
     */
    public static boolean isValidStartEndTime(Time startTime, Time endTime) {
        return startTime.getTime().isBefore(endTime.getTime());
    }

    @Override
    public String toString() {
        return String.format("[Name: " + subject.toString()
                + ", Day: " + day.toString()
                + ", startTime: " + startTime.toString()
                + ", endTime: " + endTime.toString()) + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return subject.equals(otherLesson.subject)
                && day.equals(otherLesson.day)
                && startTime.equals(otherLesson.startTime)
                && endTime.equals(otherLesson.endTime);
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }
}
