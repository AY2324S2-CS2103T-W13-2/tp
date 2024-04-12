package seedu.officeharbor.logic.commands;

import static seedu.officeharbor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.officeharbor.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.officeharbor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.officeharbor.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.ModelManager;
import seedu.officeharbor.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
