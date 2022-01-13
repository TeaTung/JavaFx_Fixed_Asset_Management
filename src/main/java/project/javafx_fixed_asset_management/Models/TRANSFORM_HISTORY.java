package project.javafx_fixed_asset_management.Models;

public class TRANSFORM_HISTORY extends TRANSFER {

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    String transferDate;
    String transferId;
    int quantityDevice;
    String department;
    String deviceStatus;
    String deviceName;
    String percentDamage;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    String deviceId;


    public String getPercentDamage() {
        return percentDamage;
    }

    public void setPercentDamage(String percentDamage) {
        this.percentDamage = percentDamage;
    }


    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    String departmentId;

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public void setDepartment(String department) {
        this.department = department;
    }


    public int getQuantityDevice() {
        return quantityDevice;
    }

    public void setQuantityDevice(int quantityDevice) {
        this.quantityDevice = quantityDevice;
    }

}
