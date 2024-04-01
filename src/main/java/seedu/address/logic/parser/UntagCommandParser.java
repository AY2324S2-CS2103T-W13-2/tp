package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.department.Department;

import java.util.Objects;
import java.util.Optional;

/**
 * Parses input arguments and creates an UntagCommand.
 */
public class UntagCommandParser implements Parser<UntagCommand> {
    @Override
    public UntagCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG, PREFIX_DEPARTMENT);

        var tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Optional<String> maybeDepartment = argMultimap.getValue(PREFIX_DEPARTMENT);
        Optional<Department> department;
        if (maybeDepartment.isPresent()){
            department = Optional.of(ParserUtil.parseDepartment(maybeDepartment.get()));
        } else {
            department = Optional.empty();
        }

        if (tags.isEmpty() && department.get().tagName.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        return new UntagCommand(index, tags, department);
    }
}
