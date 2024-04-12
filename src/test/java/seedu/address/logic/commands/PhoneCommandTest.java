package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code PhoneCommand}.
 */
public class PhoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        PhoneCommand phoneFirstCommand = new PhoneCommand(firstPredicate);
        PhoneCommand phoneSecondCommand = new PhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(phoneFirstCommand.equals(phoneFirstCommand));

        // same values -> returns true
        PhoneCommand phoneFirstCommandCopy = new PhoneCommand(firstPredicate);
        assertTrue(phoneFirstCommand.equals(phoneFirstCommandCopy));

        // different types -> returns false
        assertFalse(phoneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(phoneFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(phoneFirstCommand.equals(phoneSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(PhoneCommand.MESSAGE_PHONE_CONTACT_EMPTY);
        TagContainsKeywordsPredicate predicate = preparePredicate(" ");
        PhoneCommand command = new PhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
