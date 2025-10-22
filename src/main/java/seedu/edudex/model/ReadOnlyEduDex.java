package seedu.edudex.model;

import javafx.collections.ObservableList;
import seedu.edudex.model.person.Person;

/**
 * Unmodifiable view of an EduDex
 */
public interface ReadOnlyEduDex {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the subjects list.
     * This list will not contain any duplicate subjects.
     */
    ObservableList<seedu.edudex.model.subject.Subject> getSubjectList();


}
