module uv.lis.professionalpracticesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    opens uv.lis.professionalpracticesystem to javafx.graphics, javafx.fxml;
    opens uv.lis.professionalpracticesystem.presentation.controllers to javafx.fxml;

    exports uv.lis.professionalpracticesystem;
    exports uv.lis.professionalpracticesystem.presentation;
    exports uv.lis.professionalpracticesystem.presentation.controllers;
}