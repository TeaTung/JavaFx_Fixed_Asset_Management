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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecondLiquidationScreenController implements Initializable {
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
    TableColumn<DEVICE, String> liquidationDeviceDamageColumn;


    @FXML
    TableColumn<DEVICE, String> liquidationDeviceIdColumn;

    @FXML
    TableColumn<DEVICE, String> liquidationDeviceNameColumn;

    @FXML
    TableColumn<DEVICE, String> liquidationDeviceStatusColumn;

    @FXML
    TableView<DEVICE> liquidationDeviceTB;

    @FXML
    Button removeBtn;

    @FXML
    ComboBox<DEPARTMENT> departmentCbb;

    public ObservableList<DEVICE> listDevice;
    public ObservableList<DEVICE> listLiquidationDevice;
    public ObservableList<PERSON> listLiquidationPeople;
    FilteredList<DEVICE> filteredList;
    LocalDate liquidationDate;
    public ObservableList<DEPARTMENT> listDepartment;
    public String departmentId;

    void initData(ObservableList<PERSON> listLiquidationPeople, LocalDate liquidationDate) {
        this.listLiquidationPeople = listLiquidationPeople;
        this.liquidationDate = liquidationDate;
    }

    @FXML
    void continueBtnAction(ActionEvent event) {
        if(validateData() == true){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/LiquidationScreen/third_liquidation_screen.fxml"));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1280, 720);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ThirdLiquidationScreenController thirdLiquidationScreenController = fxmlLoader.getController();
            thirdLiquidationScreenController.initData(listLiquidationPeople, liquidationDate, listLiquidationDevice);

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
            listLiquidationDevice.add(addingDevice);
            listDevice.remove(addingDevice);
            liquidationDeviceTB.setItems(listLiquidationDevice);
            errorLabel.setVisible(false);
        }
    }


    @FXML
    void removeBtnAction(ActionEvent event) {
        DEVICE removingDevice = liquidationDeviceTB.getSelectionModel().getSelectedItem();

        if (removingDevice != null) {
            listDevice.add(removingDevice);
            listLiquidationDevice.remove(removingDevice);
            existedDeviceTB.setItems(listDevice);
        }
    }

    @FXML
    void textChangeHandler(KeyEvent event) {
        setSearchInTableView();
    }

    @FXML
    public void backButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/LiquidationScreen/first_liquidation_screen.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProperty();
        getDataInTableView();
        setSearchInTableView();
        errorLabel.setVisible(false);
    }

    boolean validateData(){
        if(listLiquidationDevice.isEmpty())  {
            errorLabel.setVisible(true);
            errorLabel.setText("Please add device to liquidation!");
            return false;
        }
        errorLabel.setVisible(false);
        return true;
    }
    @FXML
    void cbbOnChangeListener(ActionEvent event) {
        DEPARTMENT selectedDepartment = (DEPARTMENT)departmentCbb.getValue();
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
    private void getDataInTableView() {
        var devices = new DATABASE_DAO<>(DEVICE.class);
        var transfer = new DATABASE_DAO<>(TRANSFER.class);
        ObservableList<TRANSFER> listTransfer;
        ArrayList<String> listDeviceId = new ArrayList<>();
        try {
            if(departmentId == null || departmentId.equals("All Department")) {
                listTransfer = FXCollections.observableArrayList(transfer.selectList("SELECT DeviceId FROM TBTRANSFER"));
                String[] listDeviceIdTemp;
                for (int i = 0; i < listTransfer.size(); i++) {
                    String listDeviceIDString = listTransfer.get(i).getDeviceID();
                    listDeviceIdTemp = listDeviceIDString.split(",");
                    for (int j = 0; j < listDeviceIdTemp.length; j++) {
                        listDeviceId.add(listDeviceIdTemp[j]);
                    }
                }
            }
            else{
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
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }

        try {
            if(!listDeviceId.isEmpty()) {
                StringBuilder selectString = new StringBuilder("SELECT DEVICEID, DEVICENAME, DEVICESTATUS, YEARUSED, YEARMANUFACTURE, PRICE, SPECIFICATION, PercentDamage FROM tbDevice WHERE  deviceId =  '" + listDeviceId.get(0).toString() + "'");
                for (int i = 1; i < listDeviceId.size(); i++) {
                    selectString.append(" OR deviceId =  '").append(listDeviceId.get(i).toString()).append("'");

                }
                selectString.append(" AND deviceStatus !=  'Liquidated'");
                listDevice = FXCollections.observableArrayList(devices.selectList(selectString.toString()));
            }
            else{
                listDevice.clear();
            }
        } catch (Exception e) {
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }

        listLiquidationDevice = FXCollections.observableArrayList();

        existedDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceId"));
        existedDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceName"));
        existedDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("percentDamage"));
        existedDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceStatus"));

        liquidationDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceId"));
        liquidationDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceName"));
        liquidationDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("percentDamage"));
        liquidationDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DEVICE, String>("deviceStatus"));

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


    @FXML
    void searchHandler(KeyEvent event) {
        setSearchInTableView();
    }
}
