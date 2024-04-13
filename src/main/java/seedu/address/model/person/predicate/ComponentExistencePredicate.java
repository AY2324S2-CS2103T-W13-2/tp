package seedu.address.model.person.predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * A component that filters based on the existence of a nullable component of {@link Person}.
 */
public abstract class ComponentExistencePredicate implements ComponentPredicate {
    private final Person.Component component;

    /**
     * Checks whether the given component is empty or non-empty.
     * @throws CommandException if the given component can never be non-empty.
     */
    public ComponentExistencePredicate(Person.Component component) throws CommandException {
        isValidExistenceComponent(component);
        this.component = component;
    }

    private void isValidExistenceComponent(Person.Component component) throws CommandException {
        if (component != Person.Component.TAG && component != Person.Component.DEPARTMENT) {
            throw new CommandException("This filter predicate does not support the component: "
                    + component.name().toLowerCase());
        }
    }

    protected boolean exists(Person person) {
        return person.getComponent(component).findAny().isPresent();
    }

    /**
     * A predicate that checks whether the component is non-empty.
     */
    public static class Some extends ComponentExistencePredicate {
        public Some(Person.Component component) throws CommandException {
            super(component);
        }

        @Override
        public boolean test(Person person) {
            return exists(person);
        }
    }

    /**
     * A predicate that checks whether the component is empty
     */
    public static class None extends ComponentExistencePredicate {
        public None(Person.Component component) throws CommandException {
            super(component);
        }

        @Override
        public boolean test(Person person) {
            return !exists(person);
        }
    }
}
