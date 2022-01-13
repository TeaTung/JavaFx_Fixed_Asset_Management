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
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;

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
    TableColumn<DEVICE, String> inventoryDeviceDamageColumn;

    @FXML
    TableColumn<DEVICE, String> inventoryDeviceDepartmentColumn;

    @FXML
    TableColumn<DEVICE, String> inventoryDeviceIdColumn;


    @FXML
    TableColumn<DEVICE, String> inventoryDeviceNameColumn;


    @FXML
    TableColumn<DEVICE, String> inventoryDeviceSpecificationColumn;

    @FXML
    TableColumn<DEVICE, String> inventoryDeviceStatusColumn;

    @FXML
    TableView<DEVICE> inventoryDeviceTB;

    @FXML
    TableColumn<DEVICE, String> inventoryDeviceUsedColumn;

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


    public ObservableList<DEVICE> listInventoryDevice;
    public ObservableList<INVENTORY> listInventory;
    public ObservableList<PERSON> listInventoryPeople;
    private ArrayList<String> listInventoryId = new ArrayList<>();
    FilteredList<DEVICE> filteredList;

    LocalDate inventoryDate;

    @FXML
    void updateBtnAction(ActionEvent event) {
        DEVICE selectedItem = inventoryDeviceTB.getSelectionModel().getSelectedItem();
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
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    void initData(ObservableList<PERSON> listInventoryPeople, LocalDate inventoryDate, ObservableList<DEVICE> listInventoryDevice) {
        this.listInventoryPeople = listInventoryPeople;
        this.inventoryDate = inventoryDate;
        this.listInventoryDevice = listInventoryDevice;
        getDataInTableView();
    }

    @FXML
    void finishBtnAction(ActionEvent event) {
        FlatAlert confirmation = new FlatAlert(Alert.AlertType.CONFIRMATION, "Are you sure to perform this action?", ButtonType.YES, ButtonType.CANCEL);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(confirmation.getDialogPane().getScene());

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
            FlatAlert information = new FlatAlert(Alert.AlertType.CONFIRMATION, "This session is finished successfully!", ButtonType.OK);
            jMetro.setScene(information.getDialogPane().getScene());
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
        inventoryDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceId"));
        inventoryDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceName"));
        inventoryDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("percentDamage"));
        inventoryDeviceSpecificationColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("specification"));
        inventoryDeviceUsedColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("yearUsed"));
        inventoryDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceStatus"));

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
        DEVICE selectedItem = inventoryDeviceTB.getSelectionModel().getSelectedItem();
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
            filteredList.setPredicate(device -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                }

                String nameSearchValue = newValue.toLowerCase();


                if (device.getDeviceName().toLowerCase().indexOf(nameSearchValue) > -1) {
                    return true;
                }
                return false;
            });
        }));


        SortedList<DEVICE> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(inventoryDeviceTB.comparatorProperty());

        inventoryDeviceTB.setItems(sortedList);
    }

    public void onMinimizeBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    private static double xOffset = 0;
    private static double yOffset = 0;

    public void panelMousePressOnAction(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        xOffset = primaryStage.getX() - event.getScreenX();
        yOffset = primaryStage.getY() - event.getScreenY();
    }

    public void panelMouseDraggedOnAction(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setX(event.getScreenX() + xOffset);
        primaryStage.setY(event.getScreenY() + yOffset);
    }
}

