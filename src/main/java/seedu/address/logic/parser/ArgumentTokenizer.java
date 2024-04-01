package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value ...}<br>
 *     e.g. {@code some preamble text t/ 11.00 t/12.00 k/ m/ July}  where prefixes are {@code t/ k/ m/}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps prefixes to their
     * respective argument values. Only the given prefixes will be recognized in the arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to tokenize the arguments string with
     * @return           ArgumentMultimap object that maps prefixes to their arguments
     */
    public static ArgumentMultimap tokenize(String argsString, Prefix... prefixes) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, prefixes);
        return extractArguments(argsString, positions);
    }

    /**
     * Tokenizes an arguments string of the filer command and returns an {@code ArgumentMultimap} object that maps
     * prefixes to their respective argument values.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @return           ArgumentMultimap object that maps prefixes to their arguments
     */
    public static ArgumentMultimap filterTokenize(String argsString) {
        List<PrefixPosition> positionsAndModifier = findAllPrefixAndModifierPositions(argsString);
        return extractFilterArguments(argsString, positionsAndModifier);
    }

    /**
     * Finds all zero-based prefix positions in the given arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to find in the arguments string
     * @return           List of zero-based prefix positions in the given arguments string
     */
    private static List<PrefixPosition> findAllPrefixPositions(String argsString, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .flatMap(prefix -> findPrefixPositions(argsString, prefix).stream())
                .collect(Collectors.toList());
    }

    /**
     * {@see findAllPrefixPositions}
     */
    private static List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();

        int prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), 0);
        while (prefixPosition != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, prefixPosition);
            positions.add(extendedPrefix);
            prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), prefixPosition);
        }

        return positions;
    }

    private static List<PrefixPosition> findAllPrefixAndModifierPositions(String argsString) {
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_EMAIL, PREFIX_PHONE};
        return Arrays.stream(prefixes)
                .flatMap(prefix -> findPrefixAndModifierPositions(argsString, prefix).stream())
                .collect(Collectors.toList());
    }

    private static List<PrefixPosition> findPrefixAndModifierPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();
        String keyword = prefix.getPrefix().substring(0, prefix.getPrefix().length() - 1);
        int prefixPosition = findFilterPrefixPosition(argsString, keyword, 0);
        while (prefixPosition != -1) {
            Prefix prefixWithModifier = getPrefixWithModifier(argsString, keyword, prefixPosition);
            PrefixPosition extendedPrefix = new PrefixPosition(prefixWithModifier, prefixPosition);
            positions.add(extendedPrefix);
            prefixPosition = findFilterPrefixPosition(argsString, keyword, prefixPosition);
        }

        return positions;
    }

    private static Prefix getPrefixWithModifier(String argsString, String prefix, int fromIndex) {
        Modifier modifier = determinePrefixModifier(argsString, prefix, fromIndex);
        return new Prefix(prefix, modifier);
    }

    private static Modifier determinePrefixModifier(String argsString, String prefix, int fromIndex) {
        int colonIndex = argsString.indexOf(":", fromIndex);
        int addIndexForPoint = argsString.charAt(fromIndex + prefix.length()) == '.' ? 1 : 0;
        String arg = argsString.substring(fromIndex + prefix.length() + addIndexForPoint, colonIndex);
        return Modifier.getModifier(arg.trim());
    }

    /**
     * Returns the index of the first occurrence of {@code prefix} in
     * {@code argsString} starting from index {@code fromIndex}. An occurrence
     * is valid if there is a whitespace before {@code prefix}. Returns -1 if no
     * such occurrence can be found.
     *
     * E.g if {@code argsString} = "e/hip/900", {@code prefix} = "p/" and
     * {@code fromIndex} = 0, this method returns -1 as there are no valid
     * occurrences of "p/" with whitespace before it. However, if
     * {@code argsString} = "e/hi p/900", {@code prefix} = "p/" and
     * {@code fromIndex} = 0, this method returns 5.
     */
    private static int findPrefixPosition(String argsString, String prefix, int fromIndex) {
        int prefixIndex = argsString.indexOf(" " + prefix, fromIndex);
        return prefixIndex == -1 ? -1
                : prefixIndex + 1; // +1 as offset for whitespace
    }

    private static int findFilterPrefixPosition(String argsString, String prefix, int fromIndex) {
        int prefixIndexWithColon = argsString.indexOf(" " + prefix + ":", fromIndex);
        int prefixIndexWithPoint = argsString.indexOf(" " + prefix + ".", fromIndex);
        return prefixIndexWithColon != -1
                ? prefixIndexWithColon + 1
                : prefixIndexWithPoint != -1
                ? prefixIndexWithPoint + 1
                : -1;
    }

    /**
     * Extracts prefixes and their argument values, and returns an {@code ArgumentMultimap} object that maps the
     * extracted prefixes to their respective arguments. Prefixes are extracted based on their zero-based positions in
     * {@code argsString}.
     *
     * @param argsString      Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixPositions Zero-based positions of all prefixes in {@code argsString}
     * @return                ArgumentMultimap object that maps prefixes to their arguments
     */
    private static ArgumentMultimap extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Map prefixes to their argument values (if any)
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            // Extract and store prefixes and their arguments
            Prefix argPrefix = prefixPositions.get(i).getPrefix();
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            argMultimap.put(argPrefix, argValue);
        }

        return argMultimap;
    }

    private static String extractArgumentValue(String argsString,
                                        PrefixPosition currentPrefixPosition,
                                        PrefixPosition nextPrefixPosition) {
        Prefix prefix = currentPrefixPosition.getPrefix();

        int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getLength();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    private static ArgumentMultimap extractFilterArguments(String argsString, List<PrefixPosition> prefixPositions) {

        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            Prefix argPrefix = prefixPositions.get(i).getPrefix();
            String argValue = extractFilterArguments(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            argMultimap.put(argPrefix, argValue);
        }

        return argMultimap;
    }

    private static String extractFilterArguments(String argsString,
                                               PrefixPosition currentPrefixPosition,
                                               PrefixPosition nextPrefixPosition) {

        int valueStartPos = currentPrefixPosition.getStartPosition()
                + actualPrefixLength(argsString, currentPrefixPosition);
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    private static int actualPrefixLength(String argsString, PrefixPosition prefix) {
        int prefixLength = prefix.getPrefix().getPrefix().length();
        if (argsString.charAt(prefix.getStartPosition() + prefixLength) == ':') {
            return prefixLength + 1;
        }
        return prefix.getPrefix().getLength() + 1;
    }

    /**
     * Represents a prefix's position in an arguments string.
     */
    private static class PrefixPosition {
        private int startPosition;
        private final Prefix prefix;

        PrefixPosition(Prefix prefix, int startPosition) {
            this.prefix = prefix;
            this.startPosition = startPosition;
        }

        int getStartPosition() {
            return startPosition;
        }

        Prefix getPrefix() {
            return prefix;
        }
    }

}
