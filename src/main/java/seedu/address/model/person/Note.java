package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

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
    public static final Note EMPTY = new Note(new ArrayList<>(), true);

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_.-]+$";

    public final ArrayList<String> notes;

    /**
     * Constructs a {@code Note} with the specified list of notes.
     *
     * @param notes The list of notes.
     */
    public Note(ArrayList<String> notes) {
        requireNonNull(notes);
        this.notes = new ArrayList<>(notes);
    }

    /**
     * Constructs an empty {@code Note}.
     *
     * @param isSentinel Boolean flag to differentiate this constructor from the primary constructor.
     */
    private Note(ArrayList<String> arrayList, boolean isSentinel) {
        if (!isSentinel) {
            throw new IllegalArgumentException("This constructor is only for creating the EMPTY object");
        }
        this.notes = arrayList;
    }

    public void addNote(String noteToAdd) {
        this.notes.add(noteToAdd);
    }

    /**
     * Retrieves the list of notes associated with this Person.
     *
     * @return The list of notes associated with this Person.
     */
    public ArrayList<String> getNote() {
        return this.notes;
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
        return notes.isEmpty();
    }

    /**
     * Returns the string representation of the note.
     *
     * @return The string representation of the note.
     */
    @Override
    public String toString() {
        if (notes.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : notes) {
            sb.append(s);
            sb.append("/n");
        }
        return sb.toString();
    }

    /**
     * Returns the hash code of the note.
     *
     * @return The hash code of the note.
     */
    @Override
    public int hashCode() {
        return notes.hashCode();
    }
}
