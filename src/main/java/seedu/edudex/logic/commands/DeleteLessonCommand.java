package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;

/**
 * Deletes a lesson (by index) from a specific student in EduDex.
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "dellesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a lesson identified by the lesson index "
            + "from the student at the given index.\n"
            + "Parameters: STUDENT_INDEX LESSON_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS =
            "Deleted lesson %1$s from student %2$s";

    private final Index studentIndex;
    private final Index lessonIndex;

    /**
     * Constructs a {@code DeleteLessonCommand} to remove a lesson from a student.
     *
     * @param studentIndex index of the student in the displayed list
     * @param lessonIndex index of the lesson to delete from the student
     */
    public DeleteLessonCommand(Index studentIndex, Index lessonIndex) {
        this.studentIndex = studentIndex;
        this.lessonIndex = lessonIndex;
    }

    /**
     * Executes the command to delete a lesson from a student.
     * Retrieves the student and lesson from the model, removes the lesson,
     * and updates the student in the model.
     *
     * @param model {@code Model} which the command should operate on
     * @return {@code CommandResult} indicating success.
     * @throws CommandException if the student index or lesson index is invalid
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToEdit = lastShownList.get(studentIndex.getZeroBased());
        List<Lesson> currentLessons = new ArrayList<>(studentToEdit.getLessons());

        if (lessonIndex.getZeroBased() >= currentLessons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_INDEX);
        }

        Lesson lessonToDelete = currentLessons.remove(lessonIndex.getZeroBased());

        Person updatedPerson = new Person(studentToEdit.getName(),
                studentToEdit.getPhone(),
                studentToEdit.getSchool(),
                studentToEdit.getAddress(),
                studentToEdit.getTags());
        updatedPerson.setLessons(currentLessons);

        model.setPerson(studentToEdit, updatedPerson);

        return new CommandResult(String.format(
                MESSAGE_DELETE_LESSON_SUCCESS,
                lessonToDelete,
                studentToEdit.getName()));
    }

    /**
     * Checks whether this command is equal to another object.
     * Two {@code DeleteLessonCommand} objects are equal if they have the same student and lesson indices.
     *
     * @param other the object to compare against.
     * @return true if both commands have the same indices, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteLessonCommand
                && studentIndex.equals(((DeleteLessonCommand) other).studentIndex)
                && lessonIndex.equals(((DeleteLessonCommand) other).lessonIndex));
    }

    /**
     * Returns a string representation of this command for debugging.
     *
     * @return a string containing the student and lesson indices.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("lessonIndex", lessonIndex)
                .toString();
    }
}
