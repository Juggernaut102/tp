package seedu.edudex.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.edudex.commons.exceptions.DataLoadingException;
import seedu.edudex.model.ReadOnlyEduDex;

/**
 * Represents a storage for {@link seedu.edudex.model.EduDex}.
 */
public interface EduDexStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEduDexFilePath();

    /**
     * Returns EduDex data as a {@link ReadOnlyEduDex}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEduDex> readEduDex() throws DataLoadingException;

    /**
     * @see #getEduDexFilePath()
     */
    Optional<ReadOnlyEduDex> readEduDex(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEduDex} to the storage.
     * @param eduDex cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEduDex(ReadOnlyEduDex eduDex) throws IOException;

    /**
     * @see #saveEduDex(ReadOnlyEduDex)
     */
    void saveEduDex(ReadOnlyEduDex eduDex, Path filePath) throws IOException;

}
