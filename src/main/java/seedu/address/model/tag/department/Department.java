package seedu.address.model.tag.department;

import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Department extends Tag {

    public Department(String departmentName) {
        super(String.format("%s", departmentName.toUpperCase()));
    }

    public String toString() {
        return String.format("[%s]", super.tagName);
    }

}