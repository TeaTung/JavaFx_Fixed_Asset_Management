package project.javafx_fixed_asset_management.Controllers.InventoryScreenControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DELIVERY_NOTE;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.PERSON;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SecondInventoryScreenController implements Initializable {
    @FXML
    Button addBtn;

    @FXML
    Button backBtn;

    @FXML
    Button continueBtn;

    @FXML
    TextField searchTF;

    @FXML
    Text errorLabel;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceDamageColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceDepartmentColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceIdColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceNameColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceStatusColumn;

    @FXML
    TableView<DELIVERY_NOTE> existedDeviceTB;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceDamageColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceDepartmentColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceIdColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceNameColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceStatusColumn;

    @FXML
    TableView<DELIVERY_NOTE> inventoryDeviceTB;

    @FXML
    Button removeBtn;

    @FXML
    Button searchBtn;

    public ObservableList<DELIVERY_NOTE> listDevice;
    public ObservableList<DELIVERY_NOTE> listInventoryDevice;
    public ObservableList<PERSON> listInventoryPeople;
    FilteredList<DELIVERY_NOTE> filteredList;
    LocalDate inventoryDate;

    void initData(ObservableList<PERSON> listInventoryPeople, LocalDate inventoryDate) {
        this.listInventoryPeople = listInventoryPeople;
        this.inventoryDate = inventoryDate;
    }

    @FXML
    void continueBtnAction(ActionEvent event) {
        if(validateData() == true){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/third_inventory_screen.fxml"));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1280, 720);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ThirdInventoryScreenController thirdInventoryScreenController = fxmlLoader.getController();
            thirdInventoryScreenController.initData(listInventoryPeople, inventoryDate, listInventoryDevice);

            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void addBtnAction(ActionEvent event) {
        DELIVERY_NOTE addingDevice = existedDeviceTB.getSelectionModel().getSelectedItem();

        if (addingDevice != null) {
            listInventoryDevice.add(addingDevice);
            listDevice.remove(addingDevice);
            inventoryDeviceTB.setItems(listInventoryDevice);
            errorLabel.setVisible(false);
        }
    }


    @FXML
    void removeBtnAction(ActionEvent event) {
        DELIVERY_NOTE removingDevice = inventoryDeviceTB.getSelectionModel().getSelectedItem();

        if (removingDevice != null) {
            listDevice.add(removingDevice);
            listInventoryDevice.remove(removingDevice);
            existedDeviceTB.setItems(listDevice);
        }
    }

    @FXML
    void textChangeHandler(KeyEvent event) {
        setSearchInTableView();
    }

    @FXML
    public void backButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/first_inventory_screen.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataInTableView();
        setSearchInTableView();
        errorLabel.setVisible(false);
    }

    boolean validateData(){
        if(listInventoryDevice.isEmpty())  {
            errorLabel.setVisible(true);
            errorLabel.setText("Please add device to inventory!");
            return false;
        }
        errorLabel.setVisible(false);
        return true;
    }

    private void getDataInTableView() {
        var devices = new DATABASE_DAO<>(DELIVERY_NOTE.class);
        try {
            listDevice = FXCollections.observableArrayList(devices.selectList(
                    "SELECT TBDEVICE.DEVICEID, DEVICENAME, DEPARTMENTNAME, DEVICESTATUS, PERCENTDAMAGE, YEARUSED, YEARMANUFACTURE, PRICE, SPECIFICATION FROM TBDEVICE, TBDEPARTMENT, TBDELIVERYNOTE WHERE TBDEVICE.DeviceId = tbDeliveryNote.DeviceId AND tbDepartment.DepartmentId = tbDeliveryNote.DepartmentId"));
        } catch (Exception e) {
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }

        listInventoryDevice = FXCollections.observableArrayList();

        existedDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceId"));
        existedDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceName"));
        existedDeviceDepartmentColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("departmentName"));
        existedDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("percentDamage"));
        existedDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceStatus"));

        inventoryDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceId"));
        inventoryDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceName"));
        inventoryDeviceDepartmentColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("departmentName"));
        inventoryDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("percentDamage"));
        inventoryDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceStatus"));

        existedDeviceTB.setItems(listDevice);
    }

    public void setSearchInTableView() {
        filteredList = new FilteredList<>(listDevice, b -> true);

        searchTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(deliveryNote -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                }

                String nameSearchValue = newValue.toLowerCase();


                if (deliveryNote.getDeviceName().toLowerCase().indexOf(nameSearchValue) > -1) {
                    return true;
                } else if (deliveryNote.getDepartmentName().toLowerCase().indexOf(nameSearchValue) > -1){
                    return true;
                }
                return false;
            });
        }));


        SortedList<DELIVERY_NOTE> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(existedDeviceTB.comparatorProperty());

        existedDeviceTB.setItems(sortedList);
    }
}
