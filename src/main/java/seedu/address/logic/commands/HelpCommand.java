package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n: Shows program usage instructions.\n"
            + "Parameters: COMMANDWORD for more information of that particular command";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String GENERAL_HELP_MESSAGE = "OfficeHarbor has a number of commands to help you organize "
            + "your contact information: \n\n " + "1. add: adds a contact \n 2. list: list out all added contacts \n "
            + "3. delete: deletes a contact \n 4. clear: deletes all contacts 5. edit: edits a contact \n "
            + "6. tag: adds a tag/department to a contact \n 7. untag: removes a tag/department from a contact\n "
            + "8. undo: undo the latest command \n 9. redo: undo the latest undo \n 10. filter: filters the list with"
            + "specific criteria \n 11. mail: opens the mail app for the listed tags \n 11. help: displays program"
            + "usage \n 12 exit: exits OfficeHarbor \n\n For more detail and format of a command, type 'help [command]'"
            + ", where [command] can be replaced by the command you want know more about. \n For eg. 'help add' "
            + "gives you the correct format more information and about the add command.";

    private String helpMessage;

    public HelpCommand() {
        this.helpMessage = GENERAL_HELP_MESSAGE;
    }
    public HelpCommand(String helpMessage) {
        this.helpMessage = helpMessage;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(helpMessage + "\n\n", true, false);
    }
}
