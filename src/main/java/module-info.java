module project.javafx_fixed_asset_management {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires commons.dbutils;
    requires org.jfxtras.styles.jmetro;
    requires poi;

    opens project.javafx_fixed_asset_management to javafx.fxml;
    exports project.javafx_fixed_asset_management;
    exports project.javafx_fixed_asset_management.Controllers;
    exports project.javafx_fixed_asset_management.Models;
    opens project.javafx_fixed_asset_management.Controllers to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.InventoryScreenControllers;
    opens project.javafx_fixed_asset_management.Controllers.InventoryScreenControllers to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.LiquidationScreenControllers;
    opens project.javafx_fixed_asset_management.Controllers.LiquidationScreenControllers to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.TranferScreenController;
    opens project.javafx_fixed_asset_management.Controllers.TranferScreenController to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.RepairScreenController;
    opens project.javafx_fixed_asset_management.Controllers.RepairScreenController to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.AccountScreenController;
    opens project.javafx_fixed_asset_management.Controllers.AccountScreenController to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers;
    opens project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.AddStaffController;
    opens project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.AddStaffController to javafx.fxml;
    exports project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.UpdateInformationDialogController;
    opens project.javafx_fixed_asset_management.Controllers.AdminHomeScreenControllers.UpdateInformationDialogController to javafx.fxml;
}