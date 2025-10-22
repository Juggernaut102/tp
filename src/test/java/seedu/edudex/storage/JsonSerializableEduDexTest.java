package seedu.edudex.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edudex.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.exceptions.IllegalValueException;
import seedu.edudex.commons.util.JsonUtil;
import seedu.edudex.model.EduDex;
import seedu.edudex.testutil.TypicalPersons;

public class JsonSerializableEduDexTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEduDexTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsEduDex.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonEduDex.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonEduDex.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableEduDex dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableEduDex.class).get();
        EduDex eduDexFromFile = dataFromFile.toModelType();
        EduDex typicalPersonsEduDex = TypicalPersons.getTypicalEduDex();
        assertEquals(eduDexFromFile, typicalPersonsEduDex);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEduDex dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableEduDex.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableEduDex dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableEduDex.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEduDex.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }
}
