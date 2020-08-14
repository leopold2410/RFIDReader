package io.devarc.rfid.reader.info;

import lombok.Getter;

import java.util.BitSet;

@Getter
public class AntennaConfiguration {
    private final BitSet configuration;

    public AntennaConfiguration(byte[] configInByte) {
        configuration = BitSet.valueOf(configInByte);
    }

    public String representation() {
        return "AntennaConfiguration: " + configuration.toString();
    }
}
