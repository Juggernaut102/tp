package seedu.edudex.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.edudex.commons.core.LogsCenter;
import seedu.edudex.commons.exceptions.DataLoadingException;
import seedu.edudex.commons.exceptions.IllegalValueException;
import seedu.edudex.commons.util.FileUtil;
import seedu.edudex.commons.util.JsonUtil;
import seedu.edudex.model.ReadOnlyEduDex;

/**
 * A class to access EduDex data stored as a json file on the hard disk.
 */
public class JsonEduDexStorage implements EduDexStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEduDexStorage.class);

    private Path filePath;

    public JsonEduDexStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEduDexFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEduDex> readEduDex() throws DataLoadingException {
        return readEduDex(filePath);
    }

    /**
     * Similar to {@link #readEduDex()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyEduDex> readEduDex(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEduDex> jsonEduDex = JsonUtil.readJsonFile(
                filePath, JsonSerializableEduDex.class);
        if (!jsonEduDex.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEduDex.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEduDex(ReadOnlyEduDex eduDex) throws IOException {
        saveEduDex(eduDex, filePath);
    }

    /**
     * Similar to {@link #saveEduDex(ReadOnlyEduDex)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEduDex(ReadOnlyEduDex eduDex, Path filePath) throws IOException {
        requireNonNull(eduDex);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEduDex(eduDex), filePath);
    }

}
