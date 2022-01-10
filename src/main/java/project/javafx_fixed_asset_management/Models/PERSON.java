package project.javafx_fixed_asset_management.Models;

public class PERSON {

    String personId;
    String departmentId;
    String name;
    String title;

    DEPARTMENT department = new DEPARTMENT();

    public String getDepartmentName() {
        return this.department.getDepartmentName();
    }

    public void setDepartmentName(String departmentName) {
        this.department.setDepartmentName(departmentName);
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
