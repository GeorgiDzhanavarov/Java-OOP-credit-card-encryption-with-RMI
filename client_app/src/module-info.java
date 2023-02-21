module client.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    requires server;

    opens client to javafx.fxml;
    exports client to javafx.graphics;
}