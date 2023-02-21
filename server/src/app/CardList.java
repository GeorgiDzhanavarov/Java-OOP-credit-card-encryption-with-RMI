package app;

import components.Card;
import components.CardListInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardList extends UnicastRemoteObject implements CardListInterface {

    private ArrayList<Card> cardList;

    public CardList() throws RemoteException {
        this.cardList = new ArrayList<>();
    }

    public void addCard(String cardNumber,String cardEncryption) throws RemoteException
    {
        cardList.add(new Card(cardNumber,cardEncryption));
    }

    public List<String> sortByCardNumber() throws RemoteException{
        List<String> result = cardList.stream().sorted((o1,o2) -> o1.getCardNumber().compareTo(o2.getCardNumber())).map((a) -> a.toString()).collect(Collectors.toList());
        return new ArrayList<>(result);
    }

    public List<String> sortByEncryption() throws RemoteException{
        List<String> result = cardList.stream().sorted((o1,o2) -> o1.getCardEncryption().compareTo(o2.getCardEncryption())).map((a) -> a.toString()).collect(Collectors.toList());
        return new ArrayList<>(result);
    }

    public String findCardNumber(String encryption) throws RemoteException{
        for (int i = 0; i < cardList.size(); i++) {
            if(cardList.get(i).getCardEncryption().compareTo(encryption) == 0)
                return cardList.get(i).getCardNumber();
        }

        return String.format("card not found");
    }

    public int countEncryption(String cardNumber) throws RemoteException{
        int count = 0;

        for (int i = 0; i < cardList.size(); i++) {
            if(cardList.get(i).getCardNumber().compareTo(cardNumber) == 0)
                count++;
        }

        return count;
    }
}
