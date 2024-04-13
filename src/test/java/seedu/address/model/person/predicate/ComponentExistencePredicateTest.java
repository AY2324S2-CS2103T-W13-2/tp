package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.predicate.ComponentExistencePredicate.None;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.predicate.ComponentExistencePredicate.Some;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.department.Department;

class ComponentExistencePredicateTest {
    private static final Person.Component STUBBED_COMPONENT = Person.Component.TAG;

    private final Set<Tag> nonEmpty = new HashSet<>(List.of(new Tag("hello")));
    private final Set<Tag> empty = new HashSet<>();

    /**
     * A Person stub that is used to unit test ComponentExistencePredicate.
     */
    private static class PersonStub extends Person {
        /**
         * @param tags The tags to test with.
         */
        public PersonStub(Set<Tag> tags) {
            super(new Name("something"), new Phone("123"), new Email("unnecessary@unnecessary.com"),
                    new Address("somewhere"), tags, Optional.of(new Department("HR")));
        }

        @Override
        public Name getName() {
            throw new AssertionError("This should not be called");
        }

        @Override
        public Phone getPhone() {
            throw new AssertionError("This should not be called");
        }

        @Override
        public Email getEmail() {
            throw new AssertionError("This should not be called");
        }

        @Override
        public Address getAddress() {
            throw new AssertionError("This should not be called");
        }

        @Override
        public boolean isSamePerson(Person otherPerson) {
            throw new AssertionError("This should not be called");
        }

        @Override
        public boolean equals(Object other) {
            throw new AssertionError("This should not be called");
        }

        @Override
        public int hashCode() {
            throw new AssertionError("This should not be called");
        }

        @Override
        public String toString() {
            throw new AssertionError("This should not be called");
        }
    }

    @Nested
    public class SomeTest {
        private Some some;

        @BeforeEach
        public void init() {
            try {
                some = new Some(STUBBED_COMPONENT);
            } catch (CommandException e) {
                // unreachable
            }
        }

        @Test
        void test_validComponent_success() {
            assertDoesNotThrow(() -> new Some(Person.Component.TAG));
            assertDoesNotThrow(() -> new Some(Person.Component.DEPARTMENT));
        }

        @Test
        void test_invalidComponent_throwsError() {
            Stream.of(Person.Component.NAME, Person.Component.PHONE, Person.Component.ADDRESS, Person.Component.EMAIL)
                    .forEach(component -> assertThrows(CommandException.class, () -> new Some(component),
                            "This filter predicate does not support the component: " + component.name().toLowerCase()));
        }

        @Test
        void test_exists_success() {
            assertTrue(some.test(new PersonStub(nonEmpty)));
        }

        @Test
        void test_doesNotExist_failure() {
            assertFalse(some.test(new PersonStub(empty)));
        }
    }

    @Nested
    public class NoneTest {
        private None none;

        @BeforeEach
        public void init() {
            try {
                none = new None(STUBBED_COMPONENT);
            } catch (CommandException e) {
                // unreachable
            }
        }

        @Test
        void test_validComponent_success() {
            assertDoesNotThrow(() -> new None(Person.Component.TAG));
            assertDoesNotThrow(() -> new None(Person.Component.DEPARTMENT));
        }

        @Test
        void test_invalidComponent_throwsError() {
            Stream.of(Person.Component.NAME, Person.Component.PHONE, Person.Component.ADDRESS, Person.Component.EMAIL)
                    .forEach(component -> assertThrows(CommandException.class, () -> new None(component),
                            "This filter predicate does not support the component: " + component.name().toLowerCase()));
        }

        @Test
        void test_exists_failure() {
            assertFalse(none.test(new PersonStub(nonEmpty)));
        }

        @Test
        void test_doesNotExist_success() {
            assertTrue(none.test(new PersonStub(empty)));
        }
    }
}
