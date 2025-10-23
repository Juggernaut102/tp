package seedu.edudex.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.edudex.model.EduDex;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.School;
import seedu.edudex.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EduDex} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new School("Geylang Primary School"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet()),

            new Person(new Name("Bernice Yu"), new Phone("99272758"), new School("Serangoon Secondary School"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet()),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new School("NUS Primary School"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("WeakinXXTopic", "StronginXXTopic")),

            new Person(new Name("David Li"), new Phone("91031282"), new School("Raffles Primary School"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet()),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new School("Tampines Secondary School"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet()),

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new School("Yishun Primary School"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet())
        };
    }

    public static ReadOnlyEduDex getSampleEduDex() {
        EduDex sampleAb = new EduDex();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
