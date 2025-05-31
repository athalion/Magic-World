package de.athalion.game.twodgame.logs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class LogSession {

    List<String> logEntries;
    String name;

    public LogSession() {
        logEntries = new ArrayList<>();
        name = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                .replaceAll(" ", "-")
                .replaceAll(",", "")
                .replaceAll(":", "-");
    }

    public void addEntry(String s) {
        logEntries.add(s);
    }

    public List<String> logEntries() {
        return logEntries;
    }

    public String name() {
        return name;
    }

}
