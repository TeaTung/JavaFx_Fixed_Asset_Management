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
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE_ADD;
import project.javafx_fixed_asset_management.Models.DEVICE_MODEL;
import project.javafx_fixed_asset_management.Models.UNIT;

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
            scene = new Scene(fxmlLoader.load(), 1018, 720);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setUserData(newDevice);
        stage.setScene(scene);
        stage.show();
    }

    public void comboBoxChanged(ActionEvent actionEvent) {
        var unitName = new DATABASE_DAO<UNIT>(UNIT.class).selectOne("select *  from tbUnit where unitId = ?", cbType.getSelectionModel().getSelectedItem().getUnitId());
        unitTF.setText(unitName.getUnitName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


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
        cbType.setItems(models);
        cbType.getSelectionModel().select(0);

        var unitName = new DATABASE_DAO<UNIT>(UNIT.class).selectOne("select *  from tbUnit where unitId = ?", cbType.getSelectionModel().getSelectedItem().getUnitId());
        unitTF.setText(unitName.getUnitName());

    }


}






