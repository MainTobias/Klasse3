package liststests;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class NumberListTest extends FxTest {
    public static final String PATH_TO_FXML = "/layout/numberList.fxml";
    private ListView<Integer> lvLeft;
    private ListView<Integer> lvRight;
    private RadioButton rbSingleSelection;
    private RadioButton rbMultipleSelection;
    private Button btnLtr;
    private Button btnRtl;

    @Start
    void initializeGUI(Stage primaryStage) throws IOException {
        startApplication(primaryStage, PATH_TO_FXML);
        lvLeft = getById("lvLeft");
        lvRight = getById("lvRight");
        rbSingleSelection = getById("rbSingleSelection");
        rbMultipleSelection = getById("rbMultipleSelection");
        btnLtr = getById("btnLtr");
        btnRtl = getById("btnRtl");
    }

    @Test
    void initialState_leftListContainsNumbersUpTo100() {
        Integer[] expected = IntStream.rangeClosed(0, 100)
                .boxed()
                .toArray(Integer[]::new);

        assertThat(lvLeft.getItems()).containsExactly(expected);
    }

    @Test
    void initialState_rightListEmpty() {
        assertThat(lvRight.getItems()).isEmpty();
    }

    @Test
    void initialState_singleSelectionMode() {
        assertThat(rbSingleSelection.isSelected()).isTrue();
    }

    @Test
    void initialState_radioButtonsBelongToSameGroup() {
        assertThat(rbSingleSelection.getToggleGroup())
                .isSameAs(rbMultipleSelection.getToggleGroup());
    }

    @Test
    void noneSelectedAnyButtonPressedAnySelectionMode_noOp(FxRobot robot) {
        assertThatCode(() -> robot.interact(btnLtr::fire)).doesNotThrowAnyException();
        assertThatCode(() -> robot.interact(btnRtl::fire)).doesNotThrowAnyException();

        robot.interact(rbMultipleSelection::fire);

        assertThatCode(() -> robot.interact(btnLtr::fire)).doesNotThrowAnyException();
        assertThatCode(() -> robot.interact(btnRtl::fire)).doesNotThrowAnyException();
    }

    @Test
    void leftToRightSingle_selectedItemRemovedFromLeftAddedToRight(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        robot.interact(() -> lvLeft.getSelectionModel().select((Integer) 4));
        robot.interact(btnLtr::fire);

        assertThat(lvLeft.getItems()).containsExactly(0, 2, 6);
        assertThat(lvRight.getItems()).containsExactly(1, 3, 4, 5, 7);
    }

    @Test
    void rightToLeftSingle_selectedItemRemovedFromRightAddedToLeft(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        robot.interact(() -> lvRight.getSelectionModel().select((Integer) 3));
        robot.interact(btnRtl::fire);

        assertThat(lvLeft.getItems()).containsExactly(0, 2, 3, 4, 6);
        assertThat(lvRight.getItems()).containsExactly(1, 5, 7);
    }

    @Test
    void leftToRightMultiple_selectedItemsRemovedFromLeftAddedToRight(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        robot.interact(rbMultipleSelection::fire);
        robot.interact(() -> lvLeft.getSelectionModel().selectIndices(0, 3));
        robot.interact(btnLtr::fire);

        assertThat(lvLeft.getItems()).containsExactly(2, 4);
        assertThat(lvRight.getItems()).containsExactly(0, 1, 3, 5, 6, 7);
    }

    @Test
    void rightToLeftMultiple_selectedItemsRemovedFromRightAddedToLeft(FxRobot robot) {
        robot.interact(lvLeft.getItems()::clear);
        robot.interact(() -> lvLeft.getItems().addAll(0, 2, 4, 6));
        robot.interact(() -> lvRight.getItems().addAll(1, 3, 5, 7));

        robot.interact(rbMultipleSelection::fire);
        robot.interact(() -> lvRight.getSelectionModel().selectIndices(1, 2));
        robot.interact(btnRtl::fire);

        assertThat(lvLeft.getItems()).containsExactly(0, 2, 3, 4, 5, 6);
        assertThat(lvRight.getItems()).containsExactly(1, 7);
    }
}
