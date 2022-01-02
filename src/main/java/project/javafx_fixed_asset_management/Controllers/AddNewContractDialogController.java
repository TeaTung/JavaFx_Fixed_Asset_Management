package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import project.javafx_fixed_asset_management.Models.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddNewContractDialogController implements Initializable {
    @FXML
    Button addNewDeviceBtn;

    @FXML
    TextField idContractTF;


    @FXML
    DatePicker importDateDP;

    @FXML
    Text notifyLabel;


    @FXML
    TextArea noteTA;


    @FXML
    ComboBox<PROVIDER> providerCB;

    @FXML
    void addNewDeviceButtonAction(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        DEVICE_ADD new_device = (DEVICE_ADD) stage.getUserData();

        var contractId = idContractTF.getText();
        var providerId = providerCB.getSelectionModel().getSelectedItem().getProviderId();
        var importDate = importDateDP.getValue().toString();
        var note = noteTA.getText();


        new DATABASE_DAO<>(CONTRACT.class).insert("insert into tbContract ( ContractId, ProviderId, ImportDate, Note) values (? , ? , ?, ?) ", contractId, providerId, importDate, note);

        var deviceId = UUID.randomUUID().toString().substring(0, 10);

        // insert new devices
        new DATABASE_DAO<DEVICE>(DEVICE.class).
                insert("INSERT INTO tbDevice ( DeviceId, DeviceStatus, DeviceName, Price, modelId, specification, yearUsed, yearManufacture, quantityDevice, percentDamage, contractId ) values  ( ? , ? ,?, ?, ?, ?, ?, ?, ?, ?, ?) ",
                        deviceId, new_device.getDeviceStatus(), new_device.getDeviceName(), (String.valueOf(new_device.getPrice())), new_device.getModelId(), new_device.getSpecification(),
                        new_device.getYearUsed(),
                        new_device.getYearManufacture(),
                        String.valueOf(new_device.getQuantityDevice()),
                        String.valueOf(new_device.getPercentDamage()),
                        contractId
                );


        // a alert dialog before leaving
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add devices");
        alert.setHeaderText("Message");
        alert.setContentText("Adding successfully!");
        alert.showAndWait();
        stage.close();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        providerCB.setConverter(new StringConverter<PROVIDER>() {
            @Override
            public String toString(PROVIDER provider) {
                return provider.getProviderName();
            }

            @Override
            public PROVIDER fromString(final String string) {
                return new PROVIDER();
            }
        });


        var providers = FXCollections.observableArrayList(new DATABASE_DAO<PROVIDER>(PROVIDER.class).selectList("select * from tbProvider"));
        providerCB.setItems(providers);

        providerCB.getSelectionModel().select(0);

    }
}
