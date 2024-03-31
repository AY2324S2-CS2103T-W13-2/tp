package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.department.Department;

/**
 * Deletes a tag from a person with its displayed index.
 */
public class UntagCommand extends Command {
    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag from contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer) t/TAG [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + "1 t/friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag %2$s from %1$s";

    private final Index index;
    private final Set<Tag> tags;
    private final Department department;

    /**
     * Creates a command to delete a {@code tag} from the person at {@code index}.
     */
    public UntagCommand(Index index, Collection<Tag> tags, Department department) {
        this.index = index;
        this.tags = new HashSet<>(tags);
        this.department = department;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        var lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() > lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        var personToUntag = lastShownList.get(index.getZeroBased());
        var untaggedPerson = untag(personToUntag);
        model.setPerson(personToUntag, untaggedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(
                MESSAGE_DELETE_TAG_SUCCESS,
                Messages.format(untaggedPerson),
                showTags(tags)));
    }

    private Person untag(Person personToUntag) throws CommandException {
        var personTags = new HashSet<Tag>(personToUntag.getTags());

        validateAllTagsExist(personToUntag, personTags);
        personTags.removeAll(tags);

        var dep = department;

        if(!Objects.isNull(department)){
            dep = null;
        }

        return new Person(
                personToUntag.getName(),
                personToUntag.getPhone(),
                personToUntag.getEmail(),
                personToUntag.getAddress(),
                personTags,
                dep);
    }

    private void validateAllTagsExist(Person personToUntag, HashSet<Tag> personTags) throws CommandException {
        var missingTagNames = tags
                .stream()
                .filter((tag) -> !personTags.contains(tag))
                .collect(Collectors.toList());
        if (!missingTagNames.isEmpty()) {
            throw new CommandException(
                    String.format(
                            Messages.MESSAGE_MISSING_TAG,
                            personToUntag.getName(),
                            showTags(missingTagNames)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof UntagCommand) {
            UntagCommand obj = (UntagCommand) other;
            return index.equals(obj.index) && tags.equals(obj.tags);
        }

        return false;
    }

    private static String showTags(Collection<Tag> tags) {
        return tags.stream().map((tag) -> tag.tagName)
                .sorted() // making the output order deterministic
                .collect(Collectors.joining(", "));
    }
}
