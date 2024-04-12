package seedu.officeharbor.logic.parser;

import static seedu.officeharbor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.officeharbor.logic.commands.PhoneCommand;
import seedu.officeharbor.logic.parser.exceptions.ParseException;
import seedu.officeharbor.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class PhoneCommandParser implements Parser<PhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new PhoneCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
