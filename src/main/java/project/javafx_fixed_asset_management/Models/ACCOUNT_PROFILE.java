package project.javafx_fixed_asset_management.Models;

public class ACCOUNT_PROFILE extends ACCOUNT{
    String name;
    String phoneNumber;
    String address;
    String birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthDay) {
        this.birthday = birthDay;
    }
}
