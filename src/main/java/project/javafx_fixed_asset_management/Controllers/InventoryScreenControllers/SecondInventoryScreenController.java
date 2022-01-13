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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecondInventoryScreenController implements Initializable {
    @FXML
    Button addBtn;

    @FXML
    Button backBtn;

    @FXML
    Button continueBtn;

    @FXML
    TextField searchTF;

    @FXML
    Text errorLabel;

    @FXML
    TableColumn<DEVICE, String> existedDeviceDamageColumn;


    @FXML
    TableColumn<DEVICE, String> existedDeviceIdColumn;

    @FXML
    TableColumn<DEVICE, String> existedDeviceNameColumn;

    @FXML
    TableColumn<DEVICE, String> existedDeviceStatusColumn;

    @FXML
    TableView<DEVICE> existedDeviceTB;

    @FXML
    TableColumn<DEVICE, String> inventoryDeviceDamageColumn;


    @FXML
    TableColumn<DEVICE, String> inventoryDeviceIdColumn;

    @FXML
    TableColumn<DEVICE, String> inventoryDeviceNameColumn;

    @FXML
    TableColumn<DEVICE, String> inventoryDeviceStatusColumn;

    @FXML
    TableView<DEVICE> inventoryDeviceTB;

    @FXML
    Button removeBtn;

    @FXML
    ComboBox<DEPARTMENT> departmentCbb;

    @FXML
    Button searchBtn;

    public ObservableList<DEVICE> listDevice;
    public ObservableList<DEVICE> listInventoryDevice;
    public ObservableList<PERSON> listInventoryPeople;
    FilteredList<DEVICE> filteredList;
    public ObservableList<DEPARTMENT> listDepartment;
    public String departmentId;
    LocalDate inventoryDate;

    void initData(ObservableList<PERSON> listInventoryPeople, LocalDate inventoryDate) {
        this.listInventoryPeople = listInventoryPeople;
        this.inventoryDate = inventoryDate;
    }

    @FXML
    void continueBtnAction(ActionEvent event) {
        if (validateData() == true) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/third_inventory_screen.fxml"));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1280, 720);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ThirdInventoryScreenController thirdInventoryScreenController = fxmlLoader.getController();
            thirdInventoryScreenController.initData(listInventoryPeople, inventoryDate, listInventoryDevice);

            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);

            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void addBtnAction(ActionEvent event) {
        DEVICE addingDevice = existedDeviceTB.getSelectionModel().getSelectedItem();

        if (addingDevice != null) {
            listInventoryDevice.add(addingDevice);
            listDevice.remove(addingDevice);
            inventoryDeviceTB.setItems(listInventoryDevice);
            errorLabel.setVisible(false);
        }
    }


    @FXML
    void removeBtnAction(ActionEvent event) {
        DEVICE removingDevice = inventoryDeviceTB.getSelectionModel().getSelectedItem();

        if (removingDevice != null) {
            listDevice.add(removingDevice);
            listInventoryDevice.remove(removingDevice);
            existedDeviceTB.setItems(listDevice);
        }
    }

    @FXML
    void textChangeHandler(KeyEvent event) {
        setSearchInTableView();
    }

    @FXML
    public void backButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/first_inventory_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 745);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProperty();
        getDataInTableView();
        setSearchInTableView();
        errorLabel.setVisible(false);
    }

    boolean validateData() {
        if (listInventoryDevice.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Please add device to inventory!");
            return false;
        }
        errorLabel.setVisible(false);
        return true;
    }

    @FXML
    void cbbOnChangeListener(ActionEvent event) {
        DEPARTMENT selectedDepartment = (DEPARTMENT) departmentCbb.getValue();
        departmentId = selectedDepartment.getDepartmentId();
        getDataInTableView();
    }

    private void setProperty() {
        System.out.println("JUMPED");
        var departments = new DATABASE_DAO<>(DEPARTMENT.class);
        listDepartment = FXCollections.observableArrayList(departments.selectList(
                "SELECT * FROM tbDepartment"
        ));
        listDepartment.add(0, new DEPARTMENT("All Department"));
        departmentCbb.setItems(listDepartment);
        departmentCbb.setValue(listDepartment.get(0));
    }

    @FXML
    void searchHandler(KeyEvent event) {
        setSearchInTableView();
    }

    private void getDataInTableView() {
        var devices = new DATABASE_DAO<>(DEVICE.class);
        var transfer = new DATABASE_DAO<>(TRANSFER.class);
        ObservableList<TRANSFER> listTransfer;
        ArrayList<String> listDeviceId = new ArrayList<>();
        try {
            if (departmentId == null || departmentId.equals("All Department")) {
                listTransfer = FXCollections.observableArrayList(transfer.selectList("SELECT DeviceId FROM TBTRANSFER"));
                String[] listDeviceIdTemp;
                for (int i = 0; i < listTransfer.size(); i++) {
                    String listDeviceIDString = listTransfer.get(i).getDeviceID();
                    listDeviceIdTemp = listDeviceIDString.split(",");
                    for (int j = 0; j < listDeviceIdTemp.length; j++) {
                        listDeviceId.add(listDeviceIdTemp[j]);
                    }
                }
            } else {
                listTransfer = FXCollections.observableArrayList(transfer.selectList("SELECT DeviceId FROM TBTRANSFER WHERE DepartmentId = '" + departmentId + "'"));
                String[] listDeviceIdTemp;
                for (int i = 0; i < listTransfer.size(); i++) {
                    String listDeviceIDString = listTransfer.get(i).getDeviceID();
                    listDeviceIdTemp = listDeviceIDString.split(",");
                    for (int j = 0; j < listDeviceIdTemp.length; j++) {
                        listDeviceId.add(listDeviceIdTemp[j]);
                    }
                }
            }
        } catch (Exception e) {
            FlatAlert information = new FlatAlert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }

        try {
            if (!listDeviceId.isEmpty()) {
                StringBuilder selectString = new StringBuilder("SELECT DEVICEID, DEVICENAME, DEVICESTATUS, YEARUSED, YEARMANUFACTURE, PRICE, SPECIFICATION, PercentDamage FROM tbDevice WHERE  deviceId =  '" + listDeviceId.get(0).toString() + "'");
                for (int i = 1; i < listDeviceId.size(); i++) {
                    selectString.append(" OR deviceId =  '").append(listDeviceId.get(i).toString()).append("'");

                }
                listDevice = FXCollections.observableArrayList(devices.selectList(selectString.toString()));
            } else {
                listDevice.clear();
            }
        } catch (Exception e) {
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }

        listInventoryDevice = FXCollections.observableArrayList();

        existedDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceId"));
        existedDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceName"));
        existedDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("percentDamage"));
        existedDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceStatus"));

        inventoryDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceId"));
        inventoryDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceName"));
        inventoryDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("percentDamage"));
        inventoryDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceStatus"));

        existedDeviceTB.setItems(listDevice);
    }

    public void setSearchInTableView() {
        filteredList = new FilteredList<>(listDevice, b -> true);

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

        sortedList.comparatorProperty().bind(existedDeviceTB.comparatorProperty());

        existedDeviceTB.setItems(sortedList);
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
