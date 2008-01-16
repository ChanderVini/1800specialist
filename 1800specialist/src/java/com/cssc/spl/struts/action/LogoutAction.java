/*
 * LogoutAction.java
 */

package com.cssc.spl.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Chander Singh
 * Created on November 29, 2007, 9:36 AM
 */
public class LogoutAction extends Action {
    //Constants defined for forward mapping results
    private final String SUCCESS = "success";
    
    private Logger logger = null;
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger(this.getClass());
        logger.info ("Start execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        HttpSession session = request.getSession();
        session.invalidate();
        ActionForward forward = mapping.findForward(SUCCESS);
        logger.info ("End execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        return forward;
    }
}
