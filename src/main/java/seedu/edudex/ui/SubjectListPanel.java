package seedu.edudex.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.edudex.commons.core.LogsCenter;
import seedu.edudex.model.subject.Subject;

/**
 * Panel containing the list of subjects.
 */
public class SubjectListPanel extends UiPart<Region> {
    private static final String FXML = "SubjectListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SubjectListPanel.class);

    @FXML
    private ListView<Subject> subjectListView;

    /**
     * Creates a {@code SubjectListPanel} with the given {@code ObservableList}.
     */
    public SubjectListPanel(ObservableList<Subject> subjectList) {
        super(FXML);
        subjectListView.setItems(subjectList);
        subjectListView.setCellFactory(listView -> new SubjectListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Subject} using a {@code SubjectCard}.
     */
    class SubjectListViewCell extends ListCell<Subject> {
        @Override
        protected void updateItem(Subject subject, boolean empty) {
            super.updateItem(subject, empty);

            if (empty || subject == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SubjectCard(subject).getRoot());
            }
        }
    }

}
