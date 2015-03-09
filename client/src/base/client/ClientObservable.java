package base.client;

import base.Email;
import util.CurrentState;

import java.util.Observable;

public class ClientObservable extends Observable implements Client {
    public final static int port = 110;
    private Client client;

    public ClientObservable(Client client) {
        this.client = client;
    }

    @Override
    public String getSucessMessage() {
        return client.getSucessMessage();
    }

    @Override
    public Email getMessage() {
        return client.getMessage();
    }

    @Override
    public String getErrorMessage() {
        return client.getErrorMessage();
    }

    @Override
    public CurrentState getCurrentState() {
        return client.getCurrentState();
    }

    @Override
    public int getUnreadMessage() {
        return client.getUnreadMessage();
    }

    @Override
    public void openConnexion(String hostname, int port) {
        client.openConnexion(hostname, port);
    }

    @Override
    public void closeConnexion() {
        client.closeConnexion();
    }

    @Override
    public void signIn(String username) {
        client.signIn(username);
    }

    @Override
    public void getMessageList() {
        client.getMessageList();
    }

    @Override
    public void getMessageDetails(int i) {
        client.getMessageDetails(i);
    }

    @Override
    public void enterLogin(String username) {
        client.enterLogin(username);
    }

    @Override
    public void enterPassword(String password) {
        client.enterPassword(password);
    }

    @Override
    public void getMessage(int num) {
        client.getMessage(num);
    }

    @Override
    public void getStat() {
        client.getStat();
    }

    public void notifierObserver() {
        this.setChanged();
        notifyObservers();
    }
}
