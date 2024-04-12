package seedu.officeharbor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.officeharbor.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.officeharbor.commons.core.index.Index;
import seedu.officeharbor.commons.util.ToStringBuilder;
import seedu.officeharbor.logic.CommandHistory;
import seedu.officeharbor.logic.Messages;
import seedu.officeharbor.logic.commands.exceptions.CommandException;
import seedu.officeharbor.model.Model;
import seedu.officeharbor.model.person.Person;
import seedu.officeharbor.model.tag.Tag;
import seedu.officeharbor.model.tag.department.Department;

/**
 * Tags contacts identified using their displayed indices in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\n: Tags the contact identified by the index number"
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

        String tagInfo = " ";

        if (!tags.isEmpty() && (!department.isEmpty() && !department.get().tagName.equals("EMPTYDEP"))) {
            tagInfo = String.format(MESSAGE_TAG_CONTACT_SUCCESS,
                    showIndices(targetIndices), showTags(tags) + ", " + department.get());
        }

        if (!tags.isEmpty() && (department.isEmpty() || department.get().tagName.equals("EMPTYDEP"))) {
            tagInfo = String.format(MESSAGE_TAG_CONTACT_SUCCESS,
                    showIndices(targetIndices), showTags(tags));
        }

        if (tags.isEmpty() && (!department.isEmpty() && !department.get().tagName.equals("EMPTYDEP"))) {
            tagInfo = String.format(MESSAGE_TAG_CONTACT_SUCCESS,
                    showIndices(targetIndices), department.get());
        }

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

        if (dep.get().tagName.isEmpty() || dep.get().tagName.equals("EMPTYDEP")) {
            dep = personToTag.getDepartment();
        }

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
