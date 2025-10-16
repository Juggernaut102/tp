package seedu.edudex.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.edudex.model.EduDex;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Email;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.Subject;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EduDex} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet(),
                    new Subject(new Day("Monday"), new Time("14:00"), new Time("16:00"))),

            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet(),
                new Subject(new Day("Tuesday"), new Time("10:00"), new Time("12:00"))),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("WeakinXXTopic", "StronginXXTopic"),
                new Subject(new Day("Wednesday"), new Time("09:00"), new Time("11:00"))),

            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet(),
                new Subject(new Day("Thursday"), new Time("13:00"), new Time("15:00"))),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet(),
                new Subject(new Day("Friday"), new Time("11:00"), new Time("13:00"))),

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet(),
                new Subject(new Day("Saturday"), new Time("15:00"), new Time("17:00")))
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
