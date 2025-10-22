package seedu.edudex.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edudex.model.EduDex;
import seedu.edudex.model.subject.Subject;

/**
 * A utility class containing a list of {@code Subject} objects to be used in tests.
 */
public class TypicalSubjects {
    public static final Subject SCIENCE = new Subject("Science");
    public static final Subject MATH = new Subject("Math");

    private TypicalSubjects() {} // prevents instantiation

    /**
     * Returns an {@code EduDex} with all the typical persons.
     */
    public static EduDex getTypicalEduDex() {
        EduDex ab = new EduDex();
        for (Subject subject : getTypicalSubjects()) {
            ab.addSubject(subject);
        }
        return ab;
    }

    public static List<Subject> getTypicalSubjects() {
        return new ArrayList<>(Arrays.asList(SCIENCE, MATH));
    }
}
