package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;
import java.util.Optional;

public class ConfirmTranferDevicesDialogController {
    @FXML
    Label transferIdLbl;

    @FXML
    Label departmentLbl;

    @FXML
    Button confirmButton;

    @FXML
    public void confirmButtonAction (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transfer Device");
        alert.setHeaderText("Are you sure to transfer device");
        alert.setContentText("Devices below will be transferred: \n");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }
}