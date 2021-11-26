package project.javafx_fixed_asset_management.Models;

public class UNIT {

    String unitId;
    String unitName;
    String note;
//
//
//    public UNIT_DB() {
//    }
//
//    public UNIT_DB(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName) {
//        super(ServerName, PortNumber, UserName, pwd, DatabaseName);
//    }
//
//    public int insert_unit(String unitId, String unitName, String note) {
//        int result = 1;
//        try {
//            Connection con = this.getConnection();
//            String sql_query = "INSERT INTO tbUnit(UnitId, UnitName, Note) VALUES(?, ?, ?)";
//            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setString(1, unitId);
//            pstmt.setString(2, unitName);
//            pstmt.setString(3, note);
//            pstmt.executeUpdate();
//            System.out.println("Insert UNIT succeed");
//        } catch (SQLException err) {
//            System.out.println("Lỗi hệ thống - insert_unit - UNIT");
//            err.printStackTrace();
//            result = 0;
//        }
//        return result;
//    }
//
//    public int delete_unit(String unitId) {
//        int result = 1;
//        try {
//            Connection con = this.getConnection();
//            String sql_query = "DELETE FROM tbUnit WHERE UnitId = ?";
//            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setString(1, unitId);
//            pstmt.executeUpdate();
//            System.out.println("DELETE UNIT succeed");
//        } catch (SQLException err) {
//            System.out.println("Lỗi hệ thống - delete_unit - UNIT");
//            err.printStackTrace();
//            result = 0;
//        }
//        return result;
//    }
//
//    public int update_unit(String unitId, String unitName, String note){
//        int result = 1;
//        try {
//            Connection con = this.getConnection();
//            String sql_query = "UPDATE tbUnit SET  UnitName = ?, Note = ? WHERE  UnitId = ? ";
//            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setString(1, unitName);
//            pstmt.setString(2, note);
//            pstmt.setString(3, unitId);
//            pstmt.executeUpdate();
//            System.out.println("Uppdate UNIT succeed");
//        } catch (SQLException err) {
//            System.out.println("Lỗi hệ thống - update_unit - UNIT");
//            err.printStackTrace();
//            result = 0;
//        }
//        return result;
//    }


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
