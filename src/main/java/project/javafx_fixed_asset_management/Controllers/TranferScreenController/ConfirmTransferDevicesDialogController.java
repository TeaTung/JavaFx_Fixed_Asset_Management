package project.javafx_fixed_asset_management.Controllers.TranferScreenController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Models.*;

import java.util.Optional;

public class ConfirmTransferDevicesDialogController {
    public TextField transferIDTF;
    public TextField deptNameTF;
    public TextField dateTF;
    JMetro jMetro = new JMetro(Style.LIGHT);
    @FXML
    Label transferIdLbl;

    @FXML
    Label repairDateLbl;

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
    String transformDepartmentId;
    String transformDepartment;
    String transformDate;

    ObservableList<DEVICE> listTransformDevice;

    public void backButtonAction(ActionEvent event) {
        backPreviousScreen(event);
    }

    public void backPreviousScreen(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void setInit(ObservableList<DEVICE> listDevice, String id, String departmentName, String date, String departmentId) {
        transformId = id;
        transformDepartmentId = departmentId;
        listTransformDevice = listDevice;
        transformDate = date;
        transformDepartment = departmentName;

        System.out.println("DEPARTMENT: " + departmentId);
        System.out.println("DATE: " + date);

        transferIDTF.setText(id);
        deptNameTF.setText(departmentName);
        dateTF.setText(date);
        setTableView(listTransformDevice);
    }

    public void setTableView(ObservableList<DEVICE> list) {
        idDeviceTransferColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceId"));
        deviceTransferNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("DeviceName"));
        specificationDeviceTransferNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("Specification"));

        deviceTransferTableView.setItems(list);
    }

    public void confirmButtonAction(ActionEvent event) {
        FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure to transform devices");
        jMetro.setScene(alert.getDialogPane().getScene());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            var transformSQL = new DATABASE_DAO<>(TRANSFORM_HISTORY.class);

            ObservableList<String> listDevice = FXCollections.observableArrayList();
            TRANSFORM_HISTORY transform = new TRANSFORM_HISTORY();
            transform.setTransformId(transformId);
            transform.setDepartmentId(transformDepartmentId);
            transform.setTransferDate(transformDate);
            transform.setDepartment(transformDepartment);
            for (int i = 0; i < listTransformDevice.size(); i++) {
                listDevice.add(listTransformDevice.get(i).getDeviceId());
            }
            transform.setListDevice(listDevice);

            changeDeviceStatus(listDevice);
            transformSQL.insert(insertSQL(transform));

            FlatAlert alert1 = new FlatAlert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("Transfer Successfully");
            jMetro.setScene(alert1.getDialogPane().getScene());
            alert1.show();

            backPreviousScreen(event);

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public void changeDeviceStatus(ObservableList<String> listDevice) {
        var devices = new DATABASE_DAO<>(DEVICE.class);
        for (String device : listDevice) {
            devices.update("UPDATE tbDevice SET tbDevice.DeviceStatus = ? WHERE deviceId = ?", "Transferred", device);
        }
    }

    public String insertSQL(TRANSFER transform) {
        String list = "";
        for (int i = 0; i < transform.getListDevice().size(); i++) {
            list += transform.getListDevice().get(i);

            if (i < transform.getListDevice().size() - 1) {
                list += (",");
            }
        }
        System.out.println("LIST: " + list);

        return "insert into tbTransfer (TransferId, DepartmentId, DeviceId, TransferDate, Department) values ('" + transform.getTransformId() + "','" + transform.getDepartmentId() + "','" + list + "','" + dateTF.getText() + "','" + transform.getDepartment() + "')";
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

    public void onCloseWinBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
    }
}
