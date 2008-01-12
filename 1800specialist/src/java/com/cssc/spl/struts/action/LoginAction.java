/*
 * LoginAction.java
 */
package com.cssc.spl.struts.action;

import com.cssc.spl.bo.UserBO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.struts.action.common.CommonAction;
import com.cssc.spl.struts.form.LoginForm;
import com.cssc.spl.util.Constants;
import com.cssc.spl.util.Validator;
import com.cssc.spl.vo.GeneralistVO;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserVO;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:03 PM
 */
public class LoginAction extends CommonAction {
    private Logger logger = null;
    
    private final String CSSC001E = "errors.CSSC001E";
   
    private final String USER_SESSION_ATTR = "USER_SESSION_ATTR";
    
    private final String LOGIN_ADMIN_PATH = "/admlogin";
    private final String LOGIN_GEN_PATH = "/genlogin";
    private final String INDEX_PATH = "/index";
    private final String LOGIN_SPEC_PATH = "/spclogin";

    private final String ADMIN_PATH = "/admin";
    private final String GEN_PATH = "/generalist";
    private final String SPEC_PATH = "/spec";
    
    private final String GENERALIST= "GEN";
        
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger (LoginAction.class);
        logger.info ("Start execute (ActionMapping, ActionForm, HttpServletrequest, HttpServletResponse)");
        ActionForward forward = null;
        String path = mapping.getPath();
        logger.debug ("Path requested: " + path);
        LoginForm loginForm = (LoginForm) form;
        ActionMessages messages = new ActionMessages ();
        ActionErrors errors = new ActionErrors ();
        HttpSession session = request.getSession();
        
        if (ADMIN_PATH.equals (path)) {
            loginForm.setUserType("ADMIN");
            forward = mapping.findForward(SUCCESS);
        }        
        if (GEN_PATH.equals(path)) {
            loginForm.setUserType("SPEC");
            forward = mapping.findForward(SUCCESS);
        }
        if (INDEX_PATH.equals (path)) {
            forward = mapping.findForward(SUCCESS);
        }
        if (SPEC_PATH.equals(path)) {
            loginForm.setUserType("SPEC");
            forward = mapping.findForward(SUCCESS);
        }
        if (LOGIN_ADMIN_PATH.equals (path) || LOGIN_SPEC_PATH.equals(path)) {
            errors = loginForm.validate(mapping, request);
            if (errors.isEmpty()) {
                String userName = loginForm.getUserid();
                String password = loginForm.getPassword();
                String userType = loginForm.getUserType ();

                UserVO userVO = new UserVO ();
                userVO.setUsername(userName);
                userVO.setPassword(password);
                userVO.setUserType(userType);

                UserBO userBO = new UserBO ();
                userVO = userBO.authenticateUser(userVO);

                if (Validator.isBlank(userVO.getFirstName())) {
                    messages.add(ERRORS, new ActionMessage (CSSC001E, "User Id/Password"));
                    forward = mapping.findForward(ERROR);
                } else {
                    session.setAttribute(userVO.getUserType(), userVO);
                    forward = mapping.findForward(SUCCESS);
                }
            }
        }
        if (LOGIN_GEN_PATH.equals(path)) {
            errors = loginForm.validate(mapping, request);
            logger.debug ("Errors: " + errors.isEmpty());
            if (errors.isEmpty()) {           
                messages = handleGeneralistLogin (loginForm, request);
                if (!messages.isEmpty()) {
                    forward = mapping.findForward(ERROR);
                } else {
                    forward = mapping.findForward(SUCCESS);
                }                
            }
        }
        if (!errors.isEmpty()) {
            Iterator errorIter = errors.get(ERRORS + ".label.userid");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.userid", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.password");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.password", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.location");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.location", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.locpassword");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.locpassword", (ActionMessage) errorIter.next());
            }
            errorIter = errors.get(ERRORS + ".label.specialistid");
            while (errorIter.hasNext()) {
                messages.add(ERRORS + ".label.specialistid", (ActionMessage) errorIter.next());
            }            
            forward = mapping.findForward (ERROR);
        }
        saveMessages(request, messages);
        logger.info ("End execute (ActionMapping, ActionForm, HttpServletrequest, HttpServletResponse)");
        return forward;
    } 
    
    private ActionMessages handleGeneralistLogin (LoginForm loginForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleGeneralistLogin (LoginForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        
        String userName = loginForm.getUserid();
        String password = loginForm.getPassword();
        String locationName = loginForm.getLocation();
        String locationPwd = loginForm.getLocpassword();
        String specialistid = loginForm.getSpecialistid ();
        String userType = loginForm.getUserType();
        
        UserBO userBO = new UserBO ();
        GeneralistVO generalistVO = userBO.authenticateGeneralist (locationName, locationPwd, userType, userName, specialistid, password);
        if (Validator.isBlank(generalistVO.getLocationname())) {
            messages.add(ERRORS, new ActionMessage (CSSC001E, "Location Name/User Id/Password"));
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(GENERALIST, generalistVO);
        }
        logger.info ("Start handleGeneralistLogin (LoginForm, HttpServletRequest)");
        return messages;
    }
}
