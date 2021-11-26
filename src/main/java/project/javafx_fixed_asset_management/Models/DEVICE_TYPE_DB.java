package project.javafx_fixed_asset_management.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DEVICE_TYPE_DB extends  CONNECT_DB{
    String typeId;
    String typeName;
    String note;

    public DEVICE_TYPE_DB() {
    }

    public DEVICE_TYPE_DB(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName) {
        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    public int  insert_device_type(String typeId, String typeName, String note){
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "INSERT INTO tbDeviceType(TypeId, TypeName, Note) VALUES(?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, typeId);
            pstmt.setString(2, typeName);
            pstmt.setString(3, note);
            pstmt.executeUpdate();
            System.out.println("Insert DEVICE_TYPE succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - insert_device_type - DEVICE_TYPE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }

    public int  update_device_type(String typeId, String typeName, String note){
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


    public int delete_device_type(String typeId) {
        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = "DELETE FROM tbDeviceType WHERE typeId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, typeId);
            pstmt.executeUpdate();
            System.out.println("DELETE DEVICE_TYPE succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống - delete_unit - DEVICE_TYPE");
            err.printStackTrace();
            result = 0;
        }
        return result;
    }


}
