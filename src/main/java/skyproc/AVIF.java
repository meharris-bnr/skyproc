package skyproc;

import java.util.ArrayList;

/**
 * Actor value records and perk trees.
 *
 * @author Justin Swanson
 */
public class AVIF extends MajorRecordDescription {

    // Static prototypes and definitions
    static final SubPrototype AVIFproto = new SubPrototype(MajorRecordDescription.descProto) {
        @Override
        protected void addRecords() {
            add(SubString.getNew("ANAM", true));
            add(new SubData("CNAM"));
            add(new SubData("AVSK"));
            add(new SubList<>(new PerkReference(new SubPrototype() {
                @Override
                protected void addRecords() {
                    add(new SubForm("PNAM"));
                    forceExport("PNAM");
                    add(new SubInt("FNAM"));
                    add(new SubInt("XNAM"));
                    add(new SubInt("YNAM"));
                    add(new SubFloat("HNAM"));
                    add(new SubFloat("VNAM"));
                    add(new SubForm("SNAM"));
                    add(new SubList<>(new SubInt("CNAM")));
                    add(new SubInt("INAM"));
                }
            })));
        }
    };

    // Common Functions
    AVIF() {
        super();
        subRecords.setPrototype(AVIFproto);
    }

    @Override
    ArrayList<String> getTypes() {
        return Record.getTypeList("AVIF");
    }

    @Override
    Record getNew() {
        return new AVIF();
    }


    public String getAbbreviation() {
        return subRecords.getSubString("ANAM").print();
    }

    // Get/Set

    /**
     * @param abbr
     */
    public void setAbbreviation(String abbr) {
        subRecords.setSubString("ANAM", abbr);
    }


    public ArrayList<PerkReference> getPerkReferences() {
        return subRecords.getSubList("PNAM").toPublic();
    }

    @Override
    public MajorRecord merge(MajorRecord no, MajorRecord bo) {
        super.merge(no, bo);
        AVIF a = this;
        if (!(no == null && bo == null && (no instanceof AVIF) && (bo instanceof AVIF))) {
            final AVIF na = (AVIF) no;
            final AVIF ba = (AVIF) bo;
            SubRecords sList = a.subRecords;
            SubRecords nsList = na.subRecords;
            SubRecords bsList = ba.subRecords;
            for (SubRecord s : sList) {
                s.merge(nsList.get(s.getType()), bsList.get(s.getType()));
            }
        }
        return a;
    }

    /**
     * A structure that represents a perk in a perktree
     */
    public static final class PerkReference extends SubShellBulkType {

        PerkReference(SubPrototype proto) {
            super(proto, false);
        }

        @Override
        boolean isValid() {
            return true;
        }

        @Override
        SubRecord getNew(String type) {
            return new PerkReference(getPrototype());
        }

        @Override
        Record getNew() {
            return new PerkReference(getPrototype());
        }

        /**
         * @return
         */
        public FormID getPerk() {
            return subRecords.getSubForm("PNAM").getForm();
        }

        /**
         * @param id
         */
        public void setPerk(FormID id) {
            subRecords.setSubForm("PNAM", id);
        }

        /**
         * @return
         */
        public int getX() {
            return subRecords.getSubInt("XNAM").get();
        }

        /**
         * @param x
         */
        public void setX(int x) {
            subRecords.setSubInt("XNAM", x);
        }

        /**
         * @return
         */
        public int getY() {
            return subRecords.getSubInt("YNAM").get();
        }

        /**
         * @param y
         */
        public void setY(int y) {
            subRecords.setSubInt("YNAM", y);
        }

        /**
         * @return
         */
        public float getHorizontalPos() {
            return subRecords.getSubFloat("HNAM").get();
        }

        /**
         * @param horiz
         */
        public void setHorizontalPos(float horiz) {
            subRecords.setSubFloat("HNAM", horiz);
        }

        /**
         * @return
         */
        public float getVerticalPos() {
            return subRecords.getSubFloat("VNAM").get();
        }

        /**
         * @param vert
         */
        public void setVerticalPos(float vert) {
            subRecords.setSubFloat("VNAM", vert);
        }

        /**
         * @return
         */
        public FormID getSkill() {
            return subRecords.getSubForm("SNAM").getForm();
        }

        /**
         * @param skill
         */
        public void setSkill(FormID skill) {
            subRecords.setSubForm("SNAM", skill);
        }

        /**
         * @return
         */
        public ArrayList<Integer> getPointers() {
            return subRecords.getSubList("CNAM").toPublic();
        }

        /**
         * @deprecated modifying the ArrayList will now directly
         * affect the record.
         */
        public void clearPointers() {
            subRecords.getSubList("CNAM").clear();
        }

//	/**
//	 * @deprecated modifying the ArrayList will now directly
//	 * affect the record.
//	 * @param index
//	 */
//	public void addPointer(int index) {
//	    subRecords.getSubList("CNAM").add(index);
//	}

//	/**
//	 * @param ref
//	 */
//	public void addPointer(PerkReference ref) {
//	    addPointer(ref.getIndex());
//	}

        /**
         * @return
         */
        public int getIndex() {
            return subRecords.getSubInt("INAM").get();
        }

        /**
         * @param index
         */
        public void setIndex(int index) {
            subRecords.setSubInt("INAM", index);
        }
    }
}
