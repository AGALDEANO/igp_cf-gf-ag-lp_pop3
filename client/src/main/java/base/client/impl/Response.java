package base.client.impl;

import base.email.Email;

/**
 * @author Alexandre
 *         17/03/2015
 */
public class Response {
    protected String sucessMessage = null;
    protected String errorMessage = null;
    protected Email email = null;

    public String getSucessMessage() {
        return sucessMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Email getEmail() {
        return email;
    }
}
