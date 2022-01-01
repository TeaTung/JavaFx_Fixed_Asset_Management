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
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.REPAIR;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConfirmRepairDevicesDialogController {
    @FXML
    Label repairingDecisionIdLbl;

    @FXML
    Label priceLbl;

    @FXML
    Label repairingCompanyLbl;

    @FXML
    Label repairingDateLbl;

    @FXML
    Button confirmButton;

    @FXML
    Button backBtn;

    @FXML
    TableView<DEVICE> deviceRepairingTableView;

    @FXML
    TableColumn<DEVICE, String> idDeviceRepairingColumn;

    @FXML
    TableColumn<DEVICE, String> deviceRepairingNameColumn;

    @FXML
    TableColumn<DEVICE, String> specificationDeviceRepairingNameColumn;

    String repairingId;
    String repairingPrice;
    String repairingCompany;
    LocalDate repairDay;
    ObservableList<DEVICE> listRepairingDevice;

    public void setInit(ObservableList<DEVICE> listDevice, String id, String price, String company, LocalDate repairingDay) {
        repairingId = id;
        repairingPrice = price;
        repairingCompany = company;
        repairDay = repairingDay;
        listRepairingDevice = listDevice;

        repairingDecisionIdLbl.setText(id);
        priceLbl.setText(price);
        repairingCompanyLbl.setText(company);
        repairingDateLbl.setText(repairingDay.toString());
        setTableView(listRepairingDevice);


    }

    public void setTableView(ObservableList<DEVICE> list) {
        idDeviceRepairingColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceId"));
        deviceRepairingNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceName"));
        specificationDeviceRepairingNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("Specification"));

        deviceRepairingTableView.setItems(list);
    }

    public void backButtonAction(ActionEvent event) {
        backPreviousScreen(event);
    }

    public void backPreviousScreen (ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/RepairScreen/repair_devices_screen.fxml"));
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
    @FXML
    public void confirmButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Repair Device");
        alert.setHeaderText("Are you sure to repair devices");
        alert.setContentText("Devices below will be repaired: \n");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            var repairSQL = new DATABASE_DAO<>(REPAIR.class);

            for (int i = 0; i < listRepairingDevice.size(); i++) {
                REPAIR repair = new REPAIR();
                repair.setRepairDate(repairDay);
                repair.setCompany(repairingCompany);
                repair.setDeviceId(listRepairingDevice.get(i).getDeviceId());
                repair.setFixId(repairingId);
                repair.setPrice(Float.parseFloat(repairingPrice));

                System.out.println(repair.getRepairDate());
                System.out.println(repair.getPrice());
                System.out.println(repair.getDeviceId());
                System.out.println(repair.getCompany());
                System.out.println(repair.getFixId());

                repairSQL.insert(insertSQL(repair));
            }

            backPreviousScreen(event);
        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public String insertSQL (REPAIR repair) {
        return "insert into tbRepair (FixId, DeviceId, RepairDate, Company, Price) values ('"+repair.getFixId()+"','"+repair.getDeviceId()+"','"+repair.getRepairDate()+"','"+repair.getCompany()+"','"+repair.getPrice()+"')";
    }
}
