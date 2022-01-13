package project.javafx_fixed_asset_management.Controllers.TranferScreenController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.REPAIR;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddDepartmentDialogController implements Initializable {
    @FXML
    TextField departmentTF;

    @FXML
    TextField departmentIdTF;

    public void addButtonAction(ActionEvent event) throws IOException {
        validateField(event);
    }

    public void validateField(ActionEvent event) throws IOException {
        if (departmentTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Department");
            alert.setHeaderText("Make sure you fill up department field");
            alert.show();
        } else if (isDepartmentExisted()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Department");
            alert.setHeaderText("Department Existed");
            alert.show();
        } else {
            confirmButtonAction(event);
        }
    }

    public boolean isDepartmentExisted () {
        var department = new DATABASE_DAO<>(DEPARTMENT.class);

        List<DEPARTMENT> departments = FXCollections.observableArrayList(department.selectList("SELECT * FROM tbDepartment"));
        for (DEPARTMENT item : departments) {
            if (item.getDepartmentName().equalsIgnoreCase(departmentTF.getText().trim())) {
                return true;
            }
        }
        return false;
    }

    public void confirmButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Department");
        alert.setHeaderText("Are you sure to add department");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            var department = new DATABASE_DAO<>(DEPARTMENT.class);
            department.insert("INSERT into tbDepartment (DepartmentId, DepartmentName) values (?,?)",departmentIdTF.getText(),departmentTF.getText());

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Create Department");
            alert1.setHeaderText("Create Successfully");
            alert1.show();

            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var department = UUID.randomUUID().toString().substring(0, 10);

        departmentIdTF.setDisable(true);
        departmentIdTF.setText(department);
    }
}
