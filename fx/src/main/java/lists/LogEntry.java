package lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record LogEntry(String description, LocalDateTime timestamp) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");

    @Override
    public String toString() {
        return formatter.format(timestamp) + ": " + description;
    }
}