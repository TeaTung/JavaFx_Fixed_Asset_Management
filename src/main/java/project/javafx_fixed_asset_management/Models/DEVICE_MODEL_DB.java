package project.javafx_fixed_asset_management.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DEVICE_MODEL_DB extends CONNECT_DB {

    String modelId;
    String unitId;
    String typeId;
    String specification;
    int quantity;
    String modelName;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }



    /*public DEVICE_MODEL_DB() {
    }


    public int insert_device_model(String modelId, String unitId, String typeId, String specification, int quantity, String modelName) {
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO tbDeviceModel(ModelId, UnitId, TypeId, Specification, Quantity, ModelName) VALUES(?, ?, ?, ? , ? ,?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, modelId);
            pstmt.setString(2, unitId);
            pstmt.setString(3, typeId);
            pstmt.setString(4, specification);
            pstmt.setString(5, quantity + "");
            pstmt.setString(6, modelName);
            pstmt.executeUpdate();
            System.out.println("Insert DEVICE_TYPE succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - insert_device_type - DEVICE_TYPE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }


    public int update_device_type(String typeId, String typeName, String note) {
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "UPDATE  tbDeviceType SET  typeName =? , note = ? WHERE typeId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, typeName);
            pstmt.setString(2, note);
            pstmt.setString(3, typeId);
            pstmt.executeUpdate();
            System.out.println("update DEVICE_TYPE succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - update_device_type - DEVICE_TYPE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }


    public int delete_device_type(String modelId) {
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "DELETE FROM tbDeviceModel WHERE modelId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, modelId);
            pstmt.executeUpdate();
            System.out.println("DELETE DEVICE_MODEL succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - delete_unit - DEVICE_MODEL");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }
*/
}
