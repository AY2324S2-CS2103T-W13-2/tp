package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.department.Department;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    /**
     * The available components in a {@link Person} class.
     */
    public enum Component {
        NAME,
        ADDRESS,
        EMAIL,
        TAG,
        PHONE,
        DEPARTMENT
    }

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    private final Optional<Department> department;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Optional<Department> department) {
        requireAllNonNull(name, phone, email, address, tags, department);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.department = department;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Optional<Department> getDepartment() {
        return department;
    }

    /**
     * Returns the value of the component specified.
     */
    public Stream<String> getComponent(Person.Component component) {
        Stream<String> stream;
        switch (component) {
        case NAME:
            stream = Stream.of(name.fullName);
            break;
        case EMAIL:
            stream = Stream.of(email.value);
            break;
        case PHONE:
            stream = Stream.of(phone.value);
            break;
        case TAG:
            stream = tags.stream().map(tag -> tag.tagName);
            break;
        case ADDRESS:
            stream = Stream.of(address.value);
            break;
        case DEPARTMENT:
            stream = department.stream().map(department -> department.tagName);
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + component);
        }
        return stream;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && department.equals(otherPerson.department);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, department);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Department: ")
                .append(getDepartment());
        return builder.toString();
    }
}
