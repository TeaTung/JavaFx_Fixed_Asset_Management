package project.javafx_fixed_asset_management.Models;

import java.util.List;

public class TRANSFER {
    String transferId;
    List<String> listDevice;
    String transferDate;
    String DepartmentId;
    String Department;
    String DeviceID;



    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }



    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getTransformId() {
        return transferId;
    }

    public void setTransformId(String transferId) {
        this.transferId = transferId;
    }

    public List<String> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<String> listDevice) {
        this.listDevice = listDevice;
    }

    public String getTransformDate() {
        return transferDate;
    }

    public void setTransformDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }
}
