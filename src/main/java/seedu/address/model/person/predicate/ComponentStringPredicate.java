package seedu.address.model.person.predicate;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.person.Person;

/**
 * A predicate to filter a specific component of a Person class that can be treated like a string.
 * The matching is case-insensitive.
 */
public abstract class ComponentStringPredicate implements ComponentPredicate {
    private final String input;
    private final Person.Component component;

    /**
     * Constructs a component predicate.
     *
     * @param input     The input to match with.
     * @param component The component to match on.
     */
    public ComponentStringPredicate(String input, Person.Component component) {
        requireAllNonNull(input, component);
        assert !input.trim().isEmpty() : "Input should not be empty";

        this.input = input.toLowerCase();
        this.component = component;
    }

    protected String getInput() {
        return input;
    }

    protected Person.Component getComponent() {
        return component;
    }

    protected Pattern makeWordsPattern() {
        String alternatives = Arrays.stream(input.split(" "))
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
        return Pattern.compile(String.format("\\b(%s)\\b", alternatives));
    }

    /**
     * Gets the component of the Person according to component specified
     * in the constructor and preprocesses it to make it suitable for matching.
     */
    public Stream<String> getAndPreprocessComponent(Person person) {
        return person.getComponent(component).map(String::toLowerCase);
    }

    public boolean matchComponent(Person person, Predicate<String> matcher) {
        return getAndPreprocessComponent(person).anyMatch(matcher);
    }

    /**
     * A predicate that checks whether the component in Person is exactly equal to given input.
     */
    public static class Is extends ComponentStringPredicate {
        public Is(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            String input = getInput();
            return matchComponent(person, str -> str.equals(input));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            Is otherPredicate = (Is) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }

    /**
     * A predicate that checks whether the component in Person is not equal to the given input.
     */
    public static class Isnt extends ComponentStringPredicate {
        public Isnt(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            String input = getInput();
            return matchComponent(person, str -> !str.equals(input));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            Isnt otherPredicate = (Isnt) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }

    /**
     * A predicate that checks whether the component contains the given input.
     * This is basically the same as a substring match.
     */
    public static class Has extends ComponentStringPredicate {
        public Has(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            String input = getInput();
            return matchComponent(person, str -> str.contains(input));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            Has otherPredicate = (Has) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }


    /**
     * A predicate that checks whether the component doesn't contain the given input.
     */
    public static class Hasnt extends ComponentStringPredicate {
        public Hasnt(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            String input = getInput();
            return matchComponent(person, str -> !str.contains(input));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            Hasnt otherPredicate = (Hasnt) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }

    /**
     * A predicate that checks whether the component starts with the given input.
     */
    public static class StartsWith extends ComponentStringPredicate {
        public StartsWith(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            String input = getInput();
            return matchComponent(person, str -> str.startsWith(input));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            StartsWith otherPredicate = (StartsWith) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }

    /**
     * A predicate that checks whether the component ends with the given input.
     */
    public static class EndsWith extends ComponentStringPredicate {
        public EndsWith(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            String input = getInput();
            return matchComponent(person, str -> str.endsWith(input));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            EndsWith otherPredicate = (EndsWith) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }

    /**
     * A predicate that checks whether the given component contains any of the given words.
     * This predicate splits its input into different words by whitespace and checks all the words individually.
     */
    public static class Word extends ComponentStringPredicate {
        public Word(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            var matcher = makeWordsPattern().asPredicate();
            return matchComponent(person, matcher);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            Word otherPredicate = (Word) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }

    /**
     * A predicate that checks whether the given component doesn't contain any of the given words.
     * This predicate splits its input into different words by whitespace and checks all the words individually.
     */
    public static class NoWord extends ComponentStringPredicate {
        public NoWord(String input, Person.Component component) {
            super(input, component);
        }

        @Override
        public boolean test(Person person) {
            var matcher = makeWordsPattern().asPredicate();
            return matchComponent(person, str -> !matcher.test(str));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof ComponentStringPredicate)) {
                return false;
            }
            NoWord otherPredicate = (NoWord) other;
            return getInput().equals(otherPredicate.getInput()) && getComponent().equals(otherPredicate.getComponent());
        }
    }
}
