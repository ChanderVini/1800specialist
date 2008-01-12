/*
 * CommonAction.java
 */

package com.cssc.spl.struts.action.common;

import org.apache.struts.action.Action;

/**
 *
 * @author Chander Singh
 * Created on October 16, 2007, 6:53 AM
 */
public class CommonAction extends Action {
    //Constants for defining Errors, Messages and warnings.
    protected final String MESSAGES = "messages";
    protected final String ERRORS = "errors";
    protected final String WARNINGS = "warnings";
    
    //Constants defined for forward mapping results
    protected final String SUCCESS = "success";
    protected final String ERROR = "error";
    protected final String NOACCESS = "noaccess";
}
