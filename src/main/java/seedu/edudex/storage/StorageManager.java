package seedu.edudex.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.edudex.commons.core.LogsCenter;
import seedu.edudex.commons.exceptions.DataLoadingException;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.ReadOnlyUserPrefs;
import seedu.edudex.model.UserPrefs;

/**
 * Manages storage of EduDex data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EduDexStorage eduDexStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code EduDexStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(EduDexStorage eduDexStorage, UserPrefsStorage userPrefsStorage) {
        this.eduDexStorage = eduDexStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ EduDex methods ==============================

    @Override
    public Path getEduDexFilePath() {
        return eduDexStorage.getEduDexFilePath();
    }

    @Override
    public Optional<ReadOnlyEduDex> readEduDex() throws DataLoadingException {
        return readEduDex(eduDexStorage.getEduDexFilePath());
    }

    @Override
    public Optional<ReadOnlyEduDex> readEduDex(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eduDexStorage.readEduDex(filePath);
    }

    @Override
    public void saveEduDex(ReadOnlyEduDex eduDex) throws IOException {
        saveEduDex(eduDex, eduDexStorage.getEduDexFilePath());
    }

    @Override
    public void saveEduDex(ReadOnlyEduDex eduDex, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eduDexStorage.saveEduDex(eduDex, filePath);
    }

}
