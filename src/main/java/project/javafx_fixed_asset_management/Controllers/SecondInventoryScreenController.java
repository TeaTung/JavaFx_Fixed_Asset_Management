package project.javafx_fixed_asset_management.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class SecondInventoryScreenController {
    @FXML
    Button addBtn;

    @FXML
    Button continueBtn;

    @FXML
    TextField deviceNameTF;

    @FXML
    TableView<?> deviceTB;

    @FXML
    Text errorLabel;

    @FXML
    TableView<?> inventoryDeviceTB;

    @FXML
    Button removeBtn;

    @FXML
    Button searchBtn;

    @FXML
    void addBtnAction(ActionEvent event) {

    }

    @FXML
    void continueBtnAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/third_inventory_screen.fxml"));
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
    void removeBtnAction(ActionEvent event) {

    }

    @FXML
    void searchBtnAction(ActionEvent event) {

    }

}
