package base.client.impl;

import base.client.Client;
import base.client.Config;
import base.email.Email;
import base.email.EmailUtil;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import exception.UnrespondingServerException;
import org.apache.log4j.Logger;
import util.ServerUtil;
import util.pop3.Pop3Action;
import util.pop3.Pop3State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Pop3Client extends Client {
	private static Logger logger = Logger.getLogger(Pop3Client.class.getName());
	private Pop3State pop3State = null;
	private int unreadMessage = 0;
	private Pop3Action waitingTask = null;
	private String timestamp = "";

	public String getTimestamp() {
		return timestamp;
	}

	private void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSucessMessage() {
		String message = response.sucessMessage;
		response.sucessMessage = null;
		return message;
	}

	private void setSucessMessage(String sucessMessage) {
		response.sucessMessage = sucessMessage;
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public Email getMessage() {
		if (response.email == null)
			return null;
		Email message = new Email(response.email);
		response.email = null;
		return message;
	}

	private void setMessage(Email email) {
		this.response.email = email;
	}

	public String getErrorMessage() {
		String message = response.errorMessage;
		response.errorMessage = null;
		return message;
	}

	private void setErrorMessage(String errorMessage) {
		this.response.errorMessage = errorMessage;
	}

	public String[] getWaitingTaskArgs() {
		return waitingTaskArgs == null ?
				null :
				Arrays.copyOf(waitingTaskArgs, waitingTaskArgs.length);
	}

	private void setWaitingTaskArgs(String[] waitingTaskArgs) {
		this.waitingTaskArgs = waitingTaskArgs;
	}

	public Pop3State getPop3State() {
		return pop3State;
	}

	private void setPop3State(Pop3State pop3State) {
		this.pop3State = pop3State;
	}

	public int getUnreadMessage() {
		return unreadMessage;
	}

	private void setUnreadMessage(int unreadMessage) {
		this.unreadMessage = unreadMessage;
	}

	private Pop3Action getWaitingTask() {
		return waitingTask;
	}

	private void setWaitingTask(Pop3Action waitingTask) {
		this.waitingTask = waitingTask;
	}

	public void run() {
		Pop3Action todo;
		String[] todoArgs;
		String message;
		if (!(quit && getWaitingTask() == null
				&& getWaitingTaskArgs() == null)) {
			todo = getWaitingTask();
			todoArgs = getWaitingTaskArgs();
			if (todo != null && todoArgs != null) {
				try {
					message = todo.execute(pop3State, todoArgs);
					if (Pop3Action.CONNEXION.equals(todo) && Config.getApop()) {
						setTimestamp(ServerUtil.getInstance()
								.computeTimeStamp(message));
					}
					if (Pop3Action.APOP.equals(todo)) {
						setUsername(todoArgs[0]);
					}
					if (Pop3Action.QUIT.equals(todo)) {
						pop3State = null;
					}
					if (Pop3Action.RETR.equals(todo)) {
						Email received = new Email(message);
						setMessage(received);
						try {
							if (Config.getAutosave())
								EmailUtil.saveReceivedEmail(received, username);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					logger.debug(message);
					setPop3State(Pop3State
							.changeTo(getPop3State(), todo.getIfSucceed()));
					setSucessMessage(message);
				} catch (UnallowedActionException e) {
					message = e.getMessage();
					logger.error(message);
					setErrorMessage(message);
				} catch (ErrorResponseServerException e) {
					setPop3State(Pop3State
							.changeTo(getPop3State(), todo.getIfFailed()));
					message = e.getMessage();
					logger.error(message);
					setErrorMessage(message);
				} catch (UnrespondingServerException e) {
					message = e.getMessage();
					logger.error(message);
					setErrorMessage(message);
				} finally {
					setWaitingTask(null);
					setWaitingTaskArgs(null);
				}
			}
		}
	}

	public void openConnexion(String hostname, int port) {
		setWaitingTask(Pop3Action.CONNEXION);
		String[] args = { hostname, Integer.toString(port) };
		setWaitingTaskArgs(args);
		run();
	}

	public void closeConnexion() {
		setWaitingTask(Pop3Action.QUIT);
		String[] args = { };
		setWaitingTaskArgs(args);
		run();
	}

	public void exit() {
		quit = Boolean.TRUE;
		run();
	}

	public void signIn(String username, String password) {
		setWaitingTask(Pop3Action.APOP);
		String[] args = { username, password, timestamp };
		setWaitingTaskArgs(args);
		run();
	}

	public void signIn(String username) {
		setWaitingTask(Pop3Action.APOP);
		String[] args = { username, "" };
		setWaitingTaskArgs(args);
		run();
	}

	public void getMessageList() {
		setWaitingTask(Pop3Action.LIST);
		String[] args = { };
		setWaitingTaskArgs(args);
		run();
	}

	public ArrayList<Email> getSavedMessages() {
		if (!Pop3State.TRANSACTION.equals(pop3State)) {
			try {
				throw new UnallowedActionException();
			} catch (UnallowedActionException e) {
				String message = e.toString();
				logger.error(message);
				setErrorMessage(message);
				return null;
			}
		}
		return getSavedMessages(username);
	}

	public ArrayList<Email> getSavedMessages(String username) {
		try {
			return EmailUtil.getReceivedEmails(username);
		} catch (IOException e) {
			String message = e.toString();
			logger.error(message);
			setErrorMessage(message);
			return new ArrayList<>();
		}
	}

	public void getMessageDetails(int i) {
		setWaitingTask(Pop3Action.LIST);
		String[] args = { Integer.toString(i) };
		setWaitingTaskArgs(args);
		run();
	}

	public void enterLogin(String username) {
		setWaitingTask(Pop3Action.USER);
		String[] args = { username };
		setWaitingTaskArgs(args);
		run();
	}

	public void enterPassword(String password) {
		setWaitingTask(Pop3Action.PASS);
		String[] args = { password };
		setWaitingTaskArgs(args);
		run();
	}

	public void getMessage(int num) {
		setWaitingTask(Pop3Action.RETR);
		String[] args = { Integer.toString(num) };
		setWaitingTaskArgs(args);
		run();
	}

	public void getStat() {
		setWaitingTask(Pop3Action.STAT);
		String[] args = { };
		setWaitingTaskArgs(args);
		run();
	}
}
