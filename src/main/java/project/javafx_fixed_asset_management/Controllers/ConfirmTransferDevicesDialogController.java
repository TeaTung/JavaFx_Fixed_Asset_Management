package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.TRANSFORM;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ConfirmTransferDevicesDialogController {
    @FXML
    Label transferIdLbl;

    @FXML
    Label departmentLbl;

    @FXML
    Button confirmButton;

    @FXML
    Button backBtn;

    @FXML
    TableView<DEVICE> deviceTransferTableView;

    @FXML
    TableColumn<DEVICE, String> idDeviceTransferColumn;

    @FXML
    TableColumn<DEVICE, String> deviceTransferNameColumn;

    @FXML
    TableColumn<DEVICE, String> specificationDeviceTransferNameColumn;

    String transformId;
    String transformDepartment;

    ObservableList<DEVICE> listTransformDevice;

    public void backButtonAction(ActionEvent event) {
        backPreviousScreen(event);
    }

    public void backPreviousScreen(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/TransferScreen/transfer_devices_screen.fxml"));
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

    public void setInit(ObservableList<DEVICE> listDevice, String id, String department) {
        transformId = id;
        transformDepartment = department;
        listTransformDevice = listDevice;

        transferIdLbl.setText(id);
        departmentLbl.setText(department);
        setTableView(listTransformDevice);
    }

    public void setTableView(ObservableList<DEVICE> list) {
        idDeviceTransferColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceId"));
        deviceTransferNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceName"));
        specificationDeviceTransferNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("Specification"));

        deviceTransferTableView.setItems(list);
    }

    public void confirmButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transform Device");
        alert.setHeaderText("Are you sure to transform devices");
        alert.setContentText("Devices below will be transformed: \n");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            var transformSQL = new DATABASE_DAO<>(TRANSFORM.class);

            ObservableList<String> listDevice = FXCollections.observableArrayList();
            TRANSFORM transform = new TRANSFORM();
            transform.setTransformId(transformId);
            transform.setDepartment(transformDepartment);
            for (int i = 0; i < listTransformDevice.size(); i++) {
                listDevice.add(listTransformDevice.get(i).getDeviceId());
            }
            transform.setListDevice(listDevice);

            System.out.println("LIST SIZE: " + transform.getListDevice().size());

            transformSQL.insert(insertSQL(transform));

            backPreviousScreen(event);
        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public String insertSQL(TRANSFORM transform) {
        String list = "";
        for (int i = 0; i < transform.getListDevice().size(); i++) {
            list += transform.getListDevice().get(i) ;

            if ( i < transform.getListDevice().size() - 1) {
                list += (",");
            }
        }
        System.out.println("LIST: " + list);

        return "insert into tbTransform (TransformId, Department, DeviceId) values ('" + transform.getTransformId() + "','" + transform.getDepartment() + "','" + list + "')";
    }
}
