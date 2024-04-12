package seedu.officeharbor.logic.commands;

import static seedu.officeharbor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.officeharbor.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
