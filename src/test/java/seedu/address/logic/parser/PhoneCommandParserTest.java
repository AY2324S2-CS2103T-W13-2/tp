package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PhoneCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class PhoneCommandParserTest {

    private PhoneCommandParser parser = new PhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsPhoneCommand() {
        // no leading and trailing whitespaces
        PhoneCommand expectedPhoneCommand =
                new PhoneCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends")));
        assertParseSuccess(parser, "friends", expectedPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends  \t", expectedPhoneCommand);
    }

}
