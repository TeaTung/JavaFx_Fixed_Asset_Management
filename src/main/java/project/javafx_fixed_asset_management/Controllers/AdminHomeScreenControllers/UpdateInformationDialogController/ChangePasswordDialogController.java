package project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.UpdateInformationDialogController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Models.ACCOUNT;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;

import java.io.IOException;
import java.util.Optional;

public class ChangePasswordDialogController {
    @FXML
    PasswordField oldPasswordPF;

    @FXML
    PasswordField newPasswordPF;

    @FXML
    PasswordField confirmPasswordPF;

    String accountId;

    public void init (String id) {
        accountId = id;
    }

    public void updatePasswordActionButton(ActionEvent event) throws IOException {
        validateField(event);
    }

    public void confirmButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Account Information");
        alert.setHeaderText("Are you sure to update information");
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            updatePassword();

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Update Password");
            alert1.setHeaderText("Update Successfully");
            alert1.show();

            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public void updatePassword() {
        var account = new DATABASE_DAO<>(ACCOUNT.class);
        account.update("UPDATE tbAccount SET Password = ? WHERE AccountId = ?", newPasswordPF.getText().trim(),accountId);
    }

    public void validateField(ActionEvent event) throws IOException {
        if (areAllTextFieldEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Password Screen");
            alert.setHeaderText("Make sure you fill up all field");
            alert.setContentText("Some field wasn't inserted");
            alert.show();
        } else if (!verifyOldPassword()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Password Screen");
            alert.setHeaderText("Your old password isn't correct");
            alert.show();
        } else if (!isPasswordHardEnough()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Password Screen");
            alert.setHeaderText("Your new password isn't hard enough");
            alert.show();
        }else if (!isConfirmPasswordTrue()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Password Screen");
            alert.setHeaderText("Your confirm password isn't correct");
            alert.show();
        }else {
            confirmButtonAction(event);
        }

    }

    public boolean isPasswordHardEnough() {
        if (newPasswordPF.getText().trim().length() > 8) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isConfirmPasswordTrue() {
        if (newPasswordPF.getText().equalsIgnoreCase(confirmPasswordPF.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public ACCOUNT account() {
        var account = new DATABASE_DAO<>(ACCOUNT.class);
        return  account.selectOne("SELECT TOP 1 * FROM tbAccount WHERE AccountId = ?", accountId);
    }

    public boolean verifyOldPassword() {
        System.out.println("ACCOUNT ID: " + accountId);
        System.out.println("OLD PASSWORD IN DATABASE: " + account().getPassword());
        System.out.println("OLD PASSWORD: " + oldPasswordPF.getText().trim());

        if (oldPasswordPF.getText().trim().equalsIgnoreCase(account().getPassword())){
            return true;
        }
        return false;
    }

    public boolean areAllTextFieldEmpty() {
        if (!isTextFieldEmpty(confirmPasswordPF) && !isTextFieldEmpty(newPasswordPF) && !isTextFieldEmpty(oldPasswordPF)) {
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
}
