package seedu.officeharbor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.officeharbor.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.officeharbor.commons.core.LogsCenter;
import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.logic.commands.exceptions.CommandException;
import seedu.officeharbor.model.Model;

//@@author {radeon2525}-reused
//{https://github.com/se-edu/addressbook-level4/}
// with minor modifications

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n: Undo the latest command"
            + "\n Parameters: Does not take in any parameter";
    private final Logger logger = LogsCenter.getLogger(UndoCommand.class);

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        logger.log(Level.INFO, "Attempting to execute UndoCommand");

        if (!model.canUndoAddressBook()) {
            logger.log(Level.WARNING, "No more commands to undo!");
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        logger.log(Level.INFO, "Undo successful!");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
