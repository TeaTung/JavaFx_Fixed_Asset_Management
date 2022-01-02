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

public class TransferDevicesScreenController implements Initializable {
    @FXML Button addBtn;

    @FXML Button removeBtn;

    @FXML Button backBtn;

    @FXML Button transBtn;

    @FXML TextField transformIdTF;

    @FXML TextField departmentTF;

    @FXML TextField searchDeviceTF;

    @FXML TableView<DEVICE> deviceTableView;

    @FXML TableColumn<DEVICE, String> idDeviceColumn;

    @FXML TableColumn<DEVICE, String> deviceNameColumn;

    @FXML TableColumn<DEVICE, String> specificationDeviceNameColumn;

    @FXML TableView<DEVICE> deviceTransferTableView;

    @FXML TableColumn<DEVICE, String> idDeviceTransferColumn;

    @FXML TableColumn<DEVICE, String> deviceTransferNameColumn;

    @FXML TableColumn<DEVICE, String> specificationDeviceTransferNameColumn;

    public ObservableList<DEVICE> listDevice;
    public ObservableList<DEVICE> listTransferDevice;

    FilteredList<DEVICE> filteredList;

    public void addDeviceButtonAction (ActionEvent event) {
        DEVICE addingDevice = deviceTableView.getSelectionModel().getSelectedItem();

        if (addingDevice != null) {
            listTransferDevice.add(addingDevice);
            listDevice.remove(addingDevice);
        }

        deviceTransferTableView.setItems(listTransferDevice);
    }

    public void removeDeviceButtonAction (ActionEvent event) {
        DEVICE addingDevice = deviceTransferTableView.getSelectionModel().getSelectedItem();

        if (addingDevice != null) {
            listDevice.add(addingDevice);
            listTransferDevice.remove(addingDevice);

            deviceTableView.setItems(listDevice);
        }
    }

    public void backButtonAction (ActionEvent event) {
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

    public void transButtonAction (ActionEvent event) throws IOException {
        validateField(event);
    }

    public void validateField(ActionEvent event) throws IOException {
        if (isAllTextFieldEmpty() || listTransferDevice.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Transform Device");
            alert.setHeaderText("Make sure you fill up all field");
            alert.setContentText("Some field wasn't inserted");
            alert.show();
        } else {
            goToConfirmScreen(event);
        }
    }

    public boolean isAllTextFieldEmpty() {
        if (!isTextFieldEmpty(transformIdTF) && !isTextFieldEmpty(departmentTF) ){
            return false;
        }
        return true;
    }

    public void goToConfirmScreen (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/TransferScreen/confirm_transfer_devices_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent confirmTransformDevicesController  = fxmlLoader.load();
        Scene scene = new Scene(confirmTransformDevicesController);
        ConfirmTransferDevicesDialogController controller =  fxmlLoader.getController();
        controller.setInit(listTransferDevice, transformIdTF.getText(), departmentTF.getText());
        stage.setScene(scene);
        stage.show();
    }

    public boolean isTextFieldEmpty(TextField field) {
        if (field.getText().isEmpty() || field.getText().isBlank() || field.getText() == null) {
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataInTableView();
        setSearchInTableView();
        setProperty();
    }

    public void getDataInTableView() {
        var devices = new DATABASE_DAO<>(DEVICE.class);

        listDevice = FXCollections.observableArrayList(devices.selectList(
                "SELECT DeviceId, DeviceName, Specification FROM tbDevice"));
        listTransferDevice = FXCollections.observableArrayList();

        idDeviceColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceId"));
        deviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceName"));
        specificationDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("Specification"));

        idDeviceTransferColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceId"));
        deviceTransferNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceName"));
        specificationDeviceTransferNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("Specification"));

        deviceTableView.setItems(listDevice);
    }

    public void setSearchInTableView () {
        filteredList = new FilteredList<>(listDevice, b -> true);

        searchDeviceTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
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
        searchDeviceTF.setPromptText("Search...");
        searchDeviceTF.getParent().requestFocus();
    }
}
