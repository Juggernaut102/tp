package seedu.edudex.model;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.commons.core.LogsCenter;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.subject.Subject;

/**
 * Represents the in-memory model of EduDex data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EduDex eduDex;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;
    private final FilteredList<Subject> subjects;

    /**
     * Initializes a ModelManager with the given eduDex and userPrefs.
     */
    public ModelManager(ReadOnlyEduDex eduDex, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(eduDex, userPrefs);

        logger.fine("Initializing with EduDex: " + eduDex + " and user prefs " + userPrefs);

        this.eduDex = new EduDex(eduDex);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.eduDex.getPersonList());
        this.sortedPersons = new SortedList<>(filteredPersons);

        subjects = new FilteredList<>(this.eduDex.getSubjectList());
    }

    public ModelManager() {
        this(new EduDex(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getEduDexFilePath() {
        return userPrefs.getEduDexFilePath();
    }

    @Override
    public void setEduDexFilePath(Path eduDexFilePath) {
        requireNonNull(eduDexFilePath);
        userPrefs.setEduDexFilePath(eduDexFilePath);
    }

    //=========== EduDex ================================================================================

    @Override
    public void setEduDex(ReadOnlyEduDex eduDex) {
        this.eduDex.resetData(eduDex);
    }

    @Override
    public ReadOnlyEduDex getEduDex() {
        return eduDex;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return eduDex.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        eduDex.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        eduDex.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        eduDex.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasSubject(Subject subject) {
        requireNonNull(subject);
        return eduDex.hasSubject(subject);
    }

    @Override
    public void deleteSubject(Subject target) {
        eduDex.removeSubject(target);
    }

    @Override
    public void addSubject(Subject subject) {
        eduDex.addSubject(subject);
        updateSubjectList(PREDICATE_SHOW_ALL_SUBJECTS);
    }

    @Override
    public void setSubject(Subject target, Subject editedSubject) {
        requireAllNonNull(target, editedSubject);
        eduDex.setSubject(target, editedSubject);
    }

    //==== Lesson List Accessors =============================================================

    /**
     * Finds and returns a person who has a lesson that conflicts with the given lesson.
     * Excludes the specified person from the search.
     * @param lesson
     * @param personToExclude
     * @return Person with conflicting lesson, or null if none found.
     */
    @Override
    public Person findPersonWithLessonConflict(Lesson lesson, Person personToExclude) {
        requireNonNull(lesson);
        requireNonNull(personToExclude);
        return eduDex.findPersonWithLessonConflict(lesson, personToExclude);
    }

    //=========== Subject List Accessors =============================================================
    @Override
    public ObservableList<Subject> getSubjectList() {
        return subjects;
    }

    @Override
    public void updateSubjectList(Predicate<Subject> predicate) {
        requireNonNull(predicate);
        subjects.setPredicate(predicate);
    }



    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedEduDex}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void sortFilteredPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortedPersons.setComparator(comparator);
    }

    @Override
    public ObservableList<Person> getSortedPersonList() {
        return sortedPersons;
    }

    /**
     * Sorts the lessons of each {@code Person} currently in the filtered person list.
     * <p>
     * Lessons are ordered first by the numeric value of their day (Monday → Sunday),
     * and then by their start time within each day. This ensures that every person's
     * lesson schedule is consistently displayed in chronological order.
     * </p>
     *
     * <p>This operation mutates each {@code Person}'s internal lesson list by replacing
     * it with a sorted copy, but does not alter the overall list of persons.</p>
     */
    @Override
    public void sortLessonsForEachPerson() {
        filteredPersons.forEach(person -> {
            person.setLessons(person.getLessons().stream()
                    .sorted(Comparator
                            .comparing((Lesson l) -> l.getDay().getNumericValue())
                            .thenComparing(l -> l.getStartTime().getTime()))
                    .toList());
        });
    }


    /**
     * Filters and sorts lessons for each person in the filtered list,
     * keeping only lessons that match the specified subject (case-insensitive).
     *
     * @return
     */
    @Override
    public List<Person> sortLessonsForEachPersonBySubject(String subjectKeyword) {
        requireNonNull(subjectKeyword);

        return filteredPersons.stream()
                .map(person -> {
                    Person tempPerson = person.getCopyOfPerson(); // shallow copy
                    tempPerson.setLessons(person.getLessons().stream()
                            .filter(lesson -> lesson.getSubject()
                                    .getSubjectAsString()
                                    .equalsIgnoreCase(subjectKeyword))
                            .sorted(Comparator
                                    .comparing((Lesson l) -> l.getDay().getNumericValue())
                                    .thenComparing(l -> l.getStartTime().getTime()))
                            .toList());
                    return tempPerson;
                })
                .toList();
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return eduDex.equals(otherModelManager.eduDex)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
