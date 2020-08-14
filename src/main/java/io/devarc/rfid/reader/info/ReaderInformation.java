package io.devarc.rfid.reader.info;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReaderInformation {
    private final VersionInfo versionInfo;
    private final ReaderType readerType;
    private final FrequencyBand frequencyBand;
    private final RfOutputInWatt rfOutput;
    private final BeepNotification beepNotification;
    private final CheckAntenna checkAntenna;
    private final AntennaConfiguration antennaConfiguration;
    private final SerialNumber serialNumber;
    private final TemperatureInDegreeCelsius temperature;


    public String representation() {
        return versionInfo.representation() + "\n"
                + readerType.representation() + "\n"
                + frequencyBand.representation() + "\n"
                + rfOutput.representation() + "\n"
                + beepNotification.representation() + "\n"
                + checkAntenna.representation() + "\n"
                + antennaConfiguration.representation() + "\n"
                + serialNumber.representation() + "\n"
                + temperature.representation();
    }
}
