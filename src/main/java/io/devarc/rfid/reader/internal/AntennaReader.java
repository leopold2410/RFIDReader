package io.devarc.rfid.reader.internal;

import io.devarc.rfid.reader.data.TagData;
import io.devarc.rfid.reader.internal.DeviceReader;

import java.io.IOException;
import java.util.List;

public class AntennaReader {
    private final int antennaIndex;
    private final DeviceReader deviceReader;

    public AntennaReader(int antennaIndex, DeviceReader deviceReader) {
        this.antennaIndex = antennaIndex;
        this.deviceReader = deviceReader;
    }

    public List<TagData> readTags() throws IOException {
        return deviceReader.getTagsByAntenna(antennaIndex);
    }
}
