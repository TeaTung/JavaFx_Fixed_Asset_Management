package project.javafx_fixed_asset_management.Models;

import java.util.List;

public class TRANSFORM {
    String transformId;
    List<String> listDevice;
    String transformDate;
    String DepartmentId;
    String Department;

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getTransformId() {
        return transformId;
    }

    public void setTransformId(String transformId) {
        this.transformId = transformId;
    }

    public List<String> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<String> listDevice) {
        this.listDevice = listDevice;
    }

    public String getTransformDate() {
        return transformDate;
    }

    public void setTransformDate(String transformDate) {
        this.transformDate = transformDate;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }
}
