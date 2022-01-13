package project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.UpdateInformationDialogController.UpdateInformationDialogController;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminHomeScreenController implements Initializable {
    @FXML
    TextField searchTF;

    @FXML
    TableView<ACCOUNT_PROFILE_DEPARTMENT> staffTableView;

    @FXML
    TableColumn<ACCOUNT, String> idAccountColumn;

    @FXML
    TableColumn<ACCOUNT, String> emailStaffColumn;

    @FXML
    TableColumn<ACCOUNT, String> accountTypeStaffColumn;

    @FXML
    TableColumn<PROFILE, String> nameStaffColumn;

    @FXML
    TableColumn<PROFILE, String> phoneNumberStaffColumn;

    @FXML
    TableColumn<PROFILE, String> addressStaffColumn;

    @FXML
    TableColumn<PROFILE, String> birthdayStaffColumn;

    @FXML
    TableColumn<DEPARTMENT, String> departmentStaffColumn;

    public ObservableList<ACCOUNT_PROFILE_DEPARTMENT> listStaff;

    FilteredList<ACCOUNT_PROFILE_DEPARTMENT> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataInTableView();
        setProperty();
    }

    public void setDataInTableView () {
        var staffs = new DATABASE_DAO<>(ACCOUNT_PROFILE_DEPARTMENT.class);

        listStaff = FXCollections.observableArrayList(staffs.selectList(
                "SELECT  tbAccount.AccountId, tbAccount.Email, tbAccount.AccountType, tbProfile.Name, tbProfile.PhoneNumber, tbDepartment.DepartmentName, tbProfile.Address, tbProfile.Birthday FROM tbAccount INNER JOIN tbProfile on tbAccount.AccountId = tbProfile.AccountId INNER JOIN tbDepartment on tbDepartment.DepartmentId = tbProfile.DepartmentId"
        ));

        idAccountColumn.setCellValueFactory(new PropertyValueFactory<>("AccountId"));
        emailStaffColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        accountTypeStaffColumn.setCellValueFactory(new PropertyValueFactory<>("AccountType"));
        nameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        phoneNumberStaffColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        addressStaffColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        birthdayStaffColumn.setCellValueFactory(new PropertyValueFactory<>("Birthday"));
        departmentStaffColumn.setCellValueFactory(new PropertyValueFactory<>("DepartmentName"));

        staffTableView.setItems(listStaff);
    }

    public void setProperty () {
        filteredList = new FilteredList<>(listStaff, b -> true);

        searchTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(staff -> {
                if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                    return true;
                }

                String searchValue = newValue.toLowerCase();

                if (staff.getName().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (staff.getAddress().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (staff.getDepartmentName().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (staff.getBirthday().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (staff.getPhoneNumber().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (staff.getAccountType().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (staff.getEmail().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else if (staff.getAccountId().toLowerCase().indexOf(searchValue) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        }));
        SortedList<ACCOUNT_PROFILE_DEPARTMENT> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(staffTableView.comparatorProperty());
        staffTableView.setItems(sortedList);

        searchTF.setPromptText("Search...");
        searchTF.getParent().requestFocus();
    }

    public void backToPreviousScreen(ActionEvent event) {
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

    public void deleteStaffButtonAction(ActionEvent event) {
        if (staffTableView.getSelectionModel().getSelectedItem() != null) {
            FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure ?");

            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(alert.getDialogPane().getScene());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                new DATABASE_DAO<>(DEVICE.class).delete("DELETE FROM tbProfile where accountId = ? ", staffTableView.getSelectionModel().getSelectedItem().getAccountId());
                new DATABASE_DAO<>(DEVICE.class).delete("DELETE FROM tbAccount where accountId = ? ", staffTableView.getSelectionModel().getSelectedItem().getAccountId());

                setDataInTableView();
            } else {
            }
        }
    }

    public void updateStaffButtonAction(ActionEvent event) throws IOException {
        if (staffTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AdminHomeScreen/UpdateInformationDialog/update_information_dialog.fxml"));
            Node node = (Node) event.getSource();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Parent updateInformationDialogController = fxmlLoader.load();
            Scene scene = new Scene(updateInformationDialogController);
            UpdateInformationDialogController controller = fxmlLoader.getController();
            controller.init(staffTableView.getSelectionModel().getSelectedItem().getAccountId());

            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);

            stage.setScene(scene);
            stage.showAndWait();
            setDataInTableView();
        }
    }

    public void addStaff (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AdminHomeScreen/AddAccount/add_staff_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Account Information");

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setScene(scene);
        stage.showAndWait();
        setDataInTableView();
    }


}
