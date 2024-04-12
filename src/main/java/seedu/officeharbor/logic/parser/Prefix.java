package seedu.officeharbor.logic.parser;

import seedu.officeharbor.model.person.predicate.ComponentStringPredicate;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;

    private final Modifier modifier;

    /**
     * Constructor of Prefix with just a string prefix input and the modifier is set to NULL.
     *
     * @param prefix the input prefix
     */
    public Prefix(String prefix) {
        this.prefix = prefix;
        this.modifier = Modifier.NULL;
    }

    /**
     * Constructor of Prefix with just both string prefix and modifier,
     *
     * @param prefix the input prefix
     * @param modifier the input modifier
     */
    public Prefix(String prefix, Modifier modifier) {
        this.prefix = prefix;
        this.modifier = modifier;
    }

    public String getPrefix() {
        return prefix;
    }
    public Modifier getModifier() {
        return modifier;
    }

    public int getLength() {
        return prefix.length() + modifier.getLength();
    }

    /**
     * Extract out the component of the prefix which is the prefix without the last colon.
     *
     * @return
     */
    public ComponentStringPredicate.Component extractComponent() {
        String componentString = prefix.substring(0, prefix.length()).toUpperCase();
        return ComponentStringPredicate.Component.valueOf(componentString);
    }

    @Override
    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Prefix)) {
            return false;
        }

        Prefix otherPrefix = (Prefix) other;
        return prefix.equals(otherPrefix.prefix) && modifier.equals(otherPrefix.modifier);
    }
}
