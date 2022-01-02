package project.javafx_fixed_asset_management.Models;

import java.time.LocalDate;
import java.util.List;

public class TRANSFORM {
    String transformId;
    String department;
    List<String> listDevice;

    public List<String> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<String> listDevice) {
        this.listDevice = listDevice;
    }

    public String getTransformId() {
        return transformId;
    }

    public void setTransformId(String transformId) {
        this.transformId = transformId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
