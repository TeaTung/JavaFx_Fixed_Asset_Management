package project.javafx_fixed_asset_management.Models;

import java.util.Date;

public class DELIVERY_NOTE {
    String deliveryId;
    String deviceId;
    String departmentId;
    Date deliveryDate;

    DEVICE device = new DEVICE();
    DEPARTMENT department = new DEPARTMENT();
    //

    public String getDepartmentName() {
        return this.department.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.department.setDepartmentName(departmentName);
    }

    //
    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public void setDeviceName(String deviceName) {
        this.device.setDeviceName(deviceName);
    }

    public String getModelId() {
        return this.getDeviceId();
    }

    public void setModelId(String modelId) {
        this.setModelId(modelId);
    }

    public String getDeviceStatus() {
        return this.device.getDeviceStatus();
    }

    public void setDeviceStatus(String deviceStatus) {
        this.device.setDeviceStatus(deviceStatus);
    }

    public String getYearUsed() {
        return this.device.getYearUsed();
    }

    public void setYearUsed(String yearUsed) {
        this.device.setYearUsed(yearUsed);
    }

    public String getYearManufacture() {
        return this.device.getYearManufacture();
    }

    public void setYearManufacture(String yearManufacture) {
        this.device.setYearManufacture(yearManufacture);
    }

    public float getPrice() {
        return this.device.getPrice();
    }

    public void setPrice(float price) {
        this.device.setPrice(price);
    }

    public float getPercentDamage() {
        return this.device.getPercentDamage();
    }

    public void setPercentDamage(float percentDamage) {
        this.device.setPercentDamage(percentDamage);
    }

    public String getSpecification() {
        return this.device.getSpecification();
    }

    public void setSpecification(String specification) {

        if (!specification.equals("none"))
            this.device.setSpecification(specification);
    }

    //
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
