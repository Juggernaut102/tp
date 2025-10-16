package seedu.edudex.model;

import java.nio.file.Path;

import seedu.edudex.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getEduDexFilePath();

}
