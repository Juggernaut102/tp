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

/**
 * An Immutable EduDex that is serializable to JSON format.
 */
@JsonRootName(value = "edudex")
class JsonSerializableEduDex {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEduDex} with the given persons.
     */
    @JsonCreator
    public JsonSerializableEduDex(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyEduDex} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEduDex}.
     */
    public JsonSerializableEduDex(ReadOnlyEduDex source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this EduDex into the model's {@code EduDex} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EduDex toModelType() throws IllegalValueException {
        EduDex eduDex = new EduDex();
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
