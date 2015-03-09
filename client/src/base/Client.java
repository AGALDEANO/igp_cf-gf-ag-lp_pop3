package base;

import util.CurrentState;

public interface Client {
    public String getSucessMessage();

    public String getErrorMessage();

    public String[] getWaitingTaskArgs();

    public void setWaitingTaskArgs(String[] waitingTaskArgs);

    public CurrentState getCurrentState();

    public int getUnreadMessage();

    public void openConnexion(String hostname, int port);

    public void closeConnexion();

    public void signIn(String username);

    public void getMessageList();

    public void getMessageDetails(int i);

    public void enterLogin(String username);

    public void enterPassword(String password);

    public void getMessage(int num);

    public void getStat();
}
