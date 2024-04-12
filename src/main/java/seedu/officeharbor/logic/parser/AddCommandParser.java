package seedu.officeharbor.logic.parser;

import static seedu.officeharbor.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.officeharbor.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.officeharbor.logic.commands.AddCommand;
import seedu.officeharbor.logic.parser.exceptions.ParseException;
import seedu.officeharbor.model.person.Address;
import seedu.officeharbor.model.person.Email;
import seedu.officeharbor.model.person.Name;
import seedu.officeharbor.model.person.Person;
import seedu.officeharbor.model.person.Phone;
import seedu.officeharbor.model.tag.Tag;
import seedu.officeharbor.model.tag.department.Department;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_DEPARTMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DEPARTMENT);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Optional<String> maybeDepartment = argMultimap.getValue(PREFIX_DEPARTMENT);
        Optional<Department> department;
        if (maybeDepartment.isPresent()) {
            department = Optional.of(ParserUtil.parseDepartment(maybeDepartment.get()));
        } else {
            department = Optional.empty();
        }


        Person person = new Person(name, phone, email, address, tagList, department);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
