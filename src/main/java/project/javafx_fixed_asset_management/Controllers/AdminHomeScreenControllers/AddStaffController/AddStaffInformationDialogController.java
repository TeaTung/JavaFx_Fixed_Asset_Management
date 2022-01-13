package project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.AddStaffController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Controllers.AccountScreenController.AccountDialogController;
import project.javafx_fixed_asset_management.Controllers.AccountScreenController.UpdateAccountInformationDialogController;
import project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.AdminHomeScreenController;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.ACCOUNT;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.PROFILE;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddStaffInformationDialogController {
    @FXML
    TextField nameTF;

    @FXML
    DatePicker birthdayDTP;

    @FXML
    TextField phoneNumberTF;

    @FXML
    ComboBox departmentCbb;

    @FXML
    TextField addressTF;

    String email;
    String password;
    String accountType;

    public void init(String accountEmail, String accountPassword, String type) {
        email = accountEmail;
        password = accountPassword;
        accountType = type;

        addValueIntoCombobox();
        setProperty();
    }

    public String getDepartmentId(String departmentName) {
        System.out.println("DEPARTMENT NAME: " + departmentName);

        var departmentDAO = new DATABASE_DAO<>(DEPARTMENT.class);
        DEPARTMENT department = departmentDAO.selectOne("SELECT TOP 1 * FROM tbDepartment WHERE DepartmentName = ?", departmentName);

        System.out.println("DEPARTMENT ID: " + department.getDepartmentId());

        return department.getDepartmentId();
    }

    public void addValueIntoCombobox() {
        var departments = new DATABASE_DAO<>(DEPARTMENT.class);

        List<DEPARTMENT> listDepartment = departments.selectList("SELECT * FROM tbDepartment");
        ObservableList<String> listDepartmentName = FXCollections.observableArrayList();
        for (DEPARTMENT department : listDepartment) {
            listDepartmentName.add(department.getDepartmentName());
        }

        departmentCbb.setItems(listDepartmentName);
    }

    public void validateField(ActionEvent event) throws IOException {
        if (areAllTextFieldEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account Screen");
            alert.setHeaderText("Make sure you fill up all field");
            alert.setContentText("Some field wasn't inserted");
            alert.show();
        } else {
            confirmButtonAction(event);
        }

    }

    public void confirmButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Create Account");
        alert.setHeaderText("Are you sure to creat account");
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            insertAccount();

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Create Account");
            alert1.setHeaderText("Create Successfully");
            alert1.show();

            backToPreviousScreen(event);
        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public void backToPreviousScreen (ActionEvent event) throws IOException {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void insertAccount() {
        var account = new DATABASE_DAO<>(ACCOUNT.class);
        var profile = new DATABASE_DAO<>(PROFILE.class);

        var accountId = UUID.randomUUID().toString().substring(0, 10);
        account.insert("insert into tbAccount (AccountId, Email, Password, AccountType) values (? , ? , ?, ?)", accountId, email, password, accountType);
        var profileId = UUID.randomUUID().toString().substring(0, 10);
        profile.insert("insert into tbProfile (ProfileId, Name, DepartmentId, Address, AccountId, Birthday,PhoneNumber) values (? , ? , ?, ?, ?,?, ?)",
                profileId, nameTF.getText(), getDepartmentId(departmentCbb.getValue().toString()), addressTF.getText(), accountId, birthdayDTP.getValue().toString(), phoneNumberTF.getText());

    }

    public boolean areAllTextFieldEmpty() {
        if (!isTextFieldEmpty(nameTF) && !isTextFieldEmpty(phoneNumberTF) && !isTextFieldEmpty(addressTF)) {
            return false;
        }
        return true;
    }

    public boolean isTextFieldEmpty(TextField field) {
        if (field.getText().isEmpty() || field.getText().isBlank() || field.getText() == null) {
            return true;
        }
        return false;
    }

    public void setProperty() {
        phoneNumberTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String newValue, String t1) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberTF.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void backToPreviousDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AccountScreen/account_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }
}
