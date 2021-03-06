package project.javafx_fixed_asset_management.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Controllers.AccountScreenController.AccountDialogController;
import project.javafx_fixed_asset_management.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerHomeScreenController implements Initializable {
    String accountId = "1";

    public void initData(String accountId) {
        this.accountId = accountId;
    }

    @FXML
    Button logoutBtn;

    @FXML
    void logoutBtnHandler(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to logout the application? ", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AuthenticationScreen/SignIn/sign_in_screen.fxml"));
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

    public void openDeviceScreenButtonAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/DeviceScreen/device_screen.fxml"));

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.show();
    }

    public void openInventoryScreenButtonAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/first_inventory_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 750);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);

        stage.show();
    }

    public void openLiquidationScreenButtonAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/LiquidationScreen/first_liquidation_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    public void openTransferScreenButtonAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/TransferScreen/transfer_devices_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);

        stage.show();
    }

    public void openAccountScreenButtonAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AccountScreen/account_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent accountDialogController = fxmlLoader.load();
        Scene scene = new Scene(accountDialogController);
        AccountDialogController controller = fxmlLoader.getController();
        controller.init(accountId);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }

    public void openRepairScreenButtonAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/RepairScreen/repair_devices_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);

        stage.show();
    }

    public void openHistoryAndManagementScreenButtonAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HistoryAndManagementScreen/history_management_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 740);
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

    public void onMinimizeBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setIconified(true);
    }

}
