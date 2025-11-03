package seedu.edudex.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.commons.util.StringUtil;
import seedu.edudex.logic.parser.exceptions.ParseException;
import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.School;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimConsecutiveWhiteSpaces = name.replaceAll("\\s+", " ").trim();
        if (!Name.isValidName(trimConsecutiveWhiteSpaces)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimConsecutiveWhiteSpaces);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String school} into an {@code School}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code school} is invalid.
     */
    public static School parseSchool(String school) throws ParseException {
        requireNonNull(school);
        String trimmedSchool = school.trim();
        if (!School.isValidSchool(trimmedSchool)) {
            throw new ParseException(School.MESSAGE_CONSTRAINTS);
        }
        return new School(trimmedSchool);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String day} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static Day parseDay(String day) throws ParseException {
        // no validation for day as of now
        requireNonNull(day);
        String trimmedDay = day.trim();
        if (!Day.isValidDay(trimmedDay)) {
            throw new ParseException(Day.MESSAGE_CONSTRAINTS);
        }
        return new Day(trimmedDay);
    }

    /**
     * Parses a {@code String time} into a {@code LocalTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code String name} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Subject parseSubjectName(String name) throws ParseException {
        requireNonNull(name);
        String trimConsecutiveWhiteSpaces = name.replaceAll("\\s+", " ").trim();
        if (trimConsecutiveWhiteSpaces.isEmpty() || !Subject.isValidSubjectName(trimConsecutiveWhiteSpaces)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimConsecutiveWhiteSpaces);
    }

    /**
     * Parses a {@code Day day}, {@code Time startTime}, and {@code Time endTime}
     * into a {@code Subject}.
     * @throws ParseException if the given {@code day}, {@code startTime}, or {@code endTime} is invalid.
     */
    public static Lesson parseLesson(String subject, String day,
                                     String startTime, String endTime) throws ParseException {

        Subject subjectObj = parseSubjectName(subject);
        Day dayObj = parseDay(day);
        Time startTimeObj = parseTime(startTime);
        Time endTimeObj = parseTime(endTime);

        if (!Lesson.isValidStartEndTime(startTimeObj, endTimeObj)) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS);
        }
        return new Lesson(subjectObj, dayObj, startTimeObj, endTimeObj);
    }

}
