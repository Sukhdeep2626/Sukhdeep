module com.example.sukhdeep {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.sukhdeep to javafx.fxml;
    exports com.example.sukhdeep;
}
