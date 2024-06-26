---
layout: page
title: Developer Guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Idea of filter syntax
  from [taskwarrior](https://github.com/GothenburgBitFactory/taskwarrior/blob/0c8edfc50e422b69abb4b78af70fc2243e227e9d/doc/man/task.1.in#L809)
* Idea of UG navigation icons
  from [SweetRewards User Guide](https://ay2324s2-cs2103t-t13-4.github.io/tp/UserGuide.html#14-navigating-this-guide)
* Implementation of Undo/Redo Commands:
  from {https://github.com/se-edu/addressbook-level4/}
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [
_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create
and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of
classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is
in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts
e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`,
inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the
visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API
** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

Another sequence diagram below illustrates the interactions within the `Logic` component, taking
`execute("tag 1 tag: example")` API call as an example.

![](C:\Repos\TeamProject\docs\images\TagCommand.png)

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take
   several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

Below is sequence diagram that illustrates how a `FilterCommandParser` words, taking
`filter name.is: Alex` call as an example.

![](images/FilterParserSequenceDiagram.png)

### Model component

**API
** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which
  is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to
  this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API
** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package. Each component comes from one of the
three packages: `core`, `exceptions` and `util`. `DataLoadingException` for example is a components from the exception
package, which `AddressBookStorage` and `UserPrefsSroage` throws and `MainApp` catches.

<img src="images/DataLoadingExceptionObjectDiagram.png" width="450" />

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Filter

The general outline of the filter commands architecture is as follows.
![FilterCommand Architecture Diagram](images/ComponentPredicateClassDiagram.png)

The `FilterCommand` takes a nonempty list of `ComponentPredicate` which is an interface
implementing `Predicate<Person>`. It filters all the `Person`s in the `Model` with these predicates and keeps a specific
person when any of the predicates match.

`ComponentPredicate` extracts the values of a specific component and runs its test on those values. The specific values
it can extract and manipulate on its implementation.

- `ComponentStringPredicate` can extract all values that can be treated as a string and run string matching operations.
- `ComponentExistencePredicate` can extract all values that can be empty and check for existence (empty or not).

#### Implementation of specific predicates

As an example of implementation here's the `Is` class.

![Is class diagram](images/ComponentIs.png)

The `Is.test` method extracts the values of the component specified by the command with
`ComponentStringPredicate`'s `extract` method and runs an equality test between the strings with the input
and returns `true` when any of the values match the input.

In the case of singular values, `extract` returns a single element stream. In case of
aggregate values like `tag` it extracts all the values out and returns them all in the stream.

#### Design Considerations

The filter command matches the `Person`s in `Model`'s currently filtered list according to the list of predicates given
to it. A person passes if it matches any of the predicates. The predicates themselves also have to be disjunctive.

Currently the filter does not support any from of `and` matching, all operations
`or` by default, and we have `not` variations like `has` and `hasnt`.

A proposed implementation for `and` is that we can change the filter such that
subsequent `FilterCommand` operations filter the preceding list, which same as
an `and`. Therefore, we would have full access to boolean logic.

This is done because making the parser support boolean operations and parenthesizing would take more time than possible.
This does make the user interface a bit more confusing to use, but our time limitations don't allow for a better
implementation.

<div markdown="span" class="alert alert-info">
    :information_source: **Note:** ComponentExistencePredicate does not have a
    way to call it in the frontend. It is planned for a future release.
</div>

### Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

* `VersionedAddressBook#commit()`— Saves the current address book state in its history.
* `VersionedAddressBook#undo()`— Restores the previous address book state from its history.
* `VersionedAddressBook#redo()`— Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command
calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes
to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book
state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also
calls `Model#commitAddressBook()`, causing another modified address book state to be saved into
the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing
the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such
as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`.
Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

### Mail

The general outline of the mail commands architecture is as follows.
![MailCommand.png](images/MailCommand.png)

The `MailCommand` is a component of our application that enables users to seamlessly
initiate email communication with contacts associated with specific tags.

#### Functionality

* **Purpose:** The primary purpose of the `MailCommand` is to streamline the process of initiating email communication
  with contacts based on user-defined tags.
* **Execution Logic:** Upon execution, the command accepts a tag parameter from the user, filters contacts associated
  with this tag, and constructs a list of email addresses from the filtered contacts.
* **Error Handling:** The `MailCommand` includes error handling mechanisms to address potential
  issues during execution, such as catching exceptions and notifying users of any errors encountered.

#### Design Considerations

* **Command Parameters:** The `MailCommand` defines command parameters and a usage message to guide users on how to
  utilize the command effectively.
* **Initialization:** Upon instantiation, the `MailCommand` initializes a `TagContainsKeywordsPredicate`, which
  encapsulates the tag-based filtering logic.
* **Execution:** The command logic involves updating the filtered list of contacts in the model, constructing email
  address lists, and attempting to open the default email application using the Desktop class.
* **Parser Implementation:** The `MailCommandParser` is responsible for parsing user input and generating
  `MailCommand` objects with appropriate tag predicates.
* **Integration with Model:** It utilizes the `Model` interface to update the filtered list of contacts and
  ensure synchronization with the application state.

## Planned Enhancements

Our TP has 5 members

- Logical and for filter
- Making the command line interface uniform, i.e. having `mail` and `phone` take
  tags only prefixed with `tag:`
- Make `untag` delete department instead of renaming it
- Undo/Redo compatibility for all commands
- Help shows info for all commands (currently missing `phone`)
- Make `delete` command handle duplicate input index correctly (currently, the action produces no output message and 
  cannot be undone by the `undo` command)
- Make `exit` command close all the windows related to OH (currently, only closes the app and the latest help window)

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Secretary of a tech company

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Categorise people according to their department/origin
and provide methods for efficient retrieval and manipulation of the information,
making it easier to assign tasks or get all the emails.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​   | I want to …​                              | So that I can…​                                        |
|----------|-----------|-------------------------------------------|--------------------------------------------------------|
| `* * *`  | secretary | view the added contact list               | see the details of the added contact                   |
| `* * *`  | secretary | add contacts to a list                    | record contact details                                 |
| `* * *`  | secretary | remove contacts from a list               | clean up old/unused records                            |
| `* * *`  | secretary | filter contacts from the list             | To find the pertinent information from the total data  |
| `* *`    | secretary | tag individual contacts                   | organize the contact based on tags                     |
| `* *`    | secretary | delete a tag of an individual contact     | make sure that the tag only is for the necessary users |
| `*`      | secretary | mail to everyone in a department          | contact everyone in a certain department               |
| `*`      | secretary | message everyone in a department          | contact everyone in a certain department               |
| `*`      | user      | undo my command                           | save time on undoing the effects of a wrong command    |
| `*`      | user      | redo my undo                              | save time on undoing the effects of a wrong undo       |
| `*`      | user      | get help about available features quickly | get start on using the app quickly                     |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is `OfficeHarbor` (OH) and the **Actor** is the `user`, unless specified
otherwise)

**UC01: View contacts**

**MSS**

1. User requests to list people.
2. OH shows a list of people.

   Use case ends.

**Extensions**

* 2a. The list is empty.

    * 2a1. No list is shown.

      Use case ends.

**UC02: Add a contact**

**MSS**

1. User requests to add a person along with details.
2. OH adds the person.

   Use case ends.

**Extensions**

* 1a. The given format is invalid.

    * 1a1. AddressBook shows an error message.

      Use case resumes at step 1.

**UC03: Remove a contact**

**MSS**

1. User requests to list contacts (UC01).
2. User requests to remove a specific contact in the list.
3. OH removes the contact.

   Use case ends.

**Extensions**

* 3a. The given index is invalid.

    * 3a1. OH shows an error message.

      Use case resumes at step 2.

**UC04: Tagging contacts**

**MSS**

1. User requests to list contacts (UC01).
2. User requests to add a tag to a specific contact in the list.
3. OH adds the tag to the contact.

   Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. OH shows an error message.

      Use case resumes at step 2.

* 2b. The given tag is empty or invalid.

    * 2b. OH shows an error message.

      Use case resumes at step 2.

**UC05: Deleting a tag**

**MSS**

1. User requests to list contacts (UC01).
2. User requests to delete a specific tag of a person from the list.
3. OH deletes the specified tag of that person.

   Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. OH shows an error message.

      Use case resumes at step 2.

* 2b. The specified tag does not exist.

    * 2b1. OH shows an error message.

      Use case resumes at step 2.

**UC06: Undoing a Command**

**MSS**

1. User requests to undo a command.
2. OH resets to the state before the latest command.

   Use case ends.

**Extensions**

* 2a. No command has been run since opening the app.

    * 2a1. OH shows an error message.

      Use case ends.

**UC07: Redoing an Undo Command**

**MSS**

1. User requests to redo an undo.
2. OH resets to the state before the latest command.

   Use case ends.

**Extensions**

* 2a. No command has been run since opening the app.

    * 2a1. OH shows an error message.

      Use case ends.

**UC08: Mail Command**

**MSS**

1. User requests to mail contacts of a tag.
2. OH lists all contacts tagged with the keyword.
3. OH opens up the mailing app.
4. OH composes a mail to the emails of the listed contacts.

   Use case ends.

**Extensions**

* 2a. No contacts are associated with the tag.

    * 2a1. OH shows an error message.

      Use case ends.

**UC09: Phone Command**

**MSS**

1. User requests to phone contacts of a tag.
2. OH lists all contacts tagged with the keyword.
3. OH copies the phone numbers of listed contacts to the clipboard.

   Use case ends.

**Extensions**

* 2a. No contacts are associated with the tag.

    * 2a1. OH shows an error message.

      Use case ends.

*{More to be added}*

**UC10: Getting help**

**MSS**

1. User requests for a general help.
2. OH shows a quick summary of all the available features.

   Use case ends.

**Extensions**

* 1a. User requests for a specific help on a feature.

    * 1a1. OH shows detailed information about the specified feature.

      Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.
5. Should be able to hold up to1000 contant details consuming a large amount of memory.
6. Should be able to hold up to 1000 contacts without slowing down the app.
7. Should be able to hold up to 1000 contacts without losing the data.
8. Should be able to able to save contacts without losing the data even in the event of a system crash.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
