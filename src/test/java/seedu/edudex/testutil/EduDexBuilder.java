package seedu.edudex.testutil;

import seedu.edudex.model.EduDex;
import seedu.edudex.model.person.Person;

/**
 * A utility class to help with building EduDex objects.
 * Example usage: <br>
 *     {@code EduDex ab = new EduDexBuilder().withPerson("John", "Doe").build();}
 */
public class EduDexBuilder {

    private EduDex eduDex;

    public EduDexBuilder() {
        eduDex = new EduDex();
    }

    public EduDexBuilder(EduDex eduDex) {
        this.eduDex = eduDex;
    }

    /**
     * Adds a new {@code Person} to the {@code EduDex} that we are building.
     */
    public EduDexBuilder withPerson(Person person) {
        eduDex.addPerson(person);
        return this;
    }

    public EduDex build() {
        return eduDex;
    }
}
