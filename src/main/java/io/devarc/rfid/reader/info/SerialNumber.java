package io.devarc.rfid.reader.info;

import io.devarc.rfid.reader.internal.Utils;
import lombok.Getter;

@Getter
public class SerialNumber {
    private final String value;

    public SerialNumber(byte[] input) {
        if(input.length != 4) throw new IllegalArgumentException("SerialNumber: invalid number of bytes");
        this.value = Utils.getHexRepresentation(input);
    }

    public String representation() {
        return "SerialNumber: " + value;
    }
}
