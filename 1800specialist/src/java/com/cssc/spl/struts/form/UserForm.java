/*
 * UserForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.struts.form.common.CommonForm;
import com.cssc.spl.vo.UserVO;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Chander Singh
 * Created on October 25, 2007, 4:52 PM
 */
public class UserForm extends CommonForm {
    private UserVO userVO = new UserVO ();

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors retValue;
        
        retValue = super.validate(mapping, request);
        return retValue;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        userVO = new UserVO();
    }        

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }
}