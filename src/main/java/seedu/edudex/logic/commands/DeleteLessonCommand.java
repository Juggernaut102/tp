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

    public static final String COMMAND_WORD = "deleteLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a lesson identified by the lesson index "
            + "from the student at the given index.\n"
            + "Parameters: STUDENT_INDEX LESSON_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS =
            "Deleted lesson %1$s from student %2$s";

    private final Index studentIndex;
    private final Index lessonIndex;

    /**
     * Creates a DeleteLessonCommand to remove a lesson from a student.
     *
     * @param studentIndex index of the student in the displayed list
     * @param lessonIndex index of the lesson to delete from the student
     */
    public DeleteLessonCommand(Index studentIndex, Index lessonIndex) {
        this.studentIndex = studentIndex;
        this.lessonIndex = lessonIndex;
    }

    /**
     * Executes the deleteLesson command to remove a specific lesson
     * from the specified student in EduDex.
     *
     * @param model {@code Model} which the command should operate on
     * @return CommandResult with the success message
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
                studentToEdit.getEmail(),
                studentToEdit.getAddress(),
                studentToEdit.getTags());
        updatedPerson.setLessons(currentLessons);

        model.setPerson(studentToEdit, updatedPerson);

        return new CommandResult(String.format(
                MESSAGE_DELETE_LESSON_SUCCESS,
                lessonToDelete.getSubject(),
                studentToEdit.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteLessonCommand
                && studentIndex.equals(((DeleteLessonCommand) other).studentIndex)
                && lessonIndex.equals(((DeleteLessonCommand) other).lessonIndex));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex)
                .add("lessonIndex", lessonIndex)
                .toString();
    }
}
