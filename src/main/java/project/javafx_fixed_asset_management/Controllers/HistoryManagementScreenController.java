package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;
import project.javafx_fixed_asset_management.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class HistoryManagementScreenController implements Initializable {


    public TabPane myTabPane;
    public TableColumn<LIQUIDATION_HISTORY, String> idLiquidationColumn;
    public TableColumn<LIQUIDATION_HISTORY, String> departmentNameLiquidationColumn;
    public TableColumn<LIQUIDATION_HISTORY, String> dateLiquidationColumn;
    private static double xOffset = 0;
    private static double yOffset = 0;
    public VBox weekDeviceLineChartVB;
    public VBox transferBarChartVB;
    public ComboBox<Integer> yearGraphCB;
    public ComboBox<Month> monthGraphCB;
    public HBox repairDeviceAreaChartVB;

    public void setUpDeviceLineChart() {
        final NumberAxis xAxis = new NumberAxis();

        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of week");
        yAxis.setLabel("Device per week");

        //creating the chart
        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Device Sumary");
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        var db = new DATABASE_DAO<>(WEEK_DEVICE_HISTORY.class);


        var week_history = FXCollections.observableArrayList(db.selectList(
                "SELECT top 20 " +
                        "( datepart(day, datediff(day, 0, importDate)/7 * 7)/7 + 1 )  as weekNumber, " +
                        "SUM(tbDevice.quantityDevice)  as numberOfDevice  " +
                        "FROM tbContract inner join tbDevice on tbContract.contractId = tbDevice.contractId " +
                        "where MONTH(importDate) = ? AND YEAR (importDate) = ? " +
                        "group by ( datepart(day, datediff(day, 0, importDate)/7 * 7)/7 + 1 ) " +
                        "order by ( datepart(day, datediff(day, 0, importDate)/7 * 7)/7 + 1 )",
                String.valueOf(monthGraphCB.getSelectionModel().getSelectedItem().getValue()), yearGraphCB.getValue().toString()
        ));

        var data = (getLineChartDataFromList(week_history, "quantity"));
        lineChart.getData().add(data);
        weekDeviceLineChartVB.getChildren().add(lineChart);

    }


    public void setUpTransferBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Department Summary");
        yAxis.setLabel("Device per Department");


        var db = new DATABASE_DAO<>(TRANSFER_BAR_CHART_HISTORY.class);
        var transfer_list = FXCollections.observableArrayList(db.selectList("select s.department,  SUM(quantityDevice) as quantity from  " +
                "tbDevice inner join ( " +
                "select Department,value deviceId from tbTransfer  " +
                "CROSS APPLY STRING_SPLIT(deviceId, ',')  " +
                "WHERE MONTH(tbTransfer.TransferDate) = ?  AND YEAR(tbTransfer.TransferDate) = ? " +
                ") s  on tbDevice.deviceId = s.deviceId  " +
                "GROUP BY Department " +
                "order by SUM(quantityDevice) DESC" +
                ";", String.valueOf(monthGraphCB.getSelectionModel().getSelectedItem().getValue()), yearGraphCB.getValue().toString()));

        bc.getData().addAll(getTransferBarChartDataFromList(transfer_list, "Department"));
        bc.setMinHeight(590);
        transferBarChartVB.getChildren().add(bc);
    }

    public void setUpRepairLineChart() {
        var db = new DATABASE_DAO<>(REPAIR_GRAPH_HISTORY.class);
        var week_history = FXCollections.observableArrayList(db.selectList(
                "select Department " +
                        ",sum(quantityDevice) as quantity  from " +
                        "tbDevice " +
                        "inner join ( " +
                        "select tbRepair.RepairDate, value deviceId from tbRepair  " +
                        "CROSS APPLY STRING_SPLIT(deviceId, ',')  " +
                        ") s  on tbDevice.deviceId = s.deviceId " +
                        "INNER JOIN ( " +
                        "select department ,value deviceId from tbTransfer  " +
                        "CROSS APPLY STRING_SPLIT(deviceId, ',')  " +
                        ") d on s.DeviceId = d.deviceId " +
                        "where MONTH(s.RepairDate) = ? AND YEAR(s.repairDate) = ? " +
                        "group by Department",
                String.valueOf(monthGraphCB.getSelectionModel().getSelectedItem().getValue()), yearGraphCB.getValue().toString()
        ));


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();

        int length = week_history.size();
        for (int i = 0; i < length; i++) {
            pieChartData.add(new PieChart.Data(
                    week_history.get(i).getDepartment(),
                    week_history.get(i).getQuantity()));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setClockwise(true);
        chart.setStartAngle(40);
        chart.setTitle("Department Repair");
        repairDeviceAreaChartVB.getChildren().add(0, chart);


        var db_inventory = new DATABASE_DAO<>(INVENTORY_GRAPH_INVENTORY.class);
        var inventory_history = FXCollections.observableArrayList(db_inventory.selectList(
                "select department, sum(quantityDevice) as quantity from tbInventory " +
                        "inner join tbDevice on tbDevice.deviceId = tbInventory.deviceID " +
                        "inner join tbTransfer on tbDevice.DeviceId= tbTransfer.DeviceId " +
                        "where MONTH(TransferDate) = ? AND YEAR(TransferDate) = ? " +
                        "group by department",
                String.valueOf(monthGraphCB.getSelectionModel().getSelectedItem().getValue()), yearGraphCB.getValue().toString()
        ));


        ObservableList<PieChart.Data> pieChartDataInventory =
                FXCollections.observableArrayList();


        int length_inventory = inventory_history.size();
        for (int i = 0; i < length_inventory; i++) {
            pieChartDataInventory.add(new PieChart.Data(
                    inventory_history.get(i).getDepartment(),
                    inventory_history.get(i).getQuantity()));
        }


        final PieChart chart2 = new PieChart(pieChartDataInventory);
        chart.setTitle("Department Inventory");
        chart2.setClockwise(true);
        chart2.setStartAngle(-40);
        repairDeviceAreaChartVB.getChildren().add(1, chart2);


    }

    public XYChart.Series<Number, Number> getLineChartDataFromList(ObservableList<WEEK_DEVICE_HISTORY> observableList, String seriesName) {
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName(seriesName);
        var length = observableList.size();
        for (int i = 0; i < length; i++) {
            series.getData().add(new XYChart.Data(Integer.parseInt(observableList.get(i).getWeekNumber()), Integer.parseInt(observableList.get(i).getNumberOfDevice())));
        }
        return series;
    }


    public XYChart.Series getTransferBarChartDataFromList(ObservableList<TRANSFER_BAR_CHART_HISTORY> observableList, String seriesName) {

        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);
        var length = observableList.size();
        for (int i = 0; i < length; i++) {
            series.getData().add(new XYChart.Data(observableList.get(i).getDepartment(), observableList.get(i).getQuantity()));
        }
        return series;
    }

    //<editor-fold desc="TAB LIQUIDATION">

    public DATABASE_DAO<LIQUIDATION_HISTORY> liquidation_db = new DATABASE_DAO<>(LIQUIDATION_HISTORY.class);
    public TableView<LIQUIDATION_HISTORY> liquidationTableView;

    public TableColumn<DEVICE_ADD, String> nameLiquidationColumn;
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
                selectList("select department, liquidationId,  liquidationDate  from tbDevice join tbTransfer on tbDevice.DeviceId = tbTransfer.DeviceId join tbLiquidation on tbDevice.deviceId = tbLiquidation.deviceId "));

        idLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("liquidationId"));
        departmentNameLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        dateLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("liquidationDate"));
        liquidationTableView.setItems(liquidationList);


        //set up DEVICE TABLE VIEW
        nameLiquidationColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
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
                    selectList("select * from tbDevice inner join tbLiquidation  on tbLiquidation.DeviceId = tbDevice.deviceId  join tbTransfer on tbLiquidation.DeviceId = tbTransfer.DeviceId inner join tbDeviceModel on tbDevice.modelId = tbDeviceModel.modelId WHERE liquidationID = ?", liquidationTableView.getSelectionModel().getSelectedItem().getLiquidationId()));
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
                    selectList("select department, liquidationId,  liquidationDate  from tbDevice join tbTransfer on tbDevice.DeviceId = tbTransfer.DeviceId join tbLiquidation on tbDevice.deviceId = tbLiquidation.deviceId WHERE liquidationDate = CAST( ? AS DATE ) ", liquidationDateOfLiquidationDTP.getValue().toString()));
            liquidationTableView.setItems(liquidationList);
            liquidationDeviceTableView.getItems().clear();
            liquidationPersonTableView.getItems().clear();

        }

    }

    @FXML
    public void liquidationExportReportButtonAction(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        try {
            Utils.exportExcelLiquidation(thisStage, liquidationTableView);
        } catch (Exception e) {

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
                selectList("select *  from tbDepartment "));

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


        try {
            Utils.exportExcelInventory(thisStage, inventoryTableView);
        } catch (Exception e) {

        }
    }


    //</editor-fold>


    //<editor-fold desc="TAB REPAIR">
    public TableColumn<DEVICE_ADD, String> deviceQuantityRepairColumn;
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
    TableView<REPAIR> repairHistoryTableView;
    @FXML
    private TableColumn<?, ?> priceRepairColumn;
    @FXML
    TableColumn<?, ?> dateRepairColumn;
    public TableView<DEVICE_ADD> repairDeviceTableView;
    public TableColumn<DEVICE_ADD, String> deviceIdRepairColumn;


    @FXML
    void repairTabExportReportBtnAction(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        try {

            Utils.exportExcelRepair(thisStage, repairHistoryTableView);
        } catch (Exception e) {

        }
    }

    @FXML
    public void repairTabFilterBtn(ActionEvent actionEvent) {
        var database_dao = new DATABASE_DAO<>(REPAIR.class);
        var deviceList = FXCollections.observableArrayList(database_dao.
                selectList("select * from  tbRepair where RepairDate >= CAST( ? as Date) AND RepairDate <= CAST( ? as Date)", repairHistoryFromDP.getValue().toString(), repairHistoryToDP.getValue().toString()));
        repairHistoryTableView.setItems(deviceList);
        repairDeviceTableView.getItems().clear();
    }

    @FXML
    public void repairTabClearBtnOnAction(ActionEvent actionEvent) {
        var database_dao = new DATABASE_DAO<>(REPAIR.class);
        var deviceList = FXCollections.observableArrayList(database_dao.
                selectList("select * from  tbRepair"));
        repairHistoryTableView.setItems(deviceList);
        repairHistoryFromDP.getEditor().clear();
        repairHistoryToDP.getEditor().clear();
    }

    public void setUpRepair() {
        repairHistoryFromDP.setConverter(Utils.getConverter(repairHistoryFromDP));
        repairHistoryToDP.setConverter(Utils.getConverter(repairHistoryToDP));

        //myTabPane.getStyleClass().add(JMetroStyleClass.UNDERLINE_TAB_PANE);

        // REPAIR
        var database_dao = new DATABASE_DAO<>(REPAIR.class);
        var repair_histories = FXCollections.observableArrayList(database_dao.
                selectList("select * from  tbRepair"));

        // SET UP REPAIR TABLE
        idRepairColumn.setCellValueFactory(new PropertyValueFactory<>("fixId"));
        departmentRepairColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        priceRepairColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateRepairColumn.setCellValueFactory(new PropertyValueFactory<>("repairDate"));
        repairHistoryTableView.setItems(repair_histories);


        // SET UP DEVICE TABLE
        deviceIdRepairColumn.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        deviceQuantityRepairColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDevice"));
        deviceNameRepairColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));


    }

    public void repairHistoryTableViewOnMouseClicked(MouseEvent mouseEvent) {

        if (repairHistoryTableView.getSelectionModel().getSelectedItem() != null) {
            var listDeviceID = repairHistoryTableView.getSelectionModel().getSelectedItem().getDeviceId().split(",");
            var length = listDeviceID.length;
            StringBuilder selectString = new StringBuilder("SELECT * FROM tbDevice WHERE  deviceId =  '" + listDeviceID[0] + "'");
            for (int i = 1; i < length; i++) {
                selectString.append(" OR deviceId =  '").append(listDeviceID[i]).append("'");
                if (i < length - 2) {
                    selectString.append(" , ");
                }

            }

            var device_db = new DATABASE_DAO<>(DEVICE_ADD.class);
            var repaired_devices = FXCollections.observableArrayList(device_db.
                    selectList(selectString.toString()));
            repairDeviceTableView.setItems(repaired_devices);

        }
    }
    //</editor-fold>


    //<editor-fold desc="TAB TRANSFER">
    public TableColumn<TRANSFORM_HISTORY, String> quantityTransferColumn;
    public TableColumn<TRANSFORM_HISTORY, String> ebitdaTransferColumn;
    public CheckBox transferEbitdaCB;
    public TableColumn<DEVICE_ADD, String> deviceIdTransferColumn;
    public TableView<TRANSFORM_HISTORY> transferredTableView;
    public TableColumn<TRANSFORM_HISTORY, String> idTransferredColumn;
    public TableColumn<TRANSFORM_HISTORY, String> dateTransferColumn;

    @FXML
    TableView<DEVICE_ADD> transferredDeviceTableView;
    @FXML
    DatePicker liquidationDateOfLiquidationDTP;


    @FXML
    Button inventorySearchInventoryBTN;

    @FXML
    Button backBtn;

    @FXML
    TableColumn<?, ?> deviceNameTransferColumn;

    @FXML
    TableColumn<?, ?> departmentTransferColumn;


    @FXML
    TextField transferDepartmentId;


    @FXML
    TextField transferDepartmentName;


    @FXML
    TextField transferTotalDevice;


    @FXML
    Button transferTabExportReportBtn;

    ObservableList<DEVICE_ADD> transferred_devices;

    @FXML
    void transferTabExportReportBtnAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        try {
            Utils.exportExcelTransfer(thisStage, transferredTableView);
        } catch (Exception e) {

        }
    }

    @FXML
    public void onTransferTableViewMouseClicked(MouseEvent mouseEvent) {
//        try {
        if (transferredTableView.getSelectionModel().getSelectedItem() != null) {

            // SHOW DEPARTMENT
            var transferHistory = transferredTableView.getSelectionModel().getSelectedItem();
            var departmentId = transferHistory.getDepartmentId();
            var database_dao = new DATABASE_DAO<>(DEPARTMENT.class);
            var department = (database_dao.
                    selectOne("select * from  tbDepartment where departmentId = ? ", departmentId));

            if (department != null) {
                transferTotalDevice.setText(department.getTotalDevice());
                transferDepartmentName.setText(department.getDepartmentName());
                transferDepartmentId.setText(department.getDepartmentId());
            }


            // SHOW DEVICES
            var listDeviceId = transferredTableView.getSelectionModel().getSelectedItem().getDeviceID().split(",");
            var length = listDeviceId.length;
            StringBuilder selectString = new StringBuilder("SELECT * FROM tbDevice WHERE  deviceId =  '" + listDeviceId[0] + "'");
            for (int i = 1; i < length; i++) {
                selectString.append(" OR deviceId =  '").append(listDeviceId[i]).append("'");
                if (i < length - 2) {
                    selectString.append(" , ");
                }

            }
            var device_db = new DATABASE_DAO<>(DEVICE_ADD.class);
            transferred_devices = FXCollections.observableArrayList(device_db.
                    selectList(selectString.toString()));
            transferredDeviceTableView.setItems(transferred_devices);
        }
//        } catch (Exception e) {
//            JMetro jMetro = new JMetro(Style.LIGHT);
//            FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
//            jMetro.setScene(alert.getDialogPane().getScene());
//            alert.setContentText("Connection Error");
//            alert.setHeaderText("Action failed");
//            alert.showAndWait();
//        }
    }

    @FXML
    public void transferEbitdaCheckBoxOnAction(ActionEvent actionEvent) {

        if (transferred_devices != null) {
            ObservableList transferList = FXCollections.observableArrayList(transferred_devices);
            if (transferEbitdaCB.isSelected()) {
                transferList = transferList.filtered(new Predicate<DEVICE_ADD>() {
                    @Override
                    public boolean test(DEVICE_ADD o) {
                        return (o.getPercentDamage() >= 50);
                    }
                });
            }
            transferredDeviceTableView.setItems(transferList);
        }
    }


    public void setUpTransfer() {
        var transform_db = new DATABASE_DAO<>(TRANSFORM_HISTORY.class);
        var transferList = FXCollections.observableArrayList(transform_db.
                selectList("select * from tbTransfer"));


        // SET UP TRANSFER TABLE VIEW
        idTransferredColumn.setCellValueFactory(new PropertyValueFactory<>("transferId"));
        departmentTransferColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        dateTransferColumn.setCellValueFactory(new PropertyValueFactory<>("transferDate"));
        transferredTableView.setItems(transferList);


        // SET UP DEVICES TABLE VIEW
        deviceIdTransferColumn.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        deviceNameTransferColumn.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        ebitdaTransferColumn.setCellValueFactory(new PropertyValueFactory<>("percentDamage"));
        quantityTransferColumn.setCellValueFactory(new PropertyValueFactory<>("quantityDevice"));
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

    public void onMinimizeBtnOnActio(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    public void onCloseWinBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
    }


    //</editor-fold>


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(
                () -> {
                    setUpRepair();
                    setUpInventory();
                    setUpLiquidation();
                    setUpTransfer();
                }

        ).start();


        monthGraphCB.setItems(FXCollections.observableArrayList(Month.values()));
        yearGraphCB.getItems().add(2019);
        yearGraphCB.getItems().add(2020);
        yearGraphCB.getItems().add(2021);
        yearGraphCB.getItems().add(2022);
        yearGraphCB.getItems().add(2023);

        yearGraphCB.getSelectionModel().select(2);
        monthGraphCB.getSelectionModel().select(11);


        // SET UP CONVERTER
        inventoryDateOfInventoryDP.setConverter(Utils.getConverter(inventoryDateOfInventoryDP));
        liquidationDateOfLiquidationDTP.setConverter(Utils.getConverter(liquidationDateOfLiquidationDTP));

    }


    public void onTabChanged(Event event) {
        System.out.println(myTabPane.getSelectionModel().getSelectedIndex());
        if (myTabPane.getSelectionModel().getSelectedIndex() == 4) {

            transferBarChartVB.getChildren().clear();
            weekDeviceLineChartVB.getChildren().clear();
            repairDeviceAreaChartVB.getChildren().clear();
            setUpDeviceLineChart();
            setUpTransferBarChart();
            setUpRepairLineChart();
        }
    }

    public void comboBoxChanged(ActionEvent actionEvent) {
        onTabChanged(null);
    }
}
