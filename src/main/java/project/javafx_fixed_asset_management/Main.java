package project.javafx_fixed_asset_management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AuthenticationScreen/SignIn/sign_in_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setTitle("Fixed Asset Management");
        stage.setScene(scene);
        stage.show();

    }
}