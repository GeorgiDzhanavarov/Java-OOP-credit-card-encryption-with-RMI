package components;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Cipherable extends Remote {

    public String encprypt(String cardNumber,int cipherNumber) throws RemoteException;
}
