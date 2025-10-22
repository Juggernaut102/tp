package seedu.edudex.model.person;

import java.util.Comparator;

/**
 * Comparator for sorting Persons by their lesson day and start time.
 * Used primarily for subject-based find operations.
 */
public class SubjectComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        // If either person has no lessons, treat them as "greater"
        if (p1.getLessons().isEmpty() && p2.getLessons().isEmpty()) {
            return 0;
        } else if (p1.getLessons().isEmpty()) {
            return 1;
        } else if (p2.getLessons().isEmpty()) {
            return -1;
        }

        Lesson l1 = p1.getLessons().get(0);
        Lesson l2 = p2.getLessons().get(0);

        int dayCompare = Integer.compare(l1.getDay().getNumericValue(), l2.getDay().getNumericValue());
        if (dayCompare != 0) return dayCompare;

        return l1.getStartTime().getTime().compareTo(l2.getStartTime().getTime());
    }
}
