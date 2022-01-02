package project.javafx_fixed_asset_management.Models;

import java.util.Date;

public class INVENTORY {
    String inventoryId;
    String deviceId;
    int usableValue;
    Date InventoryDate;

    DEVICE device = new DEVICE();
    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public void setDeviceName(String deviceName) {
        this.device.setDeviceName(deviceName);
    }

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

    public Date getInventoryDate() {
        return InventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        InventoryDate = inventoryDate;
    }
}
