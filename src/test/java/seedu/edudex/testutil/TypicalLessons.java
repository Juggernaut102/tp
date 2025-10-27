package seedu.edudex.testutil;

import seedu.edudex.model.person.Lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {
    public static final Lesson MATH = new LessonBuilder()
            .withDay("Monday")
            .withStartTime("10:00")
            .withEndTime("11:00")
            .build();
    public static final Lesson SCIENCE = new LessonBuilder()
            .withDay("Tuesday")
            .withStartTime("13:00")
            .withEndTime("14:00")
            .build();

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns a list of {@code Lesson} with all the typical lessons.
     */
    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(MATH, SCIENCE));
    }
}
