package client;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import components.CardListInterface;
import components.Cipherable;
import components.User;
import components.UserListInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDecrypt;

    @FXML
    private Button btnEncrypt;

    @FXML
    private TextField txtCardNumber;

    @FXML
    private TextField txtResult;

    private boolean canEncrypt;

    private boolean canDecrypt;

    private CardListInterface cardList;

    private Cipherable cipher;

    private boolean checkLuhn(String cardNum)
    {
        int nDigits = cardNum.length();

        char[] cardNo = cardNum.toCharArray();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNo[i] - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits after
            // doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    @FXML
    void btnDecryptClicked(ActionEvent event) {
            if(canDecrypt == false)
                txtResult.setText("can't decrypt");
            else
            {
                try {
                    txtResult.setText(cardList.findCardNumber(txtCardNumber.getText()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    @FXML
    void btnEncryptClicked(ActionEvent event) {
            if(canEncrypt == false)
            {
                txtResult.setText("can't encrypt");
            }
            else
            {
                    if(!txtCardNumber.getText().matches("^(3|4|5|6)[0-9]{15}$"))
                    {
                        txtResult.setText("invalid card number");
                    }
                    else
                    {
                          if(!checkLuhn(txtCardNumber.getText()))
                          {
                              txtResult.setText("invalid card number");
                          }
                          else
                          {
                              try {
                                  if(cardList.countEncryption(txtCardNumber.getText()) >= 12)
                                  {
                                      txtResult.setText("Maximum encryptions");
                                  }
                                  else
                                  {
                                      String encryption = cipher.encprypt(txtCardNumber.getText(),
                                              (5 + cardList.countEncryption(txtCardNumber.getText()))%10);

                                      txtResult.setText(encryption);
                                      cardList.addCard(txtCardNumber.getText(),encryption);
                                  }
                              } catch (RemoteException e) {
                                  throw new RuntimeException(e);
                              }
                          }
                    }
            }
    }

    public void getUser(boolean canEncrypt, boolean canDecrypt)
    {
        this.canEncrypt = canEncrypt;
        this.canDecrypt = canDecrypt;
    }

    @FXML
    void initialize() {
        assert btnDecrypt != null : "fx:id=\"btnDecrypt\" was not injected: check your FXML file 'ClientScene.fxml'.";
        assert btnEncrypt != null : "fx:id=\"btnEncrypt\" was not injected: check your FXML file 'ClientScene.fxml'.";
        assert txtCardNumber != null : "fx:id=\"txtCardNumber\" was not injected: check your FXML file 'ClientScene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ClientScene.fxml'.";

        Registry registry1 = null;
        try {
            registry1 = LocateRegistry.getRegistry("localhost", 1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        try {
            try {
                cardList = (CardListInterface)registry1.lookup("cards");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        Registry registry2 = null;
        try {
            registry2 = LocateRegistry.getRegistry("localhost", 1100);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        try {
            try {
                cipher = (Cipherable) registry2.lookup("cipher");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
