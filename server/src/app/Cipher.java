package app;

import components.Cipherable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Cipher extends UnicastRemoteObject implements Cipherable {

    protected Cipher() throws  RemoteException
    {
    }

    @Override
    public String encprypt(String cardNumber,int cipherNumber) throws RemoteException {

        char[] input = cardNumber.toCharArray();
        char[] result = new char[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = (char)(Math.abs((int)input[i] - '0' + cipherNumber)%10 + '0');
        }

        return String.valueOf(result);
    }

}
