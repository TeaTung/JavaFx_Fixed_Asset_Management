package project.javafx_fixed_asset_management.Controllers.AccountScreenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.PROFILE;

import java.io.IOException;
import java.util.Optional;

public class UpdateAccountInformationDialogController {
    @FXML
    TextField nameTF;

    @FXML
    TextField birthdayTF;

    @FXML
    TextField phoneNumberTF;

    @FXML
    TextField DepartmentTF;

    @FXML
    TextField addressTF;

    @FXML
    Button backBtn;

    @FXML
    Button confirmBtn;

    PROFILE userProfile;

    public void backButtonAction (ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AccountScreen/account_screen.fxml"));
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

        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }

    public void confirmButtonAction (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Account Information");
        alert.setHeaderText("Are you sure to update information");
        alert.setContentText("Account below will be updated: \n");
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public void init (PROFILE profile) {
        userProfile = profile;

    }

    public void setLabelValue (PROFILE profile) {
        nameTF.setText(profile.getName());
    }
}
