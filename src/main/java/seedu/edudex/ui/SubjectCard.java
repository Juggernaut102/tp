package seedu.edudex.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.edudex.model.subject.Subject;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class SubjectCard extends UiPart<Region> {

    private static final String FXML = "SubjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/edudex-level4/issues/336">The issue on EduDex level 4</a>
     */

    public final Subject subject;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public SubjectCard(Subject subject) {
        super(FXML);
        this.subject = subject;
        name.setText(subject.subjectName);
    }
}
