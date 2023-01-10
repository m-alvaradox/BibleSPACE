module espol.poo.biblespace {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens espol.poo.biblespace to javafx.fxml;
    exports espol.poo.biblespace;
}
