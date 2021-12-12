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
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class AddNewDeviceDialogController {
    @FXML
    Button nextBtn;

    @FXML
    TextField ebitdaTF;

    @FXML
    DatePicker manufactureDateDP;

    @FXML
    TextField nameTF;

    @FXML
    TextField priceTF;

    @FXML
    TextArea specificationTA;

    @FXML
    TextField statusTF;

    @FXML
    TextField typeTF;

    @FXML
    TextField unitTF;

    @FXML
    DatePicker usingDateDP;

    @FXML
    void nextButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/AddNewDeviceDialog/add_new_contract_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1018, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }
}





