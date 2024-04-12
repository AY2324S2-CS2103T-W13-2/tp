package seedu.officeharbor.logic.parser;

/**
 * Represents the modifiers for the filter command
 */
public enum Modifier {
    HAS(3),
    HASNT(5),
    IS(2),
    ISNT(4),
    WORD(4),
    NOWORD(6),
    STARTSWITH(10),
    ENDSWITH(8),
    NULL(-1);

    //length of the modifier string
    private final int length;

    Modifier(int length) {
        //all has length + 1 to account for "."
        this.length = length + 1;
    }

    public static Modifier getModifier(String args) {
        try {
            if (args.length() == 0) {
                return HAS;
            }
            return Modifier.valueOf(args.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Modifier.NULL; // Defaulting to NULL modifier
        }
    }

    public int getLength() {
        return length;
    }
}
