package project.javafx_fixed_asset_management.Models;

import java.time.LocalDate;

public class REPAIR {
    String fixId;
    String deviceId;
    String company;
    float price;

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    String repairDate;


    public String getFixId() {
        return fixId;
    }

    public void setFixId(String fixId) {
        this.fixId = fixId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}
