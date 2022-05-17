package tests.lists;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class DemoListTest extends FxTest {
    public static final String PATH_TO_FXML = "/resources/lists/list-demo.fxml";
    private TextField textField;
    private Button btnAdd;
    private Button btnClear;
    private Button btnSort;
    private ListView<String> listView;
    private Button btnDelete;

    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        btnDelete = getById("delete");
        btnAdd = getById("add");
        btnClear = getById("clear");
        btnSort = getById("sort");
        listView = getById("items");
        textField = getById("input");
    }

    @Test
    void initialState_listEmpty() {
        assertThat(listView.getItems()).isEmpty();
    }

    @Test
    void addEmptyTextField_listUnchanged(FxRobot robot) {
        StringBuilder unprintable = IntStream.rangeClosed(0, 32)
                .mapToObj(i -> (char) i)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append);
        robot.interact(textField::requestFocus);
        robot.write(unprintable.toString());

        robot.interact(() -> textField.fireEvent(new ActionEvent()));

        assertThat(listView.getItems()).isEmpty();
    }

    @Test
    void addViaTextFieldAction_itemAppended(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll("List", "is", "not", "empty"));
        robot.interact(textField::requestFocus);
        robot.write("Text");

        robot.press(KeyCode.ENTER);

        assertThat(listView.getItems()).containsExactly("List", "is", "not", "empty", "Text");
    }

    @Test
    void addViaButtonAdd_itemAppended(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll("List", "is", "not", "empty"));
        robot.interact(textField::requestFocus);
        robot.write("Text");

        robot.interact(btnAdd::fire);

        assertThat(listView.getItems()).containsExactly("List", "is", "not", "empty", "Text");
    }

    @Test
    void sortButton_listSorted(FxRobot robot) {
        List<String> values = List.of("Observer", "Strategy", "Singleton", "Bridge", "Adapter");
        robot.interact(() -> listView.getItems().addAll(values));

        robot.interact(btnSort::fire);

        assertThat(listView.getItems()).containsExactlyElementsOf(new TreeSet<>(values));
    }

    @Test
    void clearButton_listCleared(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll("List", "is", "not", "empty"));

        robot.interact(btnClear::fire);

        assertThat(listView.getItems()).isEmpty();
    }

    @Test
    void deleteButtonNothingSelected_noOp(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll("List", "is", "not", "empty"));
        robot.interact(() -> listView.getSelectionModel().clearSelection());

        assertThatCode(() -> robot.interact(btnDelete::fire))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteButtonItemSelected_selectedItemRemoved(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll("List", "is", "not", "empty"));

        robot.interact(() -> listView.getSelectionModel().select(1));
        robot.interact(btnDelete::fire);

        assertThat(listView.getItems()).containsExactly("List", "not", "empty");
    }
}
