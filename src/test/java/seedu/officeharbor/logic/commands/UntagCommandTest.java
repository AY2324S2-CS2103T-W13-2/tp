package seedu.officeharbor.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.officeharbor.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.officeharbor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.officeharbor.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.officeharbor.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.officeharbor.commons.core.index.Index;
import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.logic.Messages;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.ModelManager;
import seedu.officeharbor.model.UserPrefs;
import seedu.officeharbor.model.tag.Tag;
import seedu.officeharbor.model.tag.department.Department;

/**
 * Contains integration tests (interactions with the Model) and unit tests for UntagCommand.
 */
class UntagCommandTest {
    private static final Tag TAG_OWES_MONEY = new Tag("owesMoney");
    private static final Tag TAG_FRIENDS = new Tag("friends");
    private static final Collection<Tag> TAGS = List.of(TAG_OWES_MONEY, TAG_FRIENDS);

    private Model model;

    @BeforeEach
    public void init() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_tagMissing_failure() {
        var index = INDEX_FIRST_PERSON;
        var personName = model.getFilteredPersonList().get(index.getZeroBased()).getName();
        var command = new UntagCommand(index, TAGS, Optional.of(new Department("Marketing")));

        var expectedMessage = String.format(
                Messages.MESSAGE_MISSING_TAG,
                personName,
                "owesMoney");

        assertCommandFailure(command, model, new CommandHistory(), expectedMessage);
    }

    @Test
    public void execute_indexOutOfBounds_failure() {
        var index = Index.fromOneBased(999);
        var command = new UntagCommand(index, TAGS, Optional.of(new Department("Finance")));

        var expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(command, model, new CommandHistory(), expectedMessage);
    }

    @Test
    public void equals() {
        final UntagCommand standardCommand = new UntagCommand(INDEX_FIRST_PERSON, TAGS,
                Optional.of(new Department("Finance")));

        // same object -> equal
        assertEquals(standardCommand, standardCommand);

        // same values -> equal
        var commandWithSameValues = new UntagCommand(INDEX_FIRST_PERSON, TAGS,
                Optional.of(new Department("Finance")));
        assertEquals(standardCommand, commandWithSameValues);

        // tags in different order -> equal
        var tagsInDifferentOrder = List.of(TAG_FRIENDS, TAG_OWES_MONEY);
        assertEquals(standardCommand, new UntagCommand(INDEX_FIRST_PERSON, tagsInDifferentOrder,
                Optional.of(new Department("Finance"))));

        // null -> not equal
        assertNotEquals(null, standardCommand);

        // different types -> not equal
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> not equal
        assertNotEquals(standardCommand, new UntagCommand(INDEX_SECOND_PERSON, TAGS,
                Optional.of(new Department("Finance"))));

        // different tags -> not equal
        assertNotEquals(standardCommand, new UntagCommand(INDEX_FIRST_PERSON, List.of(TAG_OWES_MONEY),
                Optional.of(new Department("Finance"))));
    }
}
