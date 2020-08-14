package io.devarc.rfid.reader.info;

import lombok.Getter;

@Getter
public class RfOutputInWatt {
    private final int output;

    public  RfOutputInWatt(byte value) {
        output = value;
    }

    public String representation() {
        return "RF Output: " + output;
    }
}
