package project.javafx_fixed_asset_management.Controllers.TranferScreenController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
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
            FlatAlert alert = new FlatAlert(Alert.AlertType.WARNING);
            alert.setTitle("Department");
            alert.setHeaderText("Make sure you fill up department field");
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(alert.getDialogPane().getScene());
            alert.show();
        } else if (isDepartmentExisted()) {
            FlatAlert alert = new FlatAlert(Alert.AlertType.WARNING);
            alert.setTitle("Department");
            alert.setHeaderText("Department Existed");
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(alert.getDialogPane().getScene());
            alert.show();
        } else {
            confirmButtonAction(event);
        }
    }

    public boolean isDepartmentExisted() {
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
        FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure to add department");

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(alert.getDialogPane().getScene());

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == ButtonType.OK) {
            var department = new DATABASE_DAO<>(DEPARTMENT.class);
            department.insert("INSERT into tbDepartment (DepartmentId, DepartmentName) values (?,?)", departmentIdTF.getText(), departmentTF.getText());

            FlatAlert alert1 = new FlatAlert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("Create Successfully");
            jMetro.setScene(alert1.getDialogPane().getScene());

            alert1.show();

            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

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

    public void onCloseWinBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.close();
    }
}
