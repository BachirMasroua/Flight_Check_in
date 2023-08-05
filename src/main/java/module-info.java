module com.bachir.checkinapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bachir.checkinapp to javafx.fxml;
    exports com.bachir.checkinapp;
}
