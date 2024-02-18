package edu.java.bot.util;

public class TextMessages {
    private TextMessages() {
    }

    // Commands
    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";
    public static final String TRACK_COMMAND = "/track";
    public static final String UNTRACK_COMMAND = "/untrack";
    public static final String LIST_COMMAND = "/list";

    // Commands description
    public static final String START_COMMAND_DESCRIPTION = "is used to register an user";
    public static final String HELP_COMMAND_DESCRIPTION = "shows a list of bot commands";
    public static final String TRACK_COMMAND_DESCRIPTION = "is used to start tracking the link";
    public static final String UNTRACK_COMMAND_DESCRIPTION = "is used to finish tracking the link";
    public static final String LIST_COMMAND_DESCRIPTION = "shows a list of currently tracking links";

    // Other
    public static final String GITHUB_DOMAIN = "github.com";
    public static final String STACKOVERFLOW_DOMAIN = "stackoverflow.com";
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String START_COMMAND_MESSAGE = "Successfully registered!";
    public static final String HELP_COMMAND_MESSAGE = "The list of bot commands: " + LINE_SEPARATOR;
    public static final String INVALID_FORMAT_TRACK_MESSAGE =
        "Invalid link. Try, for example " + TRACK_COMMAND + " " + STACKOVERFLOW_DOMAIN;
    public static final String SUCCESSFUL_TRACK_MESSAGE = "Link is tracking now!";
    public static final String INVALID_TRACK_MESSAGE = "Cannot find webpage by the link: ";
    public static final String INVALID_FORMAT_UNTRACK_MESSAGE =
        "Invalid link. You can try " + UNTRACK_COMMAND + " " + GITHUB_DOMAIN;
    public static final String SUCCESSFUL_UNTRACK_MESSAGE = "Finish tracking the link ";
    public static final String INVALID_UNTRACK_MESSAGE = "This link hadn't been tracking ";
    public static final String SUCCESSFUL_LIST_MESSAGE = "Links that are tracking now: ";
    public static final String INVALID_LIST_MESSAGE = "You are not tracking any link now" + LINE_SEPARATOR;
    public static final String UPDATE_MESSAGE = "You have an update";
    public static final String INVALID_COMMAND_MESSAGE = "I don't know the command: ";
}
