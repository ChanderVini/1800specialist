/*
 * LoginForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.struts.form.common.CommonForm;
import com.cssc.spl.util.Validator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 4:58 PM
 */
public class LoginForm extends CommonForm {
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
        
    /** Creates a new instance of LoginForm */
    public LoginForm() {
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
        String specialistidLbl = resources.getMessage("label.specialistid");
        String path = mapping.getPath();
        if (LOGIN_GEN_PATH.equals(path)) {
            boolean isBlank = Validator.isBlank(location);
            if (isBlank) {
                ActionMessage message = new ActionMessage (CSSC002E, locationLbl);
                errors.add(ERRORS + ".label.location", message); 
            }
            isBlank = Validator.isBlank(locpassword);
            if (isBlank) {
                ActionMessage message = new ActionMessage (CSSC002E, locationLbl);
                errors.add(ERRORS + ".label.locpassword", message); 
            }
            isBlank = Validator.isBlank(specialistid);
            if (isBlank) {
                ActionMessage message = new ActionMessage (CSSC002E, specialistidLbl);
                errors.add(ERRORS + ".label.specialistid", message); 
            }
        }
        if (LOGIN_ADMIN_PATH.equals(path) || LOGIN_SPEC_PATH.equals(path) || LOGIN_GEN_PATH.equals (path)) {
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
