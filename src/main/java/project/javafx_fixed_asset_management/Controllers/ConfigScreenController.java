package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.UNIT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class ConfigScreenController implements Initializable {


    //<editor-fold desc="TAB UNIT">
    public TabPane myTabPane;
    public Button backBtn;
    public Button finishButton;
    public TextArea noteTA;
    public TextField nameTF;
    public Button finishDepartmentButton;
    public TextField deptIdTF;
    public TextField nameDeptTF;
    public TextField totalDeviceTF;


    public void backDeviceButtonAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/device_screen.fxml"));

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void finishButtonAction(ActionEvent actionEvent) {
        var id = UUID.randomUUID().toString().substring(0, 10);
        new DATABASE_DAO<>(UNIT.class).insert("insert into tbUnit (unitId, unitName, note) values ( ? , ? ,? )",
                id, nameTF.getText(), noteTA.getText());

        // a alert dialog before leaving
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        alert.setContentText("Update successfully");
        alert.setHeaderText("Message");
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();
        backBtn.fire();
    }

    //</editor-fold>


    //<editor-fold desc="TAB DEVICE MODEL">
    public Button backModelBtn;
    public Button finishModelButton;
    public ComboBox<UNIT> unitModelCB;
    public TextField nameModelTF;


    public void finishModelButton(ActionEvent actionEvent) {

        var id = UUID.randomUUID().toString().substring(0, 10);
        new DATABASE_DAO<>(UNIT.class).insert("insert into tbDeviceModel (modelId, unitId, ModelName) values ( ? , ? ,? )",
                id, unitModelCB.getSelectionModel().getSelectedItem().getUnitId(), nameModelTF.getText());

        // a alert dialog before leaving
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        alert.setContentText("Update successfully");
        alert.setHeaderText("Message");
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();
        backBtn.fire();
    }


    //</editor-fold>

    public TextField nameProviderTF;
    public TextField addressProviderTF;
    public TextField phoneProviderTF;
    public TextField emailProviderTF;
    public Button backProviderBtn;
    public Button finishProviderButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // load for DEVICE MODEL
        var units = FXCollections.observableArrayList(new DATABASE_DAO<>(UNIT.class).selectList("select * from tbUnit "));
        unitModelCB.setItems(units);
        unitModelCB.setConverter(new StringConverter<UNIT>() {
            @Override
            public String toString(UNIT unit) {
                if (unit != null)
                    return unit.getUnitName();
                return "";
            }

            @Override
            public UNIT fromString(String s) {
                return null;
            }
        });
        unitModelCB.getSelectionModel().select(0);
    }

    public void finishProviderActionButton(ActionEvent actionEvent) {

        var id = UUID.randomUUID().toString().substring(0, 10);
        new DATABASE_DAO<>(UNIT.class).insert("insert into tbProvider (providerId, providerName, address , phone , email ) values ( ? , ? ,?, ? ,? )",
                id, nameProviderTF.getText(), addressProviderTF.getText(), phoneProviderTF.getText(), emailProviderTF.getText());

        // a alert dialog before leaving
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        alert.setContentText("Update successfully");
        alert.setHeaderText("Message");
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();
        backBtn.fire();
    }

    public void finishDepartmentButtonOnAction(ActionEvent actionEvent) {
        new DATABASE_DAO<>(DEPARTMENT.class).insert("insert into tbDepartment (departmentID, DepartmentName, totalDevice ) values ( ? , ? , ? )",
                deptIdTF.getText(), nameDeptTF.getText(), totalDeviceTF.getText());

        // a alert dialog before leaving
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        alert.setContentText("Insert successfully");
        alert.setHeaderText("Message");
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();
        backBtn.fire();

    }
}
