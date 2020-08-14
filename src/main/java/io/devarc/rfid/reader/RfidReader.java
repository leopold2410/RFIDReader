package io.devarc.rfid.reader;

import io.devarc.rfid.reader.data.TagData;
import io.devarc.rfid.reader.info.*;
import io.devarc.rfid.reader.internal.AntennaReader;
import io.devarc.rfid.reader.internal.DeviceReader;
import io.devarc.rfid.reader.internal.ReaderInformationInternal;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class RfidReader implements Closeable {
    private final List<AntennaReader> antennaReaders;
    private final DeviceReader deviceReader;

    public static RfidReader initialize(int comPort) throws IOException {
        System.loadLibrary("com_rfid_uhf_Device");
        RfidReader reader = new RfidReader();
        reader.init(comPort);
        return reader;
    }

    @Override
    public void close() throws IOException {
        deviceReader.close();
    }

    public ReaderInformation getInformation() throws IOException {
        ReaderInformationInternal readerInfo = deviceReader.getReaderInformation();
        return ReaderInformation.builder()
                .readerType(readerInfo.getReaderType())
                .versionInfo(readerInfo.getVersionInfo())
                .frequencyBand(readerInfo.getFrequencyBand())
                .antennaConfiguration(readerInfo.getAntennaConfiguration())
                .beepNotification(readerInfo.getBeepNotification())
                .checkAntenna(readerInfo.getCheckAntenna())
                .rfOutput(readerInfo.getRfOutput())
                .serialNumber(deviceReader.getSerialNumber())
                .temperature(deviceReader.getTemperatureInDegreeCelsius())
                .build();
    }

    private RfidReader() {
        deviceReader = new DeviceReader();
        antennaReaders = new ArrayList<>();
    }

    private void init(int comPort) throws IOException {
        deviceReader.init(comPort);
    }

    public List<TagData> readTags() {
        List<TagData> result = new ArrayList<>();
        for(AntennaReader antennaReader : antennaReaders) {
            List<TagData> tagsByAntenna;
            try {
                tagsByAntenna = antennaReader.readTags();
                result.addAll(tagsByAntenna);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void initAntennas(int countOfAntennas) throws IOException {
        if(countOfAntennas > 4) throw new IllegalArgumentException("only at max 4 antennas are supported");
        if(antennaReaders.size() == countOfAntennas) return;
        antennaReaders.clear();
        BitSet antennaBits = new BitSet(countOfAntennas);
        for(int i = 0; i < countOfAntennas; ++i) {
            antennaBits.set(i);
        }
        deviceReader.initAntennas(antennaBits);
        for(int i = 0; i < countOfAntennas; ++i) {
            antennaReaders.add(new AntennaReader(i, deviceReader));
        }
    }
}
