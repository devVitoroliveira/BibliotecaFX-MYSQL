module biblioteca.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    opens biblioteca.fx to javafx.fxml;
    opens biblioteca.fx.Controller to javafx.fxml;
    opens biblioteca.fx.DTO to javafx.base;

    exports biblioteca.fx;
}