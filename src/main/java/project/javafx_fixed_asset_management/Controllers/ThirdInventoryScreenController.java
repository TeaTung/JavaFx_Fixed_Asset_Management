package project.javafx_fixed_asset_management.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;
import java.util.Optional;

public class ThirdInventoryScreenController {
    @FXML
    Button finishBtn;

    @FXML
    TableView<?> inventoryDeviceTB;

    @FXML
    Button updateBtn;

    @FXML
    TextField usableValueTF;

    @FXML
    void finishBtnAction(ActionEvent event) {

    }

    @FXML
    void updateBtnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Usable Value Confirmation");
        alert.setHeaderText("Are you sure to update usable value to information below");
        alert.setContentText("Usable value of" + " Device Name " + "Device" + " to" + " percent");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public void backButtonAction (ActionEvent event) {
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

