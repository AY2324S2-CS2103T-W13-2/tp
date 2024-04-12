package seedu.officeharbor.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.logic.commands.exceptions.CommandException;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.person.TagContainsKeywordsPredicate;

/**
 * Lists all email of contacts associated with the tag.
 */
public class PhoneCommand extends Command {

    public static final String COMMAND_WORD = "phone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the phone numbers of contacts containing the inputted tag\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_PHONE_CONTACT_SUCCESS = "Here is the list of phone numbers: %1$s" + ".\n"
            + "They have been copied to your clipboard.";
    public static final String MESSAGE_PHONE_CONTACT_EMPTY = "There are no contacts associated with that tag.";

    private final TagContainsKeywordsPredicate predicate;

    public PhoneCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_PHONE_CONTACT_EMPTY));
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            if (i == model.getFilteredPersonList().size() - 1) {
                builder.append(model.getFilteredPersonList().get(i).getPhone());
            } else {
                builder.append(model.getFilteredPersonList().get(i).getPhone()).append(",");
            }
        }

        StringSelection stringSelection = new StringSelection(builder.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        return new CommandResult(
                String.format(MESSAGE_PHONE_CONTACT_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneCommand)) {
            return false;
        }

        PhoneCommand otherPhoneCommand = (PhoneCommand) other;
        return predicate.equals(otherPhoneCommand.predicate);
    }
}
