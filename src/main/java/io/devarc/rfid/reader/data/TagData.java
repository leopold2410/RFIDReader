package io.devarc.rfid.reader.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TagData {
    private final String id;
    private final LocalTime readTime;
}
