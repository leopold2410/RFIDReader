package io.devarc.rfid.reader.info;

import lombok.Data;

@Data
public class VersionInfo {
    public VersionInfo(byte[] versionInfo) {
        if(versionInfo.length != 2) throw new IllegalArgumentException("version info must have two bytes");
        majorVersion = versionInfo[0];
        minorVersion = versionInfo[1];
    }
    private int majorVersion;
    private int minorVersion;

    public String representation() {
        return new StringBuilder().append("VersionInfo: ").append(majorVersion).append(".").append(minorVersion).toString();
    }
}
