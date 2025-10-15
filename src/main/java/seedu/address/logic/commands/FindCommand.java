package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.DayMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or days contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + "KEYWORD [MORE_KEYWORDS]...\n"
            + PREFIX_DAY + "DAY (to search by tuition day)\n"
            + "Example:\n"
            + "  " + COMMAND_WORD + " alice bob\n"
            + "  " + COMMAND_WORD + " " + PREFIX_DAY + "Monday";


    private final NameContainsKeywordsPredicate namePredicate;
    private final DayMatchesPredicate dayPredicate;
    private final boolean isFindByDay;

    /**
     * Constructor for finding by name.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.namePredicate = predicate;
        this.dayPredicate = null;
        this.isFindByDay = false;
    }

    /**
     * Constructor for finding by day.
     */
    public FindCommand(DayMatchesPredicate predicate) {
        this.dayPredicate = predicate;
        this.namePredicate = null;
        this.isFindByDay = true;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (isFindByDay) {
            model.updateFilteredPersonList(dayPredicate);
        } else {
            model.updateFilteredPersonList(namePredicate);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return isFindByDay == otherFindCommand.isFindByDay
                && Optional.ofNullable(namePredicate).equals(Optional.ofNullable(otherFindCommand.namePredicate))
                && Optional.ofNullable(dayPredicate).equals(Optional.ofNullable(otherFindCommand.dayPredicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("findByDay", isFindByDay)
                .add("predicate", isFindByDay ? dayPredicate : namePredicate)
                .toString();
    }
}
