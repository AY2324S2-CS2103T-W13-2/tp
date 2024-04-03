package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MailCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents the parser to parse the argument of the help command.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Main parsing method of the parser, takes in the input argument for
     * and returns the correct help command.
     *
     * @param args the input string argument
     * @return the correct help command
     * @throws ParseException
     */

    public HelpCommand parse(String args) throws ParseException {
        try {
            String[] indexTokens = args.trim().split("\\s+");
            if (indexTokens[0].length() == 0) {
                return new HelpCommand();
            }
            String commandWord = indexTokens[0];
            String helpMessage = getHelpMessage(commandWord);
            return new HelpCommand(helpMessage);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), e);
        }
    }

    private String getHelpMessage(String commandWord) throws ParseException {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return AddCommand.MESSAGE_USAGE;

        case EditCommand.COMMAND_WORD:
            return EditCommand.MESSAGE_USAGE;

        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.MESSAGE_USAGE;

        case TagCommand.COMMAND_WORD:
            return TagCommand.MESSAGE_USAGE;

        case ClearCommand.COMMAND_WORD:
            return ClearCommand.MESSAGE_USAGE;

        case FilterCommand.COMMAND_WORD:
            return FilterCommand.MESSAGE_USAGE;

        case ListCommand.COMMAND_WORD:
            return ListCommand.MESSAGE_USAGE;

        case ExitCommand.COMMAND_WORD:
            return ExitCommand.MESSAGE_USAGE;

        case UntagCommand.COMMAND_WORD:
            return UntagCommand.MESSAGE_USAGE;

        case HelpCommand.COMMAND_WORD:
            return HelpCommand.MESSAGE_USAGE;

        case UndoCommand.COMMAND_WORD:
            return UndoCommand.MESSAGE_USAGE;

        case RedoCommand.COMMAND_WORD:
            return RedoCommand.MESSAGE_USAGE;

        case MailCommand.COMMAND_WORD:
            return MailCommand.MESSAGE_USAGE;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
