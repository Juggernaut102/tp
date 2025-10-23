package seedu.edudex.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.edudex.commons.exceptions.IllegalValueException;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.subject.Subject;

/**
 * Jackson-friendly version of {@link Subject}.
 */
class JsonAdaptedSubject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Subject's %s field is missing!";

    private final String subjectName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedSubject(@JsonProperty("subjectName") String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedSubject(Subject source) {
        subjectName = source.getSubjectAsString();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Subject toModelType() throws IllegalValueException {
        if (subjectName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Subject.isValidSubjectName(subjectName)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(subjectName);
    }
}
