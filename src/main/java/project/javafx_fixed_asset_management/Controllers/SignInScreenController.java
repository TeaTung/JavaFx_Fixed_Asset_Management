package project.javafx_fixed_asset_management.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.*;
import project.javafx_fixed_asset_management.Controllers.LiquidationScreenControllers.ThirdLiquidationScreenController;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.MessageDigest;

import java.io.IOException;

public class SignInScreenController implements Initializable {

    @FXML
    ImageView closeBtn;

    @FXML
    Button loginBtn;

    @FXML
    Label noticeLabel;

    @FXML
    PasswordField passwordTF;

    @FXML
    TextField usernameTF;


    @FXML
    void closeBtnHandler(MouseEvent event) {
        FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION, "Are you sure to exit the application? " , ButtonType.YES, ButtonType.CANCEL);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }


    @FXML
    void loginBtnAction(ActionEvent event) {
        if(verifyInput()) {
            String username = usernameTF.getText();
            String password = passwordTF.getText();
            AUTHENTICATION authentication = verifyLogin(username, password);
            if(authentication == null){
                noticeLabel.setVisible(true);
                noticeLabel.setText("Invalid username or password");
                return;
            }

            if (authentication.getAccountType().trim().equals("Staff")) {
                staffScreenPush(event, authentication.getAccountId());
                return;
            } else if (authentication.getAccountType().trim().equals("Admin")) {
                adminScreenPush(event);
                return;
            } else {
                noticeLabel.setVisible(true);
                noticeLabel.setText("Invalid username or password");
                return;
            }
        }
        return;
    }

    AUTHENTICATION verifyLogin(String username, String password) {
        try {
            String md5Password = md5(password);
            var authentication = new DATABASE_DAO<>(AUTHENTICATION.class);

            var auth = authentication.selectOne("SELECT * FROM TBACCOUNT WHERE EMAIL = ? AND PASSWORD = ?", username, md5Password);
            return auth;
        } catch (Exception e) {

        }
        return null;
    }

    boolean     verifyInput() {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?!.* ).{8,15}$", Pattern.CASE_INSENSITIVE);

        Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(usernameTF.getText());
        Matcher matcherPassword = VALID_PASSWORD_REGEX.matcher(passwordTF.getText());

        if(!matcherEmail.find()) {
            noticeLabel.setVisible(true);
            noticeLabel.setText("Invalid email address");
            return false;
        } else if (!matcherPassword.find()) {
            noticeLabel.setVisible(true);
            noticeLabel.setText("Invalid password");
            return false;
        }
        return true;
    }

    String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            System.out.println(hexString.toString());
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    void staffScreenPush(ActionEvent event, String accountId) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HomeScreen/Manager/manager_home_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ManagerHomeScreenController managerHomeScreenController = fxmlLoader.getController();
        managerHomeScreenController.initData(accountId);

        stage.setScene(scene);
        stage.show();
    }

    void adminScreenPush(ActionEvent event) {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noticeLabel.setVisible(false);
        noticeLabel.setTextAlignment(TextAlignment.CENTER);
        noticeLabel.setAlignment(Pos.CENTER);
    }

    
}

