package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Lists all email of contacts associated with the tag.
 */
public class MailCommand extends Command {

    public static final String COMMAND_WORD = "mail";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\n: Opens the default email app with the emails of the listed tag\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_EMAIL_CONTACT_SUCCESS = "Here is the list of emails: %1$s";
    public static final String MESSAGE_EMAIL_CONTACT_EMPTY = "There are no contacts associated with that tag.";

    private final TagContainsKeywordsPredicate predicate;

    public MailCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        if (model.isFilteredPersonListEmpty()) {
            return new CommandResult(String.format(MESSAGE_EMAIL_CONTACT_EMPTY));
        }
        String emailList = model.getFilteredPersonList().stream()
                .map(person -> person.getEmail().toString())
                .collect(Collectors.joining(","));
        try {
            openMailApp(emailList);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return new CommandResult(
                String.format(MESSAGE_EMAIL_CONTACT_SUCCESS, emailList));
    }

    private void openMailApp(String emailList) throws URISyntaxException, IOException {
        // Cited from: https://stackoverflow.com/questions/2357895/java-open-default-mail-application-and-create-new-mail-and-populate-to-and-subj
        Desktop desktop;
        if (Desktop.isDesktopSupported()
                && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            URI mailto = new URI("mailto:" + emailList);
            desktop.mail(mailto);
        } else {
            throw new RuntimeException("Desktop doesn't support mailto");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MailCommand)) {
            return false;
        }

        MailCommand otherMailCommand = (MailCommand) other;
        return predicate.equals(otherMailCommand.predicate);
    }
}
