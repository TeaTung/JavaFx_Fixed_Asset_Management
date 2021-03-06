package project.javafx_fixed_asset_management.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;
import project.javafx_fixed_asset_management.Utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddNewContractDialogController implements Initializable {
    public AnchorPane anchorPane;
    private static double xOffset = 0;
    private static double yOffset = 0;
    @FXML
    Button addNewDeviceBtn;

    @FXML
    TextField idContractTF;


    @FXML
    DatePicker importDateDP;


    @FXML
    TextArea noteTA;


    @FXML
    ComboBox<PROVIDER> providerCB;

    @FXML
    void addNewDeviceButtonAction(ActionEvent event) {

        if (!validatedData()) {
            return;
        }

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        DEVICE_ADD new_device = (DEVICE_ADD) stage.getUserData();

        var contractId = idContractTF.getText();
        var providerId = providerCB.getSelectionModel().getSelectedItem().getProviderId();
        var importDate = importDateDP.getValue().toString();
        var note = noteTA.getText();

        try {

            new DATABASE_DAO<>(CONTRACT.class).insert("insert into tbContract ( ContractId, ProviderId, ImportDate, Note) values (? , ? , ?, ?) ", contractId, providerId, importDate, note);

            var deviceId = UUID.randomUUID().toString().substring(0, 10);

            // insert new devices
            new DATABASE_DAO<DEVICE>(DEVICE.class).
                    insert("INSERT INTO tbDevice ( DeviceId, DeviceStatus, DeviceName, Price, modelId, specification, yearUsed, yearManufacture, quantityDevice, percentDamage, contractId ) values  ( ? , ? ,?, ?, ?, ?, ?, ?, ?, ?, ?) ",
                            deviceId, new_device.getDeviceStatus(), new_device.getDeviceName(), (String.valueOf(new_device.getPrice())), new_device.getModelId(), new_device.getSpecification(),
                            new_device.getYearUsed(),
                            new_device.getYearManufacture(),
                            String.valueOf(new_device.getQuantityDevice()),
                            String.valueOf(new_device.getPercentDamage()),
                            contractId
                    );


        } catch (Exception e) {
            JMetro jMetro = new JMetro(Style.LIGHT);
            FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
            alert.setContentText("Can not insert with the same contractID");
            alert.setHeaderText("Adding new devices successfully");
            jMetro.setScene(alert.getDialogPane().getScene());
            alert.showAndWait();
            return;
        }
        // a alert dialog before leaving
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        alert.setContentText("Adding new devices successfully");
        alert.setHeaderText("Message");
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();
        stage.close();
    }

    boolean validatedData() {
        JMetro jMetro = new JMetro(Style.LIGHT);
        FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
        jMetro.setScene(alert.getDialogPane().getScene());
        if (idContractTF.getText().isEmpty()) {
            alert.setContentText("Please fill in contractID");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }
        if (importDateDP.getValue() == null) {
            alert.setContentText("Please fill in import date");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }

        if (providerCB.getSelectionModel().getSelectedItem() == null) {
            alert.setContentText("Please choose supplier");
            alert.setHeaderText("Adding new devices failed");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        providerCB.setConverter(new StringConverter<PROVIDER>() {
            @Override
            public String toString(PROVIDER provider) {
                return provider.getProviderName();
            }

            @Override
            public PROVIDER fromString(final String string) {
                return new PROVIDER();
            }
        });
        importDateDP.setConverter(Utils.getConverter(importDateDP));

        anchorPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        var providers = FXCollections.observableArrayList(new DATABASE_DAO<PROVIDER>(PROVIDER.class).selectList("select * from tbProvider"));
        if (providers != null)
            providerCB.setItems(providers);

        providerCB.getSelectionModel().select(0);

    }

    public void addProviderOnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/ConfigScreen/config_screen.fxml"));
        Node node = (Node) actionEvent.getSource();
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 520);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.showAndWait();
        initialize(null, null);
    }

    public void onBackContractOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/AddNewDeviceDialog/add_new_device_dialog.fxml"));
        Node node = (Node) actionEvent.getSource();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load(), 990, 650);

        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Add New Screen Dialog");
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.show();
    }

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

    public void onMinimizeBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    public void onCloseWinBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
    }
}
