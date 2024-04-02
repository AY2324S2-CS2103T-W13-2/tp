package seedu.address.model.tag.department;

import seedu.address.model.tag.Tag;

/**
 * Stores the department of a person in the addressbook. Has to be alphanumerical
 */
public class Department extends Tag {

    public Department(String departmentName) {
        super(String.format("%s", departmentName.toUpperCase()));
    }

    public String toString() {
        return String.format("[%s]", super.tagName);
    }

}
