package io.devarc.rfid.reader.internal;

import io.devarc.rfid.reader.info.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReaderInformationInternal {
    private final VersionInfo versionInfo;
    private final ReaderType readerType;
    private final FrequencyBand frequencyBand;
    private final RfOutputInWatt rfOutput;
    private final BeepNotification beepNotification;
    private final CheckAntenna checkAntenna;
    private final AntennaConfiguration antennaConfiguration;
}
