package seedu.edudex.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.edudex.logic.parser.EditLessonDescriptor;
import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.School;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setSchool(person.getSchool());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code School} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSchool(String school) {
        descriptor.setSchool(new School(school));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the lesson index for editing a specific lesson.
     */
    public EditPersonDescriptorBuilder withLessonIndex(int lessonIndex) {
        descriptor.setLessonIndex(Index.fromZeroBased(lessonIndex));
        return this;
    }

    /**
     * Sets the {@code EditLessonDescriptor} for editing a lesson.
     */
    public EditPersonDescriptorBuilder withEditLessonDescriptor(EditLessonDescriptor editLessonDescriptor) {
        descriptor.setEditLessonDescriptor(editLessonDescriptor);
        return this;
    }

    /**
     * Sets the lesson edit fields using individual parameters.
     * Creates an EditLessonDescriptor with the specified fields.
     */
    public EditPersonDescriptorBuilder withLessonEdit(int lessonIndex, String subject,
                                                      String day, String startTime, String endTime) {
        descriptor.setLessonIndex(Index.fromZeroBased(lessonIndex));

        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        if (subject != null) {
            editLessonDescriptor.setSubject(new Subject(subject));
        }
        if (day != null) {
            editLessonDescriptor.setDay(new Day(day));
        }
        if (startTime != null) {
            editLessonDescriptor.setStartTime(new Time(startTime));
        }
        if (endTime != null) {
            editLessonDescriptor.setEndTime(new Time(endTime));
        }

        descriptor.setEditLessonDescriptor(editLessonDescriptor);
        return this;
    }


    public EditPersonDescriptor build() {
        return descriptor;
    }


}
