package skyproc;

import lev.LFlags;
import lev.LImport;
import skyproc.exceptions.BadParameter;
import skyproc.exceptions.BadRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

/**
 * Ingredient Records
 *
 * @author Justin Swanson
 */
public class INGR extends MagicItem {

    // Static prototypes and definitions
    static final SubPrototype INGRproto = new SubPrototype(MagicItem.magicItemProto) {

        @Override
        protected void addRecords() {
            after(new ScriptPackage(), "EDID");
            remove("DESC");
            add(new Model());
            add(new SubForm("YNAM"));
            add(new SubForm("ZNAM"));
            add(new DATA());
            add(new ENIT());
            reposition("EFID");
            add(SubString.getNew("ICON", true));
            add(SubString.getNew("MICO", true));
            add(new SubForm("ETYP"));
        }
    };

    // Common Functions
    INGR() {
        super();
        subRecords.setPrototype(INGRproto);
    }

    @Override
    ArrayList<String> getTypes() {
        return Record.getTypeList("INGR");
    }

    // Enums

    @Override
    Record getNew() {
        return new INGR();
    }


    public ScriptPackage getScriptPackage() {
        return subRecords.getScripts();
    }

    /**
     * @return
     * @deprecated use getModelData()
     */
    public String getModel() {
        return subRecords.getModel().getFileName();
    }

    /**
     * @param path
     * @deprecated use getModelData()
     */
    public void setModel(String path) {
        subRecords.getModel().setFileName(path);
    }

    // Get/set


    public FormID getPickupSound() {
        return subRecords.getSubForm("YNAM").getForm();
    }

    /**
     * @param pickupSound
     */
    public void setPickupSound(FormID pickupSound) {
        subRecords.setSubForm("YNAM", pickupSound);
    }


    public FormID getDropSound() {
        return subRecords.getSubForm("ZNAM").getForm();
    }

    /**
     * @param dropSound
     */
    public void setDropSound(FormID dropSound) {
        subRecords.setSubForm("ZNAM", dropSound);
    }

    DATA getDATA() {
        return (DATA) subRecords.get("DATA");
    }


    public float getWeight() {
        return getDATA().weight;
    }

    /**
     * @param weight
     */
    public void setWeight(float weight) {
        getDATA().weight = weight;
    }


    public int getValue() {
        return getDATA().value;
    }

    /**
     * @param value
     */
    public void setValue(int value) {
        getDATA().value = value;
    }

    ENIT getENIT() {
        return (ENIT) subRecords.get("ENIT");
    }


    public int getBaseCost() {
        return getENIT().baseCost;
    }

    /**
     * @param baseCost
     */
    public void setBaseCost(int baseCost) {
        getENIT().baseCost = baseCost;
    }

    /**
     * @param flag
     * @param on
     */
    public void set(INGRFlag flag, boolean on) {
        getENIT().flags.set(flag.value, on);
    }

    /**
     * @param flag
     * @return
     */
    public boolean get(INGRFlag flag) {
        return getENIT().flags.get(flag.value);
    }


    public String getInventoryIcon() {
        return subRecords.getSubString("ICON").print();
    }

    /**
     * @param filename
     */
    public void setInventoryIcon(String filename) {
        subRecords.setSubString("ICON", filename);
    }


    public String getMessageIcon() {
        return subRecords.getSubString("MICO").print();
    }

    /**
     * @param filename
     */
    public void setMessageIcon(String filename) {
        subRecords.setSubString("MICO", filename);
    }


    public FormID getEquipType() {
        return subRecords.getSubForm("ETYP").getForm();
    }

    /**
     * @param equipType
     */
    public void setEquipType(FormID equipType) {
        subRecords.setSubForm("ETYP", equipType);
    }

    /**
     * @return List of the AltTextures applied.
     * @deprecated use getModelData()
     */
    public ArrayList<AltTextures.AltTexture> getAltTextures() {
        return subRecords.getModel().getAltTextures();
    }


    public Model getModelData() {
        return subRecords.getModel();
    }

    @Override
    public MajorRecord merge(MajorRecord no, MajorRecord bo) {
        super.merge(no, bo);
        INGR i = this;
        if (!(no == null && bo == null && (no instanceof INGR) && (bo instanceof INGR))) {
            final INGR ni = (INGR) no;
            final INGR bi = (INGR) bo;
            SubRecords sList = i.subRecords;
            SubRecords nsList = ni.subRecords;
            SubRecords bsList = bi.subRecords;
            for (SubRecord s : sList) {
                s.merge(nsList.get(s.getType()), bsList.get(s.getType()));
            }
        }
        return i;
    }


    public enum INGRFlag {


        ManualCalc(0),

        Food(1),

        ReferencesPersist(8);
        final int value;

        INGRFlag(int value) {
            this.value = value;
        }
    }

    static class DATA extends SubRecord {

        int value = 0;
        float weight = 0;

        DATA() {
            super();
        }

        @Override
        void export(ModExporter out) throws IOException {
            super.export(out);
            out.write(value);
            out.write(weight);
        }

        @Override
        void parseData(LImport in, Mod srcMod) throws BadRecord, DataFormatException, BadParameter {
            super.parseData(in, srcMod);
            value = in.extractInt(4);
            weight = in.extractFloat();
            logMod(srcMod, "", "Setting DATA:    Weight: " + weight);
        }

        @Override
        SubRecord getNew(String type) {
            return new DATA();
        }

        @Override
        int getContentLength(boolean isStringTabled) {
            return 8;
        }

        @Override
        ArrayList<String> getTypes() {
            return Record.getTypeList("DATA");
        }

        @Override
        public SubRecord merge(SubRecord no, SubRecord bo) {
            DATA d = this;
            if (!(no == null && bo == null && (no instanceof DATA) && (bo instanceof DATA))) {
                final DATA nd = (DATA) no;
                final DATA bd = (DATA) bo;
                Merger.merge(d.value, nd.value, bd.value, getType(), "value");
                Merger.merge(d.weight, nd.weight, bd.weight, getType(), "weight");
            }
            return d;
        }
    }

    static class ENIT extends SubRecord {

        int baseCost = 0;
        LFlags flags = new LFlags(4);

        ENIT() {
            super();
        }

        @Override
        void export(ModExporter out) throws IOException {
            super.export(out);
            out.write(baseCost);
            out.write(flags.export(), 4);
        }

        @Override
        void parseData(LImport in, Mod srcMod) throws BadRecord, DataFormatException, BadParameter {
            super.parseData(in, srcMod);
            baseCost = in.extractInt(4);
            flags.set(in.extract(4));
            logMod(srcMod, "", "Base cost: " + baseCost + ", flags: " + flags);
        }

        @Override
        SubRecord getNew(String type) {
            return new ENIT();
        }

        @Override
        int getContentLength(boolean isStringTabled) {
            return 8;
        }

        @Override
        ArrayList<String> getTypes() {
            return Record.getTypeList("ENIT");
        }

        @Override
        public SubRecord merge(SubRecord no, SubRecord bo) {
            ENIT e = this;
            if (!(no == null && bo == null && (no instanceof ENIT) && (bo instanceof ENIT))) {
                final ENIT ne = (ENIT) no;
                final ENIT be = (ENIT) bo;
                Merger.merge(e.baseCost, ne.baseCost, be.baseCost, getType(), "base cost");
                e.flags = Merger.merge(e.flags, ne.flags, be.flags, getType());
            }
            return e;
        }
    }
}
