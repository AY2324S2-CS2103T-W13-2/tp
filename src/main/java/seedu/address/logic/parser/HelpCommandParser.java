package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

public class HelpCommandParser implements Parser<HelpCommand> {

    public HelpCommand parse(String args) throws ParseException {
        try {
            String[] indexTokens = args.trim().split("\\s+");
            if(indexTokens[0].length() == 0){
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
