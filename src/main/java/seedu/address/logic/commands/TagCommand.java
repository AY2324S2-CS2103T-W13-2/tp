package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.*;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.department.Department;

/**
 * Tags contacts identified using their displayed indices in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the contacts identified by the index numbers"
            + " used in the displayed contact list with the specified tag.\n"
            + "Parameters: INDEX1 INDEX2... tag:TAG [department:DEPARTMENT]\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 tag: friends department: IT";


    public static final String MESSAGE_TAG_CONTACT_SUCCESS = "Tagged Contacts: %1$s with %2$s";

    private final List<Index> targetIndices;
    private final Set<Tag> tags;
    private final Optional<Department> department;


    /**
     * Creates a command to add a {@code tag} to the persons at {@code indices}.
     */

    public TagCommand(List<Index> indices, Collection<Tag> tags, Optional<Department> department) {
        this.targetIndices = indices;
        this.tags = new HashSet<>(tags);
        this.department = department;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        requireNonNull(history);

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToTag = lastShownList.get(targetIndex.getZeroBased());
            Person taggedPerson = addTag(personToTag);
            model.setPerson(personToTag, taggedPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String tagInfo = String.format(MESSAGE_TAG_CONTACT_SUCCESS,
                showIndices(targetIndices), showTags(tags));
        CommandResult result = new CommandResult(tagInfo);

        // Add the executed command to the command history
        history.add(String.valueOf(this));
        model.commitAddressBook();

        return result;
    }

    static String showIndices(List<Index> indices) {
        return indices.stream()
                .map(index -> String.valueOf(index.getOneBased()))
                .collect(Collectors.joining(", "));
    }

    static String showTags(Collection<Tag> tags) {
        return tags.stream().map(tag -> tag.tagName)
                .sorted() // making the output order deterministic
                .collect(Collectors.joining(", "));
    }

    private Person addTag(Person personToTag) {
        Set<Tag> personTags = new HashSet<>(personToTag.getTags());
        personTags.addAll(tags);

        Optional<Department> dep = department;



        return new Person(
                personToTag.getName(),
                personToTag.getPhone(),
                personToTag.getEmail(),
                personToTag.getAddress(),
                personTags,
                dep);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return targetIndices.equals(otherTagCommand.targetIndices)
                && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)      
                .add("targetIndices", targetIndices)
                .add("tags", tags)
                .add("department", department)
                .toString();
    }
}
