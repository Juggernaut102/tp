package seedu.edudex.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.edudex.commons.exceptions.IllegalValueException;
import seedu.edudex.model.EduDex;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;

/**
 * An Immutable EduDex that is serializable to JSON format.
 */
@JsonRootName(value = "edudex")
class JsonSerializableEduDex {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_SUBJECT = "Subjects list contains duplicate subject(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedSubject> subjects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEduDex} with the given persons.
     */
    @JsonCreator
    public JsonSerializableEduDex(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                  @JsonProperty("subjects") List<JsonAdaptedSubject> subjects) {
        this.persons.addAll(persons);
        this.subjects.addAll(subjects);
    }

    /**
     * Converts a given {@code ReadOnlyEduDex} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEduDex}.
     */
    public JsonSerializableEduDex(ReadOnlyEduDex source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        subjects.addAll(source.getSubjectList().stream().map(JsonAdaptedSubject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this EduDex into the model's {@code EduDex} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EduDex toModelType() throws IllegalValueException {
        EduDex eduDex = new EduDex();
        for (JsonAdaptedSubject jsonAdaptedSubject : subjects) {
            Subject subject = jsonAdaptedSubject.toModelType();
            if (eduDex.hasSubject(subject)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SUBJECT);
            }
            eduDex.addSubject(subject);
        }

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (eduDex.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            eduDex.addPerson(person);
        }
        return eduDex;
    }

}
