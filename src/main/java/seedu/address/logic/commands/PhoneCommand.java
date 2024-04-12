package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;

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
        String phoneList = model.getFilteredPersonList().stream()
                .map(person -> person.getPhone().toString())
                .collect(Collectors.joining(","));

        StringSelection stringSelection = new StringSelection(phoneList);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        return new CommandResult(
                String.format(MESSAGE_PHONE_CONTACT_SUCCESS, phoneList));
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
