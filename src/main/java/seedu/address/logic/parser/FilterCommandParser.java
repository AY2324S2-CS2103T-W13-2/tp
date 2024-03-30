package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicate.ComponentPredicate;
import seedu.address.model.person.predicate.ComponentStringPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        assert(args != null);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.filterTokenize(args);
        ArrayList<ComponentPredicate> components = getAllPredicates(argMultimap);
        return new FilterCommand(components);
    }

    /**
     * Converts all argument mappings to usable component predicate for the filter command
     *
     * @param argMultimap
     * @return all the input filter component predicate in the form af an arraylist.
     * @throws ParseException
     */

    private static ArrayList<ComponentPredicate> getAllPredicates(ArgumentMultimap argMultimap) throws ParseException {
        assert(argMultimap != null);
        ArrayList<ComponentPredicate> components = new ArrayList<>();
        for (Prefix prefix: argMultimap.getPrefixes()) {
            if (prefix.getLength() == 0) {
                continue;
            }
            ArrayList<ComponentPredicate> componentPredicates = new ArrayList<>();
            for (String value : argMultimap.getAllValues(prefix)) {
                ComponentPredicate componentPredicate = convertPredicate(prefix, value);
                componentPredicates.add(componentPredicate);
            }
            components.addAll(componentPredicates);
        }
        return components;
    }

    /**
     * Takes in a pair of prefix and each mapped value and convert them into different component predicate depending
     * on the modifier of the prefix.
     *
     * @param prefix
     * @param arg
     * @return ComponentPredicate corresponding to the input prefix
     * @throws ParseException
     */

    private static ComponentPredicate convertPredicate(Prefix prefix, String arg) throws ParseException {
        assert(prefix != null && arg != null);
        ComponentStringPredicate.Component component = prefix.extractComponent();
        Modifier modifier = prefix.getModifier();
        ComponentPredicate predicateResult;
        switch (modifier) {
        case IS:
            predicateResult = new ComponentStringPredicate.Is(arg, component);
            break;
        case ISNT:
            predicateResult = new ComponentStringPredicate.Isnt(arg, component);
            break;
        case HAS:
            predicateResult = new ComponentStringPredicate.Has(arg, component);;
            break;
        case HASNT:
            predicateResult = new ComponentStringPredicate.Hasnt(arg, component);;
            break;
        case WORD:
            predicateResult = new ComponentStringPredicate.Word(arg, component);
            break;
        case NOWORD:
            predicateResult = new ComponentStringPredicate.NoWord(arg, component);
            break;
        case STARTSWITH:
            predicateResult = new ComponentStringPredicate.StartsWith(arg, component);
            break;
        case ENDSWITH:
            predicateResult = new ComponentStringPredicate.EndsWith(arg, component);
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        return predicateResult;
    }

}
