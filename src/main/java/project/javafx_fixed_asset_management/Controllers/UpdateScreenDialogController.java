package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Models.*;

import java.time.LocalDate;

public class UpdateScreenDialogController {
    public TextField unitTF;
    public TextField percentDamageTF;
    public ComboBox<DEVICE_MODEL> cbType;
    public Button saveBtn;
    public TextField quantityTF;
    public AnchorPane anchorPane;


    private DEVICE_ADD device_add;

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
    DatePicker usingDateDP;


    public void setDEVICE_ADD(DEVICE_ADD device_add) {
        this.device_add = device_add;
        loadingData(this.device_add);
    }

    public void updateButtonAction(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        device_add.setDeviceName(nameTF.getText());
        device_add.setModelId(cbType.getSelectionModel().getSelectedItem().getModelId());
        device_add.setSpecification(specificationTA.getText());
        device_add.setYearManufacture(manufactureDateDP.getValue().toString());
        device_add.setYearUsed(usingDateDP.getValue().toString());
        device_add.setDeviceName(nameTF.getText());
        device_add.setPrice(Float.parseFloat(priceTF.getText()));
        device_add.setDeviceStatus(statusTF.getText());
        device_add.setPercentDamage(Float.parseFloat(percentDamageTF.getText()));
        device_add.setQuantityDevice(Integer.parseInt(quantityTF.getText()));

        // insert new devices
        new DATABASE_DAO<DEVICE>(DEVICE.class).
                insert("UPDATE  tbDevice set  DeviceStatus = ? , DeviceName = ? , Price = ? , modelId = ? , specification = ? , yearUsed = ? , yearManufacture = ? , quantityDevice = ? , percentDamage = ?   where deviceId = ? ",
                        device_add.getDeviceStatus(), device_add.getDeviceName(), (String.valueOf(device_add.getPrice())), device_add.getModelId(), device_add.getSpecification(),
                        device_add.getYearUsed(),
                        device_add.getYearManufacture(),
                        String.valueOf(device_add.getQuantityDevice()),
                        String.valueOf(device_add.getPercentDamage()),
                        device_add.getDeviceId()
                );


        // a alert dialog before leaving
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        alert.setContentText("Update successfully");
        alert.setHeaderText("Message");
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();
        stage.close();

    }

    // UPDATE TO ANOTHER MODEL
    public void comboBoxChanged(ActionEvent actionEvent) throws Exception {
        // set UNIT DATA
        var unitName = new DATABASE_DAO<UNIT>(UNIT.class).selectOne("select *  from tbUnit where unitId = ?", cbType.getSelectionModel().getSelectedItem().getUnitId());
        unitTF.setText(unitName.getUnitName());
    }


    public void loadingData(DEVICE_ADD device_add) {
        statusTF.setText(device_add.getDeviceStatus());

        // load DEVICE_MODELS
        var deviceModels = FXCollections.observableArrayList(new DATABASE_DAO<DEVICE_MODEL>(DEVICE_MODEL.class).selectList("select * from tbDeviceModel"));

        // set converter
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


        // set DEVICE_MODEL DATA
        cbType.setItems(deviceModels);
        var selectedDeviceModel = new DATABASE_DAO<DEVICE_MODEL>(DEVICE_MODEL.class).selectOne("select * from tbDeviceModel where modelId = ?", device_add.getModelId());
        cbType.getSelectionModel().select(selectedDeviceModel);


        // set UNIT DATA
        var unitName = new DATABASE_DAO<UNIT>(UNIT.class).selectOne("select *  from tbUnit where unitId = ?", cbType.getSelectionModel().getSelectedItem().getUnitId());
        unitTF.setText(unitName.getUnitName());


        percentDamageTF.setText(String.valueOf(device_add.getPercentDamage()));
        quantityTF.setText(String.valueOf(device_add.getQuantityDevice()));
        nameTF.setText(device_add.getDeviceName());
        specificationTA.setText(device_add.getSpecification());
        usingDateDP.setValue(LocalDate.parse(device_add.getYearUsed()));
        priceTF.setText(String.valueOf(device_add.getPrice()));
        manufactureDateDP.setValue(LocalDate.parse(device_add.getYearManufacture()));
    }
}
