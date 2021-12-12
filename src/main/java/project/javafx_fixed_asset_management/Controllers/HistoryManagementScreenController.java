package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HistoryManagementScreenController {
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
    TableColumn<?, ?> deviceNameColumn;

    @FXML
    TableColumn<?, ?> deviceNameColumn1;

    @FXML
    TableColumn<?, ?> idColumn;

    @FXML
    TableColumn<?, ?> idColumn1;

    @FXML
    TableColumn<?, ?> priceColumn;

    @FXML
    TableColumn<?, ?> priceColumn1;

    @FXML
    DatePicker repairHistoryFromDP;

    @FXML
    TableView<?> repairHistoryTableView;

    @FXML
    DatePicker repairHistoryToDP;

    @FXML
    Label repairTabDepartmentIdTF;

    @FXML
    Label repairTabDepartmentIdTF1;

    @FXML
    TextField repairTabDepartmentTF;

    @FXML
    TextField repairTabDepartmentTF1;

    @FXML
    Button repairTabExportReportBtn;

    @FXML
    Label repairTabTotalDeviceTF;

    @FXML
    Label repairTabTotalDeviceTF1;

    @FXML
    Label repairTabTotalPeopleTF;

    @FXML
    Label repairTabTotalPeopleTF1;

    @FXML
    Button transferTabExportReportBtn;

    @FXML
    TableView<?> transferredDeviceTableView;

    @FXML
    TableColumn<?, ?> typeOfDeviceColumn;

    @FXML
    private TableColumn<?, ?> unitColumn;

    @FXML
    private TableColumn<?, ?> unitColumn1;

    @FXML
    private TableColumn<?, ?> unitColumn11;

    @FXML
    TableColumn<?, ?> unitColumn111;

    @FXML
    void backDeviceButtonAction(ActionEvent event) {

    }

    @FXML
    void repairTabExportReportBtnAction(ActionEvent event) {

    }

    @FXML
    void transferTabExportReportBtnAction(ActionEvent event) {

    }

    @FXML
    public void inventorySearchInventoryButtonAction(ActionEvent event) {

    }

    @FXML
    public void liquidationExportReportButtonAction(ActionEvent event) {

    }

    @FXML
    public void inventoryExportReportButtonAction(ActionEvent event) {

    }


}
