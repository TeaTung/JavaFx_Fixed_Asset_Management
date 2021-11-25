package project.javafx_fixed_asset_management.Models;

import java.time.LocalDate;
import java.util.Date;

public class Device {
    private int id;
    private String deviceName;
    private String typeOfDevice;
    private int price;
    private String unit;
    private String specification;
    private LocalDate dateOfManufacturing;
    private LocalDate dateOfUsing;
    private String status;
    private double ebitda;

    public Device(int id, String deviceName, String typeOfDevice, int price, String unit, String specification, LocalDate dateOfManufacturing, LocalDate dateOfUsing, String status, double ebitda) {
        this.id = id;
        this.deviceName = deviceName;
        this.typeOfDevice = typeOfDevice;
        this.price = price;
        this.unit = unit;
        this.specification = specification;
        this.dateOfManufacturing = dateOfManufacturing;
        this.dateOfUsing = dateOfUsing;
        this.status = status;
        this.ebitda = ebitda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTypeOfDevice() {
        return typeOfDevice;
    }

    public void setTypeOfDevice(String typeOfDevice) {
        this.typeOfDevice = typeOfDevice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public LocalDate getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public void setDateOfManufacturing(LocalDate dateOfPublication) {
        this.dateOfManufacturing = dateOfPublication;
    }

    public LocalDate getDateOfUsing() {
        return dateOfUsing;
    }

    public void setDateOfUsing(LocalDate dateOfUsing) {
        this.dateOfUsing = dateOfUsing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getEbitda() {
        return ebitda;
    }

    public void setEbitda(double ebitda) {
        this.ebitda = ebitda;
    }
}
