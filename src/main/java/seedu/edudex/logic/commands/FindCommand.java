package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Optional;

import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.person.DayMatchesPredicate;
import seedu.edudex.model.person.NameContainsKeywordsPredicate;
import seedu.edudex.model.person.SubjectComparator;
import seedu.edudex.model.person.SubjectMatchesPredicate;
import seedu.edudex.model.subject.Subject;

/**
 * Finds and lists all persons in EduDex whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose "
            + "names, lesson days, or subjects match the given keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + "KEYWORD [MORE_KEYWORDS]... (search by name)\n"
            + PREFIX_DAY + "DAY (search by lesson day)\n"
            + PREFIX_SUBJECT + "SUBJECT (search by lesson subject)\n"
            + "Examples:\n"
            + "• find alice bob\n"
            + "• find " + PREFIX_DAY + "Monday\n"
            + "• find " + PREFIX_SUBJECT + "Math";


    private final NameContainsKeywordsPredicate namePredicate;
    private final DayMatchesPredicate dayPredicate;
    private final SubjectMatchesPredicate subjectPredicate;

    private final SearchType searchType;

    private enum SearchType {
        NAME,
        DAY,
        SUBJECT
    }

    /**
     * Constructor for finding by name.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.namePredicate = predicate;
        this.dayPredicate = null;
        this.subjectPredicate = null;
        this.searchType = SearchType.NAME;
    }

    /**
     * Constructor for finding by day.
     */
    public FindCommand(DayMatchesPredicate predicate) {
        this.dayPredicate = predicate;
        this.namePredicate = null;
        this.subjectPredicate = null;
        this.searchType = SearchType.DAY;
    }

    /**
     * Constructor for find by subject
     */
    public FindCommand(SubjectMatchesPredicate predicate) {
        this.subjectPredicate = predicate;
        this.namePredicate = null;
        this.dayPredicate = null;
        this.searchType = SearchType.SUBJECT;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (searchType) {
        case DAY:
            model.updateFilteredPersonList(dayPredicate);
            model.sortFilteredPersonList(new SubjectComparator());
            model.sortLessonsForEachPerson();
            break;
        case SUBJECT:
            Subject subject = new Subject(subjectPredicate.getSubjectKeyword());
            subject.validateExistsIn(model);

            model.updateFilteredPersonList(subjectPredicate);
            model.sortFilteredPersonList(new SubjectComparator());
            model.sortLessonsForEachPersonBySubject(subjectPredicate.getSubjectKeyword());
            break;
        case NAME:
        default:
            model.updateFilteredPersonList(namePredicate);
            break;
        }

        return new CommandResult(String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }

        return searchType == otherFindCommand.searchType
                && Optional.ofNullable(namePredicate).equals(Optional.ofNullable(otherFindCommand.namePredicate))
                && Optional.ofNullable(dayPredicate).equals(Optional.ofNullable(otherFindCommand.dayPredicate))
                && Optional.ofNullable(subjectPredicate).equals(Optional.ofNullable(otherFindCommand.subjectPredicate));
    }

    @Override
    public String toString() {
        Object predicateToShow;
        if (searchType == SearchType.DAY) {
            predicateToShow = dayPredicate;
        } else if (searchType == SearchType.SUBJECT) {
            predicateToShow = subjectPredicate;
        } else {
            predicateToShow = namePredicate;
        }

        return new ToStringBuilder(this)
                .add("searchType", searchType)
                .add("predicate", predicateToShow)
                .toString();
    }
}
