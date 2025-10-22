package seedu.edudex.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Subject> PREDICATE_SHOW_ALL_SUBJECTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' EduDex file path.
     */
    Path getEduDexFilePath();

    /**
     * Sets the user prefs' EduDex file path.
     */
    void setEduDexFilePath(Path eduDexFilePath);

    /**
     * Replaces EduDex data with the data in {@code eduDex}.
     */
    void setEduDex(ReadOnlyEduDex eduDex);

    /** Returns the EduDex */
    ReadOnlyEduDex getEduDex();

    /**
     * Returns true if a person with the same identity as {@code person} exists in EduDex.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in EduDex.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in EduDex.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in EduDex.
     * The person identity of {@code editedPerson} must not be the same as another existing person in EduDex.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns true if a subject with the same name as {@code person} exists in EduDex.
     */
    boolean hasSubject(Subject subject);

    /**
     * Deletes the given person.
     * The subject must exist in EduDex.
     */
    void deleteSubject(Subject target);

    /**
     * Adds the given subject.
     * {@code subject} must not already exist in EduDex.
     */
    void addSubject(Subject subject);

    /**
     * Replaces the given subject {@code target} with {@code editedSubject}.
     * {@code target} must exist in EduDex.
     * The subject name of {@code editedSubject} must not be the same as another existing subject in EduDex.
     */
    void setSubject(Subject target, Subject editedSubject);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered subject list */
    ObservableList<Subject> getSubjectList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
<<<<<<< HEAD
     * Sorts the currently filtered person list according to the given {@link Comparator}.
     * This allows commands to customize the display order of persons dynamically.
     *
     * @param comparator Comparator used to determine the order of persons in the filtered list.
     *                   Must not be {@code null}.
     */
    void sortFilteredPersonList(Comparator<Person> comparator);

    /**
     * Returns an unmodifiable view of the sorted person list.
     * This list reflects the ordering applied by {@link #sortFilteredPersonList(Comparator)}.
     *
     * @return an unmodifiable {@code ObservableList<Person>} sorted according to the current comparator.
     */
    ObservableList<Person> getSortedPersonList();

    void sortLessonsForEachPerson();

    void updateSubjectList(Predicate<Subject> predicate);
}

