package seedu.edudex.model.person;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.PersonBuilder;

public class SubjectComparatorTest {

    @Test
    public void compare_personsSortedByEarliestLessonDayTime_success() {
        Lesson mondayLesson = new Lesson(new Subject("Math"), new Day("Monday"),
                new Time("08:00"), new Time("09:00"));
        Lesson fridayLesson = new Lesson(new Subject("Science"), new Day("Friday"),
                new Time("14:00"), new Time("15:00"));

        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        alice.setLessons(List.of(mondayLesson));
        bob.setLessons(List.of(fridayLesson));

        SubjectComparator comparator = new SubjectComparator();
        assertTrue(comparator.compare(alice, bob) < 0);
        assertTrue(comparator.compare(bob, alice) > 0);
    }

    @Test
    public void compare_noLessons_considersNullsProperly() {
        Person empty1 = new PersonBuilder().withName("A").build();
        Person empty2 = new PersonBuilder().withName("B").build();
        SubjectComparator comparator = new SubjectComparator();
        assertTrue(comparator.compare(empty1, empty2) == 0);
    }
}
