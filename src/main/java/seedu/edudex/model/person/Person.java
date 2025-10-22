package seedu.edudex.model.person;

import static seedu.edudex.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.model.tag.Tag;

/**
 * Represents a Person in EduDex.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    private List<Lesson> lessons;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.lessons = new ArrayList<>(); // Initialize with an empty list of lessons
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Returns a string representation of all lessons.
     */
    public String getLessonsAsString() {
        if (lessons.isEmpty()) {
            return "No lessons scheduled.";
        }
        StringBuilder sb = new StringBuilder();
        for (Lesson lesson : lessons) {
            sb.append(lesson.toString()).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns a list of all unique subjects from the person's lessons.
     */
    public List<Subject> getAllSubjects() {
        return lessons.stream()
                .map(Lesson::getSubject)
                .distinct()
                .toList();
    }

    /**
     * Adds a lesson to the person's list of lessons.
     */
    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && lessons.equals(otherPerson.lessons);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("lessons", lessons)
                .toString();
    }
}
