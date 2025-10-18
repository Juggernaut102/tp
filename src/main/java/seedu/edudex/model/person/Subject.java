package seedu.edudex.model.person;

import static seedu.edudex.commons.util.AppUtil.checkArgument;

//import java.nio.file.Path;
//import java.util.Optional;

//import seedu.edudex.commons.exceptions.DataLoadingException;
//import seedu.edudex.model.ReadOnlyEduDex;
//import seedu.edudex.storage.JsonEduDexStorage;

/**
 * Represents a Subject in EduDex.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {
    public static final String MESSAGE_CONSTRAINTS =
            "Subject should only be from the predefined list of subjects.";
    private final String name;

    /**
     * Constructs a {@code Subject}.
     *
     * @param name A valid subject.
     */
    public Subject(String name) {
        // check validity from Subject.json file for allowed subjects
        checkArgument(isValidSubject(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid subject from the list of tutor subjects.
     */
    public static boolean isValidSubject(String name) {
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
        return true; // Placeholder
    }

    @Override
    public String toString() {
        return name;
    }
}
