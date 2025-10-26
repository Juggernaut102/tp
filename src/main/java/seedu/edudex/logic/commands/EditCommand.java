package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.logic.commands.AddLessonCommand.MESSAGE_SUBJECT_NOT_TAUGHT;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.edudex.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.edudex.model.person.Lesson.MESSAGE_CONFLICTING_LESSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.commons.util.CollectionUtil;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.logic.parser.EditLessonDescriptor;
import seedu.edudex.model.Model;
import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.School;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.model.tag.Tag;

/**
 * Edits the details of an existing person in EduDex.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person or a specific lesson "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_SCHOOL + "SCHOOL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "OR\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LESSON + "LESSON_INDEX "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_START + "START_TIME] "
            + "[" + PREFIX_END + "END_TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_SCHOOL + "NUS Primary School \n"
            + "Example (lesson edit): " + COMMAND_WORD + " 2 "
            + PREFIX_LESSON + "1 "
            + PREFIX_DAY + "Monday";


    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in EduDex.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;

        try {
            if (editPersonDescriptor.getLessonIndex().isPresent() &&
                    editPersonDescriptor.getEditLessonDescriptor().isPresent()) {
                // Lesson edit case
                editedPerson = createEditedPersonWithLesson(personToEdit, editPersonDescriptor.getLessonIndex().get(),
                        editPersonDescriptor.getEditLessonDescriptor().get(), model);
            }
            else {
                // Regular person edit case
                editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
            }
        } catch (IllegalArgumentException e) {
            // display start time after end time exception
            throw new CommandException(e.getMessage());
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    private Person createEditedPersonWithLesson(Person personToEdit, Index index,
                                                EditLessonDescriptor editLessonDescriptor,
                                                Model model) throws CommandException {
        assert personToEdit != null;

        List<Lesson> currentLessons = personToEdit.getLessons();

        if (currentLessons == null || currentLessons.isEmpty()) {
            throw new IllegalArgumentException(Messages.MESSAGE_NO_LESSONS);
        }
        if (index.getZeroBased() >= currentLessons.size()) {
            throw new IllegalArgumentException(Messages.MESSAGE_INVALID_LESSON_INDEX);
        }

        Lesson lessonToEdit = currentLessons.get(index.getZeroBased());

        Subject updatedSubject = editLessonDescriptor.getSubject().orElse(lessonToEdit.getSubject());
        Day updatedDay = editLessonDescriptor.getDay().orElse(lessonToEdit.getDay());
        Time updatedStartTime = editLessonDescriptor.getStartTime().orElse(lessonToEdit.getStartTime());
        Time updatedEndTime = editLessonDescriptor.getEndTime().orElse(lessonToEdit.getEndTime());

        if (!model.hasSubject(updatedSubject)) {
            throw new CommandException(MESSAGE_SUBJECT_NOT_TAUGHT);
        }

        if (!Lesson.isValidStartEndTime(updatedStartTime, updatedEndTime)) {
            throw new IllegalArgumentException(Lesson.MESSAGE_CONSTRAINTS);
        }

        Lesson editedLesson = new Lesson(updatedSubject, updatedDay, updatedStartTime, updatedEndTime);

        // Check for conflicting lessons within the same person's lessons
        Lesson conflictedLesson = personToEdit.hasLessonConflict(editedLesson, index.getZeroBased());
        if (conflictedLesson != null) {
            throw new IllegalArgumentException(MESSAGE_CONFLICTING_LESSON + " Conflicts with: " + conflictedLesson);
        }

        // Check for conflicts with all other persons' lessons
        Person personWithLessonConflict = model.findPersonWithLessonConflict(editedLesson, personToEdit);
        if (personWithLessonConflict != null) {
            throw new IllegalArgumentException(MESSAGE_CONFLICTING_LESSON + " Conflicts with lesson of: "
                    + personWithLessonConflict.getName());
        }

        // Create a new list of lessons with the edited lesson
        List<Lesson> newLessons = new ArrayList<>(currentLessons);
        newLessons.set(index.getZeroBased(), editedLesson);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getSchool(),
                personToEdit.getAddress(),
                personToEdit.getTags());
        editedPerson.setLessons(newLessons); // Set the updated list of lessons
        return editedPerson;

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        School updatedSchool = editPersonDescriptor.getSchool().orElse(personToEdit.getSchool());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        List<Lesson> currentLessons = personToEdit.getLessons();

        Person updatedPerson = new Person(updatedName, updatedPhone, updatedSchool, updatedAddress, updatedTags);
        updatedPerson.setLessons(new ArrayList<>(currentLessons));
        return updatedPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private School school;
        private Address address;
        private Set<Tag> tags;

        private Index lessonIndex;
        private EditLessonDescriptor editLessonDescriptor;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setSchool(toCopy.school);
            setAddress(toCopy.address);
            setTags(toCopy.tags);

            setLessonIndex(toCopy.lessonIndex);

            // defensive copy using constructor of EditLessonDescriptor
            if (toCopy.editLessonDescriptor != null) {
                setEditLessonDescriptor(new EditLessonDescriptor(toCopy.editLessonDescriptor));
            } else {
                setEditLessonDescriptor(null);
            }

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, school, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setSchool(School school) {
            this.school = school;
        }

        public Optional<School> getSchool() {
            return Optional.ofNullable(school);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setLessonIndex(Index lessonIndex) {
            this.lessonIndex = lessonIndex;
        }

        public Optional<Index> getLessonIndex() {
            return Optional.ofNullable(lessonIndex);
        }

        public void setEditLessonDescriptor(EditLessonDescriptor editLessonDescriptor) {
            this.editLessonDescriptor = editLessonDescriptor;
        }

        public Optional<EditLessonDescriptor> getEditLessonDescriptor() {
            return Optional.ofNullable(editLessonDescriptor);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }



        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(school, otherEditPersonDescriptor.school)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(lessonIndex, otherEditPersonDescriptor.lessonIndex)
                    && Objects.equals(editLessonDescriptor, otherEditPersonDescriptor.editLessonDescriptor);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("school", school)
                    .add("address", address)
                    .add("tags", tags)
                    .add("lessonIndex", lessonIndex)
                    .add("editLessonDescriptor", editLessonDescriptor)
                    .toString();
        }
    }
}
