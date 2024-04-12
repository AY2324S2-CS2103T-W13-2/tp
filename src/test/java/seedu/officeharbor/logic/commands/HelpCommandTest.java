package seedu.officeharbor.logic.commands;

import static seedu.officeharbor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.officeharbor.logic.commands.HelpCommand.GENERAL_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(GENERAL_HELP_MESSAGE + "\n\n", true, false);
        assertCommandSuccess(new HelpCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
