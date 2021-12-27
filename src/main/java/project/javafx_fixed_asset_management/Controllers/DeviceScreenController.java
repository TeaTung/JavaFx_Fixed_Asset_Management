package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.UNIT;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class DeviceScreenController implements Initializable {

    public ObservableList<DEVICE> devicesList;
    @FXML
    public TableView<DEVICE> deviceTableView;
    //
    @FXML
    public TableColumn<DEVICE, String> deviceNameColumn;
    @FXML
    public TableColumn<DEVICE, String> idColumn;
    @FXML
    public TableColumn<DEVICE, String> typeOfDeviceColumn;
    @FXML
    public TableColumn<DEVICE, Integer> priceColumn;
    @FXML
    public TableColumn<UNIT, String> unitColumn;
    @FXML
    public TableColumn<DEVICE, String> specificationColumn;
    @FXML

    public TableColumn<DEVICE, String> dateOfManufacturingColumn;
    @FXML
    public TableColumn<DEVICE, LocalDate> dateOfUsingColumn;
    @FXML
    public TableColumn<DEVICE, String> statusColumn;
    @FXML
    public TableColumn<DEVICE, Double> ebitdaColumn;
    public Button searchBtn;
    public Button addBtn;
    public Button deleteBtn;
    public Button updateBtn;
    public Button backBtn;
    @FXML
    public TextField searchTF;
    FilteredList<DEVICE> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        var devices = new DATABASE_DAO<>(DEVICE.class);
        devicesList = FXCollections.observableArrayList(devices.selectList(
                "select * " +
                        "from tbDevice inner join tbDeviceModel " +
                        "on tbDevice.ModelId =  tbDeviceModel.ModelId " +
                        "inner join tbUnit \n" +
                        "on tbDeviceModel.UnitId = tbUnit.UnitId"));


        devicesList.get(0).getPercentDamage();

        deviceNameColumn.setCellValueFactory(
                new PropertyValueFactory<DEVICE, String>("deviceName"));

        idColumn.setCellValueFactory(
                new PropertyValueFactory<DEVICE, String>("deviceId"));

        typeOfDeviceColumn.setCellValueFactory(
                new PropertyValueFactory<>("modelName"));

        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        unitColumn.setCellValueFactory(
                new PropertyValueFactory<UNIT, String>("unitName"));


        specificationColumn.setCellValueFactory(
                new PropertyValueFactory<DEVICE, String>("specification"));

        dateOfManufacturingColumn.setCellValueFactory(
                new PropertyValueFactory<>("yearManufacture"));


        dateOfUsingColumn.setCellValueFactory(
                new PropertyValueFactory<>("yearUsed"));


        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("deviceStatus"));


        filteredList = new FilteredList<>(devicesList);


        deviceTableView.setItems(devicesList);
    }

    public void addDeviceButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/AddNewDeviceDialog/add_new_device_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load(), 1018, 720);


        stage.setTitle("Add New Screen Dialog");
        stage.setScene(scene);
//        JMetro jMetro = new JMetro(Style.LIGHT);
//        jMetro.setScene(scene);

        stage.show();
    }

    public void updateDeviceButtonAction(ActionEvent event) {

    }

    public void deleteDeviceButtonAction(ActionEvent event) {

    }

    public void onKeyTypeSearchTF(KeyEvent keyEvent) {

        String textSearch = searchTF.getText();
        if (!textSearch.isEmpty()) {


            filteredList.setPredicate(
                    new Predicate<DEVICE>() {
                        public boolean test(DEVICE t) {
                            return (t.getDeviceName().toLowerCase().startsWith(textSearch));
                        }
                    }
            );

            deviceTableView.setItems(filteredList);


        } else {
            deviceTableView.setItems(devicesList);
        }
    }

    public void searchDeviceButtonAction(ActionEvent event) {
        if (!searchTF.getText().isEmpty()) {


            String deviceNameSearch = searchTF.getText();
            filteredList.setPredicate(
                    new Predicate<DEVICE>() {
                        public boolean test(DEVICE t) {
                            return (t.getDeviceName().toLowerCase().startsWith(deviceNameSearch));
                        }
                    }
            );
            deviceTableView.setItems(filteredList);

        } else {
            deviceTableView.setItems(devicesList);

        }

    }

    public void backDeviceButtonAction(ActionEvent event) {
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
        stage.show();
    }

}
