
package project.javafx_fixed_asset_management;

import javafx.concurrent.Task;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.SerializationUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Models.CRUD_DATABASE;
import project.javafx_fixed_asset_management.Models.UNIT;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {


    private static final String DB_URL = "jdbc:sqlserver://localhost/FIXED_ASSETS_DATABASE";
    private static final String USER = "sa";
    private static final String PASS = "1234";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HomeScreen/Manager/manager_home_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Fixed Asset Management");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        QueryRunner queryRunner = new QueryRunner();
        DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        try {
            int updatedRecords = queryRunner.update(conn,
                    "UPDATE tbDevice SET tbName=? WHERE unitId=?", "THIS","unit1");
            System.out.println(updatedRecords + " record(s) updated.");
        } finally {
            DbUtils.close(conn);
        }

        System.out.println();
    }

}