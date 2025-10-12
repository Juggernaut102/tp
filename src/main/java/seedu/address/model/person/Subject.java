package seedu.address.model.person;

import java.time.LocalTime;

public class Subject {
    private final String subjectName;
    private final Day day;
    private final Time startTime;
    private final Time endTime;

    private final String DEFAULT_SUBJECT_NAME = "Science";
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
        this.startTime = startTime;
        this.endTime = endTime;
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

    @Override
    public String toString() {
        return String.format(subjectName + " on " + day.toString() + " from "
                + startTime.toString() + " to " + endTime.toString());
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
