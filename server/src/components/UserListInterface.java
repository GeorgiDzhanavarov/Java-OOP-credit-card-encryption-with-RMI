package components;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserListInterface extends Remote {

    public void writeXML(String fileName) throws RemoteException;
    public boolean containUser(String username,String password) throws RemoteException;
    public User getUser(String username) throws RemoteException;

    public void addUser(User user) throws RemoteException;
    public String printUsers() throws RemoteException;

    public boolean canEncrypt(String username) throws  RemoteException;
    public boolean canDecrypt(String username) throws  RemoteException;
}
