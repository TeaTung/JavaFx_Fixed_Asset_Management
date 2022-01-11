package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.DEVICE_ADD;
import project.javafx_fixed_asset_management.Models.UNIT;
import project.javafx_fixed_asset_management.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class DeviceScreenController implements Initializable {

    public ObservableList<DEVICE> devicesList;
    @FXML
    public TableView<DEVICE> deviceTableView;
    //
    @FXML
    public TableColumn<DEVICE, String> deviceNameColumn;
    @FXML
    public TableColumn<DEVICE, String> idColumn;
    @FXML
    public TableColumn<DEVICE, String> typeOfDeviceColumn;
    @FXML
    public TableColumn<DEVICE, Integer> priceColumn;
    @FXML
    public TableColumn<UNIT, String> unitColumn;
    @FXML
    public TableColumn<DEVICE, String> specificationColumn;
    @FXML

    public TableColumn<DEVICE, String> dateOfManufacturingColumn;
    @FXML
    public TableColumn<DEVICE, LocalDate> dateOfUsingColumn;
    @FXML
    public TableColumn<DEVICE, String> statusColumn;
    @FXML
    public TableColumn<DEVICE, Float> percentDamage;
    public TableColumn<DEVICE, Integer> quantityColumn;
    public Button ExportBtn;
    public Button addBtn;
    public Button deleteBtn;
    public Button updateBtn;
    public Button backBtn;
    @FXML
    public TextField searchTF;
    public AnchorPane anchorPane;
    public ComboBox<String> deviceViewModeCB;
    public TableColumn<DEVICE, String> departmentNameColumn;
    FilteredList<DEVICE> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {

            // TABLEVIEW
            var devices = new DATABASE_DAO<>(DEVICE_ADD.class);
            devicesList = FXCollections.observableArrayList(devices.selectList(
                    "select * " +
                            "from tbDevice inner join tbDeviceModel " +
                            "on tbDevice.ModelId =  tbDeviceModel.ModelId " +
                            "inner join tbUnit \n" +
                            "on tbDeviceModel.UnitId = tbUnit.UnitId"));


            deviceNameColumn.setCellValueFactory(
                    new PropertyValueFactory<DEVICE, String>("deviceName"));

            idColumn.setCellValueFactory(
                    new PropertyValueFactory<DEVICE, String>("deviceId"));

            typeOfDeviceColumn.setCellValueFactory(
                    new PropertyValueFactory<>("modelName"));

            priceColumn.setCellValueFactory(
                    new PropertyValueFactory<>("price"));

            unitColumn.setCellValueFactory(
                    new PropertyValueFactory<UNIT, String>("unitName"));


            specificationColumn.setCellValueFactory(
                    new PropertyValueFactory<DEVICE, String>("specification"));

            dateOfManufacturingColumn.setCellValueFactory(
                    new PropertyValueFactory<>("yearManufacture"));


            dateOfUsingColumn.setCellValueFactory(
                    new PropertyValueFactory<>("yearUsed"));


            statusColumn.setCellValueFactory(
                    new PropertyValueFactory<>("deviceStatus"));

            quantityColumn.setCellValueFactory(
                    new PropertyValueFactory<>("quantityDevice"));

            percentDamage.setCellValueFactory(new PropertyValueFactory<>("percentDamage"));
            filteredList = new FilteredList<>(devicesList);
            deviceTableView.setItems(devicesList);

            // VIEWMODECB
            deviceViewModeCB.getItems().add("TOTAL");
            deviceViewModeCB.getItems().add("IN USE");


        }).start();


        anchorPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        deviceTableView.getStyleClass().add(JMetroStyleClass.TABLE_GRID_LINES);
        deviceViewModeCB.setValue("TOTAL");

    }


    public void addDeviceButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/AddNewDeviceDialog/add_new_device_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load(), 990, 650);


        stage.setTitle("Add New Screen Dialog");
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.showAndWait();
        updateData();

    }

    public void updateDeviceButtonAction(ActionEvent event) throws IOException {

        if (deviceTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/UpdateNewDeviceDialog/update_device_dialog.fxml"));

            Node node = (Node) event.getSource();

            Stage stage = new Stage();

            // CREATE THE SCENE
            Parent root = fxmlLoader.load();
            Scene scene = (new Scene(root, 1018, 660));
            stage.setScene(scene);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);

            //Set the Stage
            UpdateScreenDialogController newProjectController = fxmlLoader.getController();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Device Dialog");


            // CREATE DEVICE_ADD OBJECT
            int cellIndex = deviceTableView.getSelectionModel().getSelectedIndex();
            var device = deviceTableView.getSelectionModel().getSelectedItem();
            var quantityDevice = quantityColumn.getCellData(cellIndex);
            var deviceAdd = new DEVICE_ADD(quantityDevice, device);

            newProjectController.setDEVICE_ADD(deviceAdd);
            stage.showAndWait();
            updateData();
        }
    }

    public void updateData() {

        var devices = new DATABASE_DAO<>(DEVICE_ADD.class);
        devicesList = FXCollections.observableArrayList(devices.selectList(
                "select * " +
                        "from tbDevice inner join tbDeviceModel " +
                        "on tbDevice.ModelId =  tbDeviceModel.ModelId " +
                        "inner join tbUnit \n" +
                        "on tbDeviceModel.UnitId = tbUnit.UnitId"));
        filteredList = new FilteredList<>(devicesList);
        deviceTableView.setItems(devicesList);

    }

    public void deleteDeviceButtonAction(ActionEvent event) {
        if (deviceTableView.getSelectionModel().getSelectedItem() != null) {
            FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure ?");

            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(alert.getDialogPane().getScene());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                new DATABASE_DAO<>(DEVICE.class).delete("DELETE FROM tbDevice where deviceId = ? ", deviceTableView.getSelectionModel().getSelectedItem().getDeviceId());
                updateData();
            } else {
            }
        }
    }

    public void onKeyTypeSearchTF(KeyEvent keyEvent) {

        String textSearch = searchTF.getText();
        if (!textSearch.isEmpty()) {


            filteredList.setPredicate(
                    new Predicate<DEVICE>() {
                        public boolean test(DEVICE t) {
                            return (t.getDeviceName().toLowerCase().startsWith(textSearch));
                        }
                    }
            );

            deviceTableView.setItems(filteredList);


        } else {
            deviceTableView.setItems(devicesList);
        }
    }

    public void exportButtonAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Utils.exportExcelDevices(stage, deviceTableView);
    }

    public void backDeviceButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HomeScreen/Manager/manager_home_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.show();
    }

    public void deviceViewModeCbOnAction(ActionEvent actionEvent) {
        switch (deviceViewModeCB.getSelectionModel().getSelectedItem().toString()) {
            case "IN USE": {
                departmentNameColumn.setVisible(true);
                var devices = new DATABASE_DAO<>(DEVICE_ADD.class);
                devicesList = FXCollections.observableArrayList(devices.selectList(
                        "select  *  " +
                                "from tbDevice inner join tbDeviceModel " +
                                "on tbDevice.ModelId =  tbDeviceModel.ModelId " +
                                "inner join tbUnit " +
                                "on tbDeviceModel.UnitId = tbUnit.UnitId " +
                                "inner join tbTransfer on tbTransfer.deviceId = tbDevice.deviceId"));
                departmentNameColumn.setCellValueFactory(
                        new PropertyValueFactory<>("department"));
                deviceTableView.setItems(devicesList);
                break;
            }
            
            case "TOTAL": {
                var devices = new DATABASE_DAO<>(DEVICE_ADD.class);
                devicesList = FXCollections.observableArrayList(devices.selectList(
                        "select * " +
                                "from tbDevice inner join tbDeviceModel " +
                                "on tbDevice.ModelId =  tbDeviceModel.ModelId " +
                                "inner join tbUnit " +
                                "on tbDeviceModel.UnitId = tbUnit.UnitId "));
                departmentNameColumn.setVisible(false);
                departmentNameColumn.setCellValueFactory(
                        new PropertyValueFactory<>("department"));
                deviceTableView.setItems(devicesList);
                System.out.println("TOTAL");
                break;
            }
        }
    }
}
