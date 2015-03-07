package base;

import java.util.Observable;

import util.CurrentState;

public class ClientObservable extends Observable implements Client
{
	private Client client;
	public final static int port=8978;
	public ClientObservable(Client client)
	{
		this.client=client;
	}
	@Override
	public String getSucessMessage()
	{
		return client.getSucessMessage();
	}
	@Override
	public String getErrorMessage()
	{
		return client.getErrorMessage();
	}
	@Override
	public String[] getWaitingTaskArgs()
	{
		return client.getWaitingTaskArgs();
	}
	@Override
	public void setWaitingTaskArgs(String[] waitingTaskArgs)
	{
		client.setWaitingTaskArgs(waitingTaskArgs);
	}
	@Override
	public CurrentState getCurrentState()
	{
		return client.getCurrentState();
	}
	@Override
	public int getUnreadMessage()
	{
		return client.getUnreadMessage();
	}
	@Override
	public void openConnexion(String hostname, int port)
	{
		client.openConnexion(hostname, port);
	}
	@Override
	public void closeConnexion()
	{
		client.closeConnexion();
	}
	@Override
	public void signIn(String username)
	{
		client.signIn(username);
	}
	@Override
	public void getMessageList()
	{
		client.getMessageList();
	}
	@Override
	public void getMessageDetails(int i)
	{
		client.getMessageDetails(i);
	}
	@Override
	public void enterLogin(String username)
	{
		client.enterLogin(username);
	}
	@Override
	public void enterPassword(String password)
	{
		client.enterPassword(password);
	}
	@Override
	public void getMessage(int num)
	{
		client.getMessage(num);
	}
	@Override
	public void getStat()
	{
		client.getStat();
	}
	public void notifierObserver()
	{
		this.setChanged();
		notifyObservers();
	}
}
