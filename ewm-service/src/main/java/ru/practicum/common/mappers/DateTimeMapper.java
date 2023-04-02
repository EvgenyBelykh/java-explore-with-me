package ru.practicum.common.mappers;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime toLocalDateTime(String string) {
        return LocalDateTime.parse(string, dateTimeFormatter);
    }

    public String toLocalDateTimeString(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }
}
