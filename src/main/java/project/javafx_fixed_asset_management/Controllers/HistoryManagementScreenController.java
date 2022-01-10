package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;
import project.javafx_fixed_asset_management.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryManagementScreenController implements Initializable {


    public TabPane myTabPane;
    public TableColumn<LIQUIDATION_HISTORY, String> idLiquidationColumn;
    public TableColumn<LIQUIDATION_HISTORY, String> departmentNameLiquidationColumn;
    public TableColumn<LIQUIDATION_HISTORY, String> dateLiquidationColumn;


    //<editor-fold desc="TAB LIQUIDATION">

    public DATABASE_DAO<LIQUIDATION_HISTORY> liquidation_db = new DATABASE_DAO<>(LIQUIDATION_HISTORY.class);
    public TableView<LIQUIDATION_HISTORY> liquidationTableView;

    public TableColumn<DEVICE_ADD, String> nameLiquidationColumn;
    public TableColumn<DEVICE_ADD, String> typeLiquidationColumn;
    public TableColumn<DEVICE_ADD, String> ebitdaLiquidationColumn;
    public TableColumn<DEVICE_ADD, String> quantityLiquidationColumn;
    public TableView<DEVICE_ADD> liquidationDeviceTableView;
    public TableColumn<PERSON_AND_INVENTORY_HISTORY, String> personTitleLiquidationColumn;
    public TableColumn<PERSON_AND_INVENTORY_HISTORY, String> personDepartmentLiquidationColumn;
    public TableColumn<PERSON_AND_INVENTORY_HISTORY, String> personNameLiquidationColumn;
    public TableView<PERSON_AND_INVENTORY_HISTORY> liquidationPersonTableView;
    public Button liquidationSearchBtn;
    public Button liquidationClearBtn;
    public TableColumn<DEVICE_ADD, String> idDeviceLiquidationColumn;

    public void setUpLiquidation() {

        var liquidationList = FXCollections.observableArrayList(liquidation_db.
                selectList("select department, liquidationId,  liquidationDate  from tbDevice join tbTransform on tbDevice.DeviceId = tbTransform.DeviceId join tbLiquidation on tbDevice.deviceId = tbLiquidation.deviceId "));

        idLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("liquidationId"));
        departmentNameLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        dateLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("liquidationDate"));
        liquidationTableView.setItems(liquidationList);


        //set up DEVICE TABLE VIEW
        nameLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        typeLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        ebitdaLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("percentDamage"));
        quantityLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDevice"));
        idDeviceLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("deviceId"));


        // set up PERSON TABLE VIEW
        personNameLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        personTitleLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        personDepartmentLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));

    }


    public void liquidationTableViewOnMouseClicked(MouseEvent mouseEvent) {
        if (liquidationTableView.getSelectionModel().getSelectedItem() != null) {
            var database_dao = new DATABASE_DAO<>(DEVICE_ADD.class);
            var deviceList = FXCollections.observableArrayList(database_dao.
                    selectList("select * from tbDevice inner join tbLiquidation  on tbLiquidation.DeviceId = tbDevice.deviceId  join tbTransform on tbLiquidation.DeviceId = tbTransform.DeviceId inner join tbDeviceModel on tbDevice.modelId = tbDeviceModel.modelId WHERE liquidationID = ?", liquidationTableView.getSelectionModel().getSelectedItem().getLiquidationId()));
            liquidationDeviceTableView.setItems(deviceList);


            var person_dao = new DATABASE_DAO<>(PERSON_AND_INVENTORY_HISTORY.class);
            var list_person = FXCollections.observableArrayList(person_dao.
                    selectList("select name, title, departmentName from tbPersonAndLiquidation inner join tbPerson on tbPerson.PersonId = tbPersonAndLiquidation.PersonId inner join tbDepartment on tbPerson.departmentId = tbDepartment.departmentId  where liquidationId = ? ", liquidationTableView.getSelectionModel().getSelectedItem().getLiquidationId()));

            liquidationPersonTableView.setItems(list_person);
        }
    }

    public void liquidationClearBtnOnAction(ActionEvent actionEvent) {
        setUpLiquidation();
        liquidationDateOfLiquidationDTP.getEditor().clear();
    }

    public void liquidationSearchBtnOnAction(ActionEvent actionEvent) {
        if (liquidationDateOfLiquidationDTP.getValue() != null) {
            var liquidationList = FXCollections.observableArrayList(liquidation_db.
                    selectList("select department, liquidationId,  liquidationDate  from tbDevice join tbTransform on tbDevice.DeviceId = tbTransform.DeviceId join tbLiquidation on tbDevice.deviceId = tbLiquidation.deviceId WHERE liquidationDate = CAST( ? AS DATE ) ", liquidationDateOfLiquidationDTP.getValue().toString()));
            liquidationTableView.setItems(liquidationList);

        }

    }

    //</editor-fold>


    //<editor-fold desc="TAB INVENTORY">
    public TableView<INVENTORY> inventoryTableView;
    public TableColumn<INVENTORY, String> idInventoryColumn;
    public TableColumn<INVENTORY, String> usableValueInventoryColumn;
    public TableColumn<INVENTORY, String> dateInventoryColumn;
    public TableColumn<INVENTORY, String> deviceNameInventoryColumn;
    public TableColumn<INVENTORY, String> typeInventoryColumn;
    public TableColumn<INVENTORY, String> quantityInventoryColumn;
    public TableView<DEVICE_ADD> inventoryDeviceTableView;
    public TableColumn<DEVICE_ADD, String> ebitdaInventoryColumn;
    public TableView<PERSON_AND_INVENTORY_HISTORY> inventoryPeopeTableView;
    public TableColumn<PERSON_AND_INVENTORY_HISTORY, String> personNameInventoryColumn;
    public TableColumn<PERSON_AND_INVENTORY_HISTORY, String> personDepartmentInventoryColumn;
    public TableColumn<PERSON_AND_INVENTORY, String> personTitleInventoryColumn;
    public ComboBox<DEPARTMENT> departmentNameInventoryCB;
    public DATABASE_DAO<INVENTORY> inventory_db = new DATABASE_DAO<>(INVENTORY.class);
    public DatePicker inventoryDateOfInventoryDP;
    public Button inventoryClearIBtn;
    public TableColumn<DEVICE_ADD, String> idDeviceInventoryColumn;


    public void setUpInventory() {

        var inventoryList = FXCollections.observableArrayList(inventory_db.
                selectList("select *   from tbInventory "));

        usableValueInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("usableValue"));
        idInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        dateInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryDate"));
        inventoryTableView.setItems(inventoryList);

        var department_db = new DATABASE_DAO<>(DEPARTMENT.class);
        var departmentList = FXCollections.observableArrayList(department_db.
                selectList("select *   from tbDepartment "));

        departmentNameInventoryCB.setConverter(new StringConverter<DEPARTMENT>() {
            @Override
            public String toString(DEPARTMENT department) {
                if (department != null)
                    return department.getDepartmentName();
                return "TOTAL";
            }

            @Override
            public DEPARTMENT fromString(String s) {
                return null;
            }
        });
        departmentNameInventoryCB.setItems(departmentList);


        deviceNameInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        typeInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        quantityInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDevice"));
        ebitdaInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("percentDamage"));
        idDeviceInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("deviceId"));

        personNameInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        personDepartmentInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        personTitleInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    public void inventoryTableViewOnMouseClicked(MouseEvent mouseEvent) {

        if (inventoryTableView.getSelectionModel().getSelectedItem() != null) {

            // LIST DEVICE
            var inventoryHistory = inventoryTableView.getSelectionModel().getSelectedItem();
            var deviceId = inventoryHistory.getDeviceId();
            var database_dao = new DATABASE_DAO<>(DEVICE_ADD.class);
            var listDevice = FXCollections.observableArrayList(database_dao.
                    selectList("select * from  tbDevice inner join tbDeviceModel on tbDevice.modelId = tbDeviceModel.modelId  where deviceId = ? ", deviceId));


            inventoryDeviceTableView.setItems(listDevice);


            // LIST PERSON INVOLVE
            var person_dao = new DATABASE_DAO<>(PERSON_AND_INVENTORY_HISTORY.class);
            var list_person = FXCollections.observableArrayList(person_dao.
                    selectList("select * from tbPersonAndInventory inner join tbPerson on tbPerson.PersonId = tbPersonAndInventory.PersonId inner join tbDepartment on tbPerson.departmentId = tbDepartment.departmentId  where inventoryId = ? ", inventoryHistory.getInventoryId()));


            inventoryPeopeTableView.setItems(list_person);
        }
    }

    public void departmentInventoryCBOnAction(ActionEvent actionEvent) {
        if (departmentNameInventoryCB.getSelectionModel().getSelectedItem() != null) {
            String departmentId = departmentNameInventoryCB.getSelectionModel().getSelectedItem().getDepartmentId();
            var inventoryList = FXCollections.observableArrayList(inventory_db.
                    selectList("select  distinct tbInventory.inventoryId , usableValue, inventoryDate, deviceId  from tbInventory " +
                            "inner join tbPersonAndInventory on tbInventory.inventoryId = tbPersonAndInventory.inventoryId " +
                            "inner join tbPerson on tbPerson.personId = tbPersonAndInventory.personID " +
                            "inner join tbDepartment on tbDepartment.departmentId = tbPerson.departmentId  where tbPerson.departmentId = ? ", departmentId));

            inventoryTableView.setItems(inventoryList);
            clearOldInventory();

        }

    }


    public void clearOldInventory() {
        inventoryPeopeTableView.getItems().clear();
        inventoryDeviceTableView.getItems().clear();
        inventoryDateOfInventoryDP.getEditor().clear();
    }

    public void inventoryCleanBtnOnAction(ActionEvent actionEvent) {
        setUpInventory();
        departmentNameInventoryCB.setValue(null);
        inventoryDateOfInventoryDP.getEditor().clear();
    }

    public void inventorySearchInventoryButtonAction(ActionEvent event) {
        if (departmentNameInventoryCB.getSelectionModel().getSelectedItem() != null) {
            String departmentId = departmentNameInventoryCB.getSelectionModel().getSelectedItem().getDepartmentId();
            var inventoryList = FXCollections.observableArrayList(inventory_db.
                    selectList("select  distinct tbInventory.inventoryId , usableValue, inventoryDate, deviceId  from tbInventory " +
                            "inner join tbPersonAndInventory on tbInventory.inventoryId = tbPersonAndInventory.inventoryId " +
                            "inner join tbPerson on tbPerson.personId = tbPersonAndInventory.personID " +
                            "inner join tbDepartment on tbDepartment.departmentId = tbPerson.departmentId  where tbPerson.departmentId = ? AND inventoryDate = CAST( ? as Date) ", departmentId, inventoryDateOfInventoryDP.getValue().toString()));

            inventoryTableView.setItems(inventoryList);
            inventoryPeopeTableView.getItems().clear();
            inventoryDeviceTableView.getItems().clear();
        }
    }


    @FXML
    public void inventoryExportReportButtonAction(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        Utils.exportExcelInventory(thisStage, inventoryTableView);
    }


    //</editor-fold>


    //<editor-fold desc="TAB REPAIR">
    public TableColumn<REPAIR_HISTORY, String> deviceQuantityRepairColumn;
    public Button repairTabReportBtn;
    @FXML
    TableColumn<?, ?> departmentRepairColumn;
    @FXML
    TableColumn<?, ?> deviceNameRepairColumn;
    @FXML
    TableColumn<?, ?> idRepairColumn;
    @FXML
    DatePicker repairHistoryFromDP;
    @FXML
    DatePicker repairHistoryToDP;
    @FXML
    TableView<REPAIR_HISTORY> repairHistoryTableView;
    @FXML
    private TableColumn<?, ?> priceRepairColumn;
    @FXML
    TableColumn<?, ?> dateRepairColumn;

    @FXML
    void repairTabExportReportBtnAction(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();


        Utils.exportExcelRepair(thisStage, repairHistoryTableView);
    }

    @FXML
    public void repairTabFilterBtn(ActionEvent actionEvent) {
        var database_dao = new DATABASE_DAO<>(REPAIR_HISTORY.class);
        var deviceList = FXCollections.observableArrayList(database_dao.
                selectList("select * from  tbRepair join tbDevice on tbRepair.deviceId = tbDevice.deviceId  where RepairDate >= CAST( ? as Date) AND RepairDate <= CAST( ? as Date)", repairHistoryFromDP.getValue().toString(), repairHistoryToDP.getValue().toString()));
        repairHistoryTableView.setItems(deviceList);
    }

    @FXML
    public void repairTabClearBtnOnAction(ActionEvent actionEvent) {
        var database_dao = new DATABASE_DAO<>(REPAIR_HISTORY.class);
        var deviceList = FXCollections.observableArrayList(database_dao.
                selectList("select * from  tbRepair join tbDevice on tbRepair.deviceId = tbDevice.deviceId"));
        repairHistoryTableView.setItems(deviceList);
        repairHistoryFromDP.getEditor().clear();
        repairHistoryToDP.getEditor().clear();
    }

    public void setUpRepair() {
        repairHistoryFromDP.setConverter(Utils.getConverter(repairHistoryFromDP));
        repairHistoryToDP.setConverter(Utils.getConverter(repairHistoryToDP));

        myTabPane.getStyleClass().add(JMetroStyleClass.UNDERLINE_TAB_PANE);

        // REPAIR
        var database_dao = new DATABASE_DAO<>(REPAIR_HISTORY.class);
        var deviceList = FXCollections.observableArrayList(database_dao.
                selectList("select * from  tbRepair join tbDevice on tbRepair.deviceId = tbDevice.deviceId"));

        idRepairColumn.setCellValueFactory(new PropertyValueFactory<>("fixId"));
        deviceQuantityRepairColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDevice"));
        deviceNameRepairColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        departmentRepairColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        priceRepairColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateRepairColumn.setCellValueFactory(new PropertyValueFactory<>("repairDate"));
        repairHistoryTableView.setItems(deviceList);

    }
    //</editor-fold>


    //<editor-fold desc="TAB TRANSFER">
    public TableColumn<TRANSFORM_HISTORY, String> quantityTransferColumn;
    public TableColumn<TRANSFORM_HISTORY, String> statusTransferColumn;
    public TableColumn<TRANSFORM_HISTORY, String> ebitdaTransferColumn;
    public CheckBox transferEbitdaCB;
    @FXML
    DatePicker liquidationDateOfLiquidationDTP;

    @FXML
    DatePicker inventoryDateOfInventoryDTP;

    @FXML
    TextField inventoryDepartmentNameTF;

    @FXML
    Label inventoryDepartmentIdLBL;

    @FXML
    Button inventorySearchInventoryBTN;

    @FXML
    Button backBtn;

    @FXML
    TableColumn<?, ?> deviceNameTransferColumn;

    @FXML
    TableColumn<?, ?> departmentTransferColumn;


    @FXML
    Label transferDepartmentId;


    @FXML
    TextField transferDepartmentName;


    @FXML
    Label transferTotalDevice;


    @FXML
    Label transferTotalPeople;

    @FXML
    Button transferTabExportReportBtn;

    @FXML
    TableView<TRANSFORM_HISTORY> transferredDeviceTableView;

    @FXML
    void transferTabExportReportBtnAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        Utils.exportExcelTransfer(thisStage, transferredDeviceTableView);
    }

    @FXML
    public void onTransferTableViewMouseClicked(MouseEvent mouseEvent) {
        if (transferredDeviceTableView.getSelectionModel().getSelectedItem() != null) {
            // CREATE DEVICE_ADD OBJECT
            int cellIndex = transferredDeviceTableView.getSelectionModel().getSelectedIndex();
            var transferHistory = transferredDeviceTableView.getSelectionModel().getSelectedItem();
            var departmentId = transferHistory.getDepartmentId();
            var database_dao = new DATABASE_DAO<>(DEPARTMENT.class);
            var department = (database_dao.
                    selectOne("select * from  tbDepartment where departmentId = ? ", departmentId));

            if (department != null) {
                transferTotalPeople.setText(department.getTotalPeople());
                transferTotalDevice.setText(department.getTotalDevice());
                transferDepartmentName.setText(department.getDepartmentName());
                transferDepartmentId.setText(department.getDepartmentId());
            }
        }
    }

    @FXML
    public void transferEbitdaCheckBoxOnAction(ActionEvent actionEvent) {

        var transform_db = new DATABASE_DAO<>(TRANSFORM_HISTORY.class);

        ObservableList transferList;
        if (transferEbitdaCB.isSelected()) {
            transferList = FXCollections.observableArrayList(transform_db.
                    selectList("select * from tbTransform join tbDevice on tbTransform.deviceId = tbDevice.DeviceId   join  tbDepartment on tbTransform.departmentId = tbDepartment.DepartmentId where percentDamage >= 50"));

        } else {
            transferList = FXCollections.observableArrayList(transform_db.
                    selectList("select * from tbTransform join tbDevice on tbTransform.deviceId = tbDevice.DeviceId   join  tbDepartment on tbTransform.departmentId = tbDepartment.DepartmentId"));
        }
        deviceNameTransferColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        departmentTransferColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        statusTransferColumn.setCellValueFactory(new PropertyValueFactory<>("deviceStatus"));
        quantityTransferColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDevice"));
        ebitdaTransferColumn.setCellValueFactory(new PropertyValueFactory<>("percentDamage"));
        transferredDeviceTableView.setItems(transferList);
    }

    //</editor-fold>


    //<editor-fold desc="MAIN">

    @FXML
    void backDeviceButtonAction(ActionEvent event) {

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


    @FXML
    public void liquidationExportReportButtonAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(
                () -> {
                    setUpRepair();
                    setUpInventory();
                    setUpLiquidation();
                    // TRANSFER
                    var transform_db = new DATABASE_DAO<>(TRANSFORM_HISTORY.class);
                    var transferList = FXCollections.observableArrayList(transform_db.
                            selectList("select * from tbTransform join tbDevice on tbTransform.deviceId = tbDevice.DeviceId   join  tbDepartment on tbTransform.departmentId = tbDepartment.DepartmentId"));

                    deviceNameTransferColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
                    departmentTransferColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
                    statusTransferColumn.setCellValueFactory(new PropertyValueFactory<>("deviceStatus"));
                    quantityTransferColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDevice"));
                    ebitdaTransferColumn.setCellValueFactory(new PropertyValueFactory<>("percentDamage"));
                    transferredDeviceTableView.setItems(transferList);
                }
        ).start();

        // SET UP CONVERTER
        inventoryDateOfInventoryDP.setConverter(Utils.getConverter(inventoryDateOfInventoryDP));
        liquidationDateOfLiquidationDTP.setConverter(Utils.getConverter(liquidationDateOfLiquidationDTP));

    }


    //</editor-fold>


}
