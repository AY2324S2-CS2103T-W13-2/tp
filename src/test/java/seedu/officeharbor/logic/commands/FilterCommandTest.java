package seedu.officeharbor.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.officeharbor.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.officeharbor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.officeharbor.testutil.TypicalPersons.CARL;
import static seedu.officeharbor.testutil.TypicalPersons.ELLE;
import static seedu.officeharbor.testutil.TypicalPersons.FIONA;
import static seedu.officeharbor.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.ModelManager;
import seedu.officeharbor.model.UserPrefs;
import seedu.officeharbor.model.person.predicate.ComponentPredicate;
import seedu.officeharbor.model.person.predicate.ComponentStringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_missingKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ArrayList<ComponentPredicate> predicate = new ArrayList<>(List.of(
                new ComponentStringPredicate.Word("Alcia", ComponentStringPredicate.Component.NAME)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(val -> predicate.stream().anyMatch(pred -> pred.test(val)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ArrayList<ComponentPredicate> predicate = new ArrayList<>(List.of(
                new ComponentStringPredicate.Word("Kurz Elle Kunz", ComponentStringPredicate.Component.NAME)));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(val -> predicate.stream().anyMatch(pred -> pred.test(val)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        ArrayList<ComponentPredicate> predicate = new ArrayList<>(List.of(
                new ComponentStringPredicate.Is("hello", ComponentStringPredicate.Component.NAME)));
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }
}
