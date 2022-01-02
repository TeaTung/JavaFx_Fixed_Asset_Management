package project.javafx_fixed_asset_management.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import jfxtras.styles.jmetro.JMetro;
import com.pixelduke.control.ribbon.RibbonGroup;

import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;

public class ManagerHomeScreenController {
    public Button btnConfig;
    @FXML
    Button btnAccount;

    @FXML
    Button btnDevice;

    @FXML
    Button btnHistory;

    @FXML
    Button btnInventory;

    @FXML
    Button btnLiquidation;

    @FXML
    Button btnRepair;

    @FXML
    Button btnTransfer;

    public void openDeviceScreenButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/device_screen.fxml"));

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
            JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);

        stage.show();
    }

    public void openInventoryScreenButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/first_inventory_screen.fxml"));
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

    public void openLiquidationScreenButtonAction(ActionEvent event) throws IOException {
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

    public void openTransferScreenButtonAction(ActionEvent event) throws IOException {
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

    public void openAccountScreenButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AccountScreen/account_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 550, 442);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    public void openRepairScreenButtonAction(ActionEvent event) throws IOException {
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

    public void openHistoryAndManagementScreenButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HistoryAndManagementScreen/history_management_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
            JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    public void openConfigScreenButtonAction(ActionEvent actionEvent) {

        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        TabPane tabPane = new TabPane();

        Tab fileTab = new Tab();
        fileTab.setClosable(false);
        fileTab.setText("File");

        Tab homeTab = new Tab();
        homeTab.setClosable(false);
        homeTab.setText("Home");

        Tab insertTab = new Tab();
        insertTab.setClosable(false);
        insertTab.setText("Insert");

        Tab tableTab = new Tab();
        tableTab.setClosable(false);
        tableTab.setText("Table");

        Tab optionsTab = new Tab();
        optionsTab.setClosable(false);
        optionsTab.setText("Options");

        tabPane.getTabs().addAll(fileTab, homeTab, insertTab, tableTab, optionsTab);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(tabPane);

        borderPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        Scene scene = new Scene(borderPane, 500, 200);

        new JMetro(scene, Style.DARK);

//        ScenicView.show(scene);


        stage.setTitle("TabPane Sample");
        stage.setScene(scene);
        stage.show();

    }
}
