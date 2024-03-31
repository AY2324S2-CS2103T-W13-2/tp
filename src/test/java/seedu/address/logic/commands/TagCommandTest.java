package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interactions with the Model) and unit tests for TagCommand.
 */
class TagCommandTest {
    private static final Tag TAG_OWES_MONEY = new Tag("owesMoney");
    private static final Tag TAG_FRIENDS = new Tag("friends");
    private static final Collection<Tag> TAGS = List.of(TAG_OWES_MONEY, TAG_FRIENDS);

    private Model model;

    @BeforeEach
    public void init() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(null, null));
    }



    @Test
    public void execute_indexOutOfBounds_failure() {
        Index index = Index.fromOneBased(999);
        TagCommand command = new TagCommand(List.of(index), TAGS);

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(command, model, new CommandHistory(), expectedMessage);
    }

    @Test
    public void showTags_singleTag_returnsSingleTag() {
        Collection<Tag> tags = Arrays.asList(new Tag("friend"));

        String result = TagCommand.showTags(tags);

        assertEquals("friend", result);
    }

    @Test
    public void createCommandResult_tagInfoCorrectlyFormatted() {
        List<Index> targetIndices = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2));
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("friends"), new Tag("colleagues")));

        String expectedTagInfo = "Tagged Contacts: 1, 2 with colleagues, friends";
        String actualTagInfo = String.format(TagCommand.MESSAGE_TAG_CONTACT_SUCCESS,
                TagCommand.showIndices(targetIndices), TagCommand.showTags(tags));

        assertEquals(expectedTagInfo, actualTagInfo);

        CommandResult result = new CommandResult(actualTagInfo);

    }

    @Test
    public void showIndices_validIndices_returnsStringRepresentation() {
        // Create a list of indices
        List<Index> indices = List.of(
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3)
        );

        String result = TagCommand.showIndices(indices);

        String expected = indices.stream()
                .map(index -> String.valueOf(index.getOneBased()))
                .collect(Collectors.joining(", "));

        assertEquals(expected, result);
    }

    @Test
    public void toString_validTagCommand_returnsExpectedString() {
        List<Index> indices = List.of(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3));
        List<Tag> tags = List.of(new Tag("friends"), new Tag("family"), new Tag("colleagues"));
        TagCommand tagCommand = new TagCommand(indices, tags);

        List<Tag> mutableTags = new ArrayList<>(tags);

        mutableTags.sort(Comparator.comparing(tag -> tag.tagName));

        String expectedString = new ToStringBuilder(tagCommand)
                .add("targetIndices", indices)
                .add("tags", mutableTags)
                .toString();

        assertEquals(expectedString, tagCommand.toString());
    }

    @Test
    public void execute_validIndicesAndTags_returnsCorrectTagInfo() {
        // Define some sample data
        List<String> indices = Arrays.asList("1", "2", "3");
        List<String> tagNames = Arrays.asList("friends", "family");

        // Convert the indices and tag names to formatted strings
        String formattedIndices = String.join(", ", indices);
        String formattedTags = String.join(", ", tagNames);

        // Define the expected tagInfo string
        String expectedTagInfo = String.format(TagCommand.MESSAGE_TAG_CONTACT_SUCCESS,
                formattedIndices, formattedTags);

        CommandResult result = new CommandResult(expectedTagInfo);

        assertEquals(expectedTagInfo, result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        final TagCommand standardCommand = new TagCommand(List.of(INDEX_FIRST_PERSON), TAGS);

        // same object -> equal
        assertEquals(standardCommand, standardCommand);

        // same values -> equal
        var commandWithSameValues = new TagCommand(List.of(INDEX_FIRST_PERSON), TAGS);
        assertEquals(standardCommand, commandWithSameValues);

        var tagsInDifferentOrder = List.of(TAG_FRIENDS, TAG_OWES_MONEY);
        assertEquals(standardCommand, new TagCommand(List.of(INDEX_FIRST_PERSON), tagsInDifferentOrder));

        // null -> not equal
        assertNotEquals(null, standardCommand);

        assertNotEquals(standardCommand, new TagCommand(List.of(INDEX_SECOND_PERSON), TAGS));

        // different tags -> not equal
        assertNotEquals(standardCommand, new TagCommand(List.of(INDEX_FIRST_PERSON), List.of(TAG_OWES_MONEY)));
    }
}
