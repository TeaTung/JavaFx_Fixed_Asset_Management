package project.javafx_fixed_asset_management.Controllers.LiquidationScreenControllers;

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

public class ThirdLiquidationScreenController implements Initializable {

    @FXML
    Button backBtn;

    @FXML
    Button finishBtn;

    @FXML
    TableColumn<DEVICE, String> liquidationDeviceDamageColumn;

    @FXML
    TableColumn<DEVICE, String> liquidationDeviceDepartmentColumn;

    @FXML
    TableColumn<DEVICE, String> liquidationDeviceIdColumn;


    @FXML
    TableColumn<DEVICE, String> liquidationDeviceNameColumn;


    @FXML
    TableColumn<DEVICE, String> liquidationDeviceSpecificationColumn;

    @FXML
    TableColumn<DEVICE, String> liquidationDeviceStatusColumn;

    @FXML
    TableView<DEVICE> liquidationDeviceTB;

    @FXML
    TableColumn<DEVICE, String> liquidationDeviceUsedColumn;

    @FXML
    Button liquidationBtn;


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


    public ObservableList<DEVICE> listLiquidationDevice;
    public ObservableList<INVENTORY> listInventory;
    public ObservableList<PERSON> listLiquidationPeople;
    private ArrayList<String> listLiquidationId = new ArrayList<>();
    FilteredList<DEVICE> filteredList;

    LocalDate liquidationDate;

    @FXML
    void liquidationBtnAction(ActionEvent event) {

        DEVICE selectedItem = liquidationDeviceTB.getSelectionModel().getSelectedItem();
        FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION, "The " + selectedItem.getDeviceName() + " will be liquidated.\nThis action can't be undone later.\nAre you sure to perform this action? ", ButtonType.YES, ButtonType.CANCEL);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            var liquidation = new DATABASE_DAO<>(LIQUIDATION.class);
            var device = new DATABASE_DAO<>(DEVICE.class);

            var liquidationId = UUID.randomUUID().toString().substring(0, 10);
            listLiquidationId.add(liquidationId);
            liquidation.insert("INSERT INTO TBLIQUIDATION(LIQUIDATIONID, DEVICEID, LIQUIDATIONDATE) VALUES (?, ?, ?)", liquidationId, selectedItem.getDeviceId(), liquidationDate.toString());
            device.update("UPDATE TBDEVICE SET DEVICESTATUS = ? WHERE DEVICEID = ?", "Liquidated", selectedItem.getDeviceId());
            FlatAlert information = new FlatAlert(Alert.AlertType.INFORMATION, "The " + selectedItem.getDeviceName() + " have been liquidated!", ButtonType.OK);
            jMetro.setScene(information.getDialogPane().getScene());
            information.showAndWait();
            listLiquidationDevice.remove(selectedItem);
            updateLiquidationHistoryTable();
            backBtn.setDisable(true);
        }
    }

    public void backButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/LiquidationScreen/second_liquidation_screen.fxml"));
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

    void initData(ObservableList<PERSON> listLiquidationPeople, LocalDate liquidationDate, ObservableList<DEVICE> listLiquidationDevice) {
        this.listLiquidationPeople = listLiquidationPeople;
        this.liquidationDate = liquidationDate;
        this.listLiquidationDevice = listLiquidationDevice;
        getDataInTableView();
    }

    @FXML
    void finishBtnAction(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to perform this action?", ButtonType.YES, ButtonType.CANCEL);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(confirmation.getDialogPane().getScene());
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            var PERSON_AND_LIQUIDATION = new DATABASE_DAO<>(PERSON_AND_LIQUIDATION.class);
            for (int i = 0; i < listLiquidationId.size(); i++) {
                for (int j = 0; j < listLiquidationPeople.size(); j++) {
                    var linkId = UUID.randomUUID().toString().substring(0, 10);
                    var liquidationId = listLiquidationId.get(i);
                    var personId = listLiquidationPeople.get(j).getPersonId();
                    PERSON_AND_LIQUIDATION.insert("INSERT INTO tbPersonAndLiquidation(LINKID, LIQUIDATIONID, PERSONID) VALUES (?, ?, ?)", linkId, liquidationId, personId);
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
        liquidationDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceId"));
        liquidationDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceName"));
        liquidationDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("percentDamage"));
        liquidationDeviceSpecificationColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("specification"));
        liquidationDeviceUsedColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("yearUsed"));
        liquidationDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceStatus"));

        liquidationDeviceTB.setItems(listLiquidationDevice);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataInTableView();
    }

    @FXML
    void rowSelectedHandler(MouseEvent event) {
        updateLiquidationHistoryTable();
    }

    void updateLiquidationHistoryTable() {
        listInventory = FXCollections.observableArrayList();
        if (liquidationDeviceTB.getSelectionModel().getSelectedItem() != null) {
            DEVICE selectedItem = liquidationDeviceTB.getSelectionModel().getSelectedItem();

            var deviceId = selectedItem.getDeviceId();
            var inventory = new DATABASE_DAO<>(INVENTORY.class);
            try {
                listInventory = FXCollections.observableArrayList(inventory.selectList(
                        "SELECT  InventoryId, tbDevice.DeviceId, UsableValue,  InventoryDate, DeviceName FROM tbInventory, tbDevice WHERE tbInventory.DeviceId = ? AND tbDevice.DeviceId = tbInventory.DeviceId", deviceId));
            } catch (Exception e) {
                FlatAlert information = new FlatAlert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);

                JMetro jMetro = new JMetro(Style.LIGHT);
                jMetro.setScene(information.getDialogPane().getScene());

                information.showAndWait();
                return;
            }
            inventoryDeviceHistoryIdColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("inventoryId"));
            inventoryDeviceHistoryNameColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("deviceName"));
            inventoryDeviceHistoryUsableColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("usableValue"));
            inventoryDeviceHistoryDateColumn.setCellValueFactory(new PropertyValueFactory<INVENTORY, String>("InventoryDate"));

            inventoryDeviceHistoryTV.setItems(listInventory);
        }
    }

    @FXML
    void textChangeHandler(KeyEvent event) {
        setSearchInTableView();
    }

    public void setSearchInTableView() {
        filteredList = new FilteredList<>(listLiquidationDevice, b -> true);

        searchTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(deliveryNote -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                }

                String nameSearchValue = newValue.toLowerCase();


                if (deliveryNote.getDeviceName().toLowerCase().indexOf(nameSearchValue) > -1) {
                    return true;
                }
                return false;
            });
        }));


        SortedList<DEVICE> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(liquidationDeviceTB.comparatorProperty());

        liquidationDeviceTB.setItems(sortedList);
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

