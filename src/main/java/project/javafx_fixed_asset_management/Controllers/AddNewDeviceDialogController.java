package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE_ADD;
import project.javafx_fixed_asset_management.Models.DEVICE_MODEL;
import project.javafx_fixed_asset_management.Models.UNIT;
import project.javafx_fixed_asset_management.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddNewDeviceDialogController implements Initializable {
    public AnchorPane anchorPane;
    @FXML
    TextField quantityTF;

    @FXML
    Button nextBtn;

    @FXML
    TextField percentDamageTF;

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
    TextField unitTF;

    @FXML
    DatePicker usingDateDP;


    @FXML
    ComboBox<DEVICE_MODEL> cbType;


    @FXML
    void nextButtonAction(ActionEvent event) {

        if (!validatedData()) {
            return;
        }

        var id = UUID.randomUUID().toString().substring(0, 10);
        var modelId = ((DEVICE_MODEL) cbType.getSelectionModel().getSelectedItem()).getModelId();
        var specification = specificationTA.getText();
        //var unitId = ((UNIT) cbUnit.getSelectionModel().getSelectedItem()).getUnitId();


        var newDevice = new DEVICE_ADD();
        newDevice.setDeviceId(id);
        newDevice.setModelId(modelId);
        newDevice.setSpecification(specification);
        newDevice.setYearManufacture(manufactureDateDP.getValue().toString());
        newDevice.setYearUsed(usingDateDP.getValue().toString());
        newDevice.setDeviceName(nameTF.getText());
        newDevice.setPrice(Float.parseFloat(priceTF.getText()));
        newDevice.setDeviceStatus(statusTF.getText());
        // newDevice.setUnitId(unitId);
        newDevice.setPercentDamage(Float.parseFloat(percentDamageTF.getText()));
        newDevice.setQuantityDevice(Integer.parseInt(quantityTF.getText()));


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/AddNewDeviceDialog/add_new_contract_dialog.fxml"));

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 924, 431);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setUserData(newDevice);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void comboBoxChanged(ActionEvent actionEvent) {
        var unitName = new DATABASE_DAO<UNIT>(UNIT.class).selectOne("select *  from tbUnit where unitId = ?", cbType.getSelectionModel().getSelectedItem().getUnitId());
        unitTF.setText(unitName.getUnitName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        manufactureDateDP.setConverter(Utils.getConverter(manufactureDateDP));
        usingDateDP.setConverter(Utils.getConverter(usingDateDP));
        anchorPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        cbType.setConverter(new StringConverter<DEVICE_MODEL>() {
            @Override
            public String toString(DEVICE_MODEL model) {
                return model.getModelName();
            }

            @Override
            public DEVICE_MODEL fromString(final String string) {
                return new DEVICE_MODEL();
            }
        });
        var models = FXCollections.observableArrayList(new DATABASE_DAO<DEVICE_MODEL>(DEVICE_MODEL.class).selectList("select * from tbDeviceModel"));
        if (models.size() > 0) {
            cbType.setItems(models);
            cbType.getSelectionModel().select(0);

            var unitName = new DATABASE_DAO<UNIT>(UNIT.class).selectOne("select *  from tbUnit where unitId = ?", cbType.getSelectionModel().getSelectedItem().getUnitId());
            unitTF.setText(unitName.getUnitName());

        }
    }


    public boolean validatedData() {
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        jMetro.setScene(alert.getDialogPane().getScene());

        if (nameTF.getText().isEmpty()) {
            alert.setContentText("Please fill in device name");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }
        if (cbType.getValue() == null) {
            alert.setContentText("Please choose device model");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }

        if (priceTF.getText().isEmpty()) {
            alert.setContentText("Please set the price");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }
        if (statusTF.getText().isEmpty()) {
            alert.setContentText("Please set the status");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }

        if (percentDamageTF.getText().isEmpty()) {
            alert.setContentText("Please set the EBITDA");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }


        if (manufactureDateDP.getValue() == null) {
            alert.setContentText("Please fill in manufacture date");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }

        if (usingDateDP.getValue() == null) {
            alert.setContentText("Please fill in using date");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }


        return true;
    }

    public void addUnitOnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/ConfigScreen/config_screen.fxml"));
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 520);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}






