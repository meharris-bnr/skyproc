package skyproc;

import lev.LImport;
import lev.Ln;
import skyproc.Mod.Mod_Flags;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.zip.DataFormatException;

/**
 * @author Justin Swanson
 */
class SubStringPointer extends SubRecordTyped {

    static boolean shortNull = true;
    final SubData data;
    final SubString text;
    final SubStringPointer.Files file;
    boolean forceExport = false;

    SubStringPointer(String type, SubStringPointer.Files file) {
        super(type);
        data = new SubData(type, new byte[1]);
        text = SubString.getNew(type, true);
        this.file = file;
    }

    @Override
    SubRecord getNew(String type) {
        SubStringPointer out = new SubStringPointer(type, file);
        out.forceExport = forceExport;
        return out;
    }

    void setText(String textIn) {
        text.setString(textIn);
    }

    @Override
    boolean isValid() {
        return (text.isValid() && !text.print().equals("")) || forceExport;
    }

    @Override
    void export(ModExporter out) throws IOException {
        if (isValid()) {
            if (out.getExportMod().isFlag(Mod.Mod_Flags.STRING_TABLED)) {
                data.setData(Ln.toByteArray(out.getExportMod().addOutString(text.string, file), 4));
                data.export(out);
            } else {
                if (text.isValid()) {
                    text.export(out);
                } else if (forceExport) {
                    data.setData(0, shortNull ? 1 : 4); // If short null 1, else 4
                    data.export(out);
                }
            }
        }
    }

    @Override
    void parseData(LImport in, Mod srcMod) throws BadRecord, DataFormatException, BadParameter {
        data.parseData(in, srcMod);
        fetchStringPointers(srcMod);
    }

    void fetchStringPointers(Mod srcMod) {
        if (srcMod.isFlag(Mod_Flags.STRING_TABLED)) {
            Map<SubStringPointer.Files, LImport> streams = srcMod.stringStreams;
            if (data.isValid() && streams.containsKey(file)) {
                int index = Ln.arrayToInt(data.getData());
                if (srcMod.stringLocations.get(file).containsKey(index)) {
                    int offset = srcMod.stringLocations.get(file).get(index);
                    LImport stream = streams.get(file);

                    stream.pos(offset);

                    switch (file) {
                        case STRINGS:
                            int input;
                            StringBuilder string = new StringBuilder();
                            while ((input = stream.read()) != 0) {
                                string.append((char) input);
                            }
                            text.setString(string.toString());
                            break;
                        default:
                            int length = Ln.arrayToInt(stream.extractInts(0, 4));
                            String in = Ln.arrayToString(stream.extractInts(0, length - 1)); // -1 to exclude null end
                            if (!in.equals("")) {
                                text.setString(in);
                            }
                    }
                } else {
                    if (logging() && SPGlobal.debugStringPairing) {
                        boolean nullPtr = true;
                        for (byte b : data.getData()) {
                            if (b != 0) {
                                nullPtr = false;
                                break;
                            }
                        }
                        if (!nullPtr) {
                            logMod(srcMod, "", file + " pointer " + Ln.printHex(data.getData(), true, false) + " COULD NOT BE PAIRED");
                        }
                    }
                    data.setData(0, 1); // Invalidate data to stop export
                }
            }
        } else {
            text.setString(Ln.arrayToString(data.getData()));
        }
    }

    @Override
    public String print() {
        if (text.isValid()) {
            return text.print();
        } else {
            return "<NO TEXT>";
        }
    }

    @Override
    int getContentLength(boolean isStringTabled) {
        if (isValid()) {
            if (isStringTabled) {
                return 4; // length of 4
            } else if (text.isValid()) {
                return text.getContentLength(isStringTabled);
            }
        }

        return shortNull ? 1 : 4; // returning null ptr length
    }

    @Override
    int getTotalLength(boolean isStringTabled) {
        if (isValid() || forceExport) {
            return getContentLength(isStringTabled) + getHeaderLength();
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.text);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubStringPointer other = (SubStringPointer) obj;
        return Objects.equals(this.text, other.text);
    }

    /**
     * Merges straight SubStringPointers with logging capabilities. Accesses the
     * SubString merge code for merging the text.
     *
     * @param no The new SubStringPointer to be merged in.
     * @param bo The base SubStringPointer, to prevent base data from being
     *           re-merged.
     * @return The modified SubStringPointer.
     */
    @Override
    public SubRecord merge(SubRecord no, SubRecord bo) {
        SubStringPointer s = this;
        if (!(no == null && bo == null && (no instanceof SubStringPointer) && (bo instanceof SubStringPointer))) {
            final SubStringPointer newstring = (SubStringPointer) no;
            final SubStringPointer basestring = (SubStringPointer) bo;
            s.text.merge(newstring.text, basestring.text);
        }
        return s;
    }

    /*
     * SkyBash methods.
     */

    enum Files {

        STRINGS, ILSTRINGS, DLSTRINGS
    }

}
