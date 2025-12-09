module biblioteca.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;

    opens biblioteca.fx to javafx.fxml;
    opens biblioteca.fx.Controller to javafx.fxml;
    opens biblioteca.fx.DAO to javafx.base;
    opens biblioteca.fx.DTO to javafx.base;

    exports biblioteca.fx;
    exports biblioteca.fx.Controller;
    exports biblioteca.fx.DAO;
    exports biblioteca.fx.DTO;
}
