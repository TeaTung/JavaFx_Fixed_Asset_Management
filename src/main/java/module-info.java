module project.javafx_fixed_asset_management {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens project.javafx_fixed_asset_management to javafx.fxml;
    exports project.javafx_fixed_asset_management;
    exports project.javafx_fixed_asset_management.Controllers;
    opens project.javafx_fixed_asset_management.Controllers to javafx.fxml;
}