package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.predicate.ComponentStringPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validArgs_returnsFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.Has("Alice", ComponentStringPredicate.Component.NAME);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter name:Alice", expectedFilterCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " filter \n\n\n name: \n\n\n Alice", expectedFilterCommand);
    }
    @Test
    public void parse_argIsModifier_returnsIsFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.Is("Alice", ComponentStringPredicate.Component.NAME);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter name.is:Alice", expectedFilterCommand);
    }

    @Test
    public void parse_argIsntModifier_returnsIsntFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.Isnt("Alice", ComponentStringPredicate.Component.NAME);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter name.isnt:Alice", expectedFilterCommand);
    }

    @Test
    public void parse_argHasModifier_returnsHasFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.Has("123", ComponentStringPredicate.Component.PHONE);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter phone.has:123", expectedFilterCommand);
    }

    @Test
    public void parse_argHasntModifier_returnsHasntFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.Hasnt("123", ComponentStringPredicate.Component.PHONE);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter phone.hasnt:123", expectedFilterCommand);
    }

    @Test
    public void parse_argWordModifier_returnsWordFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.Word("friends", ComponentStringPredicate.Component.TAG);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter tag.word:friends", expectedFilterCommand);
    }

    @Test
    public void parse_argNoWordModifier_returnsNoWordFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.NoWord("friends", ComponentStringPredicate.Component.TAG);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter tag.noword:friends", expectedFilterCommand);
    }

    @Test
    public void parse_argStartsWithModifier_returnsStartsWithFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.StartsWith("Blk 5", ComponentStringPredicate.Component.ADDRESS);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter address.startsWith:Blk 5", expectedFilterCommand);
    }

    @Test
    public void parse_argEndsWithModifier_returnsEndsWithFilterCommand() {
        ComponentStringPredicate expectedPredicate =
                new ComponentStringPredicate.EndsWith("harbor.com", ComponentStringPredicate.Component.EMAIL);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(List.of(expectedPredicate)));
        assertParseSuccess(parser, "filter email.endsWith:harbor.com", expectedFilterCommand);
    }

    @Test
    public void parse_argMultiSameComponentSameModifier_returnsCorrectFilterCommand() {
        ComponentStringPredicate expectedPredicate1 =
                new ComponentStringPredicate.Is("Alice", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate2 =
                new ComponentStringPredicate.Is("James", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate3 =
                new ComponentStringPredicate.Is("Ben", ComponentStringPredicate.Component.NAME);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(
                List.of(expectedPredicate1, expectedPredicate2, expectedPredicate3)));
        assertParseSuccess(parser, "filter name.is: Alice name.is: James name.is: Ben", expectedFilterCommand);
    }

    @Test
    public void parse_argMultiSameComponentDifferentModifier_returnsCorrectFilterCommand() {
        ComponentStringPredicate expectedPredicate1 =
                new ComponentStringPredicate.Is("Alice", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate2 =
                new ComponentStringPredicate.Isnt("James", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate3 =
                new ComponentStringPredicate.Has("Ben", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate4 =
                new ComponentStringPredicate.Hasnt("Carl", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate5 =
                new ComponentStringPredicate.Word("Daniel", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate6 =
                new ComponentStringPredicate.NoWord("Ella", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate7 =
                new ComponentStringPredicate.StartsWith("Frank", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate8 =
                new ComponentStringPredicate.EndsWith("George", ComponentStringPredicate.Component.NAME);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(
                List.of(expectedPredicate1, expectedPredicate2, expectedPredicate3, expectedPredicate4,
                        expectedPredicate5, expectedPredicate6, expectedPredicate7, expectedPredicate8)));
        assertParseSuccess(parser, "filter name.is: Alice name.isnt: James name.has: Ben "
                + "name.hasnt: Carl name.word: Daniel name.noword: Ella name.startswith:Frank name.endswith: George",
                expectedFilterCommand);
    }

    @Test
    public void parse_argMultiDifferentComponentSameModifier_returnsCorrectFilterCommand() {
        ComponentStringPredicate expectedPredicate1 =
                new ComponentStringPredicate.Is("Alice", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate2 =
                new ComponentStringPredicate.Is("james@email.com", ComponentStringPredicate.Component.EMAIL);
        ComponentStringPredicate expectedPredicate3 =
                new ComponentStringPredicate.Is("12345678", ComponentStringPredicate.Component.PHONE);
        ComponentStringPredicate expectedPredicate4 =
                new ComponentStringPredicate.Is("little india", ComponentStringPredicate.Component.ADDRESS);
        ComponentStringPredicate expectedPredicate5 =
                new ComponentStringPredicate.Is("friends", ComponentStringPredicate.Component.TAG);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(
                List.of(expectedPredicate4, expectedPredicate3, expectedPredicate1, expectedPredicate5,
                        expectedPredicate2)));
        assertParseSuccess(parser, "filter name.is: Alice email.is: james@email.com phone.is: 12345678 "
               + "address.is: little india tag.is:friends" , expectedFilterCommand);
    }

    @Test
    public void parse_argMultiDifferentComponentDifferentModifier_returnsCorrectFilterCommand() {
        ComponentStringPredicate expectedPredicate1 =
                new ComponentStringPredicate.Is("Alice", ComponentStringPredicate.Component.NAME);
        ComponentStringPredicate expectedPredicate2 =
                new ComponentStringPredicate.Has("james", ComponentStringPredicate.Component.EMAIL);
        ComponentStringPredicate expectedPredicate3 =
                new ComponentStringPredicate.EndsWith("678", ComponentStringPredicate.Component.PHONE);
        ComponentStringPredicate expectedPredicate4 =
                new ComponentStringPredicate.Word("india", ComponentStringPredicate.Component.ADDRESS);
        ComponentStringPredicate expectedPredicate5 =
                new ComponentStringPredicate.Isnt("friends", ComponentStringPredicate.Component.TAG);
        FilterCommand expectedFilterCommand = new FilterCommand(new ArrayList<>(
                List.of(expectedPredicate4, expectedPredicate3, expectedPredicate1, expectedPredicate5,
                        expectedPredicate2)));
        assertParseSuccess(parser, "filter name.is: Alice email.has: james phone.endswith: 678 "
                + "address.word: india tag.isnt:friends" , expectedFilterCommand);
    }

}
