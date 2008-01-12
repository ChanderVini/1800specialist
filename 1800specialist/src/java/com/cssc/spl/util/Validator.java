/*
 * Validator.java
 */

package com.cssc.spl.util;

/**
 *
 * @author Chander Singh
 * Created on October 16, 2007, 6:33 AM
 */
public class Validator {    
    /** Creates a new instance of Validator */
    public Validator() {
    }
    
    public static boolean isBlank (String data) {
        boolean isblank = false;
        
        if (data == null || "".equals (data.trim())) {
            isblank = true;
        }
        return isblank;
    }
}
