package de.athalion.game.twodgame.logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Logger {

    private static LogSession logSession;

    /**
     * Creates a new {@link LogSession} instance.
     * This method must be invoked before performing any logging operations.
     */
    public static void createSession() {
        logSession = new LogSession();
    }

    public static LogSession logSession() {
        return logSession;
    }

    /**
     * Logs a message to the console and records it in the current {@link LogSession}.
     * <p>
     * The message is prefixed with the current local time and a fixed log level label,
     * printed to {@code System.out}, and then appended to the session's log entries.
     *
     * @param s the text to log
     */
    public static void log(String s) {
        String log = "[" + LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "] [LOG]: " + s;
        System.out.println(log);
        logSession.addEntry(log);
    }

    /**
     * Logs a warning message. The message is prefixed with the current local time and the
     * {@code [WARN]} label, printed to {@code System.out} in yellow color, and appended to
     * the current {@link LogSession}.
     *
     * @param s the text to log
     */
    public static void warn(String s) {
        String log = "[" + LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "] [WARN]: " + s;
        System.out.println(ConsoleColors.YELLOW + log + ConsoleColors.RESET);
        logSession.addEntry(log);
    }

    /**
     * Logs an error message to the console and records it in the current {@link LogSession}.
     * <p>
     * The message is prefixed with the current local time and the {@code [ERROR]} label,
     * printed to {@code System.out} in red color, and appended to the session's log entries.
     *
     * @param s the text to log as an error
     */
    public static void error(String s) {
        String log = "[" + LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "] [ERROR]: " + s;
        System.out.println(ConsoleColors.RED + log + ConsoleColors.RESET);
        logSession.addEntry(log);
    }

    /**
     * Logs each element of the provided stack trace array as an error message.
     * <p>
     * For every {@link StackTraceElement} in the array, its string representation is
     * forwarded to {@link #error(String)} which writes the message to the console
     * and records it in the current {@link LogSession}.
     *
     * @param stackTrace the array of stack trace elements to log
     */
    public static void stackTrace(StackTraceElement[] stackTrace) {
        for (StackTraceElement stackTraceElement : stackTrace) {
            error(stackTraceElement.toString());
        }
    }

    /**
     * Persists the current {@link LogSession} to disk.
     * <p>
     * The method creates a {@code logs} directory under the current working
     * directory if it does not already exist. A file named
     * {@code <session-name>.log} is created in that directory, where
     * {@code <session-name>} is the value returned by {@link LogSession#name()}.
     * Each entry stored in the session (retrieved via {@link LogSession#logEntries()})
     * is written to the file.
     * <p>
     * Clients should invoke {@link Logger#createSession()} before any logging
     * activity to ensure a {@link LogSession} instance is available. The method
     * does not return a value.
     *
     * @see Logger#createSession()
     * @see LogSession#logEntries()
     * @see LogSession#name()
     */
    public static void saveLog() {
        File logDir = new File(System.getProperty("user.dir"), "logs");
        if (!logDir.exists()) logDir.mkdirs();
        try (PrintWriter printWriter = new PrintWriter(new File(logDir, logSession.name() + ".log"))) {
            for (String logEntry : logSession.logEntries()) {
                printWriter.println(logEntry);
            }
            printWriter.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
