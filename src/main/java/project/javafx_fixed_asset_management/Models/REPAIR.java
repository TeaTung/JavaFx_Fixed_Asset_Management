package project.javafx_fixed_asset_management.Models;

import java.util.List;

public class REPAIR {
    String fixId;
    List<String> listDevice;
    String repairDate;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    String deviceId ;

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }


    String company;
    float price;



    public String getFixId() {
        return fixId;
    }

    public void setFixId(String fixId) {
        this.fixId = fixId;
    }

    public List<String> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<String> listDevice) {
        this.listDevice = listDevice;
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
