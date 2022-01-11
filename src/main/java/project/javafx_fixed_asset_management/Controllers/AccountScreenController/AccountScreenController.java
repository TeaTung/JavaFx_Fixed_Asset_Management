package project.javafx_fixed_asset_management.Controllers.AccountScreenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class AccountScreenController {
    @FXML
    Label nameLbl;

    @FXML
    Label birthdayLbl;

    @FXML
    Label phoneNumberLbl;

    @FXML
    Label addressLbl;

    @FXML
    Label departmentNameLbl;

    @FXML
    Button backBtn;

    @FXML
    Button updateBtn;

    @FXML
    public void backButtonAction (ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void updateButtonAction (ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AccountScreen/update_account_information_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 550, 450);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setTitle("Update profile");
        stage.setScene(scene);
        stage.show();
    }

}
