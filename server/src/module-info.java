module server {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.fxml;
    requires java.rmi;

    exports components;
    opens components to java.rmi, javafx.fxml;
    exports app;
    opens app to java.rmi, javafx.fxml;

}