module jky.aluguelfacilcarros {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens jky.aluguelfacilcarros to javafx.fxml;
    exports jky.aluguelfacilcarros;
}