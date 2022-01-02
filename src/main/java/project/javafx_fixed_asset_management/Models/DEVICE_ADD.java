package project.javafx_fixed_asset_management.Models;

public class DEVICE_ADD extends DEVICE {
    int quantityDevice;


    public DEVICE_ADD() {

    }

    public DEVICE_ADD(int quantity, DEVICE device) {
        this.setPercentDamage(device.getPercentDamage());
        this.setDeviceId(device.getDeviceId());
        this.setDeviceName(device.getDeviceName());
        this.setQuantityDevice(quantity);
        this.setDeviceStatus(device.getDeviceStatus());
        this.setModelId(device.getModelId());
        this.setUnitId(device.getUnitId());
        this.setDeviceName(device.getDeviceName());
        this.setYearUsed(device.getYearUsed());
        this.setPrice(device.getPrice());
        this.setSpecification(device.getSpecification());
        this.setYearManufacture(device.getYearManufacture());
    }

    public int getQuantityDevice() {
        return quantityDevice;
    }

    public void setQuantityDevice(int quantityDevice) {
        this.quantityDevice = quantityDevice;
    }
}
