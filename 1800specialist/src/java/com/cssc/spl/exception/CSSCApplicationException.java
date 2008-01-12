/*
 * DbConnectionException.java
 */

package com.cssc.spl.exception;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:22 PM
 */
public class CSSCApplicationException  extends Exception {    
    private String errorCode = "";
    
    /** Creates a new instance of DbConnectionException */
    public CSSCApplicationException() {
        super ();
    }
    
    public CSSCApplicationException (String message) {
        super (message);
    }
    
    public CSSCApplicationException (String code, String message) {
        super (message);
        errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }    
}

