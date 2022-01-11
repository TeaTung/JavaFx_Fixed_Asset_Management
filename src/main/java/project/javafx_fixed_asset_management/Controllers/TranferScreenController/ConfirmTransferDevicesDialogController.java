package project.javafx_fixed_asset_management.Controllers.TranferScreenController;

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
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.TRANSFORM;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

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
    ObservableList<DEPARTMENT> listDepartment;


    public void getListDepartment() {
        var departments = new DATABASE_DAO<>(DEPARTMENT.class);

        listDepartment = FXCollections.observableArrayList(departments.selectList(
                "SELECT * FROM tbDepartment"));

        System.out.println("LIST DEPARTMENT SIZE: " + listDepartment.size());
    }

    public void backButtonAction(ActionEvent event) {
        backPreviousScreen(event);
    }

    public void backPreviousScreen(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void setInit(ObservableList<DEVICE> listDevice, String id, String department) {
        getListDepartment();

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
            insertDepartment();
            transformSQL.insert(insertSQL(transform));

            backPreviousScreen(event);

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public Integer getRandomId() {
        Random random = new Random();
        int randomId = random.nextInt(999);

        if (listDepartment.size() != 0) {
            for (DEPARTMENT department : listDepartment) {
                if (department.getDepartmentId() != null) {
                    if (randomId == Integer.parseInt(department.getDepartmentId())) {
                        return getRandomId();
                    }
                }
            }
        } else {
            return randomId;
        }
        return randomId;
    }

    public void insertDepartment () {
        boolean isDepartmentExisted = false;
        for (DEPARTMENT department : listDepartment) {
            if (Objects.equals(department.getDepartmentName().toLowerCase(), transformDepartment.toLowerCase())) {
                isDepartmentExisted = true;
            }
        }

        if (!isDepartmentExisted) {
            var departments = new DATABASE_DAO<>(DEPARTMENT.class);

            departments.insert("insert into tbDepartment (DepartmentId, DepartmentName) values (?, ?)",getRandomId().toString(),transformDepartment);
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

        return "insert into tbTransfer (TransferId, Department, DeviceId) values ('" + transform.getTransformId() + "','" + transform.getDepartment() + "','" + list + "')";
    }


}
