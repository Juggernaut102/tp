---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# EduDex Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

Our project EduDex is based on [AddressBook-Level3 (AB3)](https://github.com/se-edu/addressbook-level3). 

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/edudex/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/edudex/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/edudex/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/edudex/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/edudex/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `EduDexParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `EduDexParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `EduDexParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/edudex/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores EduDex data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `EduDex`, which `Person` references. This allows `EduDex` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-T12-1/tp/tree/master/src/main/java/seedu/edudex/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both EduDex data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `EduDexStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.edudex.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Find feature (Enhanced)

#### Implementation

The `FindCommand` has been enhanced to support two additional types of search:
* **Find by Day** – searches for students who have lessons on a specified day.
* **Find by Subject** – searches for students who have lessons for a specified subject.

This is in addition to the existing **Find by Name** functionality.

The command is structured as follows:
* `FindCommand` – Executes the filtering logic.
* `FindCommandParser` – Parses user input and constructs the correct predicate.
* `NameContainsKeywordsPredicate`, `DayMatchesPredicate`, and `SubjectMatchesPredicate` – Determine whether a `Person` matches the given search criteria.

---

#### Design considerations

| Aspect | Alternatives | Current choice | Reason |
|--------|---------------|----------------|--------|
| Predicate handling | Create separate commands for day and subject searches | Unified `FindCommand` with multiple constructors | Reduces command duplication and simplifies parsing |
| Case sensitivity | Exact match | Case-insensitive match | Provides better user experience |
| Error handling | Reject invalid days/subjects silently | Validate against allowed days and empty subjects | Ensures robust input validation |

---

#### Example usages

| Command            | Description |
|--------------------|-------------|
| `find alice bob`   | Finds students whose names contain “alice” or “bob” |
| `find d/Tuesday`   | Finds students with lessons on Tuesday |
| `find sub/Science` | Finds students taking Science lessons |

---

#### Example UI output

| Input           | Output |
|-----------------|--------|
| `find d/Friday` | Displays list of students with Friday lessons |
| `find sub/Math` | Displays students taking Math, with lesson details |

---

### DeleteLesson feature

#### Implementation

The `DeleteLessonCommand` allows users to delete a specific lesson from a student using two indices:
1. **Student index** – The index of the student in the displayed student list.
2. **Lesson index** – The index of the lesson in that student’s list of lessons.

The command word is `dellesson`.

**Example usage:**

Deletes the 2nd lesson of the 1st student in the current displayed list.

The command structure is as follows:
* `DeleteLessonCommand` – Executes lesson deletion logic.
* `DeleteLessonCommandParser` – Parses and validates user input.
* `ModelManager` – Handles the modification of the `Person` object.

**Step-by-step execution:**

1. User executes `dellesson 1 2`.
2. `DeleteLessonCommandParser` parses both indices and creates a `DeleteLessonCommand`.
3. `LogicManager` executes the command.
4. The command retrieves the student at index 1 from `model.getFilteredPersonList()`.
5. The lesson at index 2 is removed from the student’s lesson list.
6. The model updates the modified student using `model.setPerson()`.
7. A success message is displayed:

---

#### Design considerations

| Aspect | Alternatives | Current choice | Reason |
|--------|---------------|----------------|--------|
| Command format | `delete lesson 1 2` | `dellesson 1 2` | Shorter and easier to parse |
| Validation | Ignore invalid indices | Throw `CommandException` with proper messages | Prevents accidental deletions |
| Data mutation | Modify lesson list directly | Create a new `Person` instance with updated lessons | Preserves immutability of model data |

---

#### Example usages

| Command | Description |
|----------|-------------|
| `dellesson 1 1` | Deletes the first lesson from the first student |
| `dellesson 3 2` | Deletes the second lesson from the third student |
| `dellesson 2 5` | Deletes the fifth lesson from the second student |

---

#### Example error cases

| Input | Result |
|--------|--------|
| `dellesson 0 1` | Error: Invalid student index |
| `dellesson 1 0` | Error: Invalid lesson index |
| `dellesson 1 99` | Error: Lesson index out of bounds |
| `dellesson 1 a` | Error: Invalid format — indices must be integers |

---

#### Interaction with Model

The `DeleteLessonCommand` interacts with the `ModelManager` as follows:
1. Retrieves the displayed list of students via `getFilteredPersonList()`.
2. Extracts the target `Person` using the given student index.
3. Creates a modified copy of the `Person` with the target lesson removed.
4. Replaces the old person with the new one using `model.setPerson()`.
---

### Example test cases

| Scenario | Command            | Expected Result |
|-----------|--------------------|-----------------|
| Find by day | `find d/Monday`    | Lists all students with Monday lessons |
| Find by subject | `find sub/Science` | Lists all students taking Science |
| Delete valid lesson | `dellesson 1 2`    | Deletes 2nd lesson of 1st student |
| Invalid student index | `dellesson 0 1`    | Error: Invalid student index |
| Invalid lesson index | `dellesson 1 5`    | Error: Invalid lesson index |
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

**Target user profile**:

* technically able freelance tutor
* has a need to manage a significant number of students
* teaches multiple subjects at different times
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Provides fast access to student details such as subjects taught, lessons taken, school name
and optimised to those who prefer a CLI


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                 | I want to …​                              | So that I can…​                                           |
|----------|-----------------------------------------|-------------------------------------------|-----------------------------------------------------------|
| `* * *`  | tutor                                   | add a student entry into the dataset      | start keeping track of that student's contact information |
| `* * *`  | tutor                                   | view my students' contacts                | see my students' contact information all at once          |
| `* * *`  | tutor                                   | exit the program                          | close the program when I’m done                           |
| `* * *`  | tutor who has a student who is quitting | delete the student's contact              | ensure PDPA compliance and clean up my EduDex             |
| `* *`    | new user                                | view a help page for the usage of the app | understand how to navigate EduDex                         |
| `* *`    | new user                                | set my profile with subjects I teach      | customise the app to subjects I teach                     |
| `* *`    | tutor                                   | get help for specific commands I enter    | see what fields I have entered incorrectly                |
| `* *`    | tutor                                   | search for my students' names             | find their contact information                            |
| `* *`    | tutor                                   | search for my lessons for the day         | better plan my personal time and schedule                 |
| `* *`    | tutor                                   | search for my lessons for each subject    | better allocate my time for each subject                  |
| `* *`    | tutor who wants to view her workload    | filter by subject	                        | see which subjects are most popular                       |
| `*`      | tutor who has changed tuition fees      | check how much I earn per week            | track money earned from tuition lessons                   |

### Use cases

(For all use cases below, the **System** is the `EduDex` and the **Actor** is the `user`, unless specified otherwise)

#### Use Case 1: Add a New Student

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt
- User has the student's information ready

**Main Success Scenario:**
1. User enters the `add` command with all required parameters
2. EduDex validates the parameters
3. EduDex adds the student to the contact list
4. EduDex shows a success message with the added student's details  
Use case ends.

**Extensions:**
- **2a. Invalid phone number format**
    - 2a1. EduDex shows error: *"Phone number must contain only numbers!"*  
      Use case ends.

- **2b. Invalid time format**
    - 2b1. EduDex shows error: *"Enter the time as HHMM (24-hour format)"*  
      Use case ends.

- **2c. Invalid day of week**
    - 2c1. EduDex shows error: *"Please enter a valid day of the week"*  
      Use case ends.

- **2d. Missing required parameters**
    - 2d1. EduDex shows error with correct command format  
      Use case ends.


#### Use Case 2: List All Students

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt

**Main Success Scenario:**
1. User enters the `list` command
2. EduDex retrieves all student contacts
3. EduDex displays all students in a formatted list  
Use case ends.


#### Use Case 3: Delete a Student

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt
- At least one student exists in the list

**Main Success Scenario:**
1. User enters `list` to view current students and their indices
2. User enters `delete INDEX` (where `INDEX` is a valid number)
3. EduDex removes the student at the specified index
4. EduDex shows a confirmation message  
Use case ends.

**Extensions:**
- **2a. Invalid index format (non-number)**
    - 2a1. EduDex shows error: *"INDEX must be a positive integer"*  
      Use case ends.

- **2b. Index out of bounds**
    - 2b1. EduDex shows error: *"The student index provided is invalid"*  
      Use case ends.


#### Use Case 4: Find by Name

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt
- At least one student exists in the list

**Main Success Scenario:**
1. User enters `find alice`.
2. EduDex performs a case-insensitive search over names.
3. EduDex filters the list to students whose names contain “alice”.
4. EduDex displays the filtered list and a result summary (e.g., “3 students listed”).  
   Use case ends.

**Postconditions:**
- Current list view shows only matched students.

#### Use Case 5: Find by Day

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt
- At least one student exists in the list
- At least one student has at least one lesson scheduled.

**Main Success Scenario:**
1. User enters `find d/Monday`.
2. EduDex validates the day value.
3. EduDex filters to students who have ≥1 lesson on Monday.
4. EduDex displays the filtered list and a result summary.  
   Use case ends.

**Extensions:**
- **2a. Invalid day value**
    - 2a1. EduDex shows error: *"Days should only be one of the following: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday."*  
      Use case ends.

#### Use Case 6: Exit the Application

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt

**Main Success Scenario:**
1. User enters the `exit` command
2. EduDex displays a goodbye message
3. EduDex closes the application  
Use case ends.

**Extensions:**
- **2a. User has unsaved changes (for future versions)**
    - 2a1. EduDex displays warning message
    - 2a2. EduDex asks for confirmation
    - 2a3. User confirms
    - Use case resumes from step 3.

#### Use Case 7: Handle Invalid Command

**Preconditions:**
- User has launched the EduDex application
- User is at the command prompt

**Main Success Scenario:**
1. User enters an unrecognized command
2. EduDex shows error: *"Invalid command. Try again"*
3. EduDex shows available command formats as hints  
Use case ends.

**Extensions:**
- **2a. Command is partially correct**
    - 2a1. EduDex provides specific error about the incorrect parameter  
      Use case ends.

- **2b. Command has typo**
    - 2b1. EduDex suggests possible correct commands  
      Use case ends.

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should respond to any command within 2 seconds when managing up to 1000 student records.
3. A user with above average typing speed of >= 50 words per minute for regular English text should be able to accomplish most tasks faster using commands than using the mouse.
4. A user should be able to successfully add, view, and delete a student record within 30 minutes of first use.
5. Should not crash during normal usage (e.g., invalid user input should be handled gracefully and should display appropriate error messages to allow continued operation).
6. Application should start up within 3 seconds on a typical computer with 8GB RAM and SSD storage.
7. All student data should be automatically saved locally in a human editable text file and persist between application sessions
8. Application should be able to operate without an internet connection. 
9. All similar operations should follow the same command patterns and provide similar output formats
10. Product developed in a breadth-first incremental manner, with each increment being a usable product that satisfies all the requirements identified up to that point.

### Glossary

* **CLI**: Command Line Interface. A text-based interface where users interact with a program by typing commands.
* **GUI**: Graphical User Interface. A visual-based interface where users interact with a program by interacting with windows, icons and menus.
* **Java**: The programming language used to implement this application.
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **OOP**: Object Oriented Programming. A programming paradigm where the real world is represented as objects, with state and behaviour.
* **Parameters**: Inputs provided to a command. A command may have zero or more parameters depending on its functionality.
* **PDPA**: Personal Data Protection Act. A Singapore law that governs the collection, use, and disclosure of individuals’ personal data.
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **RAM**: Random Access Memory. Main memory of the computer, used for temporary storage of data and instructions.
* **SSD**: Solid State Drive. A high speed storage device that stores data permanently.
* **Student**: Refers to a learner or individual being taught by the user of this application.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Finding Persons by Name

1. Finding a person by a single keyword

    1. Prerequisites: Ensure the EduDex application is launched with at least one student in the list.

    1. Test case: `find alice`  
       Expected: All students whose names contain "alice" (case-insensitive) are listed. Example: "Alice Tan", "Alicia Wong". Status message shows the number of students listed.

    1. Test case: `find ALICE`  
       Expected: Same results as above (case-insensitive behavior).

    1. Test case: `find bob` (when no “bob” exists)  
       Expected: list panel becomes empty.

### Finding Persons by Day

1. Finding students with lessons on a specific day

    1. Prerequisites: At least one student has a lesson on Monday.

    1. Test case: `find d/Monday`  
       Expected: Only students with lessons scheduled on Monday are listed.

    1. Test case: `find d/monDAY`  
       Expected: Same results as above (case-insensitive).

    1. Test case: `find d/Funday`  
       Expected: Error message displayed — “Days should only be one of the following: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday” No change to list.

    1. Test case: `find d/Sunday` (no students with Sunday lessons)  
       Expected: list panel becomes empty.

### Saving data

1. Dealing with missing/corrupted data files

   1. Close EduDex (if running). 
   2. Navigate to [JAR file location]/data/. 
   3. Move edudex.json outside the data folder, or delete it. 
   4. Launch EduDex by double-clicking the JAR.  
Expected: EduDex detects that the data file is missing and starts with an empty data file.
   5. A new edudex.json is created under [JAR file location]/data/.

<box type="info" seamless>

**Note:** This aligns with the guide’s warning that if the data file is not valid for use, EduDex will start with an empty data file on the next run.

</box>
