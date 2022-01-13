package project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.UpdateInformationDialogController;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.ACCOUNT;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.PROFILE;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class UpdateInformationDialogController {
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

    @FXML
    ComboBox accountTypeCbb;

    String accountId;

    public void init(String id) {
        accountId = id;

        setLabelValue(getProfile());
        addValueIntoCombobox();
        setProperty();
    }

    public PROFILE getProfile() {
        var profile = new DATABASE_DAO<>(PROFILE.class);

        return profile.selectOne("SELECT TOP 1 * FROM tbProfile WHERE AccountId = ?", accountId);
    }

    public void setLabelValue(PROFILE profile) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(profile.getBirthDay().toLowerCase(), formatter);

        nameTF.setText(profile.getName());
        phoneNumberTF.setText(profile.getPhoneNumber());
        departmentCbb.setValue(getDepartment(profile.getDepartmentId()));
        addressTF.setText(profile.getAddress());
        birthdayDTP.setValue(birthday);
        setAccountTypeCbb();
    }

    public boolean isAnyThingChange() {
        var userAccount = new DATABASE_DAO<>(ACCOUNT.class);

        ACCOUNT account = userAccount.selectOne("SELECT TOP 1 * FROM tbAccount WHERE AccountId = ?", accountId);
        PROFILE profile = getProfile();

        if (nameTF.getText().equalsIgnoreCase(profile.getName())
                && birthdayDTP.getValue().toString().equalsIgnoreCase(profile.getBirthDay())
                && phoneNumberTF.getText().equalsIgnoreCase(profile.getPhoneNumber())
                && departmentCbb.getValue().toString().equalsIgnoreCase(getDepartment(profile.getDepartmentId()))
                && addressTF.getText().equalsIgnoreCase(profile.getAddress())
                && accountTypeCbb.getValue().toString().equalsIgnoreCase(account.getAccountType())) {
            return false;
        }
        return true;
    }

    public void setAccountTypeCbb() {
        System.out.println("ACCOUNT ID: " + accountId);
        var account = new DATABASE_DAO<>(ACCOUNT.class);

        ACCOUNT userAccount = account.selectOne("SELECT TOP 1 * FROM tbAccount WHERE AccountId = ?", accountId);
        accountTypeCbb.setValue(userAccount.getAccountType());

        ObservableList<String> items = FXCollections.observableArrayList(
                "Admin", "Staff");
        accountTypeCbb.setItems(items);
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

    public String getDepartment(String departmentId) {
        var departmentDAO = new DATABASE_DAO<>(DEPARTMENT.class);
        DEPARTMENT department = departmentDAO.selectOne("SELECT TOP 1 * FROM tbDepartment WHERE departmentId = ?", departmentId);
        return department.getDepartmentName();
    }

    public String getDepartmentId(String departmentName) {
        System.out.println("DEPARTMENT NAME: " + departmentName);

        var departmentDAO = new DATABASE_DAO<>(DEPARTMENT.class);
        DEPARTMENT department = departmentDAO.selectOne("SELECT TOP 1 * FROM tbDepartment WHERE DepartmentName = ?", departmentName);

        System.out.println("DEPARTMENT ID: " + department.getDepartmentId());

        return department.getDepartmentId();
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

    public void confirmButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Account Information");
        alert.setHeaderText("Are you sure to update information");
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            updateUserAccount(event);
        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public void validateField(ActionEvent event) throws IOException {
        if (areAllTextFieldEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Account Screen");
            alert.setHeaderText("Make sure you fill up all field");
            alert.setContentText("Some field wasn't inserted");
            alert.show();
        } else if (!isAnyThingChange()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Account Screen");
            alert.setHeaderText("Nothing changed");
            alert.show();
        } else {
            confirmButtonAction(event);
        }

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

    public void updateUserAccount(ActionEvent event) throws IOException {
        var profile = new DATABASE_DAO<>(PROFILE.class);
        var account = new DATABASE_DAO<>(ACCOUNT.class);

        profile.update("UPDATE tbProfile SET Name = ?, PhoneNumber = ?, DepartmentId = ?, Address = ?, Birthday = ? WHERE ProfileId = ?",
                nameTF.getText(), phoneNumberTF.getText(), getDepartmentId(departmentCbb.getValue().toString()), addressTF.getText(), birthdayDTP.getValue().toString(), getProfile().getProfileId());
        account.update("UPDATE tbAccount SET AccountType = ? WHERE AccountId = ?", accountTypeCbb.getValue().toString(), accountId);

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Update Profile");
        alert1.setHeaderText("Update Successfully");
        alert1.show();

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void changePasswordButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AdminHomeScreen/UpdateInformationDialog/change_password_dialog.fxml"));
        Node node = (Node) event.getSource();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent changePasswordDialogController = fxmlLoader.load();
        Scene scene = new Scene(changePasswordDialogController);
        ChangePasswordDialogController controller = fxmlLoader.getController();
        controller.init(accountId);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setScene(scene);
        stage.showAndWait();
    }
}
