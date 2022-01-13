package project.javafx_fixed_asset_management.Controllers.LiquidationScreenControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.PERSON;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

public class FirstLiquidationScreenController implements Initializable {
    @FXML
    Button addBtn;

    @FXML
    Button addNewPersonBtn;

    @FXML
    Button backBtn;

    @FXML
    Button continueBtn;

    @FXML
    DatePicker dateOfLiquidationDtp;

    @FXML
    TextField departmentTF;

    @FXML
    Label errorLabel;

    @FXML
    TableColumn<PERSON, String> existedPeopleDepartmentColumn;

    @FXML
    TableColumn<PERSON, String> existedPeopleIdColumn;

    @FXML
    TableColumn<PERSON, String> existedPeopleNameColumn;

    @FXML
    TableView<PERSON> existedPeopleTV;

    @FXML
    TableColumn<PERSON, String> existedPeopleTitleColumn;

    @FXML
    TableColumn<PERSON, String> liquidationPeopleTitleColumn;

    @FXML
    TableColumn<PERSON, String> liquidationPeopleDepartmentColumn;

    @FXML
    TableColumn<PERSON, String> liquidationPeopleIdColumn;

    @FXML
    TableColumn<PERSON, String> liquidationPeopleNameColumn;

    @FXML
    TableView<PERSON> liquidationPeopleTV;

    @FXML
    TextField nameTF;

    @FXML
    Button removeBtn;

    @FXML
    TextField titleTF;

    boolean isRowSelected = false;
    public ObservableList<PERSON> listPeople;
    public ObservableList<PERSON> listLiquidationPeople;
    FilteredList<PERSON> filteredList;

    @FXML
    void addNewButtonAction(ActionEvent event) {
        String personToAdd = "Name: " + nameTF.getText() + "\n" + "Department: " + departmentTF.getText() + "\n" + "Title: " + titleTF.getText();
        FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION, "Are you sure to add this new person? \n" + personToAdd, ButtonType.YES, ButtonType.CANCEL);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(alert.getDialogPane().getScene());
        alert.showAndWait();


        if (alert.getResult() == ButtonType.YES) {
            var people = new DATABASE_DAO<>(PERSON.class);
            var department = new DATABASE_DAO<>(DEPARTMENT.class);
            var departmentName = departmentTF.getText().trim().toLowerCase();
            DEPARTMENT departmentData = null;
            try {
                departmentData = department.selectOne("SELECT TOP 1 * FROM tbDepartment WHERE DepartmentName = ?", departmentName);
            } catch (Exception e) {
                FlatAlert information = new FlatAlert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
                jMetro.setScene(information.getDialogPane().getScene());
                information.showAndWait();
                return;
            }

            var personId = UUID.randomUUID().toString().substring(0, 10);
            if (departmentData == null) {
                try {
                    var departmentId = UUID.randomUUID().toString().substring(0, 10);
                    departmentName = departmentName.substring(0, 1).toUpperCase() + departmentName.substring(1).toLowerCase();
                    department.insert("INSERT INTO TBDEPARTMENT (DepartmentId, DepartmentName) VALUES ('" + departmentId + "', '" + departmentName + "')");
                    people.insert("INSERT INTO TBPERSON (PersonID, Name, DepartmentId, Title) VALUES ('" + personId + "', '" + nameTF.getText() + "', '" + departmentId + "', '" + titleTF.getText() + "')");
                    Alert information = new Alert(Alert.AlertType.INFORMATION, "Added person successful! \n" + personToAdd, ButtonType.OK);
                    information.showAndWait();
                    getDataInTableView();
                    clearTextFields();
                    return;
                } catch (Exception e) {
                    Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
                    information.showAndWait();
                    return;
                }

            } else if (departmentData.getDepartmentName().toLowerCase().equals(departmentName)) {
                try {
                    var departmentId = departmentData.getDepartmentId();
                    people.insert("INSERT INTO TBPERSON (PersonID, Name, DepartmentId, Title) VALUES ('" + personId + "', '" + nameTF.getText() + "', '" + departmentId + "', '" + titleTF.getText() + "')");
                    Alert information = new Alert(Alert.AlertType.INFORMATION, "Added person successful! \n" + personToAdd, ButtonType.OK);
                    information.showAndWait();
                    getDataInTableView();
                    clearTextFields();
                    return;
                } catch (Exception e) {
                    Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
                    information.showAndWait();
                    return;
                }
            }
        }
    }

    void clearTextFields() {
        nameTF.clear();
        departmentTF.clear();
        titleTF.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataInTableView();
        addNewPersonBtn.setDisable(true);
        var minDate = LocalDate.of(2000, 1, 1);
        var maxDate = LocalDate.now();
        dateOfLiquidationDtp.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }
                });
        dateOfLiquidationDtp.setValue(LocalDate.now());
        errorLabel.setVisible(false);
    }

    @FXML
    void addPersonButtonAction(ActionEvent event) {
        PERSON addingPerson = existedPeopleTV.getSelectionModel().getSelectedItem();

        if (addingPerson != null) {
            listLiquidationPeople.add(addingPerson);
            listPeople.remove(addingPerson);
            liquidationPeopleTV.setItems(listLiquidationPeople);
        }
    }


    @FXML
    void existedPeopleRowSelectedHandler(MouseEvent event) {
        isRowSelected = true;
        addNewPersonBtn.setDisable(true);
        PERSON selectedPerson = existedPeopleTV.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            nameTF.setText(selectedPerson.getName());
            departmentTF.setText(selectedPerson.getDepartmentName());
            titleTF.setText(selectedPerson.getTitle());
        }
    }

    @FXML
    void removePersonButtonAction(ActionEvent event) {
        PERSON removingPerson = liquidationPeopleTV.getSelectionModel().getSelectedItem();

        if (removingPerson != null) {
            listPeople.add(removingPerson);
            listLiquidationPeople.remove(removingPerson);
            existedPeopleTV.setItems(listPeople);
        }
    }

    @FXML
    void textChangedHandler(KeyEvent event) {
        isRowSelected = false;
        setSearchInTableView();
        addNewButtonStateHandler();
    }

    void addNewButtonStateHandler() {
        if (nameTF.getText().isEmpty() || departmentTF.getText().isEmpty() || titleTF.getText().isEmpty()) {
            addNewPersonBtn.setDisable(true);
        } else {
            addNewPersonBtn.setDisable(false);
        }
    }

    @FXML
    void backButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/HomeScreen/Manager/manager_home_screen.fxml"));
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

    boolean validateData() {
        if (listLiquidationPeople.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Please add people to liquidation!");
            return false;
        } else if (dateOfLiquidationDtp.getValue() == null) {
            errorLabel.setVisible(true);
            errorLabel.setText("Please select date of liquidation!");
            return false;
        }
        errorLabel.setVisible(false);
        return true;
    }

    @FXML
    void continueButtonAction(ActionEvent event) {
        if (validateData() == true) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/LiquidationScreen/second_liquidation_screen.fxml"));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1280, 720);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SecondLiquidationScreenController secondLiquidationScreenController = fxmlLoader.getController();
            secondLiquidationScreenController.initData(listLiquidationPeople, dateOfLiquidationDtp.getValue());
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            stage.setScene(scene);
            stage.show();
        }
    }

    private void getDataInTableView() {
        var people = new DATABASE_DAO<>(PERSON.class);
        try {
            listPeople = FXCollections.observableArrayList(people.selectList(
                    "SELECT PersonId, DepartmentName, Name, Title FROM tbPerson LEFT JOIN tbDepartment ON tbPerson.DepartmentId = tbDepartment.DepartmentId"));
        } catch (Exception e) {
            Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
            information.showAndWait();
            return;
        }

        listLiquidationPeople = FXCollections.observableArrayList();

        existedPeopleIdColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("PersonId"));
        existedPeopleNameColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Name"));
        existedPeopleTitleColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Title"));
        existedPeopleDepartmentColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("DepartmentName"));

        liquidationPeopleIdColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("PersonId"));
        liquidationPeopleNameColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Name"));
        liquidationPeopleTitleColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Title"));
        liquidationPeopleDepartmentColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("DepartmentName"));

        existedPeopleTV.setItems(listPeople);
    }

    public void setSearchInTableView() {
        if (isRowSelected != true) {
            filteredList = new FilteredList<>(listPeople, b -> true);

            nameTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(person -> {
                    if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }

                    String nameSearchValue = newValue.toLowerCase();
                    String departmentSearchValue = departmentTF.getText().toLowerCase();
                    String titleSearchValue = titleTF.getText().toLowerCase();
                    if (person.getDepartmentName().toLowerCase().contains(departmentSearchValue)) {
                        if (person.getTitle().toLowerCase().contains(titleSearchValue)) {
                            if (person.getName().toLowerCase().indexOf(nameSearchValue) > -1) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                    return false;
                });
            }));

            departmentTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(person -> {
                    if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }

                    String nameSearchValue = nameTF.getText().toLowerCase();
                    String departmentSearchValue = newValue.toLowerCase();
                    String titleSearchValue = titleTF.getText().toLowerCase();
                    if (person.getName().toLowerCase().contains(nameSearchValue)) {
                        if (person.getTitle().toLowerCase().contains(titleSearchValue)) {
                            if (person.getDepartmentName().toLowerCase().indexOf(departmentSearchValue) > -1) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                    return false;
                });
            }));

            titleTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(person -> {
                    if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }

                    String nameSearchValue = nameTF.getText().toLowerCase();
                    String departmentSearchValue = departmentTF.getText().toLowerCase();
                    String titleSearchValue = newValue.toLowerCase();
                    if (person.getName().toLowerCase().contains(nameSearchValue)) {
                        if (person.getDepartmentName().toLowerCase().contains(departmentSearchValue)) {
                            if (person.getTitle().toLowerCase().indexOf(titleSearchValue) > -1) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                    return false;
                });
            }));

            SortedList<PERSON> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(existedPeopleTV.comparatorProperty());

            existedPeopleTV.setItems(sortedList);
        }

    }

    @FXML
    void deletePeopleButtonAction(ActionEvent event) {

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