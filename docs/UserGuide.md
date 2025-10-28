---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# EduDex User Guide

EduDex is a **desktop app for managing student's contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, EduDex can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T12-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your EduDex.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar edudex.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 sch/Raffles Primary School a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/strongInMaths` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/owesMoney`, `t/owesMoney t/strongInScience` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to EduDex.

Format: `add n/NAME p/PHONE_NUMBER sch/SCHOOL a/ADDRESS [t/TAG]`


<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 sch/NUS a/John street, block 123`
* `add n/Betsy Crowe sch/Queenstown Primary School a/Clementi p/1234567 t/weakInAlgebra`

![AddContact](images/addContactSuccess.png)
_Student John Doe added successfully._


### Listing all persons : `list`

Shows a list of all persons in EduDex.

Format: `list`

### Editing a person : `edit`

Edits an existing person in EduDex.

Format: `edit INDEX [n/NAME] [p/PHONE] [sch/SCHOOL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 sch/Jurong Primary School` Edits the phone number and school of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

![EditSuccess](images/editSuccess.png)
_John Doe school field edited successfully._

### Locating persons by Name, Day, or Subject: `find`

Finds persons whose **names**, **lesson days**, or **lesson subjects** match the given keywords.

Format:  
`find KEYWORD [MORE_KEYWORDS]`  
`find d/DAY`  
`find sub/SUBJECT`

* The search is **case-insensitive**.  
  e.g. `find hans` will match `Hans`.
* The order of keywords does not matter.  
  e.g. `find Hans Bo` will match `Bo Hans`.
* For name search, persons matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* When searching by day (`d/`), only valid days of the week are accepted.  
  e.g. `Monday`, `Tuesday`, `Wednesday`, `Thursday`, `Friday`, `Saturday`, `Sunday`.
* When searching by subject (`sub/`), only subjects matching lesson names will be shown.
* Persons matching **at least one** keyword or criterion will be displayed.
* For `find sub/SUBJECT`, lessons within each student are automatically **sorted by day and start time**.

<box type="tip" seamless>

**Tip:** You can search by name, day, or subject independently.  
e.g.
- `find alice` — finds students with names containing "alice".
- `find d/Monday` — finds all students with lessons on Monday.
- `find sub/Math` — finds all students taking Math lessons.
  </box>

Examples:
* `find John` returns all persons with "John" in their names.
* `find alex david` returns both `Alex Yeoh` and `David Li`.
* `find d/Friday` returns all students with Friday lessons.
* `find sub/math` returns all students taking Science lessons.

![result for 'find Alex David'](images/findAlexDavidResult.png)
_Find result for names 'Alex' or 'David'_

![result for 'find sub/math'](images/findSubjectResult.png)
_Find result for subject 'math'_

![result for 'find d/Monday'](images/findDayResult.png)
_Find result for day 'Monday'_

### Deleting a person : `delete`

Deletes the specified person from EduDex.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in EduDex.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding a subject: `addsub`

Adds a subject to EduDex.

Format: `addsub SUBJECT`


* A subject can be any alphanumerical string.
* A subject is case-insensitive, stored in lower case. e.g `Math` will be stored as `math`

Examples:
* `addsub English`
* `addsub MATH`

![addSubSuccess](images/addSub.png)
_Subject "mathematics" added successfully._

### Deleting a subject : `delsub`

Deletes the specified subject from EduDex.

Format: `delsub INDEX`

* Deletes the subject at the specified `INDEX`.
* The index refers to the index number shown in the displayed subject list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `delsub 1` deletes the 1st subject in EduDex.

### Adding a lesson: `addlesson`

Adds a lesson to the student in EduDex, specified by index.

Format: `addlesson STUDENT_INDEX sub/SUBJECT d/DAY start/START_TIME end/END_TIME`

* STUDENT_INDEX refers to the index in the currently displayed list of students.
* STUDENT_INDEX must be a positive integer that is not greater than the size of the
  currently displayed list of students.
* SUBJECT **must match** (case-insensitive) at least one subject in EduDex.
* DAY must match (case-insensitive) one of the following strings:
  { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }.
* START_TIME and END_TIME must be in the 24-hour format HH:MM, and must be a valid time.
* START_TIME must be earlier than END_TIME.

Examples:
* `addlesson 1 sub/mathematics d/Monday start/12:00 end/13:00`
* `addlesson 3 d/Tuesday sub/physics start/13:00 end/15:00`

![addLessonSuccess](images/addLesson.png)
_Lesson "mathematics" added successfully to student John Doe._

### Deleting a lesson from a student: `dellesson`

Deletes a specific lesson (by index) from a given student.

Format: `dellesson STUDENT_INDEX LESSON_INDEX`

* Deletes the lesson at the specified `LESSON_INDEX` from the student at `STUDENT_INDEX`.
* The indices refer to:
    * `STUDENT_INDEX` — position of the student in the currently displayed student list.
    * `LESSON_INDEX` — position of the lesson within that student’s list of lessons.
* Both indices **must be positive integers** (1, 2, 3, …).

<box type="info" seamless>

**Example:**  
If student 1 has 3 lessons (Math, Science, English),  
`dellesson 1 2` will delete **Science**.
</box>

Examples:
* `dellesson 1 1` — Deletes the first lesson of the first student in the list.
* `dellesson 2 3` — Deletes the third lesson of the second student in the list.

If an invalid index is entered:
* EduDex will display **“Invalid student index.”** if `STUDENT_INDEX` is out of range.
* EduDex will display **“Invalid lesson index.”** if `LESSON_INDEX` does not exist.

![before 'dellesson 1 2'](images/beforeDeleteLesson.png)
_Student Alex Yeoh has 3 lessons before deletion._

![result for 'dellesson 1 2'](images/deleteLessonResult.png)
_Student Alex Yeoh has 2 lessons after deletion of the 3rd lesson._


### Clearing all persons : `clear`

Clears all persons from EduDex.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

EduDex data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the JSON data file

EduDex data are saved automatically as a JSON file `[JAR file location]/data/edudex.json`. Advanced users are welcome to update data directly by editing that data file.


The `edudex.json` file defines the **data model** for managing tutees in the EduDex application.
This is intended for power users who wish to edit `edudex.json` without using the GUI.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, EduDex will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the EduDex to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

#### JSON: Top-Level Overview

```json
{
  "persons": [ ... ], // required
  "subjects": [ ... ] // required
}
```

**Person**
```json
{
  "name": "Full Name", // required, 
  "phone": "8-digit Singapore number", // required
  "school": "string", // required
  "address": "Full address string", // required
  "tags": [ "string", ... ], // optional
  "lessons": [ // optional
    {
    "name": "string",
    "day": "string",
    "startTime": "HH:MM",
    "endTime": "HH:MM"
    }
  ] 
}

```

**Rules**

| Field              | Type              | Constraint                                                                           |
|--------------------|-------------------|--------------------------------------------------------------------------------------|
| `name`             | string            | Alphanumeric Characters and Spaces, must not be blank                                |
| `phone`            | string (ISO 8601) | Number, must be at least 3 digits long                                               |
| `school`           | string            | Alphanumeric Characters and Spaces, must not be blank                                |
| `address`          | string            | Any value, must not be blank                                                         |
| `tags`             | string[]          | Alphanumeric, optional                                                               |
| `lessons`          | Lesson[]          | See fields below                                                                     |
| `lesson:name`      | string            | Alphanumeric Characters and Spaces, must not be blank                                |
| `lesson:day`       | string            | Only one of the following: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday |
| `lesson:startTime` | string            | HH:MM (24-hour format), must be a valid time before endTime                          |
| `lesson:endTime`   | string            | HH:MM (24-hour format), must be a valid time after startTime                         |



**Example**
```json
{
  "name": "Alex Yeoh",
  "phone": "87438808",
  "school": "Clementi Village Primary School",
  "address": "Blk 30 Geylang Street 29, #06-40",
  "tags": [
    "prefersMorningLessons",
    "A-levelCandidate"
  ],
  "lessons": [
    {
    "subject": "Science",
    "day": "Monday",
    "startTime": "10:00",
    "endTime": "12:00"
    },
    {
      "name": "Math",
      "day": "Tuesday",
      "startTime": "14:00",
      "endTime": "16:00"
    }
  ]
}
```

#### JSON: Subject

Each entry in `subjects` defines a distinct academic subject.

**Structure**
```json
{
  "subjectName": "Subject Title"
}
```

**Rules**
- Each subject **must** have a unique `subjectName`.
- Subject names are **case-insensitive**.
- No duplicate entries allowed.

**Example**
```json
{
  "subjects": [
    { "subjectName": "Chemistry" },
    { "subjectName": "Physics" },
    { "subjectName": "Biology" }
  ]
}
```

**JSON Example**
```json
{
  "persons": {
    "name" : "Bernice Yu",
    "phone" : "99272758",
    "school": "Clementi West Secondary School",
    "address" : "Blk 30 Lorong 3 Serangoon Gardens, #07-18",
    "tags" : [ ],
    "lessons" : [
      {
        "subject": "Science",
        "day": "Monday",
        "startTime": "10:00",
        "endTime": "12:00"
      }
    ]
  },
  "subjects": [
    { "subjectName": "Science" }
  ]
}

```
<box type="tip" seamless>

**JSON Tips:**
1. **Formatting**
- Use **UTF-8 encoding**.
- Always maintain **valid JSON syntax**:
    - Double quotes for all keys and strings.
    - No trailing commas.
- Validate the file with a JSON linter before saving.

2. **Adding Entries**
- To add a new person, append a new object to the `persons` array.
- To add a new subject, append a new object to `subjects`.
  </box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous EduDex home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action | Format, Examples |
| :--- | :--- |
| **Add Person** | `add n/NAME p/PHONE_NUMBER sch/SCHOOL a/ADDRESS [t/TAG]...`<br>e.g., `add n/James Ho p/22224444 sch/Clementi Primary School a/123, Clementi Rd, 1234665 t/strongInMaths` |
| **Edit Person** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [sch/SCHOOL] [a/ADDRESS] [t/TAG]...`<br>e.g., `edit 2 n/James Lee sch/Jurong Primary School` |
| **Delete Person** | `delete INDEX`<br>e.g., `delete 3` |
| **List Persons** | `list` |
| **Find Persons** | `find KEYWORD [MORE_KEYWORDS]`<br>`find d/DAY`<br>`find sub/SUBJECT`<br>e.g., `find James`, `find d/Monday`, `find sub/Math` |
| **Add Subject** | `addsub SUBJECT`<br>e.g., `addsub Mathematics` |
| **Delete Subject**| `delsub INDEX`<br>e.g., `delsub 2` |
| **Add Lesson** | `addlesson STUDENT_INDEX sub/SUBJECT d/DAY start/START_TIME end/END_TIME`<br>e.g., `addlesson 1 sub/Mathematics d/Monday start/12:00 end/13:00` |
| **Delete Lesson** | `dellesson STUDENT_INDEX LESSON_INDEX`<br>e.g., `dellesson 1 2` |
| **Clear** | `clear` |
| **Help** | `help` |
| **Exit** | `exit` |

find James Jake`
**List**   | `list`
**Help**   | `help`
