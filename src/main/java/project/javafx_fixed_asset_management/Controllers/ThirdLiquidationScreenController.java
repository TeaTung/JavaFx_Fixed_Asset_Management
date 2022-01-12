package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;
import java.util.Optional;

public class ThirdLiquidationScreenController {

    @FXML
    Button backBtn;

    @FXML
    Button finishBtn;

    @FXML
    TableView<?> inventoryDeviceTB;

    @FXML
    Button liquidateBtn;

    @FXML
    void backButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/LiquidationScreen/second_liquidation_screen.fxml"));
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
    void finishBtnAction(ActionEvent event) {

    }

    @FXML
    void liquidateBtnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Liquidate Device");
        alert.setHeaderText("Are you sure to liquidate device");
        alert.setContentText("Devices below will be liquidated: \n");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

}
