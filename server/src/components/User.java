package components;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class User  extends UnicastRemoteObject {

    private String username;
    private String password;
    private Boolean canEncrypt;
    private Boolean canDecrypt;


    public User() throws RemoteException {
        username = "";
        password = "";
        canDecrypt = false;
        canEncrypt = false;
    }

    public User(String username, String password, Boolean canEncrypt, Boolean canDecrypt) throws RemoteException {
        this.setUsername(username);
        this.setPassword(password);
        this.setCanEncrypt(canEncrypt);
        this.setCanDecrypt(canDecrypt);
    }

    public String getUsername() throws RemoteException {
        return username;
    }

    public String getPassword() throws RemoteException {
        return password;
    }

    public Boolean getCanEncrypt() {
        return canEncrypt;
    }

    public Boolean getCanDecrypt() {
        return canDecrypt;
    }

    public void setUsername(String username) throws RemoteException {
        if(username != null)
        this.username = username;
    }

    public void setPassword(String password) throws RemoteException {
        if(password != null)
        this.password = password;
    }

    public void setCanEncrypt(Boolean canEncrypt) throws RemoteException {
        this.canEncrypt = canEncrypt;
    }

    public void setCanDecrypt(Boolean canDecrypt) throws RemoteException {
        this.canDecrypt = canDecrypt;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", canEncrypt=" + canEncrypt +
                ", canDecrypt=" + canDecrypt +
                '}';
    }
}
