package components;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Card extends UnicastRemoteObject {

    private String cardNumber;
    private String cardEncryption;

    public Card(String cardNumber,String cardEncryption) throws RemoteException {
        this.cardNumber = cardNumber;
        this.cardEncryption = cardEncryption;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardEncryption() {
        return cardEncryption;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardEncryption(String cardEncryption) {
        this.cardEncryption = cardEncryption;
    }

    @Override
    public String toString() {
        return String.format("card Number: %s, card encryption: %s\n",cardNumber,cardEncryption);
    }
}
