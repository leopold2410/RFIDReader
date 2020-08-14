package io.devarc.rfid.reader.info;

import lombok.Getter;

@Getter
public class FrequencyBand {
    private final FrequencyBandType bandType;
    private final FrequencyInMHz maxFrequency;
    private final FrequencyInMHz minFrequency;
    public FrequencyBand(FrequencyBandType bandType, int max, int min) {
        this.bandType = bandType;
        this.maxFrequency = this.bandType.calculateFrequency(max);
        this.minFrequency = this.bandType.calculateFrequency(min);
    }

    public String representation() {
        return "FrequencyBand: " + bandType.representation() + " maxFrequency: " + maxFrequency.representation() + " minFrequency: " + minFrequency.representation();
    }
}
