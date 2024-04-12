package seedu.officeharbor.logic.commands;

import static seedu.officeharbor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.officeharbor.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.model.AddressBook;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.ModelManager;
import seedu.officeharbor.model.UserPrefs;

public class ClearCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
