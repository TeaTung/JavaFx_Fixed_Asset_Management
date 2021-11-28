package project.javafx_fixed_asset_management.Models;

public class UNIT {

    String unitId;
     String unitName;
     String note;

    @Override
    public String toString() {
        return ("\n" + unitId + " : " + unitName + " : " + note);
    }


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
