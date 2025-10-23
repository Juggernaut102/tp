package seedu.edudex.model.person;

import java.util.Comparator;

/**
 * Comparator for sorting Persons by their lesson day and start time.
 * Used primarily for subject-based find operations.
 */
public class SubjectComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        // Sort by earliest day/time of each student's lessons
        Lesson firstLesson1 = p1.getLessons().stream()
                .sorted(Comparator
                        .comparing((Lesson l) -> l.getDay().getNumericValue())
                        .thenComparing(l -> l.getStartTime().getTime()))
                .findFirst()
                .orElse(null);

        Lesson firstLesson2 = p2.getLessons().stream()
                .sorted(Comparator
                        .comparing((Lesson l) -> l.getDay().getNumericValue())
                        .thenComparing(l -> l.getStartTime().getTime()))
                .findFirst()
                .orElse(null);

        if (firstLesson1 == null && firstLesson2 == null) {
            return 0;
        }

        if (firstLesson1 == null) {
            return 1;
        }

        if (firstLesson2 == null) {
            return -1;
        }

        return Comparator
                .comparing((Lesson l) -> l.getDay().getNumericValue())
                .thenComparing(l -> l.getStartTime().getTime())
                .compare(firstLesson1, firstLesson2);
    }
}
