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
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DELIVERY_NOTE;
import project.javafx_fixed_asset_management.Models.PERSON;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    TableColumn<DELIVERY_NOTE, String> existedDeviceDamageColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceDepartmentColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceIdColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceNameColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> existedDeviceStatusColumn;

    @FXML
    TableView<DELIVERY_NOTE> existedDeviceTB;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceDamageColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceDepartmentColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceIdColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceNameColumn;

    @FXML
    TableColumn<DELIVERY_NOTE, String> liquidationDeviceStatusColumn;

    @FXML
    TableView<DELIVERY_NOTE> liquidationDeviceTB;

    @FXML
    Button removeBtn;

    public ObservableList<DELIVERY_NOTE> listDevice;
    public ObservableList<DELIVERY_NOTE> listLiquidationDevice;
    public ObservableList<PERSON> listLiquidationPeople;
    FilteredList<DELIVERY_NOTE> filteredList;
    LocalDate liquidationDate;

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

            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void addBtnAction(ActionEvent event) {
        DELIVERY_NOTE addingDevice = existedDeviceTB.getSelectionModel().getSelectedItem();

        if (addingDevice != null) {
            listLiquidationDevice.add(addingDevice);
            listDevice.remove(addingDevice);
            liquidationDeviceTB.setItems(listLiquidationDevice);
            errorLabel.setVisible(false);
        }
    }


    @FXML
    void removeBtnAction(ActionEvent event) {
        DELIVERY_NOTE removingDevice = liquidationDeviceTB.getSelectionModel().getSelectedItem();

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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/LiquidationScreen/first_inventory_screen.fxml"));
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

    private void getDataInTableView() {
        var devices = new DATABASE_DAO<>(DELIVERY_NOTE.class);
        try {
            listDevice = FXCollections.observableArrayList(devices.selectList(
                    "SELECT TBDEVICE.DEVICEID, DEVICENAME, DEPARTMENTNAME, DEVICESTATUS, PERCENTDAMAGE, YEARUSED, YEARMANUFACTURE, PRICE, SPECIFICATION FROM TBDEVICE, TBDEPARTMENT, TBDELIVERYNOTE WHERE TBDEVICE.DeviceId = tbDeliveryNote.DeviceId AND tbDepartment.DepartmentId = tbDeliveryNote.DepartmentId"));
        } catch (Exception e) {
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }

        listLiquidationDevice = FXCollections.observableArrayList();

        existedDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceId"));
        existedDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceName"));
        existedDeviceDepartmentColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("departmentName"));
        existedDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("percentDamage"));
        existedDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceStatus"));

        liquidationDeviceIdColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceId"));
        liquidationDeviceNameColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceName"));
        liquidationDeviceDepartmentColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("departmentName"));
        liquidationDeviceDamageColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("percentDamage"));
        liquidationDeviceStatusColumn.setCellValueFactory(new PropertyValueFactory<DELIVERY_NOTE, String>("deviceStatus"));

        existedDeviceTB.setItems(listDevice);
    }

    public void setSearchInTableView() {
        filteredList = new FilteredList<>(listDevice, b -> true);

        searchTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(deliveryNote -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                }

                String nameSearchValue = newValue.toLowerCase();


                if (deliveryNote.getDeviceName().toLowerCase().indexOf(nameSearchValue) > -1) {
                    return true;
                } else if (deliveryNote.getDepartmentName().toLowerCase().indexOf(nameSearchValue) > -1){
                    return true;
                }
                return false;
            });
        }));


        SortedList<DELIVERY_NOTE> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(existedDeviceTB.comparatorProperty());

        existedDeviceTB.setItems(sortedList);
    }
}
