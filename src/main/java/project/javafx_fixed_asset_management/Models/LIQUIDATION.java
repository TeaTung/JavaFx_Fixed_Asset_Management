package project.javafx_fixed_asset_management.Models;

import java.util.Date;

public class LIQUIDATION {

    String liquidationId;
    String deviceId;
    Date liquidationDate;

    DEVICE device = new DEVICE();
    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public void setDeviceName(String deviceName) {
        this.device.setDeviceName(deviceName);
    }

    public String getLiquidationId() {
        return liquidationId;
    }

    public void setLiquidationId(String liquidationId) {
        this.liquidationId = liquidationId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(Date liquidationDate) {
        this.liquidationDate = liquidationDate;
    }
}

