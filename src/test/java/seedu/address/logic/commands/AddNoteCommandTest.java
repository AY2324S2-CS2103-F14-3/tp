package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ClassGroup;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AddNoteCommand}.
 */
public class AddNoteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        Person person = new Person(
                new Name("John"),
                new ClassGroup("2A"),
                new Email("john@gmail.com"),
                new Phone("98989898"),
                Optional.of(new Telegram("@john")),
                Optional.of(new Github("johncena")),
                Optional.of(Note.EMPTY)
        );

        model.addPerson(person);

        int index = model.getFilteredPersonList().indexOf(person);

        AddNoteCommand addNoteCommand = new AddNoteCommand(Index.fromZeroBased(index), "hardworking");

        CommandResult commandResult = addNoteCommand.execute(model);

        String expectedMessage = String.format(AddNoteCommand.MESSAGE_ADD_NOTE_SUCCESS, person);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        Person updatedPerson = model.getFilteredPersonList().get(index);

        assertTrue(updatedPerson.getNote().isPresent());
        assertTrue(updatedPerson.getNote().get().getNote().contains("hardworking"));
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddNoteCommand addNoteCommand = new AddNoteCommand(outOfBoundIndex, "Some note");

        assertCommandFailure(addNoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddNoteCommand standardCommand = new AddNoteCommand(INDEX_FIRST_PERSON, "Test note");

        AddNoteCommand commandWithSameValues = new AddNoteCommand(INDEX_FIRST_PERSON, "Test note");

        assertEquals(standardCommand, commandWithSameValues);

        assertTrue(standardCommand.equals(standardCommand));

        assertTrue(!standardCommand.equals(null));

        assertTrue(!standardCommand.equals(new ClearCommand()));

        assertTrue(!standardCommand.equals(new AddNoteCommand(INDEX_FIRST_PERSON, "Different note")));
    }
}
