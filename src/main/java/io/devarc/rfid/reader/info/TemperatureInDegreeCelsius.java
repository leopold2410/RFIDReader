package io.devarc.rfid.reader.info;

import lombok.Getter;

@Getter
public class TemperatureInDegreeCelsius {
    private final int value;
    public TemperatureInDegreeCelsius(byte prefix, byte input) {
        String repPrefix = (prefix == 1) ? "+" : "-";
        String repValue = repPrefix + input;
        value = Integer.parseInt(repValue);
    }

    public String representation() {
        return new StringBuilder().append("Temperature: ").append(value).append("°C").toString();
    }
}
