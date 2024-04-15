---
layout: page
title: OfficeHarbor User Guide
---

# Welcome Message

Welcome to the user guide of OfficeHarbor (OH). OH is a desktop app designed to assist secretaries of large teach firms in
efficiently managing the contacts of the tech teams. The app is optimized primarily for keyboard use, allowing for a fast 
workflow. Additionally, it also has a dynamic on-screen feedback to keep the users informed of their operation throughout 
the interaction. If you can type fast, OH can get your contact management tasks done faster than traditional mouse-based apps.

## Purpose of this Guide

The user guide is mainly intended for secretaries of a large tech firm. The program is managed almost entirely by commands from 
keyboard, so a moderately fast typing speed is necessary to be efficient with it. You not need any experience in a command based program, 
and it will be explained in this document.

The primary purpose of the user guide is to quickly help novice users to familiarize with the keyboard-based environment 
and learn how to use the basic function of the app. The secondary purpose is to provide a fast way for the experienced users
to find the app information that they are looking for.

## Navigating the guide

For quickly finding the information that you are looking, you can make use of the [Table of Contents](#table-of-contents) 
below, which has a list of all the information, by clicking on the items, you will be directly to the content that you just clicked.

Throughout the guide, there are also some icons which represents information outside just technicalities.

* :bulb: **Tip**: This icon indicates a tip, suggestion, or piece of advice to help you use OH more effectively.

* :information_source: **Note**: This icon tells you about some key details of the app usage that you could miss.

* :warning: **Warning**: This icon alerts you about potential issues that you may face when using the app.

When reading through the guide, you can look for these icons for extra information.

*Acknowledgement: The usage of these special icons is inspired by section 1.4 of the user guide of the app SweetRewards [link](https://ay2324s2-cs2103t-t13-4.github.io/tp/UserGuide.html#14-navigating-this-guide)*

# Table of Contents
1. [Getting Started](#1-getting-started)<br>
2. [Features](#2-features)<br>
   2.1 [Getting Help](#21-getting-help--help)<br>
   2.2 [Adding A Person](#22-adding-a-person--add)<br>
   2.3 [Listing All Contacts](#23-listing-all-contacts--list)<br>
   2.4 [Deleting A Person](#24-deleting-a-person--delete)<br>
   2.5 [Clearing All Contacts](#25-clearing-all-contacts--clear)<br>
   2.6 [Tagging A Contact](#26-tagging-a-contact--tag)<br>
   2.7 [Deleting A Tag](#27-deleting-a-tag--untag)<br>
   2.8 [Editing A Contact](#28-editing-a-contact--edit)<br>
   2.9 [Filtering Contacts](#29-filtering-contacts--filter)<br>
   2.10 [Mailing To A List Of People](#210-mailing-to-a-list-of-people--mail)<br>
   2.11 [Copying Phone Numbers](#211-copying-phone-numbers--phone)<br>
   2.12 [Undoing A Command](#212-undoing-a-command--undo)<br>
   2.13 [Redoing A Command](#213-redoing-a-command--redo)<br>
   2.14 [Exiting The Program](#214-exiting-the-program--exit)<br>
   2.15 [Saving The Data](#215-saving-the-data)<br>
3. [FAQ](#3-faq)<br>
4. [Other Know Issues](#4-other-known-issues--warning-)<br>
5. [Command Summary](#5-command-summary)<br>

--------------------------------------------------------------------------------------------------------------------

# 1. Getting Started

1. Ensure you have Java `11` or above installed in your Computer. You can also check out this [guide](https://becomegeeks.com/blog/java/how-to-install-java-11-on-windows-macos-and-linux/)
   for the installation guide and how to go about verifying the installation. 

2. Download the latest `OfficeHarbor.jar` from
   [here](https://github.com/AY2324S2-CS2103T-W13-2/tp/releases/latest).

3. Copy the file to the folder you want to store and use your OH.

4. Double-click on the `officeharbor.jar` file to run the application.
   An app similar to the below image should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
    
    <div markdown="block" class="alert alert-info">

    **:information_source: Note for Advance User:**<br>
    You can also call the jar file via the `java -jar officeharbor.jar` command in the terminal, 
    after the `cd` to the main folder.

    </div>

5. Type the command in the command box and press Enter to run it. e.g. typing **`help`** and pressing **| Enter &#9166; |** will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add name:John Doe phone:98765432 email:johnd@example.com address:John street, block 123, #01-01` : Adds a contact named `John Doe` to OH.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

6. Refer to the [Features](#2-features) below for details of each command.

# 2. Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words enclosed between diamond brackets `<>`, are the information to be entered by the user.<br>
  e.g. in `add name:<name>`, `<name>` is which you enter the name information, so `add name:John Doe`, will enter exactly
  `John Doe` as the name, including the space in between.

* Space can be added between the keyword and the word in `<>` bracket.<br>
  e.g. either `name: <name> phone: <phone number>` or `name:<name> phone:<phone number>` is acceptable.

* Any component of a command surrounded in `[]` bracket are optional.<br>
  e.g. the command [`add`](#22-adding-a-person--add) has an optional `[department:<department>]` part, so it can be 
  executed with or without a department.

* Any component of a command with `...` after it means that it can be specified multiple times after the first one.
  e.g. In [`delete`](#24-deleting-a-person--delete), `<id>...` means that more ids can be entered after the first one.

* The component of a command can be in any order.<br>
  e.g. if the command needs `name:<name> phone:<phone number>`, `phone:<phone number> name:<name>` is also acceptable.

* Extra inputs for commands that do not take in any input (such as [`list`](#23-listing-all-contacts--list), 
  [`exit`](#214-exiting-the-program--exit) and [`clear`](#25-clearing-all-contacts--clear)) will be ignored.<br>
  e.g. if the command specifies `list 123`, it will be interpreted as `list`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines 
  as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

## 2.1 Getting Help : `help`

Shows either a general summary of the available command or a more detailed help message for the entered command if there is 
any. All help window also has an url link to the user guide at the bottom.

<div markdown="block" class="alert alert-info">

**:information_source: Note about help windows:**<br>
If you have more than one help window opened when you are leaving the main app either through the [exit](#214-exiting-the-program--exit) 
command or through clicking the close button on window, some help windows will still be opened, and you will have to
close them individually by yourself.

</div>

Format: `help [command]`

Examples:
* `help`
* `help add`

Output:

The message "Opened help window" in the output box. A separate help window will also be shown.

Example of help command without input command
![Help.PNG](images/user-guide/Help.PNG)
Example of help command for `add` command
![HelpAdd.PNG](images/user-guide/help_add_output.png)

## 2.2 Adding A Person: `add`

Adds a contact with the input details to OH.

Format: `add name:<name> phone:<phone number> email:<email address> address:<address> [tag:<tag>]... [department:<department>]`

* `<name>` must only contain alphanumeric characters and space, no special character is allowed. 
   It must also be **unique** for each contact.
* `<phone>` must only contain numbers, and it should be at least 3 digits long.
* `<email address>` must follow the **local-part@domain** format, where 
    1. The local-part should only contain alphanumeric characters and these special characters, excluding the parentheses, (+_.-). The local-part may not start or end with any special characters.
    2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels separated by periods.
       The domain name must:
        - end with a domain label at least 2 characters long
        - have each domain label start and end with alphanumeric characters
        - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.
* `address` can take in any character or value.
* `tag` must only contain alphanumeric characters and no space or special character is allowed.
* `deparatment` must only contain alphanumeric characters and no space or special character is allowed.



<div markdown="block" class="alert alert-info">

**:bulb: Tip:**<br>
You can add the contact of a certain employee directly with tags and department with just one `add` command.

</div>

<div markdown="block" class="alert alert-info">

**:information_source: Note:**<br>
Notice how the tag details are specified as `[tag:<tag>]...`, this means that more tags can be entered
after the first one, however since the first tag is in `[]` brackets, it will be optional, that means that the add command accepts
zero or more tags.

</div>

Examples:
* `add name:John Doe phone:98765432 email:johnd@example.com address:John street, block 123, #01-01`
* `add name:Virat Kohli phone:98765432 email:virat@gmail.com address:Altamount Road, block 10, #05-02`

Output:
The message “New person added: `<name>`; Phone: `<phone number>`; Email: `<email>`; Address: `<address>`; Tags: `<tag>...`; Department: `<department>`” will be shown.
A new contact entry with the given user will be displayed in the list.

![Add.png](images/user-guide/Add.png)

## 2.3 Listing All Contacts : `list`

Shows a list of all contacts in OH.

Format: `list`

Output:
The message "Listed all contacts" if there is any contact, 
or else the message is "The list is empty".
A list of added contacts, if any, in the form of a scrollable pane will be shown. 
The contacts are listed in the order in which they are added.

## 2.4 Deleting A Person : `delete`

Deletes the specified contact(s) from OH.

Format: `delete <id>...`

* Deletes the person at the specified `<id> ...`.
* The id refers to the index number shown in the contact list.
* The id **must be a positive integer** 1, 2, 3, …​

Examples:
* `delete 2 3` deletes the 2nd and 3rd person of the contact list from OH.

Output: The message "Deleted Person: `name`

`name`, Phone: `phone number`, Email: `email`, Address: `address`. Tags: `tags`, Department: `department`” will be shown.

The entry with the given id will disappear from the list.

<div markdown="block" class="alert alert-warning">

**:warning: Warning:**<br>
Due to a problem in the app, try not to delete duplicate contact, for e.g. `delete 1 1`, the specified 
contact will be deleted, but there will be no output message, and you will not be able to undo the deletion. 

</div>

![Delete.png](images/user-guide/Delete.png)

## 2.5 Clearing All Contacts : `clear`

Clears all contacts from OH.

Format: `clear`

Output:
The message "Address book has been cleared!" will be shown.

## 2.6 Tagging A Contact : `tag`

Tags the specified contact with the input tag name and/or input department name. In case you want to add more tags
to the contact.

* Tags the person(s) at the specified `<id>...`.
* The id refers to the index number(s) shown in the displayed person list.
* The id **must be a positive integer** 1, 2, 3, …

<div markdown="block" class="alert alert-info">

**:information_source: Note:**<br>
There must be at least the department specified or at least one tag. The command
cannot only have an id.

</div>

Format: `tag <id> tag:<tag>... [tag:<tag>]... [department:<department>]`

Example:
`tag 2 3 tag: colleagues`, `tag 2 3 department: FINANCE`, `tag 2 3 tag: colleagues department: FINANCE`

Output:
The message "Tagged Contacts: `contact index` with `tag name` and/or `department name`.” will be shown,
where contact index is the number representing where the current contact is
on the currently displayed list.

![TagCommand.png](images/user-guide/TagCommand.png)
![TagCommand2.png](images/user-guide/TagCommand2.png)

## 2.7 Deleting A Tag : `untag`

Deletes the specified tag from the specified contact

Format: `untag <id> tag:<tag>... [tag:<tag>]... [department:<department>]`

* Delete the specified tag of the person at the specified `<id>`.
* The id refers to the index number shown in the displayed person list.
* The id **must be a positive integer** 1, 2, 3, …
* All the specified tags (if specified) must match the ones
  stored in the id.

<div markdown="block" class="alert alert-warning">

**:warning: Warning about untagging department:**<br>
`untag` currently doesn't support untagging department. Instead, it just changes the department to what you type in. 
So unfortunately, if you want to remove a contact's department, you will have to delete the contact and add it back 
without the department. The fix for this issue is planned for a future release.

</div>

<div markdown="block" class="alert alert-info">

**:information_source: Note:**<br>
`untag` currently doesn't support multiple `<id>` like `tag` and `delete`. It is
planned for a future release.

</div>

Format: `untag <id> tag:<tag>... [tag:<tag>]... [department:<department>]`

Output:
The message "Deleted tag `<tag>` from: `contact info`." will be shown,
where contact info is all the information of the contact. 
The list entry of the user with <id> will not have the tag anymore.

![untag.png](images/user-guide/untag.png)

## 2.8 Editing A Contact: `edit`

Edits an existing person in the contact list.

Format: `edit <id> [name:<name>] [phone:<phone number>] [email:<email address>] [address:<address>] [tag:<tag>]…​ [department: <department>]`

* Edits the person at the specified contact `<id>`. The `<id>` **must be a positive integer** 1, 2, 3, …​
* The other components follows the same format as the [`add`](#22-adding-a-person--add) command.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e. adding of tags is not cumulative.

<div markdown="block" class="alert alert-info">

**:bulb: TIp:**<br>
You can remove all the person’s tags by typing `tag: ` without specifying any tags after it.

</div>

Output:
The message: "Edited Person: `<name>`; Phone: `<phone>`; Email: `<email>`; Address: `<address>`; Tags: `<tag>`; Department: `<department>`" will be displayed.
The specified component of the contact to be edited will also be changed to the new input.

Examples:
*  `edit 1 phone: 91234567 email: johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 name: Betsy Crower tag:` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

## 2.9 Filtering Contacts : `filter`

Filters the entire list of contacts.

Format: `filter <component>[.<modifier>]:<value> ...`

`component` is one of `name`, `phone`, `email`, `address`, `tag`, or `department` corresponding to the values in [add](#22-adding-a-person--add):
name, phone, email, address, tag and department respectively, all following the same input restriction.

This command is case-insensitive: it treats uppercase and lowercase letters the
same.

There can be duplicate components. If there are multiple components, the
contacts that match any of the components are shown.

The `modifier` specifies how filter should match the value to the component,
it is optional and defaults to `has`. The modifiers are
- `has`: value has to match any part of the component

  Example: `filter address:serangoon`
  > The default modifier is `has`, so this lists every contact with an address that
  > has serangoon in it.

  ![filter1.PNG](images/user-guide/filter1.PNG)

  Another example: `filter name.has:will`
  > Returns the contacts whose name has "will" anywhere in them.
  > For example, names like "Will Pherell", "William Smith", "Bruce Willis", "Fred
  > Mcwilliams"

- `hasnt`: value should not match any part of the component
  Example: `filter tag.hasnt:senior`
  > Returns all the contacts whose name does not have "senior" anywhere in them.
  
- `is`: value has to match the component exactly

  Example: `filter phone.is:93210283`
  > Returns the contact with the phone number 93210283

  ![filter2.PNG](images/user-guide/filter2.PNG)

- `isnt`: value should not match the component exactly
  Example: `filter department.isnt:finance`
  > Returns the contacts who are not in the "finance" department.

- `word`: value has to match a distinct word in the component, a word is any
sequence of letters and numbers surrounded by spaces
  Example: `filter name.word:will`
  > Returns the contacts who have "will" as a word.
  > For example, names like "Will Pherell" but not ones containing "will"
  > somewhere in the middle of the word like "Fred Mcwilliams".
  
- `noword`: value should not match a distinct word in the component.
  Example: `filter name.noword:will`
  > Returns the contacts who do not have "will" as a word.
  > For example, any name that does not contain "will" like "Bernice Yu" and
  > names containing "will" in the middle of a name like "Fred Mcwilliams" but
  > not "Will Pherell".

- `startswith`: value must match the very beginning of the component.
  Example: `filter name.startswith:Jack`
  > Returns the contacts who start with "Jack".
  > For example, names like "Jack Doe" but not ones starting with "John" 
  > like "John Doe".

- `endswith`: value must match the very end of the component.
   Example: `filter name.endswith:Daniel`
   >Returns the contacts who end with "Daniel".
   >For example, names like "John Daniel" but not ones ending with "Doe"
   >like "John Doe".

Output:
If 10 contacts match the list, the output message is "10 persons listed"

## 2.10 Mailing To A List Of People : `mail`

Composes an email to the people tagged with the selected tag.

Format: `mail <tag>`
* `tag` must only contain alphanumeric characters and no space or special character is allowed.

Output: The message "Here is the list of emails:
`email1`,`email2`,`email3`..." will be shown, where `email1` is the
email of the first person currently displayed in the contact list.
Additionally, the system default mailing app will open and a mail will
be drafted. The recipients field will be automatically filled up with
the emails shown earlier.

Example of mail command: `mail colleagues`
![mail.PNG](images/user-guide/mail.PNG)
![mail2.PNG](images/user-guide/mail2.PNG)

<div markdown="block" class="alert alert-info">
**:information_source: Note:**<br>
If the command does not do anything, it might be that you do not have a default mail app set up.

Here is how to do so for Windows and Mac:
https://www.lessannoyingcrm.com/help/setting-your-computers-default-email-program
</div>

## 2.11 Copying phone numbers : `phone`

Copies the phone numbers of people tagged with the selected tag to
your clipboard for ease of messaging.

Format: `phone <tag>`
* `tag` must only contain alphanumeric characters and no space or special character is allowed.

Output: The message "Here is the list of phone numbers:
`phone1`,`phone2`,`phone3`...
They have been copied to your clipboard."

Example of phone command: `phone colleague`
![phone.PNG](images/user-guide/phone.PNG)


## 2.12 Undoing A Command : `undo`

Resets the state of OH to before the execution of the latest command.
However, the undo command does not work for exiting and then relaunching the app.

<div markdown="block" class="alert alert-warning">

**:warning: Warning:**<br>
OfficeHarbor can undo up to 5 times, and only commands run from the command's
startup. We recommend you to keep regular backups of your [data](#215-saving-the-data) in case you want
to change something you did beforehand.

</div>

Format: `undo`

Output:
The message "Undo success!" will be shown.
The list entry of the user will return to the state before the latest command.
If no command has been run at all, an error message "No more commands to undo!" will be shown instead.

<div markdown="block" class="alert alert-info">

**:information_source: Note:**<br>
Undo command only supports [add](#22-adding-a-person--add), [delete](#24-deleting-a-person--delete), 
[clear](#25-clearing-all-contacts--clear), [tag](#26-tagging-a-contact--tag), and 
[untag](#27-deleting-a-tag--untag) commands.
The undo command will basically not take into account any of the "unsupported" commands and will just undo
the last command that was supported.
Example:
1. delete 1
2. list
3. undo
   This will undo the delete command and the list will show the contact that was deleted.

</div>

Example of undoing a delete command.

Contact after a delete command:
![deleteUndo.PNG](images/user-guide/deleteUndo.PNG)

Contact after the delete command is undone:
![undosuccess.PNG](images/user-guide/undosuccess.PNG)

## 2.13 Redoing A Command : `redo`

Resets the state of OH to before the latest undo command.
However, the redo command does not work for closing and then relaunching the app.
All commands except 'filter' can be redone.

Format: `redo`

Output:
The message "Redo success!" will be shown.
The list entry of the user will return to the state before the latest undo command.
If no undo command has been run at all, an error message "No more commands to redo!" will be shown instead.

<div markdown="block" class="alert alert-info" >

**:information_source: Note:**<br>
Just like [Undo](#212-undoing-a-command--undo), Redo command only supports [add](#22-adding-a-person--add), 
[delete](#24-deleting-a-person--delete), [clear](#25-clearing-all-contacts--clear), 
[tag](#26-tagging-a-contact--tag), and [untag](#27-deleting-a-tag--untag) commands.
The redo command will basically not take into account any of the "unsupported" commands and will just undo
the last command that was supported.
Example:
1. delete 1
2. list
3. undo
4. redo
This will redo the delete command and will again delete the contact.

</div>

Example of redoing the previous undo command:
![redosuccess.PNG](images/user-guide/redosuccess.PNG)

## 2.14 Exiting The Program : `exit`

Exits the program.

Format: `exit`

<div markdown="span" class="alert alert-info">

**:information_source: Note: <br>**
The command only closes the window of the app and the latest help window(s), 
if there are other windows for the [`help`](#21-getting-help--help) command, you will have to close them yourself.

</div>

## 2.15 Saving The Data

OfficeHarbor data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<div markdown="span" class="alert alert-info">

**:information_source: Notes about data file: <br>**
The data are saved as a JSON file `[JAR file location]/dataddress:officeharbor.json`.
Advanced users are welcome to update data directly by editing that data file.

</div>

<div markdown="span" class="alert alert-warning">

**:warning: Caution: <br>** 
If your changes to the data file makes its format invalid, OfficeHarbor will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the OfficeHarbor to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</div>

--------------------------------------------------------------------------------------------------------------------

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous OfficeHarbor home folder.

--------------------------------------------------------------------------------------------------------------------

## 4. Other Known Issues :warning:

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## 5. Command summary

| Action         | Format, Examples                                                                                                                                                                                                                                           |
|----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**        | `add name:<name> phone:<phone> email:<email address> address:<address> [tag:<tag>]... [department:<department>]` <br> e.g., `add name:James Ho phone:22224444 email:jamesho@example.com address:123, Clementi Rd, 1234665 tag:Friend department:Marketing` |
| **Clear**      | `clear`                                                                                                                                                                                                                                                    |
| **Delete**     | `delete <id>...`<br> e.g., `delete 3`                                                                                                                                                                                                                      |
| **Delete Tag** | `untag <id> [tag:<tag>]... [department:<department>]` <br> e.g., `untag 2 tag:friends department:HR`                                                                                                                                                       |
| **Edit**       | `edit <id> [name:<name>] [phone:<phone number>] [email:<email address>] [address:<address>] [tag:<tag>]…​ [department: <department>]` <br> e.g., `edit 1 name: Betsy Crower`                                                                               |
| **Exit**       | `exit`                                                                                                                                                                                                                                                     |
| **Filter**     | `filter <component>[.<modifier>]:<value>... ` <br> e.g., `filter name.has: Alex`                                                                                                                                                                           |
| **Help**       | `help [<command>]`                                                                                                                                                                                                                                         |
| **List**       | `list`                                                                                                                                                                                                                                                     |
| **Mail**       | `mail <tag>`                                                                                                                                                                                                                                               |
| **Phone**      | `phone <tag>`                                                                                                                                                                                                                                              |
| **Redo**       | `redo`                                                                                                                                                                                                                                                     |
| **Tag**        | `tag <id>... tag:<tag>... [department:<department>]`  <br> e.g., `tag 2 tag:friends department:HR`                                                                                                                                                         |
| **Undo**       | `undo`                                                                                                                                                                                                                                                     |
