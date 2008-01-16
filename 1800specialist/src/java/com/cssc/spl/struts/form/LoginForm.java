/*
 * LoginForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.util.Validator;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 4:58 PM
 */
public class LoginForm extends ActionForm {
    private final String CSSC002E = "errors.CSSC002E";
        
    private final String LOGIN_ADMIN_PATH = "/admlogin";
    private final String LOGIN_GEN_PATH = "/genlogin";
    private final String LOGIN_SPEC_PATH = "/spclogin";
    
    private String userid = "";
    private String password = "";
    private String userType = "";
    private String specialistid = "";
    private String location = "";
    private String locpassword = "";
    
    //Constant defined for Key to be associated with Messages to be displayed to the user on the screen.
    protected final String MESSAGES = "messages";
    //Constant defined for Key to be associated with Errors to be displayed to the user on the screen.
    protected final String ERRORS = "errors";
    //Constant defined for Key to be associated with Warnings to be displayed to the user on the screen.	
    protected final String WARNINGS = "warnings";
    
    //Properties used for Pagination if required on the screen.
    //Current Page Number
    private String pageNumber;
    //Total Number of Pages records are devided into.
    private String totalPages;
    //Total Number of records as per search.

    private String totalRecords;
    //First Record number being displayed on the Current Page
    private String pageFirstRecordNbr;
    //Last Record number being displayed on the Current Page    
    private String pageLastRecordNbr;
    
    //Operation request to be performed.
    private String operation;
    //Last Operation requested by the user.
    private String lastOperation;

    //Properties for Sorting Operation.
    //Order in which Sorting needs to be performed.
    private String sortorder = "A";
    //Column to be sorted.
    private String sortcolumn = "";    

    private boolean logout = false;
    
    private String displayname = "";
    
    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getPageFirstRecordNbr() {
        return pageFirstRecordNbr;
    }

    public void setPageFirstRecordNbr(String pageFirstRecordNbr) {
        this.pageFirstRecordNbr = pageFirstRecordNbr;
    }

    public String getPageLastRecordNbr() {
        return pageLastRecordNbr;
    }

    public void setPageLastRecordNbr(String pageLastRecordNbr) {
        this.pageLastRecordNbr = pageLastRecordNbr;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation = lastOperation;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getSortcolumn() {
        return sortcolumn;
    }

    public void setSortcolumn(String sortcolumn) {
        this.sortcolumn = sortcolumn;
    }    

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }   

    public String getLocpassword() {
        return locpassword;
    }

    public void setLocpassword(String locpassword) {
        this.locpassword = locpassword;
    }

    public String getSpecialistid() {
        return specialistid;
    }

    public void setSpecialistid(String specialistid) {
        this.specialistid = specialistid;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        userid = "";
        password = "";
        userType = "";
        location = "";
        locpassword = "";
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors ();
        MessageResources resources = (MessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
        String locationLbl = resources.getMessage("label.location");
        String useridLbl = resources.getMessage("label.userid");
        String passwordLbl = resources.getMessage("label.password");
        
        String path = mapping.getPath();
        if (LOGIN_GEN_PATH.equals(path)) {
            boolean isBlank = Validator.isBlank(locpassword);
            if (isBlank) {
                ActionMessage message = new ActionMessage (CSSC002E, locationLbl);
                errors.add(ERRORS + ".label.locpassword", message); 
            }
            isBlank = Validator.isBlank(userid);
            if (isBlank) {
                ActionMessage message = new ActionMessage (CSSC002E, useridLbl);
                errors.add(ERRORS + ".label.userid", message); 
            }
        }
        if (LOGIN_ADMIN_PATH.equals(path) || LOGIN_SPEC_PATH.equals(path)) {
            boolean isBlank = Validator.isBlank(userid);
            if (isBlank) {
                ActionMessage message = new ActionMessage (CSSC002E, useridLbl);
                errors.add(ERRORS + ".label.userid", message); 
            }
            isBlank = Validator.isBlank(password);
            if (isBlank) {
                ActionMessage message = new ActionMessage (CSSC002E, passwordLbl);
                errors.add(ERRORS + ".label.password", message);
            }
        }
        return errors;
    }
}
