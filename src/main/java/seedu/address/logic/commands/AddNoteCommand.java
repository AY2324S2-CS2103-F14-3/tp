package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Adds a note for a person identified by their index number in the displayed person list.
 */
public class AddNoteCommand extends Command {
    public static final String COMMAND_WORD = "adn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a note to a person identified by the index number used in the displayed person list.\n"
            + "Parameters:"
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_NOTE + "NOTE\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_NOTE + "hardworking";

    public static final String MESSAGE_SUCCESS = "New note added to %1$s.\nNote: %2$s";

    private static final Logger logger = LogsCenter.getLogger(AddNoteCommand.class);

    private final Index index;

    private final Note note;

    /**
     * @param index of the person in the filtered person list to add a note
     * @param note the note that should be added
     */
    public AddNoteCommand(Index index, Note note) {
        requireNonNull(index);
        requireNonNull(note);

        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddNote = lastShownList.get(index.getZeroBased());
        personToAddNote.addNote(note);

        logger.log(Level.INFO, "Added note '" + note.toString() + "' to person: " + personToAddNote);

        String message = String.format(MESSAGE_SUCCESS, Messages.format(personToAddNote), note);
        return new CommandResult(message);
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

        AddNoteCommand otherAddNoteCommand = (AddNoteCommand) other;
        return index.equals(otherAddNoteCommand.index)
                && note.equals(otherAddNoteCommand.note);
    }

    /**
     * Generates a string representation of this AddNoteCommand.
     *
     * @return The string representation of this AddNoteCommand.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("note", note)
                .toString();
    }
}
