package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lecture.LectureName;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.tag.Tag;
import seedu.address.model.video.VideoName;

public class TagCommandParserTest {
    private static final String VALID_MODULE = "CS2030";
    private static final String VALID_LECTURE = "OOP";
    private static final String VALID_VIDEO = "Video1";
    private static final String VALID_TAG_1 = "cool";
    private static final String VALID_TAG_2 = "hard";
    private static final String VALID_TAG_MODULE_COMMAND =
            VALID_MODULE + " " + PREFIX_TAG + " "
                    + VALID_TAG_1 + ", " + VALID_TAG_2;
    private static final String VALID_TAG_LECTURE_COMMAND =
            VALID_LECTURE + " " + PREFIX_MODULE + " " + VALID_TAG_MODULE_COMMAND;
    private static final String VALID_TAG_VIDEO_COMMAND =
            VALID_VIDEO + " " + PREFIX_LECTURE + " " + VALID_TAG_LECTURE_COMMAND;
    private static final String INVALID_TAG_MODULE_COMMAND =
            PREFIX_MODULE + " " + VALID_MODULE + " " + VALID_TAG_1 + ", " + VALID_TAG_2;
    private static final String INVALID_TAG_LECTURE_COMMAND = VALID_LECTURE + PREFIX_MODULE + " " + VALID_MODULE;
    private static final String INVALID_TAG_VIDEO_COMMAND =
            VALID_VIDEO + PREFIX_LECTURE + " " + VALID_LECTURE;
    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_tagModule_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE);
        Set<Tag> tags = new HashSet<>(List.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        TagCommand expectedTagCommand = new TagCommand(tags, moduleCode);
        assertParseSuccess(parser, VALID_TAG_MODULE_COMMAND, expectedTagCommand);
    }

    @Test
    public void parse_tagLecture_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE);
        LectureName lectureName = new LectureName(VALID_LECTURE);
        Set<Tag> tags = new HashSet<>(List.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        TagCommand expectedTagCommand = new TagCommand(tags, moduleCode, lectureName);
        assertParseSuccess(parser, VALID_TAG_LECTURE_COMMAND, expectedTagCommand);
    }

    @Test
    public void parse_tagVideo_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE);
        LectureName lectureName = new LectureName(VALID_LECTURE);
        VideoName videoName = new VideoName(VALID_VIDEO);
        Set<Tag> tags = new HashSet<>(List.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        TagCommand expectedTagCommand = new TagCommand(tags, moduleCode, lectureName, videoName);
        assertParseSuccess(parser, VALID_TAG_VIDEO_COMMAND, expectedTagCommand);
    }
    @Test
    public void parse_invalidTagModule_throwParseException() {
        assertThrows(ParseException.class, () -> parser.parse(INVALID_TAG_MODULE_COMMAND));
    }

    @Test
    public void parse_invalidTagLecture_throwParseException() {
        assertThrows(ParseException.class, () -> parser.parse(INVALID_TAG_LECTURE_COMMAND));
    }

    @Test
    public void parse_invalidTagVideo_throwParseException() {
        assertThrows(ParseException.class, () -> parser.parse(INVALID_TAG_VIDEO_COMMAND));
    }
}
