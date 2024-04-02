package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    private String helpMessage;
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(helpMessage, true, false);
    }

    public HelpCommand(){
        this.helpMessage = MESSAGE_USAGE;
    }

    public HelpCommand(String helpMessage){
        this.helpMessage = helpMessage;
    }
}
