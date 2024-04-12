package seedu.officeharbor.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.officeharbor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.officeharbor.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.officeharbor.testutil.Assert.assertThrows;
import static seedu.officeharbor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.officeharbor.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.officeharbor.commons.core.index.Index;
import seedu.officeharbor.logic.commands.AddCommand;
import seedu.officeharbor.logic.commands.ClearCommand;
import seedu.officeharbor.logic.commands.Command;
import seedu.officeharbor.logic.commands.DeleteCommand;
import seedu.officeharbor.logic.commands.ExitCommand;
import seedu.officeharbor.logic.commands.HelpCommand;
import seedu.officeharbor.logic.commands.ListCommand;
import seedu.officeharbor.logic.commands.MailCommand;
import seedu.officeharbor.logic.commands.PhoneCommand;
import seedu.officeharbor.logic.commands.RedoCommand;
import seedu.officeharbor.logic.commands.TagCommand;
import seedu.officeharbor.logic.commands.UndoCommand;
import seedu.officeharbor.logic.commands.UntagCommand;
import seedu.officeharbor.logic.parser.exceptions.ParseException;
import seedu.officeharbor.model.person.Person;
import seedu.officeharbor.model.person.TagContainsKeywordsPredicate;
import seedu.officeharbor.model.tag.Tag;
import seedu.officeharbor.model.tag.department.Department;
import seedu.officeharbor.testutil.PersonBuilder;
import seedu.officeharbor.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_undoCommand_returnsUndoCommand() throws ParseException {
        Command command = parser.parseCommand("undo");
        assertTrue(command instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redoCommand_returnsRedoCommand() throws ParseException {
        Command command = parser.parseCommand("redo");
        assertTrue(command instanceof RedoCommand);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        // Create a command with multiple indices separated by space
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + INDEX_SECOND_PERSON.getOneBased());

        // Create a list containing the indices used to construct the command
        List<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);

        // Assert that the parsed command is equal to a command constructed with the same indices
        assertEquals(new DeleteCommand(indices), command);
    }


    // TODO: reinstate in v1.4 after lorenz fixes whatever his code is
    // @Test
    // public void parseCommand_edit() throws Exception {
    //     Person person = new PersonBuilder().build();
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
    //     EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
    //             + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
    //     assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    // }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        // TODO: fix this test after implementing frontend for filter
        // List<String> keywords = Arrays.asList("foo", "bar", "baz");
        // FilterCommand command = (FilterCommand) parser.parseCommand(
        //         FilterCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        // assertEquals(new FilterCommand(null), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " add") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_tag() throws Exception {
        TagCommand expectedCommand = new TagCommand(List.of(INDEX_FIRST_PERSON), List.of(new Tag("alpha"),
                new Tag("beta")), Optional.of(new Department("IT")));
        assertEquals(expectedCommand, parser.parseCommand(TagCommand.COMMAND_WORD
                + " "
                + INDEX_FIRST_PERSON.getOneBased()
                + " tag:alpha tag:beta "
                + " department:IT"));
    }

    @Test
    public void parseCommand_untag() throws Exception {
        var index = INDEX_FIRST_PERSON;
        var expectedCommand = new UntagCommand(index, List.of(new Tag("alpha"), new Tag("beta")),
                Optional.of(new Department("Accounting")));
        assertEquals(expectedCommand, parser.parseCommand(UntagCommand.COMMAND_WORD
                + " "
                + INDEX_FIRST_PERSON.getOneBased()
                + " tag:alpha tag:beta "
                + " department:Accounting"));
    }

    @Test
    public void parseCommand_mail() throws Exception {
        List<String> keywords = List.of("friends", "owesMoney");
        MailCommand expectedCommand = (MailCommand) parser.parseCommand(
                MailCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new MailCommand(new TagContainsKeywordsPredicate(keywords)), expectedCommand);
    }

    @Test
    public void parseCommand_phone() throws Exception {
        List<String> keywords = List.of("friends", "owesMoney");
        PhoneCommand expectedCommand = (PhoneCommand) parser.parseCommand(
                PhoneCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new PhoneCommand(new TagContainsKeywordsPredicate(keywords)), expectedCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
