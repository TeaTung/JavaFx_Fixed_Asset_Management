package project.javafx_fixed_asset_management.Controllers.AccountScreenController;

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
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.PROFILE;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UpdateAccountInformationDialogController {
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
    Button backBtn;

    @FXML
    Button confirmBtn;

    PROFILE userProfile;
    int userId;

    public void backButtonAction(ActionEvent event) throws IOException {
        backToPreviousDialog(event);
    }

    public void backToPreviousDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AccountScreen/account_screen.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent accountDialogController = fxmlLoader.load();
        Scene scene = new Scene(accountDialogController);
        AccountDialogController controller = fxmlLoader.getController();
        controller.init(userId);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
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
            alert.setTitle("Repair Device");
            alert.setHeaderText("Make sure you fill up all field");
            alert.setContentText("Some field wasn't inserted");
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

        profile.update("UPDATE tbProfile SET Name = ?, PhoneNumber = ?, DepartmentId = ?, Address = ?, Birthday = ? WHERE ProfileId = ?",
                nameTF.getText(), phoneNumberTF.getText(), getDepartmentId(departmentCbb.getValue().toString()), addressTF.getText(), birthdayDTP.getValue().toString(), userProfile.getProfileId());

        System.out.println(profile);
        backToPreviousDialog(event);
    }

    public void init(PROFILE profile, int id ) {
        userProfile = profile;
        userId = id;

        setLabelValue(userProfile);
        addValueIntoCombobox();
        setProperty();
    }

    public void setLabelValue(PROFILE profile) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(profile.getBirthDay().toLowerCase(), formatter);

        nameTF.setText(profile.getName());
        phoneNumberTF.setText(profile.getPhoneNumber());
        departmentCbb.setValue(getDepartment(profile.getDepartmentId()));
        addressTF.setText(profile.getAddress());
        birthdayDTP.setValue(birthday);
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

    public void setProperty () {
        phoneNumberTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String newValue, String t1) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberTF.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

}
