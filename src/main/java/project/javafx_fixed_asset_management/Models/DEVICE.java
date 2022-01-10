package project.javafx_fixed_asset_management.Models;

public class DEVICE {
    String deviceId;

    String modelId;
    String deviceStatus;
    String yearUsed;
    String yearManufacture;
    float price;
    String deviceName;
    float percentDamage;
    String specification;


    // LINKS
    UNIT unit = new UNIT();
    DEVICE_MODEL model = new DEVICE_MODEL();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getSpecification() {
        //  System.out.println(this.specification);
        return specification;
    }

    public void setSpecification(String specification) {

        if (!specification.equals("none"))
            this.specification = specification;

        System.out.println(this.specification + "\n" + this.deviceId);

    }


    ///////////////////////// LINK
    public void setUnitId(String unitId) {
        this.unit.setUnitId(unitId);
    }

    public String getUnitId(    ) {
        return this.unit.getUnitId();
    }

    public String getUnitName() {
        return this.unit.unitName;
    }

    public void setUnitName(String unitName) {
        this.unit.setUnitName(unitName);
    }

    public String getModelName() {
        return this.model.getModelName();
    }

    public void setModelName(String modelName) {
        this.model.setModelName(modelName);
    }


    ////////////////////////////////////

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPercentDamage() {
        return percentDamage;
    }

    public void setPercentDamage(float percentDamage) {
        this.percentDamage = percentDamage;
    }
}
