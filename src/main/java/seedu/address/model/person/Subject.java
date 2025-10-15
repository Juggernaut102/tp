package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartEndTime(Time, Time)}
 */
public class Subject {
    public static final String MESSAGE_CONSTRAINTS =
            "Start time should be before end time.";
    public static final String DEFAULT_SUBJECT_NAME = "Science";
    public final String value;
    private final String subjectName;
    private final Day day;
    private final Time startTime;
    private final Time endTime;

    /**
     * Constructs a {@code Subject}.
     *
     * @param day A valid day of the week.
     * @param startTime A valid start time.
     * @param endTime A valid end time.
     */
    public Subject(Day day, Time startTime, Time endTime) {
        this.subjectName = DEFAULT_SUBJECT_NAME;
        this.day = day;
        checkArgument(isValidStartEndTime(startTime, endTime), MESSAGE_CONSTRAINTS);
        this.startTime = startTime;
        this.endTime = endTime;

        value = this.toString();
    }

    public String getSubjectName() {
        return subjectName;
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
        return String.format("[Name: " + subjectName
                + ", Day: " + day.toString()
                + ", startTime: " + startTime.toString()
                + ", endTime: " + endTime.toString()) + "]";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && subjectName.equals(((Subject) other).subjectName)); // state check
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }
}
