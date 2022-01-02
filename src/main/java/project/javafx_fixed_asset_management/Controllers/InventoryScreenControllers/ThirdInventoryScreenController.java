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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DELIVERY_NOTE;
import project.javafx_fixed_asset_management.Models.INVENTORY;
import project.javafx_fixed_asset_management.Models.PERSON;
import project.javafx_fixed_asset_management.Models.PERSON_AND_INVENTORY;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ThirdInventoryScreenController implements Initializable {

    @FXML
    Button backBtn;

    @FXML
    Button finishBtn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceDamageColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceDepartmentColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceIdColumn;


    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceNameColumn;


    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceSpecificationColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceStatusColumn;

    @FXML
    TableView<DELIVERY_NOTE> inventoryDeviceTB;

    @FXML
    TableColumn<DELIVERY_NOTE, String> inventoryDeviceUsedColumn;

    @FXML
    Button updateBtn;

    @FXML
    TextField usableValueTF;

    @FXML
    TextField searchTF;


    @FXML
    TableColumn<INVENTORY, String> inventoryDeviceHistoryDateColumn;

    @FXML
    TableColumn<INVENTORY, String> inventoryDeviceHistoryIdColumn;

    @FXML
    TableColumn<INVENTORY, String> inventoryDeviceHistoryNameColumn;

    @FXML
    TableView<INVENTORY> inventoryDeviceHistoryTV;

    @FXML
    TableColumn<INVENTORY, String> inventoryDeviceHistoryUsableColumn;


    public ObservableList<DELIVERY_NOTE> listInventoryDevice;
    public ObservableList<INVENTORY> listInventory;
    public ObservableList<PERSON> listInventoryPeople;
    private ArrayList<String> listInventoryId = new ArrayList<>();
    FilteredList<DELIVERY_NOTE> filteredList;

    LocalDate inventoryDate;

    @FXML
    void updateBtnAction(ActionEvent event) {
        DELIVERY_NOTE selectedItem = inventoryDeviceTB.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Usable value of " + selectedItem.getDeviceName() + " to " + usableValueTF.getText() + " percent?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            var inventory = new DATABASE_DAO<>(INVENTORY.class);
            var inventoryId = UUID.randomUUID().toString().substring(0, 10);
            listInventoryId.add(inventoryId);
            inventory.insert("INSERT INTO TBINVENTORY(INVENTORYID, DEVICEID, USABLEVALUE, INVENTORYDATE) VALUES (?, ?, ?, ?)", inventoryId, selectedItem.getDeviceId(), usableValueTF.getText(), inventoryDate.toString());
            updateInventoryHistoryTable();
        }
    }

    public void backButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/second_inventory_screen.fxml"));
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

    void initData(ObservableList<PERSON> listInventoryPeople, LocalDate inventoryDate, ObservableList<DELIVERY_NOTE> listInventoryDevice) {
        this.listInventoryPeople = listInventoryPeople;
        this.inventoryDate = inventoryDate;
        this.listInventoryDevice = listInventoryDevice;
        getDataInTableView();
    }

    @FXML
    void finishBtnAction(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "If you finish this session, all related data will be update to system and can't be edited anymore. Are you sure to perform this action?", ButtonType.YES, ButtonType.CANCEL);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            var PERSON_AND_INVENTORY = new DATABASE_DAO<>(PERSON_AND_INVENTORY.class);
            for (int i = 0; i < listInventoryId.size(); i++) {
                for (int j = 0; j < listInventoryPeople.size(); j++) {
                    var linkId = UUID.randomUUID().toString().substring(0, 10);
                    var inventoryId = listInventoryId.get(i);
                    var personId = listInventoryPeople.get(j).getPersonId();
                    PERSON_AND_INVENTORY.insert("INSERT INTO tbPersonAndInventory(LINKID, INVENTORYID, PERSONID) VALUES (?, ?, ?)", linkId, inventoryId, personId);
                }
            }
            Alert information = new Alert(Alert.AlertType.CONFIRMATION, "This inventory session is finished successfully!", ButtonType.OK);
            information.showAndWait();
            if (information.getResult() == ButtonType.OK) {
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
        }
    }

    private void getDataInTableView() {
        inventoryDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceId"));
        inventoryDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceName"));
        inventoryDeviceDepartmentColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("departmentName"));
        inventoryDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("percentDamage"));
        inventoryDeviceSpecificationColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("specification"));
        inventoryDeviceUsedColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("yearUsed"));
        inventoryDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceStatus"));

        inventoryDeviceTB.setItems(listInventoryDevice);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataInTableView();
    }

    @FXML
    void rowSelectedHandler(MouseEvent event) {
        updateInventoryHistoryTable();
    }

    void updateInventoryHistoryTable() {
        listInventory = FXCollections.observableArrayList();
        DELIVERY_NOTE selectedItem = inventoryDeviceTB.getSelectionModel().getSelectedItem();
        var deviceId = selectedItem.getDeviceId();
        var inventory = new DATABASE_DAO<>(INVENTORY.class);
        try {
            listInventory = FXCollections.observableArrayList(inventory.selectList(
                    "SELECT  InventoryId, tbDevice.DeviceId, UsableValue,  InventoryDate, DeviceName FROM tbInventory, tbDevice WHERE tbInventory.DeviceId = ? AND tbDevice.DeviceId = tbInventory.DeviceId", deviceId));
        } catch (Exception e) {
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }
        inventoryDeviceHistoryIdColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("inventoryId"));
        inventoryDeviceHistoryNameColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("deviceName"));
        inventoryDeviceHistoryUsableColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("usableValue"));
        inventoryDeviceHistoryDateColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("InventoryDate"));

        inventoryDeviceHistoryTV.setItems(listInventory);
    }

    @FXML
    void textChangeHandler(KeyEvent event) {
        setSearchInTableView();
    }

    public void setSearchInTableView() {
        filteredList = new FilteredList<>(listInventoryDevice, b -> true);

        searchTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(deliveryNote -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                }

                String nameSearchValue = newValue.toLowerCase();


                if (deliveryNote.getDeviceName().toLowerCase().indexOf(nameSearchValue) > -1) {
                    return true;
                } else if (deliveryNote.getDepartmentName().toLowerCase().indexOf(nameSearchValue) > -1) {
                    return true;
                }
                return false;
            });
        }));


        SortedList<DELIVERY_NOTE> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(inventoryDeviceTB.comparatorProperty());

        inventoryDeviceTB.setItems(sortedList);
    }
}

