/*
 * AdminForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.struts.form.common.CommonForm;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserTypeVO;
import com.cssc.spl.vo.UserVO;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Chander Singh
 * Created on November 9, 2007, 1:47 PM
 */
public class AdminForm extends CommonForm {
    private final String UPLOAD_LOCATION_MAP = "/uadmloc";

    private final String CSSC011E = "errors.CSSC011E";
        
    private UserTypeVO[] userTypeVOs = new UserTypeVO [0];
    private UserVO[] userVOs = new UserVO [0];

    public UserTypeVO[] getUserTypeVOs() {
        return userTypeVOs;
    }

    public void setUserTypeVOs(UserTypeVO[] userTypeVOs) {
        this.userTypeVOs = userTypeVOs;
    }    

    public UserVO[] getUserVOs() {
        return userVOs;
    }

    public void setUserVOs(UserVO[] userVOs) {
        this.userVOs = userVOs;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors ();
        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        for (int cnt = 0; cnt < userVOs.length; cnt++) {
            LocationVO[] locationVOs = userVOs[cnt].getLocationVOs();
            for (int loccnt =0; loccnt < locationVOs.length; loccnt++) {
                locationVOs[loccnt].setFormFile(null);
            }
        }
    }
}