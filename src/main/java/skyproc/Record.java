package skyproc;

import lev.LImport;
import lev.LInChannel;
import lev.LShrinkArray;
import lev.Ln;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Abstract class outlining functions for a generic record.
 *
 * @author Justin Swanson
 */
public abstract class Record implements Serializable {

    final static HashMap<String, ArrayList<String>> typeLists = new HashMap<>();
    private static int datasize = -1;

    Record() {
    }

    static ArrayList<String> getTypeList(String t) {
        return typeLists.computeIfAbsent(t, t1 -> new ArrayList<>(List.of(t1)));
    }

    /**
     * Returns the header tag of the next record.
     * If the next record is a XXXX type record, its data will be read out
     * directly and stored in the static internal storage. The function then
     * returns the directly following real record header and makes the extended
     * datablock size given by the XXXX record available via the
     * getDataBlockSize() function.
     *
     * @param in the input buffer to read from
     * @return the record type of the next real record (XXXX records are not
     * explicitly returned)
     */
    static String getNextType(LImport in) {
        datasize = -1;
        String type = Ln.arrayToString(in.getInts(0, 4));
        if (type.equals("XXXX")) {
            int blocksize = Ln.arrayToInt(in.extract(4, 2));
            datasize = Ln.arrayToInt(in.extract(blocksize));
            type = Ln.arrayToString(in.getInts(0, 4));
        }
        return type;
    }

    /**
     * Size of the next record's datablock, if larger than 2^16 -1 byte.
     * If the previously extracted record type started as "XXXX" type, this
     * function will return the datablock size that was extracted from the XXXX
     * record. If no XXXX block preceded the real record header, the function
     * will return -1 and the datasize can be extracted as usual from the bytes
     * following the record header tag.
     *
     * @return the data block size extracted from the preceding XXXX record or
     * -1 if no such record preceded the last tag extracted with getNextType()
     */
    static int getDataBlockSize() {
        return datasize;
    }

    static void logMod(Mod srcMod, String header, String... data) {
        SPGlobal.logMod(srcMod, header, data);
    }

    void parseData(LImport in, Mod srcMod) throws BadRecord, BadParameter, DataFormatException {
        in.skip(getIdentifierLength() + getSizeLength());
    }

    final void parseData(ByteBuffer in, Mod srcMod) throws BadRecord, BadParameter, DataFormatException {
        parseData(new LShrinkArray(in), srcMod);
    }

    boolean isValid() {
        return true;
    }

    /**
     * Returns a short summary/title of the record.
     *
     * @return A short summary/title of the record.
     */
    @Override
    public abstract String toString();

    /**
     * Returns the contents of the record, or exports them to a log, depending
     * on the record type.
     *
     * @return The contents of the record, OR the empty string, depending on the
     * record type.
     */
    public abstract String print();

    abstract ArrayList<String> getTypes();

    /**
     * @return The type string associated with record.
     */
    public String getType() {
        return getTypes().get(0);
    }

    Record getNew() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void export(ModExporter out) throws IOException {
        if (isValid()) {
            out.write(getType());
            out.write(getContentLength(out.getExportMod().isFlag(Mod.Mod_Flags.STRING_TABLED)));
        }
    }

    /**
     * Determines the size of the record.
     * If the current record was preceded by a XXXX record, the size parameter
     * will be taken from the Record class' static storage, otherwise it is read
     * from the bytes following the header tag.
     *
     * @param in the import buffer to extract from
     * @return the size of the record in bytes
     */
    public int getRecordLength(LImport in) {
        int blocksize = Record.getDataBlockSize();
        if (blocksize == -1) {
            blocksize = Ln.arrayToInt(in.getInts(getIdentifierLength(),
                    getSizeLength()));
        }
        return blocksize + getSizeLength() + getIdentifierLength() +
                getFluffLength();
    }

    LImport extractRecordData(LImport in) {
        return extractData(in, getRecordLength(in));
    }

    LImport extractData(LImport in, int size) {
        LImport extracted;
        if (SPGlobal.streamMode && (in instanceof RecordShrinkArray || in instanceof LInChannel)) {
            extracted = new RecordShrinkArray(in, size);
        } else {
            extracted = new LShrinkArray(in, size);
        }
        in.skip(size);
        return extracted;
    }

    int getHeaderLength() {
        return getIdentifierLength() + getSizeLength() + getFluffLength();
    }

    final int getIdentifierLength() {
        return 4;
    }

    abstract int getSizeLength();

    abstract int getFluffLength();

    int getTotalLength(boolean isStringTabled) {
        return getContentLength(isStringTabled) + getHeaderLength();
    }

    abstract int getContentLength(boolean isStringTabled);

    boolean logging() {
        return SPGlobal.logging();
    }

    void logMain(String header, String... log) {
        SPGlobal.logMain(header, log);
    }

    void logSync(String header, String... log) {
        SPGlobal.logSync(getType(), log);
    }

    void logError(String header, String... log) {
        SPGlobal.logError(header, log);
    }

    void logSpecial(Enum e, String header, String... log) {
        SPGlobal.logSpecial(e, header, log);
    }

    void log(String header, String... log) {
        SPGlobal.log(header, log);
    }
}
