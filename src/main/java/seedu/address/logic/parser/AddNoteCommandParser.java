package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddNoteCommand object.
 */
public class AddNoteCommandParser implements Parser<AddNoteCommand> {
    public static final String MESSAGE_ONE_BASED_INDEXING =
            "Please input a 1-based index.";

    /**
     * Parses the given {@code args} and creates a new AddNoteCommand object.
     *
     * @param args The arguments provided for the command in the format: {@code INDEX NOTE}.
     * @return A new AddNoteCommand object.
     * @throws ParseException If the input arguments are invalid.
     */
    public AddNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        String[] strArr = args.split(" ");

        Index index;

        try {
            index = ParserUtil.parseIndex(strArr[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE), pe);
        }
        if (index.getOneBased() <= 0) {
            throw new ParseException(MESSAGE_ONE_BASED_INDEXING);
        }
        return new AddNoteCommand(index, strArr[1]);
    }
}
