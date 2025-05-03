module jky.aluguelfacilcarros {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens jky.aluguelfacilcarros to javafx.fxml;
    exports jky.aluguelfacilcarros;
}