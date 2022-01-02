package project.javafx_fixed_asset_management.Controllers.InventoryScreenControllers;

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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import project.javafx_fixed_asset_management.Main;
import project.javafx_fixed_asset_management.Models.DATABASE_DAO;
import project.javafx_fixed_asset_management.Models.DEPARTMENT;
import project.javafx_fixed_asset_management.Models.DEVICE;
import project.javafx_fixed_asset_management.Models.PERSON;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

public class FirstInventoryScreenController implements Initializable {
    @FXML
    Button addBtn;

    @FXML
    Button backBtn;

    @FXML
    Button continueBtn;

    @FXML
    DatePicker dateOfInventoryDtp;

    @FXML
    TextField departmentTF;

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
    TableColumn<PERSON, String> inventoryPeopleDepartmentColumn;

    @FXML
    TableColumn<PERSON, String> inventoryPeopleIdColumn;

    @FXML
    TableColumn<PERSON, String> inventoryPeopleNameColumn;

    @FXML
    TableView<PERSON> inventoryPeopleTV;

    @FXML
    TableColumn<PERSON, String> inventoryPeopleTitleColumn;

    @FXML
    TextField nameTF;

    @FXML
    TextField titleTF;

    @FXML
    Button removeBtn;

    @FXML
    Button addNewPersonBtn;

    @FXML
    Label errorLabel;

    boolean isRowSelected = false;
    public ObservableList<PERSON> listPeople;
    public ObservableList<PERSON> listInventoryPeople;
    FilteredList<PERSON> filteredList;

    @FXML
    void addPersonButtonAction(ActionEvent event) {
        PERSON addingPerson = existedPeopleTV.getSelectionModel().getSelectedItem();

        if (addingPerson != null) {
            listInventoryPeople.add(addingPerson);
            listPeople.remove(addingPerson);
            inventoryPeopleTV.setItems(listInventoryPeople);
        }
    }


    @FXML
    void removePersonButtonAction(ActionEvent event) {
        PERSON removingPerson = inventoryPeopleTV.getSelectionModel().getSelectedItem();

        if (removingPerson != null) {
            listPeople.add(removingPerson);
            listInventoryPeople.remove(removingPerson);
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
    void existedPeopleRowSelectedHandler(MouseEvent event) {
        isRowSelected = true;
        addNewPersonBtn.setDisable(true);
        PERSON selectedPerson = existedPeopleTV.getSelectionModel().getSelectedItem();
        if(selectedPerson != null) {
            nameTF.setText(selectedPerson.getName());
            departmentTF.setText(selectedPerson.getDepartmentName());
            titleTF.setText(selectedPerson.getTitle());
        }
    }

    @FXML
    public void backButtonAction(ActionEvent event) {
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

    @FXML
    public void continueButtonAction(ActionEvent event) {
        if(validateData() == true) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/InventoryScreen/second_inventory_screen.fxml"));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 1280, 720);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SecondInventoryScreenController secondInventoryScreenController = fxmlLoader.getController();
            secondInventoryScreenController.initData(listInventoryPeople, dateOfInventoryDtp.getValue());
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void addNewButtonAction(ActionEvent event) {
        String personToAdd = "Name: " + nameTF.getText() + "\n" + "Department: " + departmentTF.getText() + "\n" + "Title: " + titleTF.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to add this new person? \n" + personToAdd, ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();


        if (alert.getResult() == ButtonType.YES) {
            var people = new DATABASE_DAO<>(PERSON.class);
            var department = new DATABASE_DAO<>(DEPARTMENT.class);
            var departmentName = departmentTF.getText().trim().toLowerCase();
            DEPARTMENT departmentData = null;
            try {
                departmentData = department.selectOne("SELECT TOP 1 * FROM tbDepartment WHERE DepartmentName = ?", departmentName);
            } catch (Exception e) {
                Alert information = new Alert(Alert.AlertType.ERROR, "Something went wrong! Detail information below: \n" + e.toString(), ButtonType.OK);
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
        dateOfInventoryDtp.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }
                });
        dateOfInventoryDtp.setValue(LocalDate.now());
        errorLabel.setVisible(false);
    }

    boolean validateData(){
        if(listInventoryPeople.isEmpty())  {
            errorLabel.setVisible(true);
            errorLabel.setText("Please add people to inventory!");
            return false;
        } else if(dateOfInventoryDtp.getValue() == null) {
            errorLabel.setVisible(true);
            errorLabel.setText("Please select date of inventory!");
            return false;
        }
        errorLabel.setVisible(false);
        return true;
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

        listInventoryPeople = FXCollections.observableArrayList();

        existedPeopleIdColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("PersonId"));
        existedPeopleNameColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Name"));
        existedPeopleTitleColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Title"));
        existedPeopleDepartmentColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("DepartmentName"));

        inventoryPeopleIdColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("PersonId"));
        inventoryPeopleNameColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Name"));
        inventoryPeopleTitleColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("Title"));
        inventoryPeopleDepartmentColumn.setCellValueFactory(new PropertyValueFactory<PERSON, String>("DepartmentName"));

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
}
