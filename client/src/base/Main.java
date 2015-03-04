package base;

import base.action.Action;
import exception.ErrorResponseServerException;
import exception.UnallowedActionException;
import org.apache.log4j.Logger;
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
        ServerUtil.initialize(new Server(hostname[0], port[0]));
        State state = State.AUTHORIZATION;
        try {
            Action.response();
        } catch (ErrorResponseServerException e) {
            e.printStackTrace();
        }

        try {
            Action.USER.execute(state, "admin");
            state = state.changeTo(Action.USER.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            e.printStackTrace();
            state = state.changeTo(Action.PASS.getIfFailed());
            e.printStackTrace();
        }
        try {
            Action.PASS.execute(state, "pw");
            state = state.changeTo(Action.PASS.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            state = state.changeTo(Action.PASS.getIfFailed());
            e.printStackTrace();
        }
        try {
            Action.STAT.execute(state);
            state = state.changeTo(Action.STAT.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            state = state.changeTo(Action.STAT.getIfFailed());
            e.printStackTrace();
        }
        try {
            Action.QUIT.execute(state);
            state = state.changeTo(Action.QUIT.getIfSucceed());
        } catch (UnallowedActionException e) {
            e.printStackTrace();
        } catch (ErrorResponseServerException e) {
            state = state.changeTo(Action.QUIT.getIfFailed());
            e.printStackTrace();
        }
    }
}
