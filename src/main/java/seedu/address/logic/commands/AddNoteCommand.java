package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDNOTE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ClassGroup;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;

/**
 * Adds a note for a person identified by their index number in the displayed person list.
 */
public class AddNoteCommand extends Command {
    public static final String COMMAND_WORD = "adn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add a note for the person identified by the index number used in the displayed person list."
            + " Parameters: "
            + PREFIX_ADDNOTE + "INDEX (must be a positive integer) STRING (must contain only alphanumeric characters"
                + " and symbols."
            + " Example: " + COMMAND_WORD + " 1 hardworking";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "New note added: %1$s";

    private static final Logger logger = LogsCenter.getLogger(AddNoteCommand.class);

    private final Index targetIndex;

    private final String noteToAdd;

    /**
     * Constructs an AddNoteCommand to add a note to the person at the specified index.
     *
     * @param targetIndex The index of the person to add the note to.
     * @param noteToAdd   The note content to add.
     */
    public AddNoteCommand(Index targetIndex, String noteToAdd) {
        this.targetIndex = targetIndex;
        this.noteToAdd = noteToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddNote = lastShownList.get(targetIndex.getZeroBased());
        Optional<Note> currNote = personToAddNote.getNote();
        Note note = currNote.orElse(Note.EMPTY);
        note.addNote(noteToAdd);

        // Log a message indicating the note addition
        logger.log(Level.INFO, "Added note '" + noteToAdd + "' to person: " + personToAddNote);

        return new CommandResult(String.format(MESSAGE_ADD_NOTE_SUCCESS, Messages.format(personToAddNote)));
    }

    private static Person createAddedNotePerson(Person personToAddNote, ArrayList<String> notesArr) {
        assert personToAddNote != null;

        Name updatedName = personToAddNote.getName();
        ClassGroup updatedClassGroup = personToAddNote.getClassGroup();
        Email updatedEmail = personToAddNote.getEmail();
        Phone updatedPhone = personToAddNote.getPhone();
        Optional<Telegram> updatedTelegram = personToAddNote.getTelegram().isPresent()
                ? personToAddNote.getTelegram() : Optional.of(Telegram.EMPTY);
        Optional<Github> updatedGithub = personToAddNote.getGithub().isPresent()
                ? personToAddNote.getGithub() : Optional.of(Github.EMPTY);
        return new Person(updatedName, updatedClassGroup, updatedEmail,
                updatedPhone, updatedTelegram, updatedGithub, Optional.of(new Note(notesArr)));
    }

    // For now, allow duplicate notes. Will include implementation to exclude it and not allow duplicate notes
    private void updatePerson(Person personToAddNote, Person addedNotePerson, Model model) {
        model.deletePerson(personToAddNote);
        model.addPersonKeepFilter(addedNotePerson);
    }

    private void updateLastViewedPersonIfNecessary(Person personToAddNote, Person addedNotePerson, Model model) {
        model.getLastViewedPerson()
                .filter(lastViewedPerson -> lastViewedPerson.equals(personToAddNote))
                .ifPresent(lastViewedPerson -> model.updateLastViewedPerson(addedNotePerson));
    }

    /**
     * Checks if this AddNoteCommand is equal to another object.
     *
     * @param other The other object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddNoteCommand)) {
            return false;
        }

        AddNoteCommand otherViewCommand = (AddNoteCommand) other;
        return noteToAdd.equals(otherViewCommand.noteToAdd);
    }

    /**
     * Generates a string representation of this AddNoteCommand.
     *
     * @return The string representation of this AddNoteCommand.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("noteToAdd", noteToAdd)
                .toString();
    }
}
