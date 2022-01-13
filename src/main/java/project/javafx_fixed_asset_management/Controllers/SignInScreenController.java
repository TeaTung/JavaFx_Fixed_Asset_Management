package project.javafx_fixed_asset_management.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class SignInScreenController {
    @FXML
    Hyperlink forgotPasswordLabel;

    @FXML
    Button loginBtn;

    @FXML
    Label noticeLabel;

    @FXML
    PasswordField passwordTF;

    @FXML
    TextField usernameTF;

    @FXML
    void forgotPasswordLabelAction(ActionEvent event) {

    }

    @FXML
    void loginBtnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HomeScreen/Manager/manager_home_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loginBtnAdminAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AdminHomeScreen/admin_home_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setScene(scene);
        stage.show();
    }

}

