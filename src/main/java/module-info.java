module biblioteca.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens biblioteca.fx to javafx.fxml;

    exports biblioteca.fx;
}
