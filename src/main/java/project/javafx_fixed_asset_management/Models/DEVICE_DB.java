package project.javafx_fixed_asset_management.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DEVICE_DB  extends  CONNECT_DB{
    String modelId ;
    String deviceStatus ;
    String yearUsed ;
    String yearManufacture ;
    Date price ;
    float percentDamage;

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

    public Date getPrice() {
        return price;
    }

    public void setPrice(Date price) {
        this.price = price;
    }

    public float getPercentDamage() {
        return percentDamage;
    }

    public void setPercentDamage(float percentDamage) {
        this.percentDamage = percentDamage;
    }

    /*    public DEVICE_DB() {
    }

    public DEVICE_DB(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName) {
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }


    public int insert_device(String deviceId, String modelId, String deviceStatus, String yearUsed, String yearManufacture, float price, float percentDamage) {
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO tbDevice(deviceId, modelId, deviceStatus, yearUsed, yearManufacture, price, percentDamage) VALUES(?, ?, ?, ? , ? ,?, ? )";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, deviceId);
            pstmt.setString(2, modelId);
            pstmt.setString(3, deviceStatus);
            pstmt.setString(4, yearUsed + "");
            pstmt.setString(5, yearManufacture + "");
            pstmt.setString(6, price +"");
            pstmt.setString(7, percentDamage +"");
            pstmt.executeUpdate();
            System.out.println("Insert DEVICE succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - insert_device - DEVICE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }


    public int update_device(String deviceId,  String modelId, String deviceStatus, String yearUsed, String yearManufacture, float price, float percentDamage) {
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "UPDATE  tbDevice SET  modelId = ? , deviceStatus = ? , yearUsed = ? , yearManufacture = ? , price = ? , percentDamage = ?    WHERE deviceId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, modelId);
            pstmt.setString(2, deviceStatus);
            pstmt.setString(3, yearUsed );
            pstmt.setString(4, yearManufacture );
            pstmt.setString(5, price + "");
            pstmt.setString(6, percentDamage + "");
            pstmt.setString(7, deviceId);
            pstmt.executeUpdate();
            System.out.println("update DEVICE succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - update_device - DEVICE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }


    public int delete_device(String deviceId) {
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "DELETE FROM tbDevice WHERE deviceId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, deviceId);
            pstmt.executeUpdate();
            System.out.println("DELETE DEVICE succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - delete_unit - DEVICE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }*/
}
