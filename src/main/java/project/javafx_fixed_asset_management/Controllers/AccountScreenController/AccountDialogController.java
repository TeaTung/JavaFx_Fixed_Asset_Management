package project.javafx_fixed_asset_management.Controllers.AccountScreenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.PROFILE;

import java.io.IOException;

public class AccountDialogController {
    @FXML
    TextField nameLbl;

    @FXML
    TextField birthdayLbl;

    @FXML
    TextField phoneNumberLbl;

    @FXML
    TextField addressLbl;

    @FXML
    TextField departmentNameLbl;

    @FXML
    Button backBtn;

    @FXML
    Button updateBtn;

    PROFILE userProfile;
    String userId;

    public void backButtonAction (ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void init(String id) {
        userId = id;
        System.out.println("USER ID: " + userId);

        var profile = new DATABASE_DAO<>(PROFILE.class);
        String myId = String.valueOf(id);

        System.out.println("Account ID: " + this.userId);
        userProfile = profile.selectOne("SELECT TOP 1 * FROM tbProfile WHERE AccountId = ?", myId);
        setLabelValue(userProfile);
    }

    public void setLabelValue (PROFILE profile) {
        nameLbl.setText(profile.getName());
        birthdayLbl.setText(profile.getBirthDay());
        phoneNumberLbl.setText(profile.getPhoneNumber());
        addressLbl.setText(profile.getAddress());
        departmentNameLbl.setText(getDepartment(profile.getDepartmentId()));
    }

    public String getDepartment(String departmentId) {
        var departmentDAO = new DATABASE_DAO<>(DEPARTMENT.class);
        DEPARTMENT department = departmentDAO.selectOne("SELECT TOP 1 * FROM tbDepartment WHERE departmentId = ?", departmentId);
        return  department.getDepartmentName();
    }

    public void updateButtonAction (ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/AccountScreen/update_account_information_dialog.fxml"));
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        Parent updateAccountInformationDialogController = fxmlLoader.load();
        Scene scene = new Scene(updateAccountInformationDialogController);
        UpdateAccountInformationDialogController controller = fxmlLoader.getController();
        controller.init(userProfile, userId);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setTitle("Update profile");
        stage.setScene(scene);
        stage.show();
    }


    public void onMinimizeBtnOnAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setIconified(true);
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


}
