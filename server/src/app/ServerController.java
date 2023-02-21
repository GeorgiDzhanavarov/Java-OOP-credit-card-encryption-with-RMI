package app;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import components.CardListInterface;
import components.Cipherable;
import components.User;
import components.UserListInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static java.nio.file.StandardOpenOption.CREATE;

public class ServerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnExportByCardNumber;

    @FXML
    private Button btnExportByEncryption;

    @FXML
    private CheckBox checkDecryption;

    @FXML
    private CheckBox checkEncryption;

    @FXML
    private TextArea txaOutput;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    public UserListInterface userList;

    public CardListInterface cardList;

    public  Cipherable cipher;

    @FXML
    void btnAddUserClicked(ActionEvent event) throws RemoteException {

        User user = new User(txtUsername.getText(),txtPassword.getText(),checkEncryption.isSelected(),checkDecryption.isSelected());
        if(userList.getUser(txtUsername.getText()) == null)
        {
            userList.addUser(user);
            userList.writeXML("C:\\Users\\Georgi Dzh\\IdeaProjects\\_8MI0600036_Project4\\server\\src\\app\\users.xml");
        }
        else
        {
            txaOutput.appendText("username already exist\n");
        }
    }

    @FXML
    void btnExportByCardNumberClicked(ActionEvent event) throws RemoteException {
        List<String> lines = cardList.sortByCardNumber();
        Path path = Paths.get("C:\\Users\\Georgi Dzh\\IdeaProjects\\_8MI0600036_Project4\\server\\src\\app\\sortedByCardNumber.txt");
        try {
            Files.write(path,lines, Charset.defaultCharset(),CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnExportByEncryptionClicked(ActionEvent event) throws RemoteException {
        List<String> lines = cardList.sortByEncryption();
        Path path = Paths.get("C:\\Users\\Georgi Dzh\\IdeaProjects\\_8MI0600036_Project4\\server\\src\\app\\sortedByEncryption.txt");
        try {
            Files.write(path,lines, Charset.defaultCharset(),CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert btnAddUser != null : "fx:id=\"btnAddUser\" was not injected: check your FXML file 'ServerScene.fxml'.";
        assert btnExportByCardNumber != null : "fx:id=\"btnExportByCardNumber\" was not injected: check your FXML file 'ServerScene.fxml'.";
        assert btnExportByEncryption != null : "fx:id=\"btnExportByEncryption\" was not injected: check your FXML file 'ServerScene.fxml'.";
        assert checkDecryption != null : "fx:id=\"checkDecryption\" was not injected: check your FXML file 'ServerScene.fxml'.";
        assert checkEncryption != null : "fx:id=\"checkEncryption\" was not injected: check your FXML file 'ServerScene.fxml'.";
        assert txaOutput != null : "fx:id=\"txaOutput\" was not injected: check your FXML file 'ServerScene.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'ServerScene.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'ServerScene.fxml'.";

        try{
            userList = new UserList("C:\\Users\\Georgi Dzh\\IdeaProjects\\_8MI0600036_Project4\\server\\src\\app\\users.xml");

            Registry registry1 = LocateRegistry.createRegistry(1101);
            registry1.rebind("users",userList);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        try{
            cardList = new CardList();

            Registry registry2 = LocateRegistry.createRegistry(1099);
            registry2.rebind("cards",cardList);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }


        try{
            cipher = new Cipher();

            Registry registry3 = LocateRegistry.createRegistry(1100);
            registry3.rebind("cipher",cipher);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

}