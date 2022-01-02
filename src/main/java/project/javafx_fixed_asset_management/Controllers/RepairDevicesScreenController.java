package project.javafx_fixed_asset_management.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RepairDevicesScreenController implements Initializable {
    @FXML Button addBtn;

    @FXML Button removeBtn;

    @FXML Button backBtn;

    @FXML Button repairBtn;

    @FXML TextField searchTF;

    @FXML TextField repairingDecisionIdTF;

    @FXML TextField priceTF;

    @FXML TextField repairingCompanyTF;

    @FXML DatePicker repairingDateDTP;

    @FXML TableView<DEVICE> deviceTableView;

    @FXML TableColumn<DEVICE, String> idDeviceColumn;

    @FXML TableColumn<DEVICE, String> deviceNameColumn;

    @FXML TableColumn<DEVICE, String> specificationDeviceNameColumn;

    @FXML TableView<DEVICE> deviceRepairingTableView;

    @FXML TableColumn<DEVICE, String> idDeviceRepairingColumn;

    @FXML TableColumn<DEVICE, String> deviceRepairingNameColumn;

    @FXML TableColumn<DEVICE, String> specificationDeviceRepairingNameColumn;

    public ObservableList<DEVICE> listDevice;
    public ObservableList<DEVICE> listRepairingDevice;

    FilteredList<DEVICE> filteredList;


    public void addDeviceButtonAction(ActionEvent event) {
        DEVICE addingDevice = deviceTableView.getSelectionModel().getSelectedItem();

        if (addingDevice != null) {
            listRepairingDevice.add(addingDevice);
            listDevice.remove(addingDevice);
        }

        deviceRepairingTableView.setItems(listRepairingDevice);
    }

    public void removeDeviceButtonAction(ActionEvent event) {
        DEVICE addingDevice = deviceRepairingTableView.getSelectionModel().getSelectedItem();

        if (addingDevice != null) {
            listDevice.add(addingDevice);
            listRepairingDevice.remove(addingDevice);

            deviceTableView.setItems(listDevice);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataInTableView();
        setSearchInTableView();
        setProperty();
    }


    public void backButtonAction(ActionEvent event) {
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
        stage.show();
    }

    public void repairButtonAction(ActionEvent event) throws IOException {
        validateField(event);
    }


    public void getDataInTableView() {
        var devices = new DATABASE_DAO<>(DEVICE.class);

        listDevice = FXCollections.observableArrayList(devices.selectList(
                "SELECT DeviceId, DeviceName, Specification FROM tbDevice"));
        listRepairingDevice = FXCollections.observableArrayList();

        idDeviceColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceId"));
        deviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceName"));
        specificationDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("Specification"));

        idDeviceRepairingColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceId"));
        deviceRepairingNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceName"));
        specificationDeviceRepairingNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("Specification"));

        deviceTableView.setItems(listDevice);
    }

    public void setSearchInTableView () {
        filteredList = new FilteredList<>(listDevice, b -> true);

        searchTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(device -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                }

                String searchValue = newValue.toLowerCase();

                if (device.getDeviceName().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (device.getDeviceId().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (device.getSpecification().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        }));

        SortedList<DEVICE> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(deviceTableView.comparatorProperty());

        deviceTableView.setItems(sortedList);
    }

    public void setProperty () {
        priceTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String newValue, String t1) {
                if (!newValue.matches("\\d*")) {
                    priceTF.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        searchTF.setPromptText("Search...");
        searchTF.getParent().requestFocus();
    }

    public void goToConfirmScreen (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/RepairScreen/confirm_repair_devices_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent confirmRepairDevicesController  = fxmlLoader.load();
        Scene scene = new Scene(confirmRepairDevicesController);
        ConfirmRepairDevicesDialogController controller =  fxmlLoader.getController();
        controller.setInit(listRepairingDevice, repairingDecisionIdTF.getText(), priceTF.getText(),repairingCompanyTF.getText(),repairingDateDTP.getValue());
        stage.setScene(scene);
        stage.show();
    }

    public void validateField(ActionEvent event) throws IOException {
        if (isAllTextFieldEmpty() || listRepairingDevice.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Repair Device");
            alert.setHeaderText("Make sure you fill up all field");
            alert.setContentText("Some field wasn't inserted");
            alert.show();
        } else {
            goToConfirmScreen(event);
        }

    }

    public boolean isAllTextFieldEmpty() {
        if (!isTextFieldEmpty(repairingCompanyTF) && !isTextFieldEmpty(priceTF) && !isTextFieldEmpty(repairingDecisionIdTF) && repairingDateDTP.getValue() != null){
            return false;
        }
        return true;
    }

    public boolean isTextFieldEmpty(TextField field) {
        if (field.getText().isEmpty() || field.getText().isBlank() || field.getText() == null) {
            return true;
        }
        return false;
    }


}
