package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ManagerHomeScreenController {
    @FXML
    Button btnAccount;

    @FXML
    Button btnDevice;

    @FXML
    Button btnHistory;

    @FXML
    Button btnInventory;

    @FXML
    Button btnLiquidation;

    @FXML
    Button btnRepair;

    public void Click(ActionEvent event) throws IOException {
        System.out.println("Clicked");
    }
}
