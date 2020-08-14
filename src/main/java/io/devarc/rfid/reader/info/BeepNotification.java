package io.devarc.rfid.reader.info;

import lombok.Getter;

@Getter
public class BeepNotification {
    private final BeepNotificationState state;
    public BeepNotification(byte notification) {
        state = notification == 0 ? BeepNotificationState.BEEP_MUTED : BeepNotificationState.BEEP_ON;
    }

    public String representation() {
        return "BeepNotification is " +  getStateRepresentation();
    }

    private String getStateRepresentation() {
        return state == BeepNotificationState.BEEP_ON ? "on" : "off";
    }
}
