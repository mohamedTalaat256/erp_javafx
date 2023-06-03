module com.example.erp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.erp to javafx.fxml;
    exports com.example.erp;
    exports com.example.erp.controllers;
    exports com.example.erp.models;
    opens com.example.erp.models to javafx.fxml;
    opens com.example.erp.controllers to javafx.fxml;
}