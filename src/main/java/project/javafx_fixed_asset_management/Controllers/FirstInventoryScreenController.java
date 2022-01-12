package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class FirstInventoryScreenController {
    @FXML
    TableView<?> inventoryPeopleTV;

    @FXML
    TextField nameTF;

    @FXML
    TextField departmentTF;

    @FXML
    DatePicker dateOfInventoryDtp;

    @FXML
    Button addBtn;

    @FXML
    Button deleteBtn;

    @FXML
    Button backBtn;

    @FXML
    Button continueBtn;

    @FXML
    public void addPeopleButtonAction (ActionEvent event) {

    }
    @FXML
    public void deletePeopleButtonAction (ActionEvent event) {

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
