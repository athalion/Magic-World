package de.athalion.game.twodgame.logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Logger {

    private static LogSession logSession;

    public static void createSession() {
        logSession = new LogSession();
    }

    public static LogSession logSession() {
        return logSession;
    }

    public static void log(String s) {
        String log = "[" + LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "] [LOG]: " + s;
        System.out.println(log);
        logSession.addEntry(log);
    }

    public static void warn(String s) {
        String log = "[" + LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "] [WARN]: " + s;
        System.out.println(ConsoleColors.YELLOW + log + ConsoleColors.RESET);
        logSession.addEntry(log);
    }

    public static void error(String s) {
        String log = "[" + LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "] [WARN]: " + s;
        System.out.println(ConsoleColors.RED + log + ConsoleColors.RESET);
        logSession.addEntry(log);
    }

    public static void stackTrace(StackTraceElement[] stackTrace) {
        for (StackTraceElement stackTraceElement : stackTrace) {
            error(stackTraceElement.toString());
        }
    }

    public static void saveLog() {
        File logDir = new File(System.getProperty("user.dir") + File.separator + "logs");
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
