package base;

import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import org.apache.log4j.Logger;
import util.Action;
import util.CurrentState;
import util.ServerUtil;

/**
 * Created by alexandreg on 02/03/2015.
 */
public class Main {
    public static String[] hostname = {
            "localhost"
    };
    public static int[] port = {
            110
    };
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        CurrentState currentState = null;
        try {
            ServerUtil.initialize(new Server(hostname[0], port[0]));
            Action.response();
            currentState = CurrentState.AUTHORIZATION;
        } catch (ErrorResponseServerException e) {
            e.printStackTrace();
        }

        try {
            Action.USER.execute(currentState, "admin");
            currentState = currentState.changeTo(Action.USER.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            e.printStackTrace();
            currentState = currentState.changeTo(Action.PASS.getIfFailed());
            e.printStackTrace();
        }
        try {
            Action.PASS.execute(currentState, "pw");
            currentState = currentState.changeTo(Action.PASS.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            currentState = currentState.changeTo(Action.PASS.getIfFailed());
            e.printStackTrace();
        }
        try {
            Action.STAT.execute(currentState);
            currentState = currentState.changeTo(Action.STAT.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            currentState = currentState.changeTo(Action.STAT.getIfFailed());
            e.printStackTrace();
        }
        try {
            Action.QUIT.execute(currentState);
            currentState = currentState.changeTo(Action.QUIT.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            currentState = currentState.changeTo(Action.QUIT.getIfFailed());
            e.printStackTrace();
        }
    }
}
