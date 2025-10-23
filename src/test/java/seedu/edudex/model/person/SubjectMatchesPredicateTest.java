package seedu.edudex.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.PersonBuilder;

public class SubjectMatchesPredicateTest {

    @Test
    public void test_personHasMatchingSubject_returnsTrue() {
        Lesson mathLesson = new Lesson(new Subject("Math"), new Day("Monday"),
                new Time("10:00"), new Time("11:00"));
        Lesson sciLesson = new Lesson(new Subject("Science"), new Day("Tuesday"),
                new Time("13:00"), new Time("14:00"));

        Person student = new PersonBuilder().withName("Alex").build();
        student.setLessons(List.of(mathLesson, sciLesson));

        SubjectMatchesPredicate predicate = new SubjectMatchesPredicate("Math");
        assertTrue(predicate.test(student));
    }

    @Test
    public void test_personHasNoMatchingSubject_returnsFalse() {
        Lesson engLesson = new Lesson(new Subject("English"), new Day("Wednesday"),
                new Time("10:00"), new Time("11:00"));
        Person student = new PersonBuilder().withName("Alex").build();
        student.setLessons(List.of(engLesson));

        SubjectMatchesPredicate predicate = new SubjectMatchesPredicate("Math");
        assertFalse(predicate.test(student));
    }

    @Test
    public void test_personHasEmptyLessons_returnsFalse() {
        Person student = new PersonBuilder().withName("Alex").build();
        SubjectMatchesPredicate predicate = new SubjectMatchesPredicate("Math");
        assertFalse(predicate.test(student));
    }

    @Test
    public void equals() {
        SubjectMatchesPredicate firstPredicate = new SubjectMatchesPredicate("Math");
        SubjectMatchesPredicate secondPredicate = new SubjectMatchesPredicate("Science");

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new SubjectMatchesPredicate("math")));
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(5));
    }
}
