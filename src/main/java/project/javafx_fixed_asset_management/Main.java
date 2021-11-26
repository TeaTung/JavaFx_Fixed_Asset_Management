package project.javafx_fixed_asset_management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Models.CRUD_DATABASE;

import java.io.IOException;

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
        launch();
        CRUD_DATABASE.crud_database.select("", new String[]{},
                "tbUnit","", "", "" );

    }
}