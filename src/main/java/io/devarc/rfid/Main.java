package io.devarc.rfid;

import io.devarc.rfid.reader.data.TagData;
import io.devarc.rfid.reader.info.ReaderInformation;
import io.devarc.rfid.reader.RfidReader;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RfidReader reader;

        try {
            reader = RfidReader.initialize(3);
            ReaderInformation info = reader.getInformation();
            System.out.println(info.representation());
            reader.initAntennas(2);

            LocalTime end = LocalTime.now().plus(60, ChronoUnit.SECONDS);
            while(LocalTime.now().isBefore(end)) {
                List<TagData> result = reader.readTags();
                for (TagData tag : result) {
                    System.out.println(tag.getId() + ": " + tag.getReadTime().format(DateTimeFormatter.ofPattern("HH:mm:ss.nnnnnnnnn")));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
