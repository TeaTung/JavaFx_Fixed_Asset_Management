package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class AddNewContractDialogController {
    @FXML
    Button addNewDeviceBtn;

    @FXML
    TextField idContractTF;

    @FXML
    TextField idSupplierTF;

    @FXML
    DatePicker importDateDP;

    @FXML
    Text notifyLabel;

    @FXML
    TextField supplierAddressTF;

    @FXML
     TextField supplierNameTF;

    @FXML
    TextArea termTA;

    @FXML
    void addNewDeviceButtonAction(ActionEvent event) {

    }

}
