package project.javafx_fixed_asset_management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.UNIT;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HomeScreen/Manager/manager_home_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Fixed Asset Management");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        //launch();
        DATABASE_DAO<UNIT> unitdatabase_dao = new DATABASE_DAO<>(UNIT.class);
        unitdatabase_dao.insert("insert into tbUnit( unitId) values (? )", "sieunhan");
        List<UNIT> list = unitdatabase_dao.selectList("select * from tbUnit");
        System.out.println(Arrays.toString(list.toArray()));

    }
}