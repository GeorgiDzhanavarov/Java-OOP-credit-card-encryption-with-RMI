package app;

import components.User;
import components.UserListInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

public class UserList extends UnicastRemoteObject implements UserListInterface {

    private ArrayList<User> userList;

    public UserList(String fileName) throws RemoteException {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        XMLDecoder decoder = new XMLDecoder(fis);
        User user;
        userList = new ArrayList<User>();

        boolean flag = true;

        while(flag == true)
        {
            try {
                user = (User) decoder.readObject();

            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                flag = false;
                user = null;
            }

            if(flag == true)
            {
                userList.add(user);
            }
        }

        decoder.close();
        try {
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeXML(String fileName) throws RemoteException{
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        XMLEncoder encoder = new XMLEncoder(fos);

        for (int i = 0; i < userList.size(); i++) {
            encoder.writeObject(userList.get(i));
        }

        encoder.close();
        try {
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) throws RemoteException
    {
        boolean flag = true;
        for (int i = 0; i < userList.size(); i++) {
            if(user.getUsername() == userList.get(i).getUsername())
                flag = false;
        }
        if(flag = true)
        userList.add(user);
    }

    public boolean containUser(String username,String password) throws RemoteException
    {
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUsername().compareTo(username) == 0
                    && userList.get(i).getPassword().compareTo(password) == 0)
                return true;
        }

        return false;
    }

    public User getUser(String username) throws RemoteException
    {
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUsername().compareTo(username) == 0)
                return userList.get(i);
        }

        return null;
    }

    public String printUsers() throws RemoteException{
        String result = "";

        for (int i = 0; i < userList.size(); i++) {
            result += "\n" + userList.get(i).toString();
        }

        return result;
    }

    public boolean canEncrypt(String username) throws  RemoteException{
        return this.getUser(username).getCanEncrypt();
    }
    public boolean canDecrypt(String username) throws  RemoteException{
        return this.getUser(username).getCanDecrypt();
    }
}
