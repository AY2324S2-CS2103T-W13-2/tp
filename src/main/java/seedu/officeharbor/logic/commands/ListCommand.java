package seedu.officeharbor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.officeharbor.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n: Lists all contacts"
            + "\n Parameters: Does not take in any parameter";

    public static final String MESSAGE_EMPTY = "The list is empty";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().size() == 0) {
            return new CommandResult(MESSAGE_EMPTY);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
