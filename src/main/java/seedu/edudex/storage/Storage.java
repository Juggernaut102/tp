package seedu.edudex.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.edudex.commons.exceptions.DataLoadingException;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.ReadOnlyUserPrefs;
import seedu.edudex.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EduDexStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEduDexFilePath();

    @Override
    Optional<ReadOnlyEduDex> readEduDex() throws DataLoadingException;

    @Override
    void saveEduDex(ReadOnlyEduDex eduDex) throws IOException;

}
