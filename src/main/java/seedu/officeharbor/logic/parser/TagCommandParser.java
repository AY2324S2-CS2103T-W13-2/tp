package seedu.officeharbor.logic.parser;

import static seedu.officeharbor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import seedu.officeharbor.commons.core.index.Index;
import seedu.officeharbor.logic.commands.TagCommand;
import seedu.officeharbor.logic.parser.exceptions.ParseException;
import seedu.officeharbor.model.tag.Tag;
import seedu.officeharbor.model.tag.department.Department;


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
        Optional<Department> department = Optional.of(ParserUtil.parseDepartment(argMultimap
                .getValue(PREFIX_DEPARTMENT)
                .orElse("EMPTYDEP")));

        if (tags.isEmpty() && (department.get().tagName.isEmpty() || department.get().tagName.equals("EMPTYDEP"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        List<Index> indices = ParserUtil.parseIndices(argMultimap.getPreamble());


        return new TagCommand(indices, tags, department);
    }
}
