package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.edudex.model.person.Lesson.MESSAGE_CONFLICTING_LESSON;

import java.util.List;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;

/**
 * Adds a Lesson to the specified Student.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a lesson to the student specified by index. "
            + "Parameters: "
            + "STUDENT_INDEX "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_DAY + "DAY "
            + PREFIX_START + "START_TIME "
            + PREFIX_END + "END_TIME \n"
            + "Example: " + COMMAND_WORD
            + " 1 "
            + PREFIX_SUBJECT + "mathematics "
            + PREFIX_DAY + "Monday "
            + PREFIX_START + "12:00 "
            + PREFIX_END + "13:00";

    public static final String MESSAGE_ADD_LESSON_SUCCESS = "New lesson: %1$s, added to student: %2$s";
    public static final String MESSAGE_SUBJECT_NOT_TAUGHT = "You tried to add a lesson with "
            + "a subject that is not in the list of subjects you teach\n. "
            + "Use \"addsub SUBJECT\" to add a subject first.";
    // public static final String MESSAGE_CONFLICTING_LESSON_TIMING = ;

    private Index studentIndex;
    private Lesson lessonToAdd;

    /**
     * Constructs a AddLessonCommand.
     *
     * @param studentIndex index of the student in the last shown list.
     */
    public AddLessonCommand(Index studentIndex, Lesson lessonToAdd) {
        requireNonNull(studentIndex);
        requireNonNull(lessonToAdd);
        this.studentIndex = studentIndex;
        this.lessonToAdd = lessonToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (studentIndex.getOneBased() > lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // if list of subjects taught does not contain input subject, throw exception
        Subject subjectOfAddedLesson = lessonToAdd.getSubject();
        if (!model.hasSubject(subjectOfAddedLesson)) {
            throw new CommandException(MESSAGE_SUBJECT_NOT_TAUGHT);
        }

        Person originalStudent = lastShownList.get(studentIndex.getZeroBased());
        Person updatedStudent = originalStudent.getCopyOfPerson();

        // If we have conflicting lesson timings, throw exception
        // 1) Check for conflicting lessons within the same student's lessons
        Lesson conflictedLesson = originalStudent.hasLessonConflict(lessonToAdd, null);
        if (conflictedLesson != null) {
            throw new CommandException(MESSAGE_CONFLICTING_LESSON
                    + "\nConflicts with an existing lesson the student has: " + conflictedLesson);
        }

        // 2) Check for conflicts with all other students' lessons
        Person personWithLessonConflict = model.findPersonWithLessonConflict(lessonToAdd, originalStudent);
        if (personWithLessonConflict != null) {
            throw new CommandException(MESSAGE_CONFLICTING_LESSON + "\nConflicts with lesson of: "
                    + personWithLessonConflict.getName());
        }

        updatedStudent.addLesson(lessonToAdd);
        model.setPerson(originalStudent, updatedStudent);

        return new CommandResult(String.format(
                MESSAGE_ADD_LESSON_SUCCESS,
                lessonToAdd,
                updatedStudent.getName()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand other = (AddLessonCommand) obj;
        return this.studentIndex.equals(other.studentIndex)
                && this.lessonToAdd.equals(other.lessonToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentIndex", studentIndex.getOneBased())
                .add("lessonToAdd", lessonToAdd)
                .toString();
    }
}
