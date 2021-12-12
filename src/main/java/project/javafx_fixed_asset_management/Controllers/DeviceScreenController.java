package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class DeviceScreenController implements Initializable {
//    public ObservableList<Device> devicesList;

    @FXML
//    public TableView<Device> deviceTableView;
//
//    public TableColumn<Device, String> deviceNameColumn;
//    public TableColumn<Device, Integer> idColumn;
//    public TableColumn<Device, String> typeOfDeviceColumn;
//    public TableColumn<Device, Integer> priceColumn;
//    public TableColumn<Device, String> unitColumn;
//    public TableColumn<Device, String> specificationColumn;
//    public TableColumn<Device, LocalDate> dateOfManufacturingColumn;
//    public TableColumn<Device, LocalDate> dateOfUsingColumn;
//    public TableColumn<Device, String> statusColumn;
//    public TableColumn<Device, Double> ebitdaColumn;

    public Button searchBtn;
    public Button addBtn;
    public Button deleteBtn;
    public Button updateBtn;
    public Button backBtn;

    public TextField searchTF;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        devicesList = FXCollections.observableArrayList(
//                new Device(1,"Name", "Type", 12, "Unit","Specification",LocalDate.now(),LocalDate.now(),"Status",10.5)
//        );
    }

    public void addDeviceButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/AddNewDeviceDialog/add_new_device_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load(), 1018, 720);
        stage.setTitle("Add New Screen Dialog");
        stage.setScene(scene);
        stage.show();
    }

    public void updateDeviceButtonAction(ActionEvent event) {

    }

    public void deleteDeviceButtonAction(ActionEvent event) {

    }

    public void searchDeviceButtonAction(ActionEvent event) {

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
