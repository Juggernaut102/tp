package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;

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
            + "a subject that is not in the list of subjects you teach";
    // public static final String MESSAGE_CONFLICTING_LESSON_TIMING = ;

    private Index studentIndex;
    private Lesson lessonToAdd;

    /**
     * Constructs a AddLessonCommand.
     *
     * @param studentIndex index of the student in the last shown list.
     */
    public AddLessonCommand(Index studentIndex, Lesson lessonToAdd) {
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

        // if conflicting lesson timings, throw exception

        Person originalStudent = lastShownList.get(studentIndex.getZeroBased());
        Person updatedStudent = originalStudent.getCopyOfPerson();
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
                .add("studentIndex", studentIndex)
                .add("lessonToAdd", lessonToAdd)
                .toString();
    }
}
