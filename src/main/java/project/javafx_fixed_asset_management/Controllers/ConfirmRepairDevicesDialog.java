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

public class ConfirmRepairDevicesDialog {
    @FXML
    Label repairingDecisionIdLbl;

    @FXML
    Label priceLbl;

    @FXML
    Label repairingCompanyLbl;

    @FXML
    Label repairingDateLbl;

    @FXML
    Button confirmButton;

    @FXML
    Button backBtn;

    public void backButtonAction (ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/RepairScreen/confirm_repair_devices_dialog.fxml"));
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
    public void confirmButtonAction (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Repair Device");
        alert.setHeaderText("Are you sure to repair devices");
        alert.setContentText("Devices below will be repaired: \n");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }
}
