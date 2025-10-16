package seedu.edudex.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.model.EduDex;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonEduDexStorage eduDexStorage = new JsonEduDexStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(eduDexStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void eduDexReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEduDexStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonEduDexStorageTest} class.
         */
        EduDex original = getTypicalEduDex();
        storageManager.saveEduDex(original);
        ReadOnlyEduDex retrieved = storageManager.readEduDex().get();
        assertEquals(original, new EduDex(retrieved));
    }

    @Test
    public void getEduDexFilePath() {
        assertNotNull(storageManager.getEduDexFilePath());
    }

}
