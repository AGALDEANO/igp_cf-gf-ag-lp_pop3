package base.client;

import base.client.impl.Response;
import base.email.Email;

/**
 * @author Alexandre
 *         01/04/2015
 */
public abstract class Client {
    protected String[] waitingTaskArgs = null;
    protected Boolean quit = Boolean.FALSE;
    protected Response response = new Response();
    protected String username;

    protected abstract String getSucessMessage();

    protected abstract String getErrorMessage();

    protected abstract Email getMessage();
}
