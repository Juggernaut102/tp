package seedu.edudex.model.person;

import static seedu.edudex.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.edudex.model.subject.Subject;

/**
 * Represents a Lesson in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartEndTime(Time, Time)}
 */
public class Lesson {
    public static final String MESSAGE_CONSTRAINTS =
            "Start time should be before end time.";
    public static final String MESSAGE_CONFLICTING_LESSON = "This lesson conflicts with an existing lesson.";
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
     *
     * @param startTime provided
     * @param endTime provided
     */
    public static boolean isValidStartEndTime(Time startTime, Time endTime) {
        return startTime.getTime().isBefore(endTime.getTime());
    }

    @Override
    public String toString() {
        return String.format("[Subject: " + subject.toString()
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
        return Objects.hash(subject, day, startTime, endTime);
    }

    /**
     * Makes a copy of this Subject, and return a new Subject object with the same attributes.
     * All fields are copied defensively.
     */
    public Lesson getCopyOfLesson() {
        return new Lesson(subject.getCopyOfSubject(), day.getCopyOfDay(),
                startTime.getCopyOfTime(), endTime.getCopyOfTime());
    }

    /**
     * Checks if this lesson conflicts with another lesson.
     * @param otherLesson The other lesson to check against.
     * @return true if there is a conflict, false otherwise.
     */
    public boolean conflictsWith(Lesson otherLesson) {
        if (!this.day.equals(otherLesson.day)) {
            return false; // Different days, no conflict
        }

        // Check if time intervals overlap
        boolean isOverlapping = this.startTime.getTime().isBefore(otherLesson.endTime.getTime())
                && otherLesson.startTime.getTime().isBefore(this.endTime.getTime());

        return isOverlapping; // return existing lesson if conflict exists, else null
    }
}
