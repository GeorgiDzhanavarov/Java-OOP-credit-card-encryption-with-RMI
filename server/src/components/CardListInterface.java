package components;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CardListInterface extends Remote {

    public  void addCard(String cardNumber,String cardEncryption) throws RemoteException;
    public List<String> sortByCardNumber() throws RemoteException;
    public List<String> sortByEncryption() throws RemoteException;

    public String findCardNumber(String encryption) throws RemoteException;

    public int countEncryption(String cardNumber) throws RemoteException;
}
