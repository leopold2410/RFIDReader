package io.devarc.rfid.reader.internal;

import io.devarc.rfid.reader.data.TagData;
import io.devarc.rfid.reader.info.*;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class DeviceReader implements Closeable {

    private final byte[] comAddr;
    private final int[] portHandle;
    private final com.rfid.uhf.Device device;
    private final byte baud;
    private boolean initialized = false;

    public DeviceReader()  {
        comAddr = new byte[1];
        comAddr[0] = (byte)255;
        portHandle= new int[1];
        baud = 5;
        device = new com.rfid.uhf.Device();
    }

    public void init(int comPort) throws IOException{
        int result = device.OpenComPort(comPort,comAddr, baud, portHandle);
        if(result != 0) throw new IOException("cannot open rfid reader device: " + result);
        initialized = true;
    }

    @Override
    public void close() {
        if(initialized) {
            device.CloseSpecComPort(portHandle[0]);
        }
    }

    public SerialNumber getSerialNumber() throws IOException {
        device.SetReadMode(comAddr, (byte)(ReadMode.ANSWER.ordinal()), portHandle[0]);
        byte[] serialNumber = new byte[4];
        int result = device.GetSeriaNo(comAddr, serialNumber, portHandle[0]);
        if(result != 0) throw new IOException("GetSeriaNo failed: " + result);
        return new SerialNumber(serialNumber);
    }

    public TemperatureInDegreeCelsius getTemperatureInDegreeCelsius() throws IOException {
        device.SetReadMode(comAddr, (byte)(ReadMode.ANSWER.ordinal()), portHandle[0]);
        byte[] plusMinus = new byte[1];
        byte[] temp = new byte[1];
        int result = device.GetReaderTemperature(comAddr, plusMinus, temp, portHandle[0]);
        if(result != 0) throw new IOException("GetReaderTemperature failed: " + result);
        return new TemperatureInDegreeCelsius(plusMinus[0],temp[0]);
    }

    public ReaderInformationInternal getReaderInformation() throws IOException {
        byte[]versionInfo=new byte[2];
        byte[]readerType=new byte[1];
        byte[]trType=new byte[1];
        byte[]dmaxfre=new byte[1];
        byte[]dminfre=new byte[1];
        byte[]powerdBm=new byte[1];
        byte[]inventoryScanTime=new byte[1];
        byte[]ant=new byte[1];
        byte[]beepEn=new byte[1];
        byte[]outputRep=new byte[1];
        byte[]checkAnt=new byte[1];

        int result = device.GetReaderInformation(comAddr, versionInfo, readerType, trType, dmaxfre, dminfre, powerdBm, inventoryScanTime,
                ant, beepEn, outputRep, checkAnt, portHandle[0]);
        if(result != 0) throw new IOException("GetReaderInformation failed: " + result);

        int maxFreq = dmaxfre[0] & 0x3F;
        int maxBand = (dmaxfre[0] & 0xC0) >> 6;

        int minFreq = dminfre[0] & 0x3F;
        int minBand = (dminfre[0] & 0xC0) >> 6;

        return ReaderInformationInternal.builder()
                .readerType(new ReaderType(readerType[0]))
                .versionInfo(new VersionInfo(versionInfo))
                .frequencyBand(new FrequencyBand(FrequencyBandType.findByType(maxBand,minBand).orElseThrow(() -> new IllegalArgumentException("band not found")),maxFreq, minFreq))
                .checkAntenna(new CheckAntenna(checkAnt[0]))
                .beepNotification(new BeepNotification(beepEn[0]))
                .rfOutput(new RfOutputInWatt(powerdBm[0]))
                .antennaConfiguration(new AntennaConfiguration(ant))
                .build();
    }

    public List<TagData> getTagsByAntenna(int indexOfAntenna) throws IOException {
        device.SetReadMode(comAddr, (byte)(ReadMode.ANSWER.ordinal()), portHandle[0]);
        byte QValue=4;
        byte Session=0;
        byte MaskMem=2;
        byte[]MaskAdr=new byte[2];
        byte MaskLen=0;
        byte[]MaskData=new byte[256];
        byte MaskFlag=0;
        byte AdrTID=0;
        byte LenTID=6;
        byte TIDFlag=1;
        byte Target=0;
        byte antennaIndex= (byte) ((byte)0x80 + (byte)indexOfAntenna);
        byte scantime=10;
        byte fastFlag=0;
        byte[] pEPCList = new byte[20000];
        int[] totalLen = new int[1];
        int[] cardCount =new int[1];
        byte[] antennaId = new byte[1];
        int result = device.Inventory_G2(comAddr,QValue,Session,MaskMem,MaskAdr,MaskLen,MaskData,MaskFlag,
                AdrTID,LenTID,TIDFlag,Target, antennaIndex, scantime, fastFlag, pEPCList, antennaId, totalLen,
                cardCount, portHandle[0]);
        LocalTime readTime = LocalTime.now();
        if(result > 2) throw new IOException("Inventory_G2 failed: " + result);
        List<TagData> tags = new ArrayList<>();
        if(cardCount[0]>0)
        {
            int m=0;
            for(int index=0;index<cardCount[0];index++)
            {
                int epclen = pEPCList[m++]&255;

                byte[]epc = new byte[epclen];
                for(int n=0;n<epclen;n++)
                {
                    byte bbt = pEPCList[m++];
                    epc[n] = bbt;
                }
                //rssi
                m++;

                String tagId = Utils.getHexRepresentation(epc);
                tags.add(new TagData(tagId, readTime));
            }
        }
        return tags;
    }

    public void initAntennas(BitSet antennaBits) throws IOException {
        byte[] antennaBytes = antennaBits.toByteArray();
        int result = device.SetAntennaMultiplexing(comAddr, antennaBytes[0], portHandle[0]);
        if(result != 0) throw new IOException("SetAntennaMultiplexing failed: " + result);
    }
}
