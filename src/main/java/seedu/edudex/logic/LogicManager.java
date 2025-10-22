package seedu.edudex.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.commons.core.LogsCenter;
import seedu.edudex.logic.commands.Command;
import seedu.edudex.logic.commands.CommandResult;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.logic.parser.EduDexParser;
import seedu.edudex.logic.parser.exceptions.ParseException;
import seedu.edudex.model.Model;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EduDexParser eduDexParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        eduDexParser = new EduDexParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = eduDexParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveEduDex(model.getEduDex());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEduDex getEduDex() {
        return model.getEduDex();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Subject> getSubjectList() {
        return model.getSubjectList();
    }

    @Override
    public Path getEduDexFilePath() {
        return model.getEduDexFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
