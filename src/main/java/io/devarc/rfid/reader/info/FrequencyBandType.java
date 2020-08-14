package io.devarc.rfid.reader.info;
import lombok.Getter;

import java.util.Optional;

@Getter
public class FrequencyBandType {
    public static final FrequencyBandType ChineseBand = new FrequencyBandType(FrequencyBandName.CHINESE_BAND_2, 0, 1);
    public static final FrequencyBandType USBand = new FrequencyBandType(FrequencyBandName.US_BAND, 0, 2);
    public static final FrequencyBandType KoreanBand = new FrequencyBandType(FrequencyBandName.KOREAN_BAND, 0, 3);
    public static final FrequencyBandType EUBand = new FrequencyBandType(FrequencyBandName.EU_BAND, 1, 0);
    public static final FrequencyBandType UkraineBand = new FrequencyBandType(FrequencyBandName.UKRAINE_BAND, 1, 2);
    public static final FrequencyBandType ChineseBand1 = new FrequencyBandType(FrequencyBandName.CHINESE_BAND_1, 2, 0);
    public static final FrequencyBandType USBand3 = new FrequencyBandType(FrequencyBandName.US_BAND_3, 3, 0);

    private final FrequencyBandName name;
    private final int maxType;
    private final int minType;

    private FrequencyBandType(FrequencyBandName name, int maxType, int minType) {
        this.name = name;
        this.maxType = maxType;
        this.minType = minType;
    }

    private boolean compareByType(int maxType, int minType) {
        return this.maxType == maxType && this.minType == minType;
    }

    public static Optional<FrequencyBandType> findByType(int maxType, int minType) {
        if(ChineseBand.compareByType(maxType, minType)) return Optional.of(ChineseBand);
        if(USBand.compareByType(maxType, minType)) return Optional.of(USBand);
        if(KoreanBand.compareByType(maxType, minType)) return Optional.of(KoreanBand);
        if(EUBand.compareByType(maxType, minType)) return Optional.of(EUBand);
        if(UkraineBand.compareByType(maxType, minType)) return Optional.of(UkraineBand);
        if(ChineseBand1.compareByType(maxType, minType)) return Optional.of(ChineseBand1);
        if(USBand3.compareByType(maxType, minType)) return Optional.of(USBand3);
        return Optional.empty();
    }

    public FrequencyInMHz calculateFrequency(int value) {
        if(name == FrequencyBandName.CHINESE_BAND_2) {
            if(value < 0 || value > 19) throw new IllegalArgumentException("value not in range for Chinese band 2");
            return new FrequencyInMHz(920.125f + value * 0.25f);
        }
        if(name == FrequencyBandName.US_BAND) {
            if(value < 0 || value > 49) throw new IllegalArgumentException("value not in range for US band");
            return new FrequencyInMHz(902.75f + value * 0.5f);
        }
        if(name == FrequencyBandName.KOREAN_BAND) {
            if(value < 0 || value > 31) throw new IllegalArgumentException("value not in range for Korean band");
            return new FrequencyInMHz(917.1f + value * 0.2f);
        }
        if(name == FrequencyBandName.EU_BAND) {
            if(value < 0 || value > 14) throw new IllegalArgumentException("value not in range for EU band");
            return new FrequencyInMHz(865.1f + value * 0.2f);
        }
        if(name == FrequencyBandName.UKRAINE_BAND) {
            if(value < 0 || value > 6) throw new IllegalArgumentException("value not in range for Ukraine band");
            return new FrequencyInMHz(868.0f + value * 0.1f);
        }
        if(name == FrequencyBandName.CHINESE_BAND_1) {
            if(value < 0 || value > 19) throw new IllegalArgumentException("value not in range for Chinese band 1");
            return new FrequencyInMHz(840.125f + value * 0.25f);
        }
        if(name == FrequencyBandName.US_BAND_3) {
            if(value < 0 || value > 52) throw new IllegalArgumentException("value not in range for Chinese band 1");
            return new FrequencyInMHz(902f + value * 0.5f);
        }
        throw new IllegalArgumentException("unknown band type");
    }

    public String representation() {
        return name.toString();
    }

}
