package seedu.edudex.testutil;

import seedu.edudex.model.subject.Subject;

/**
 * A utility class to help with building Subject objects.
 */
public class SubjectBuilder {

    public static final String DEFAULT_NAME = "Science";

    private String name;

    /**
     * Creates a {@code SubjectBuilder} with the default details.
     */
    public SubjectBuilder() {
        name = DEFAULT_NAME;
    }

    /**
     * Initializes the SubjectBuilder with the data of {@code subjectToCopy}.
     */
    public SubjectBuilder(Subject subjectToCopy) {
        name = subjectToCopy.toString();
    }

    /**
     * Sets the {@code Name} of the {@code Subject} that we are building.
     */
    public SubjectBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Builds the Subject object.
     */
    public Subject build() {
        Subject subject = new Subject(name);
        return subject;
    }

}
