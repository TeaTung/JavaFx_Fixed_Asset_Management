package project.javafx_fixed_asset_management.Models;

import java.util.Date;

public class INVENTORY {
    String inventoryId;
    String deviceId;
    int usableValue;
    Date InvertoryDate;

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getUsableValue() {
        return usableValue;
    }

    public void setUsableValue(int usableValue) {
        this.usableValue = usableValue;
    }

    public Date getInvertoryDate() {
        return InvertoryDate;
    }

    public void setInvertoryDate(Date invertoryDate) {
        InvertoryDate = invertoryDate;
    }
}
