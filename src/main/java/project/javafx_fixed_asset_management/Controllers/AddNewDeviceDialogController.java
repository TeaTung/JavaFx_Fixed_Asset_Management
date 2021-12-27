package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.DEVICE_MODEL;
import project.javafx_fixed_asset_management.Models.UNIT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddNewDeviceDialogController implements Initializable {
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
    ComboBox cbUnit;

    @FXML
    DatePicker usingDateDP;


    @FXML
    ComboBox cbType;


    @FXML
    void nextButtonAction(ActionEvent event) {


        String id = UUID.randomUUID().toString().substring(0, 10);
        String modelId = ((DEVICE_MODEL) cbType.getSelectionModel().getSelectedItem()).getModelId();

        String specification = specificationTA.getText();

        new DATABASE_DAO<DEVICE>(DEVICE.class).
                insert("INSERT INTO tbDevice ( DeviceId, DeviceStatus, DeviceName, Price, modelId, specification, yearUsed, yearManufacture) values  ( ? , ? ,?, ?, ?, ?, ?, ?) ",
                        id, statusTF.getText(), nameTF.getText(), priceTF.getText(), modelId,
                        specification,
                        usingDateDP.getValue().toString(),
                        manufactureDateDP.getValue().toString());


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/AddNewDeviceDialog/add_new_contract_dialog.fxml"));


        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1018, 720);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    public void comboBoxChanged(ActionEvent actionEvent) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        cbType.setItems(models);
        cbType.setValue("modelName");
        cbType.getSelectionModel().select(0);


        var units = FXCollections.observableArrayList(new DATABASE_DAO<UNIT>(UNIT.class).selectList("select * from tbUnit"));
        cbUnit.setItems(units);
        cbUnit.setValue("unitId");
        cbUnit.setConverter(new StringConverter<UNIT>() {
            @Override
            public String toString(UNIT o) {
                return o.getUnitName();
            }

            @Override
            public UNIT fromString(String s) {
                return new UNIT();
            }
        });
        cbUnit.getSelectionModel().select(0);
    }

}





