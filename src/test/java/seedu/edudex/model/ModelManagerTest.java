package seedu.edudex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.edudex.testutil.Assert.assertThrows;
import static seedu.edudex.testutil.TypicalPersons.ALICE;
import static seedu.edudex.testutil.TypicalPersons.BENSON;
import static seedu.edudex.testutil.TypicalSubjects.MATH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.model.person.NameContainsKeywordsPredicate;
import seedu.edudex.testutil.EduDexBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EduDex(), new EduDex(modelManager.getEduDex()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEduDexFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setEduDexFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setEduDexFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setEduDexFilePath(null));
    }

    @Test
    public void setEduDexFilePath_validPath_setsEduDexFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setEduDexFilePath(path);
        assertEquals(path, modelManager.getEduDexFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInEduDex_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInEduDex_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasSubject_nullSubject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSubject(null));
    }

    @Test
    public void hasSubject_subjectNotInEduDex_returnsFalse() {
        assertFalse(modelManager.hasSubject(MATH));
    }

    @Test
    public void hasSubject_subjectInEduDex_returnsTrue() {
        modelManager.addSubject(MATH);
        assertTrue(modelManager.hasSubject(MATH));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        EduDex eduDex = new EduDexBuilder().withPerson(ALICE).withPerson(BENSON).build();
        EduDex differentEduDex = new EduDex();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(eduDex, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(eduDex, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different eduDex -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEduDex, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(eduDex, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEduDexFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(eduDex, differentUserPrefs)));
    }

}
