package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class TransferDevicesScreenController {
    @FXML
    Button addBtn;

    @FXML
    Button removeBtn;

    @FXML
    Button backBtn;

    @FXML
    Button transBtn;

    @FXML
    Button chooseBtn;

    @FXML
    TextField departmentIdTF;

    @FXML
    TextField departmentTf;

    @FXML
    public void addDeviceButtonAction (ActionEvent event) {

    }

    @FXML
    public void removeDeviceButtonAction (ActionEvent event) {

    }

    @FXML
    public void chooseDepartmentButtonAction (ActionEvent event) {

    }

    @FXML
    public void backButtonAction (ActionEvent event) {
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
    public void transButtonAction (ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/TransferScreen/confirm_transfer_devices_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 550, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }
}
