package seedu.edudex.model.subject;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edudex.model.subject.exceptions.DuplicateSubjectException;
import seedu.edudex.model.subject.exceptions.SubjectNotFoundException;

/**
 * A list of subject that enforces uniqueness between its elements and does not allow nulls.
 * A subject is considered unique by comparing using {@code Subject#isSameSubject(Subject)}. As such, adding and
 * updating of subjects uses Subject#isSameSubject(Subject) for equality so as to ensure that the subject being added or
 * updated is unique in terms of identity in the UniqueSubjectList. However, the removal of a subject uses
 * Subject#equals(Object) so as to ensure that the subject with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Subject#isSameSubject(Subject)
 */
public class UniqueSubjectList implements Iterable<Subject> {

    private final ObservableList<Subject> internalList = FXCollections.observableArrayList();
    private final ObservableList<Subject> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent subject as the given argument.
     */
    public boolean contains(Subject toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSubject);
    }

    /**
     * Adds a subject to the list.
     * The subject must not already exist in the list.
     */
    public void add(Subject toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSubjectException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the subject {@code target} in the list with {@code editedSubject}.
     * {@code target} must exist in the list.
     * The subject name of {@code editedSubject} must not be the same as another existing subject in the list.
     */
    public void setSubject(Subject target, Subject editedSubject) {
        requireAllNonNull(target, editedSubject);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SubjectNotFoundException();
        }

        if (!target.isSameSubject(editedSubject) && contains(editedSubject)) {
            throw new DuplicateSubjectException();
        }

        internalList.set(index, editedSubject);
    }

    /**
     * Removes the equivalent subject from the list.
     * The subject must exist in the list.
     */
    public void remove(Subject toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SubjectNotFoundException();
        }
    }

    public void setSubjects(seedu.edudex.model.subject.UniqueSubjectList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code subjects}.
     * {@code subjects} must not contain duplicate subjects.
     */
    public void setSubjects(List<Subject> subjects) {
        requireAllNonNull(subjects);
        if (!subjectsAreUnique(subjects)) {
            throw new DuplicateSubjectException();
        }

        internalList.setAll(subjects);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Subject> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Subject> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.edudex.model.subject.UniqueSubjectList)) {
            return false;
        }

        seedu.edudex.model.subject.UniqueSubjectList otherUniqueSubjectList =
                (seedu.edudex.model.subject.UniqueSubjectList) other;
        return internalList.equals(otherUniqueSubjectList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code subjects} contains only unique subjects.
     */
    private boolean subjectsAreUnique(List<Subject> subjects) {
        for (int i = 0; i < subjects.size() - 1; i++) {
            for (int j = i + 1; j < subjects.size(); j++) {
                if (subjects.get(i).isSameSubject(subjects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

