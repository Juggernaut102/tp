package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.edudex.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.edudex.model.person.Lesson.MESSAGE_CONFLICTING_LESSON;

import java.util.ArrayList;
import java.util.List;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.logic.parser.EditLessonDescriptor;
import seedu.edudex.model.Model;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;

/**
 * Edits a specific lesson of a student in EduDex.
 */
public class EditLessonCommand extends Command {
    public static final String COMMAND_WORD = "editlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a lesson of the student "
            + "specified by the person and lesson index numbers. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "LESSON_INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_START + "START_TIME] "
            + "[" + PREFIX_END + "END_TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 2 "
            + PREFIX_DAY + "Monday "
            + PREFIX_START + "14:00";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited lesson for student: %1$s\nLesson: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one lesson field to edit must be provided.";
    private static final String MESSAGE_EDIT_SUBJECT_NOT_TAUGHT = "You tried to edit a lesson with "
            + "a subject not taught.\nEither use \"addsub SUBJECT\" to add a subject first."
            + "\nOr use \"dellesson STUDENT_INDEX LESSON_INDEX\" to delete lesson of old subject";

    private final Index personIndex;
    private final Index lessonIndex;
    private final EditLessonDescriptor editLessonDescriptor;

    /**
     * @param personIndex index of the person in the filtered person list
     * @param lessonIndex index of the lesson in the person's lesson list
     * @param editLessonDescriptor details to edit the lesson with
     */
    public EditLessonCommand(Index personIndex, Index lessonIndex, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(personIndex);
        requireNonNull(lessonIndex);
        requireNonNull(editLessonDescriptor);

        this.personIndex = personIndex;
        this.lessonIndex = lessonIndex;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        Person editedPerson;

        try {
            editedPerson = createEditedPersonWithLesson(personToEdit, lessonIndex, editLessonDescriptor, model);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        Lesson editedLesson = editedPerson.getLessons().get(lessonIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS,
                editedPerson.getName(), editedLesson));
    }

    /**
     * Creates and returns a {@code Person} with the edited lesson.
     *
     * @param personToEdit The person whose lesson is to be edited.
     * @param lessonIndex The index of the lesson to edit in the person's lesson list.
     * @param editLessonDescriptor Details of the lesson fields to edit.
     * @param model The model containing the application's data and business logic.
     * @return A new {@code Person} object with the edited lesson.
     * @throws CommandException If the lesson cannot be edited due to validation failures.
     */
    private Person createEditedPersonWithLesson(Person personToEdit, Index lessonIndex,
                                                EditLessonDescriptor editLessonDescriptor,
                                                Model model) throws CommandException {
        assert personToEdit != null;

        List<Lesson> currentLessons = personToEdit.getLessons();

        if (currentLessons == null || currentLessons.isEmpty()) {
            throw new IllegalArgumentException(Messages.MESSAGE_NO_LESSONS);
        }
        if (lessonIndex.getZeroBased() >= currentLessons.size()) {
            throw new IllegalArgumentException(Messages.MESSAGE_INVALID_LESSON_INDEX);
        }

        Lesson lessonToEdit = currentLessons.get(lessonIndex.getZeroBased());

        Subject updatedSubject = editLessonDescriptor.getSubject().orElse(lessonToEdit.getSubject());
        Day updatedDay = editLessonDescriptor.getDay().orElse(lessonToEdit.getDay());
        Time updatedStartTime = editLessonDescriptor.getStartTime().orElse(lessonToEdit.getStartTime());
        Time updatedEndTime = editLessonDescriptor.getEndTime().orElse(lessonToEdit.getEndTime());

        if (!model.hasSubject(updatedSubject)) {
            throw new CommandException(MESSAGE_EDIT_SUBJECT_NOT_TAUGHT);
        }

        if (!Lesson.isValidStartEndTime(updatedStartTime, updatedEndTime)) {
            throw new IllegalArgumentException(Lesson.MESSAGE_CONSTRAINTS);
        }

        Lesson editedLesson = new Lesson(updatedSubject, updatedDay, updatedStartTime, updatedEndTime);

        // Check for conflicting lessons within the same person's lessons
        Lesson conflictedLesson = personToEdit.hasLessonConflict(editedLesson, lessonIndex);
        if (conflictedLesson != null) {
            throw new IllegalArgumentException(MESSAGE_CONFLICTING_LESSON
                    + "\nConflicts with an existing lesson the student has: " + conflictedLesson);
        }

        // Check for conflicts with all other persons' lessons
        Person personWithLessonConflict = model.findPersonWithLessonConflict(editedLesson, personToEdit);
        if (personWithLessonConflict != null) {
            throw new IllegalArgumentException(MESSAGE_CONFLICTING_LESSON + "\nConflicts with lesson of: "
                    + personWithLessonConflict.getName());
        }

        // Create a new list of lessons with the edited lesson
        List<Lesson> newLessons = new ArrayList<>(currentLessons);
        newLessons.set(lessonIndex.getZeroBased(), editedLesson);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getSchool(),
                personToEdit.getAddress(),
                personToEdit.getTags());
        editedPerson.setLessons(newLessons);
        return editedPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditLessonCommand)) {
            return false;
        }

        EditLessonCommand otherCommand = (EditLessonCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && lessonIndex.equals(otherCommand.lessonIndex)
                && editLessonDescriptor.equals(otherCommand.editLessonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("lessonIndex", lessonIndex)
                .add("editLessonDescriptor", editLessonDescriptor)
                .toString();
    }
}
