package seedu.edudex.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalPersons.ALICE;
import static seedu.edudex.testutil.TypicalPersons.HOON;
import static seedu.edudex.testutil.TypicalPersons.IDA;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.edudex.commons.exceptions.DataLoadingException;
import seedu.edudex.model.EduDex;
import seedu.edudex.model.ReadOnlyEduDex;

public class JsonEduDexStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEduDexStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEduDex_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEduDex(null));
    }

    private java.util.Optional<ReadOnlyEduDex> readEduDex(String filePath) throws Exception {
        return new JsonEduDexStorage(Paths.get(filePath)).readEduDex(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEduDex("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readEduDex("notJsonFormatEduDex.json"));
    }

    @Test
    public void readEduDex_invalidPersonEduDex_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduDex("invalidPersonEduDex.json"));
    }

    @Test
    public void readEduDex_invalidAndValidPersonEduDex_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduDex("invalidAndValidPersonEduDex.json"));
    }

    @Test
    public void readAndSaveEduDex_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEduDex.json");
        EduDex original = getTypicalEduDex();
        JsonEduDexStorage jsonEduDexStorage = new JsonEduDexStorage(filePath);

        // Save in new file and read back
        jsonEduDexStorage.saveEduDex(original, filePath);
        ReadOnlyEduDex readBack = jsonEduDexStorage.readEduDex(filePath).get();
        assertEquals(original, new EduDex(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonEduDexStorage.saveEduDex(original, filePath);
        readBack = jsonEduDexStorage.readEduDex(filePath).get();
        assertEquals(original, new EduDex(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonEduDexStorage.saveEduDex(original); // file path not specified
        readBack = jsonEduDexStorage.readEduDex().get(); // file path not specified
        assertEquals(original, new EduDex(readBack));

    }

    @Test
    public void saveEduDex_nullEduDex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduDex(null, "SomeFile.json"));
    }

    /**
     * Saves {@code eduDex} at the specified {@code filePath}.
     */
    private void saveEduDex(ReadOnlyEduDex eduDex, String filePath) {
        try {
            new JsonEduDexStorage(Paths.get(filePath))
                    .saveEduDex(eduDex, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEduDex_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduDex(new EduDex(), null));
    }
}
