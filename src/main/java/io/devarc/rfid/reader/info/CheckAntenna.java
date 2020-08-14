package io.devarc.rfid.reader.info;

import lombok.Getter;

@Getter
public class CheckAntenna {
    private final CheckAntennaState state;
    public CheckAntenna(byte input) {
        state = input == 0 ? CheckAntennaState.CHECK_DISABLED : CheckAntennaState.CHECK_ENABLED;
    }

    public String representation() {
        return "CheckAntenna is " + getStateRepresentation();
    }

    private String getStateRepresentation() {
        return state == CheckAntennaState.CHECK_ENABLED ? "on" : "off";
    }
}
