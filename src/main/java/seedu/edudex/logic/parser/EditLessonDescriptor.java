package seedu.edudex.logic.parser;

import java.util.Objects;
import java.util.Optional;

import seedu.edudex.commons.util.CollectionUtil;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;

public class EditLessonDescriptor {
    private Subject subject;
    private Day day;
    private Time startTime;
    private Time endTime;

    public EditLessonDescriptor() {}

    public EditLessonDescriptor(EditLessonDescriptor toCopy) {
        setSubject(toCopy.subject);
        setDay(toCopy.day);
        setStartTime(toCopy.startTime);
        setEndTime(toCopy.endTime);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(subject, day, startTime, endTime);
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public void setDay(Day day) {
        this.day = day;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Optional<Subject> getSubject() {
        return Optional.ofNullable(subject);
    }
    public Optional<Day> getDay() {
        return Optional.ofNullable(day);
    }
    public Optional<Time> getStartTime() {
        return Optional.ofNullable(startTime);
    }
    public Optional<Time> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    @Override
    public boolean equals (Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditLessonDescriptor)) {
            return false;
        }

        EditLessonDescriptor e = (EditLessonDescriptor) other;

        return getSubject().equals(e.getSubject())
                && getDay().equals(e.getDay())
                && getStartTime().equals(e.getStartTime())
                && getEndTime().equals(e.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, day, startTime, endTime);
    }
}
