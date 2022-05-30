package tests.lists;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import lists.LogEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class LoggerTest extends FxTest {
    public static final String PATH_TO_FXML = "/layout/logger.fxml";
    private Button button;
    private CheckBox checkBox;
    private ListView<LogEntry> listView;
    private RadioButton[] rbOptions;

    private static LogEntry[] createMockEntries() {
        return IntStream.range(0, 3)
                .mapToObj(i -> {
                    LogEntry entry = mock(LogEntry.class);
                    when(entry.toString()).thenReturn("Mock Entry " + i);
                    return entry;
                }).toArray(LogEntry[]::new);
    }

    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        button = getById("button");
        checkBox = getById("checkBox");
        listView = getById("listView");
        rbOptions = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> "rbOption" + i)
                .map(id -> this.<RadioButton>getById("rbOption1"))
                .toArray(RadioButton[]::new);
    }

    @Test
    void initialState_radioButtonsBelongToSameGroup() {
        assertThat(rbOptions[0].getToggleGroup())
                .isSameAs(rbOptions[1].getToggleGroup())
                .isSameAs(rbOptions[2].getToggleGroup());
    }

    @Test
    void initialState_checkBoxUnchecked() {
        assertThat(checkBox.isSelected()).isFalse();
    }

    @Test
    void initialState_listViewEmpty() {
        assertThat(listView.getItems()).isEmpty();
    }

    @Test
    void button_logEntryAppendedToList(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll(createMockEntries()));

        robot.interact(button::fire);

        assertThat(getLastMessage()).contains("Button pressed");
    }

    private String getLastMessage() {
        int lastIndex = listView.getItems().size() - 1;
        return listView.getItems().get(lastIndex).toString();
    }

    @Test
    void checkBoxChecked_logEntryCheckedAppendedToList(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll(createMockEntries()));

        robot.interact(checkBox::fire);

        assertThat(getLastMessage()).containsIgnoringCase("CheckBox checked");
    }

    @Test
    void checkBoxUnchecked_logEntryUncheckedAppendedToList(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll(createMockEntries()));
        robot.interact(checkBox::fire);
        assumeThat(checkBox.isSelected()).isTrue();

        robot.interact(checkBox::fire);

        assertThat(getLastMessage()).containsIgnoringCase("CheckBox unchecked");
    }

    @Test
    void sameRadioButtonSelectedMultipleTimes_oneLogEntryAppendedToList(FxRobot robot) {
        robot.interact(rbOptions[0]::fire);

        robot.interact(rbOptions[0]::fire);

        assertThat(listView.getItems()).hasSize(1);
    }

    @Test
    void radioButtonSelected_logEntryAppendedToList(FxRobot robot) {
        robot.interact(() -> listView.getItems().addAll(createMockEntries()));

        robot.interact(rbOptions[0]::fire);

        assertThat(getLastMessage()).containsPattern(".*1 selected");
    }
}
