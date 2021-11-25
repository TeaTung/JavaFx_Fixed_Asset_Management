package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class FirstInventoryScreenController {
//    public TableView<Device> deviceTableView;
//
//    public TableColumn<Device, String> deviceNameColumn;
//    public TableColumn<Device, Integer> idColumn;

    @FXML
    public TextField nameTF;
    public TextField departmentTF;

    public DatePicker dateOfInventoryDtp;

    public Button addBtn;
    public Button deleteBtn;
    public Button backBtn;
    public Button continueBtn;


    public void addPeopleButtonAction (ActionEvent event) {

    }

    public void deletePeopleButtonAction (ActionEvent event) {

    }

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

    public void continueButtonAction (ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/second_inventory_screen.fxml"));
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
}
