package seedu.edudex.model.subject;

import static seedu.edudex.commons.util.AppUtil.checkArgument;

//import java.nio.file.Path;
//import java.util.Optional;

//import seedu.edudex.commons.exceptions.DataLoadingException;
//import seedu.edudex.model.ReadOnlyEduDex;
//import seedu.edudex.storage.JsonEduDexStorage;

/**
 * Represents a Subject in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubjectName(String)}
 */
public class Subject {
    public static final String MESSAGE_CONSTRAINTS =
            "Subject should only be from the predefined list of subjects.";
    public final String subjectName;

    /**
     * Constructs a {@code Subject}.
     *
     * @param name A valid subject.
     */
    public Subject(String name) {
        // check validity from Subject.json file for allowed subjects
        checkArgument(isValidSubjectName(name), MESSAGE_CONSTRAINTS);
        this.subjectName = name;
    }

    public String getName() {
        return subjectName;
    }

    /**
     * Returns true if a given string is a valid subject from the list of tutor subjects.
     */
    public static boolean isValidSubjectName(String name) {
        // Implement validation logic based on predefined list of subjects in Subject.json
        // Read the Subject.json file and check if the name exists
        /*
        Path filePath = Path.of("Subject.json"); // temp path to be replaced with actual path
        try {
            ReadOnlyEduDex subjectListJson = new JsonEduDexStorage(filePath).readEduDex().get();
            for (Subject subject : subjectListJson.getSubjectList()) {
                if (subject.name.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } catch (DataLoadingException e) {
            System.out.println("Error loading subject list: " + e.getMessage());
            return false;
        }
        */

        // Ryan: to clarify: this method is intended just to ensure the string is valid
        // NOT to check if the input by the user is valid (i.e. entered a subject that matches
        // a subject in the subject list)
        return true; // Placeholder
    }

    @Override
    public String toString() {
        return subjectName;
    }

    /**
     * Returns true if both subjects have the same name.
     * This defines a weaker notion of equality between two subjects.
     */
    public boolean isSameSubject(Subject otherSubject) {
        if (otherSubject == this) {
            return true;
        }

        return otherSubject != null
                && otherSubject.getName().equals(getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherSubject = (Subject) other;
        return otherSubject.subjectName.equals(this.subjectName);
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }
}
