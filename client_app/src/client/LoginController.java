package client;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import app.UserList;
import components.UserListInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private UserListInterface userList;

    @FXML
    void btnLoginClicked(ActionEvent event) throws IOException {

        if(userList.containUser(txtUsername.getText(),txtPassword.getText()))
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientScene.fxml"));
            Parent root = (Parent) loader.load();
            ClientController controller = loader.getController();
            controller.getUser(userList.canEncrypt(txtUsername.getText()),userList.canDecrypt(txtUsername.getText()));

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setTitle("Platform");
            stage.sizeToScene();
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Wrong username or password");
        }


    }

    @FXML
    void initialize() {
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'LoginScene.fxml'.";

        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", 1101);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        try {
            try {
                userList = (UserListInterface)registry.lookup("users");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
