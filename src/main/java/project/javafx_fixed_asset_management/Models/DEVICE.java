package project.javafx_fixed_asset_management.Models;

import java.util.Date;

public class DEVICE {
    String modelId ;
    String deviceStatus ;
    String yearUsed ;
    String yearManufacture ;
    Date price ;
    float percentDamage;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getYearUsed() {
        return yearUsed;
    }

    public void setYearUsed(String yearUsed) {
        this.yearUsed = yearUsed;
    }

    public String getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(String yearManufacture) {
        this.yearManufacture = yearManufacture;
    }

    public Date getPrice() {
        return price;
    }

    public void setPrice(Date price) {
        this.price = price;
    }

    public float getPercentDamage() {
        return percentDamage;
    }

    public void setPercentDamage(float percentDamage) {
        this.percentDamage = percentDamage;
    }
}
