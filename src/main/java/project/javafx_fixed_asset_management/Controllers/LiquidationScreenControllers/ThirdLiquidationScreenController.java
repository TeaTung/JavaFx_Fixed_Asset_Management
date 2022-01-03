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
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceDamageColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceDepartmentColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceIdColumn;


    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceNameColumn;


    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceSpecificationColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceStatusColumn;

    @FXML
    TableView<DELIVERY_NOTE> liquidationDeviceTB;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceUsedColumn;

    @FXML
    Button liquidationBtn;


    @FXML
    TextField searchTF;


    @FXML
    TableColumn<LIQUIDATION, String> liquidationDeviceHistoryDateColumn;

    @FXML
    TableColumn<LIQUIDATION, String> liquidationDeviceHistoryIdColumn;

    @FXML
    TableColumn<LIQUIDATION, String> liquidationDeviceHistoryNameColumn;

    @FXML
    TableView<LIQUIDATION> liquidationDeviceHistoryTV;



    public ObservableList<DELIVERY_NOTE> listLiquidationDevice;
    public ObservableList<LIQUIDATION> listLiquidation;
    public ObservableList<PERSON> listLiquidationPeople;
    private ArrayList<String> listLiquidationId = new ArrayList<>();
    FilteredList<DELIVERY_NOTE> filteredList;

    LocalDate liquidationDate;

    @FXML
    void liquidationBtnAction(ActionEvent event) {
        DELIVERY_NOTE selectedItem = liquidationDeviceTB.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The " + selectedItem.getDeviceName() + " will be liquidated, this action can't be undone later. Are you sure to perform this action? " , ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            var liquidation = new DATABASE_DAO<>(LIQUIDATION.class);
            var device = new DATABASE_DAO<>(DEVICE.class);

            var liquidationId = UUID.randomUUID().toString().substring(0, 10);
            listLiquidationId.add(liquidationId);
            liquidation.insert("INSERT INTO TBLIQUIDATION(LIQUIDATIONID, DEVICEID, LIQUIDATIONDATE) VALUES (?, ?, ?)", liquidationId, selectedItem.getDeviceId(), liquidationDate.toString());
            device.update("UPDATE TBDEVICE SET DEVICESTATUS = ? WHERE DEVICEID = ?", "Liquidated", selectedItem.getDeviceId());
            updateLiquidationHistoryTable();
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

    void initData(ObservableList<PERSON> listLiquidationPeople, LocalDate liquidationDate, ObservableList<DELIVERY_NOTE> listLiquidationDevice) {
        this.listLiquidationPeople = listLiquidationPeople;
        this.liquidationDate = liquidationDate;
        this.listLiquidationDevice = listLiquidationDevice;
        getDataInTableView();
    }

    @FXML
    void finishBtnAction(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "If you finish this session, all related data will be update to system and can't be edited anymore. Are you sure to perform this action?", ButtonType.YES, ButtonType.CANCEL);
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
            Alert information = new Alert(Alert.AlertType.CONFIRMATION, "This liquidation session is finished successfully!", ButtonType.OK);
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
        liquidationDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceId"));
        liquidationDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceName"));
        liquidationDeviceDepartmentColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("departmentName"));
        liquidationDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("percentDamage"));
        liquidationDeviceSpecificationColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("specification"));
        liquidationDeviceUsedColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("yearUsed"));
        liquidationDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceStatus"));

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
        listLiquidation = FXCollections.observableArrayList();
        DELIVERY_NOTE selectedItem = liquidationDeviceTB.getSelectionModel().getSelectedItem();
        var deviceId = selectedItem.getDeviceId();
        var liquidation = new DATABASE_DAO<>(LIQUIDATION.class);
        try {
            listLiquidation = FXCollections.observableArrayList(liquidation.selectList(
                    "SELECT  LiquidationId, tbDevice.DeviceId, LiquidationDate, DeviceName FROM tbLiquidation, tbDevice WHERE tbLiquidation.DeviceId = ? AND tbDevice.DeviceId = tbLiquidation.DeviceId", deviceId));
        } catch (Exception e) {
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }
        liquidationDeviceHistoryIdColumn.setCellValueFactory(new PropertyValueFactory<LIQUIDATION, String>("liquidationId"));
        liquidationDeviceHistoryNameColumn.setCellValueFactory(new PropertyValueFactory<LIQUIDATION, String>("deviceName"));
        liquidationDeviceHistoryDateColumn.setCellValueFactory(new PropertyValueFactory<LIQUIDATION, String>("liquidationDate"));

        liquidationDeviceHistoryTV.setItems(listLiquidation);
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
                } else if (deliveryNote.getDepartmentName().toLowerCase().indexOf(nameSearchValue) > -1) {
                    return true;
                }
                return false;
            });
        }));


        SortedList<DELIVERY_NOTE> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(liquidationDeviceTB.comparatorProperty());

        liquidationDeviceTB.setItems(sortedList);
    }
}

