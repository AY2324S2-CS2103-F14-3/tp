package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;

/**
 * Represents a note associated with a person in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {
    public static final String MESSAGE_CONSTRAINTS =
            "Note should only contain alphanumeric characters and symbols, and it should not be blank";

    /**
     * Represents an empty note.
     */
    public static final Note EMPTY = new Note("", true);

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_.-]+$";

    private final String noteToAdd;
    private final ArrayList<String> notes = new ArrayList<>();

    /**
     * Constructs a {@code Note}.
     *
     * @param noteToAdd A valid note.
     */
    public Note(String noteToAdd) {
        requireNonNull(noteToAdd);
        checkArgument(isValidNote(noteToAdd), MESSAGE_CONSTRAINTS);
        this.noteToAdd = noteToAdd;
        notes.add(noteToAdd);
    }

    /**
     * Constructs a {@code Note}.
     *
     * @param noteToAdd An empty string.
     * @param isSentinel Boolean flag to differentiate this constructor from the primary constructor.
     */
    private Note(String noteToAdd, boolean isSentinel) {
        if (!isSentinel) {
            throw new IllegalArgumentException("This constructor is only for creating the EMPTY object");
        }
        this.noteToAdd = noteToAdd;
    }

    /**
     * Returns true if a given string is a valid note.
     *
     * @param test The string to test.
     * @return True if the string is a valid note, false otherwise.
     */
    public static boolean isValidNote(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if this note is empty.
     *
     * @return True if the note is empty, false otherwise.
     */
    public boolean isEmpty() {
        return noteToAdd.isEmpty();
    }

    /**
     * Returns the string representation of the note.
     *
     * @return The string representation of the note.
     */
    @Override
    public String toString() {
        return notes.toString();
    }

    /**
     * Returns the hash code of the note.
     *
     * @return The hash code of the note.
     */
    @Override
    public int hashCode() {
        return noteToAdd.hashCode();
    }
}
