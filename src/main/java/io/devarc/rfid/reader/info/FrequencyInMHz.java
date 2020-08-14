package io.devarc.rfid.reader.info;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrequencyInMHz {
    private float frequency;

    public String representation() {
        return new StringBuilder().append(frequency).append(" MHz").toString();
    }
}
