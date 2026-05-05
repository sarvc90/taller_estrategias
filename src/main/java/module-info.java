module com.taller.taller_estrategias {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.taller.taller_estrategias to javafx.fxml;
    exports com.taller.taller_estrategias;
}