package project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.AddStaffController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.ACCOUNT;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddStaffDialogController implements Initializable {
    @FXML
    TextField emailTF;

    @FXML
    PasswordField passwordPF;

    @FXML
    ComboBox accountTypeCbb;

    @FXML
    PasswordField confirmPasswordPF;

    public void backButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void nextButtonAction(ActionEvent event) throws IOException {
        validateField(event);
    }

    public boolean validateEmailAddress(String emailAddress) {
        Pattern regexPattern;
        Matcher regMatcher;

        regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        regMatcher = regexPattern.matcher(emailAddress);
        if (regMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailExisted() {
        var emails = new DATABASE_DAO<>(ACCOUNT.class);

        List<ACCOUNT> listAccount = FXCollections.observableArrayList(emails.selectList("SELECT * FROM tbACCOUNT"));

        for (ACCOUNT account : listAccount) {
            if (account.getEmail().equalsIgnoreCase(emailTF.getText().trim().toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public void validateField(ActionEvent event) throws IOException {
        if (isAllTextFieldEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account Information");
            alert.setHeaderText("Make sure you fill up all field");
            alert.setContentText("Some field wasn't inserted");
            alert.show();
        } else if (!isConfirmPasswordTrue()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account Information");
            alert.setHeaderText("Your confirm password isn't correct");
            alert.setContentText("Some field wasn't inserted");
            alert.show();
        } else if (!isPasswordHardEnough()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account Information");
            alert.setHeaderText("Your password isn't long enough");
            alert.show();
        } else if (!validateEmailAddress(emailTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account Information");
            alert.setHeaderText("Your email isn't correct");
            alert.show();
        } else if (!isEmailExisted()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account Information");
            alert.setHeaderText("Your email isn existed");
            alert.show();
        } else if (accountTypeCbb.getValue().toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Account Information");
            alert.setHeaderText("Choose account type");
            alert.show();
        } else {
            goToUserInformationScreen(event);
        }
    }

    public boolean isPasswordHardEnough() {
        if (passwordPF.getText().trim().length() > 8) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isConfirmPasswordTrue() {
        if (passwordPF.getText().equalsIgnoreCase(confirmPasswordPF.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public void goToUserInformationScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AdminHomeScreen/AddAccount/add_staff_information_dialog.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent addStaffInformationDialogController = fxmlLoader.load();
        Scene scene = new Scene(addStaffInformationDialogController);
        AddStaffInformationDialogController controller = fxmlLoader.getController();
        controller.init(emailTF.getText(), passwordPF.getText(),accountTypeCbb.getValue().toString());

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setScene(scene);

        stage.show();
    }

    public boolean isAllTextFieldEmpty() {
        if (!isTextFieldEmpty(emailTF) && !isTextFieldEmpty(confirmPasswordPF) && !isTextFieldEmpty(passwordPF)) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items =FXCollections.observableArrayList (
                "Admin", "Staff");

        accountTypeCbb.setItems(items);
        accountTypeCbb.setValue("Staff");
    }
}
