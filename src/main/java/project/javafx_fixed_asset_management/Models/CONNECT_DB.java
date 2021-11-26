package project.javafx_fixed_asset_management.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class CONNECT_DB {
    private String ServerName;
    private int PortNumber;
    private String UserName;
    private String pwd;
    private String DatabaseName;
    private Connection database_connection;

    // Kích hoạt tài khỏan SA trên SQL Server
    public CONNECT_DB() {
        this.setAll("localhost\\HELLO",
                1433,
                "sa",
                "1234",
                "FIXED_ASSETS_DATABASE");
    }

    public CONNECT_DB(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName) {
        /* Constructor tạo object kết nối đến database
           ServerName: Mở trong MS-SQL Server, trước khi kết nối có phần Server Name bảng chọn
           PortNumber: Kết nối TCP/IP
           UserName: tên user
           pwd: password kết nối
           DatabaseName: Tên database kết nối tới
         */

        this.setAll(ServerName, PortNumber, UserName, pwd, DatabaseName);
    }

    void setAll(String ServerName, int PortNumber, String UserName, String pwd, String DatabaseName) {
        // GET-SET
        this.UserName = UserName;
        this.pwd = pwd;
        this.ServerName = ServerName;
        this.PortNumber = PortNumber;
        this.DatabaseName = DatabaseName;
    }

    public Connection getConnection() {
        // Tạo kết nối đến database


        String urlConnection = "jdbc:sqlserver://"
                + this.ServerName + ";"
                + "user=" + this.UserName + ";"
                + "password=" + this.pwd + ";"
                + "databaseName=" + this.DatabaseName + ";";

        String dbURL = "jdbc:sqlserver://localhost\\HELLO;user=sa;password=1234;databaseName=FIXED_ASSETS_DATABASE";
        try {
            this.database_connection = DriverManager.getConnection(urlConnection);
        } catch (SQLException err) {
            System.out.println("Can't not connect, check login information");
        }
        return this.database_connection;
    }
}
