package pds.stardust.scaccount.exception;

import org.springframework.stereotype.Service;
import pds.stardust.scaccount.exception.CustomException;

/**
 * ConstantException : Defines all constant exceptions of the API account
 */
public class ConstantException {

    // connect
    public static final CustomException CONNECT_AUTH_FAILURE = new CustomException(1, "Authentication failed!", "Wrong username or password, try again.");

    // update/token
    public static final CustomException UPDATE_BAD_ID = new CustomException(2, "Update Token failure!", "Bad customer ID.");
    public static final CustomException UPDATE_AUTH_FAILURE = new CustomException(3, "Update Token failure!", "Bad username or password.");
}
