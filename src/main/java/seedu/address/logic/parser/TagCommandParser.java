package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.department.Department;


/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_DEPARTMENT);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DEPARTMENT);

        Collection<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Optional<Department> department;
        Optional<String> stringDepartment = argMultimap.getValue(PREFIX_DEPARTMENT);

        if (stringDepartment.isEmpty()) {
            department = Optional.empty();
        } else {
            department = Optional.of(new Department(stringDepartment.get()));
        }

        // Check if there are any arguments
        if (tags.isEmpty() && department.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        List<Index> indices = ParserUtil.parseIndices(argMultimap.getPreamble());


        return new TagCommand(indices, tags, department);
    }
}
