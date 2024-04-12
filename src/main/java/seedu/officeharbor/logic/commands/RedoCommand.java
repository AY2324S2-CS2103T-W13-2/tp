package seedu.officeharbor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.officeharbor.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.logic.commands.exceptions.CommandException;
import seedu.officeharbor.model.Model;
//@@author {radeon2525}-reused
//Reused from {https://github.com/se-edu/addressbook-level4/}
/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n: Redo the latest undo command"
            + "\n Parameters: Does not take in any parameter";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
