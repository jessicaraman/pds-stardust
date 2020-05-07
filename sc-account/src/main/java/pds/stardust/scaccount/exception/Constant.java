package pds.stardust.scaccount.exception;


/**
 * ConstantException : Defines all constant exceptions of the API account
 */
public class Constant {

    private Constant() {
    }

    // connect
    public static final CustomException CONNECT_AUTH_FAILURE = new CustomException(1, "Authentication failed!", "Wrong username or password, try again.");

    // updateToken
    public static final CustomException UPDATE_BAD_ID = new CustomException(2, "Update Token failure!", "Bad customer ID.");
    public static final CustomException UPDATE_AUTH_FAILURE = new CustomException(3, "Update Token failure!", "Bad username or password.");

    // getToken
    public static final CustomException GET_TOKEN_BAD_USERNAME = new CustomException(4, "Get Token failure!", "Bad username.");
}
