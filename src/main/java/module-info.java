module com.example.filecrypt {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.filecrypt to javafx.fxml;
    exports com.example.filecrypt;
    exports com.example.filecrypt.algorithmes;
    opens com.example.filecrypt.algorithmes to javafx.fxml;
    exports com.example.filecrypt.utils;
    opens com.example.filecrypt.utils to javafx.fxml;
}