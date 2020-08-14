package io.devarc.rfid.reader.info;

public class ReaderType {
    private final int readerType;

    public ReaderType(int readerType) {
        this.readerType = readerType;
    }

    public int getOrdinal() {
        return readerType;
    }

    public String representation() {
        return "ReaderType: " + getOrdinal();
    }
}
