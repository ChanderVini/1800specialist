/*
 * AdminAction.java
 */

package com.cssc.spl.struts.action;

import com.cssc.spl.bo.AdminBO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.struts.form.AdminForm;
import com.cssc.spl.util.CSSCUtil;
import com.cssc.spl.util.Constants;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserTypeVO;
import com.cssc.spl.vo.UserVO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Chander Singh
 * Created on November 9, 2007, 1:45 PM
 */

public class AdminAction extends Action {
    //Constants for defining Errors, Messages and warnings.
    private final String ERRORS = "errors";
    private final String MESSAGES = "messages";
    
    //Constants defined for forward mapping results
    private final String SUCCESS = "success";
    private final String ERROR = "error";
    private final String NOACCESS = "noaccess";
    
    private Logger logger = null;
    
    private final String ADMIN_SESSION = "ADMIN";
    private final String SPEC = "SPEC";
    
    private final String USERTYPE_OPS = "USERTYPE";
    
    private final String DEFAULT_UPLOAD_DIR = "DEFAULT_UPLOAD_DIR";
    
    private final String CSSC005M = "messages.CSSC005M";
    private final String CSSC006E = "errors.CSSC007E";
    
    private final String LIST_USERTYPE_MAP = "/lutype";
    private final String ADD_USERTYPE_MAP = "/autype";
    private final String SAVE_USERTYPE_MAP = "/sutype";
    private final String DELETE_USERTYPE_MAP = "/dutype";    
    
    private final String LIST_SPEC_MAP = "/lspec";
    private final String SAVE_SPEC_MAP = "/sspec";
    private final String DELETE_SPEC_MAP = "/dspec";
    
    private final String UPLOAD_LOCATION_MAP = "/uadmloc";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger(this.getClass());
        logger.info ("Start execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        ActionForward forward = null;
        AdminForm adminForm = (AdminForm) form;
        ActionMessages messages = new ActionMessages ();
        ActionErrors errors = new ActionErrors ();
        try {
            String path = mapping.getPath();            
            logger.debug ("Path: " + path);
            HttpSession session = request.getSession();
            Object adminProfileObj = session.getAttribute(ADMIN_SESSION);            
            if (adminProfileObj == null) {
                forward = mapping.findForward(NOACCESS);
                ActionMessage message = new ActionMessage (CSSC006E);
                messages.add (ERRORS, message);
                saveMessages(request, messages);
                return forward;
            }            
            UserVO userVO = (UserVO) adminProfileObj;
            String firstName = userVO.getFirstName();
            String lastName = userVO.getLastName();
            adminForm.setDisplayname(firstName + " " + lastName);
            adminForm.setLogout(false);
            if (LIST_SPEC_MAP.equals(path)) {
                messages = handleListSpecialists (adminForm, request);
                forward = mapping.findForward(SUCCESS);
            }
            if (DELETE_SPEC_MAP.equals(path)) {
                messages = handleDeleteSpecialists (adminForm, request);
                forward = mapping.findForward(SUCCESS);
            }
            if (SAVE_SPEC_MAP.equals(path)) {                
                messages = handleSaveSpecialists (adminForm, request, userVO);
                forward = mapping.findForward(SUCCESS);
            }            
            if (ADD_USERTYPE_MAP.equals (path)) {
                handleAddUserType (adminForm, request);
                forward = mapping.findForward(SUCCESS);
            }
            if (DELETE_USERTYPE_MAP.equals(path)) {
                messages = handleDeleteUserType(adminForm, request);
                forward = mapping.findForward(SUCCESS);
            }
            if (LIST_USERTYPE_MAP.equals (path)) {
                messages = handleListUserType (adminForm, request);
                forward = mapping.findForward(SUCCESS);
            }
            if (SAVE_USERTYPE_MAP.equals (path)) {
                messages = handleSaveUserType(adminForm, request, userVO.getUsername());
                forward = mapping.findForward(SUCCESS);
            }
            if (UPLOAD_LOCATION_MAP.equals(path)) {
                errors = adminForm.validate(mapping, request);
                logger.debug("After Validating File: " + errors.isEmpty());
                if (errors.isEmpty()) {
                    messages = handleUploadFile (adminForm, request, userVO);
                    forward = mapping.findForward(SUCCESS);
                } else {
                    UserVO[] userVOs = adminForm.getUserVOs();
                    for (int cnt = 0; cnt < userVOs.length; cnt++) {
                        LocationVO[] locationVOs = userVOs[cnt].getLocationVOs();
                        String errortag = ERRORS + "." + userVOs[cnt].getUsername();
                        Iterator errorIter = errors.get(errortag);
                        while (errorIter.hasNext()) {
                            messages.add(errortag, (ActionMessage) errorIter.next());
                        }
                    }
                    forward = mapping.findForward (ERROR);
                }
            }
        } catch (CSSCSystemException csscsexp) {
            messages.add(ERRORS, new ActionMessage (csscsexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        } catch (CSSCApplicationException csscaexp) {
            messages.add(ERRORS, new ActionMessage (csscaexp.getErrorCode(), csscaexp.getMessage()));
            forward = mapping.findForward(ERROR);
        }
        
        saveMessages(request, messages);
        logger.debug("End execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        return forward;
    }
    
    private ActionMessages handleAddUserType (AdminForm adminForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        ActionMessages messages = new ActionMessages ();
        UserTypeVO[] userTypeVOs = adminForm.getUserTypeVOs();
        ArrayList userTypeVOAL = new ArrayList (Arrays.asList(userTypeVOs));
        userTypeVOAL.add (new UserTypeVO());
        userTypeVOs = (UserTypeVO[]) userTypeVOAL.toArray(new UserTypeVO [userTypeVOAL.size()]);
        userTypeVOAL = null;
        adminForm.setUserTypeVOs(userTypeVOs);
        adminForm.setOperation(USERTYPE_OPS);
        return messages;
    }
    
    private ActionMessages handleDeleteSpecialists (AdminForm adminForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleDeleteSpecialists (AdminForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        UserVO[] userVOs = extractSelectedRows(adminForm);
        UserVO userVO = new UserVO();
        userVO.setUserType(SPEC);
        AdminBO adminBO = new AdminBO ();
        userVOs = adminBO.deleteSpecialists(userVOs, userVO);
        adminForm.setUserVOs(userVOs);
         ActionMessage message = new ActionMessage (CSSC005M, "Delete");
        messages.add (MESSAGES, message);
        logger.info ("End handleDeleteSpecialists (AdminForm, HttpServletRequest)");
        return messages;
    }

    private ActionMessages handleSaveSpecialists (AdminForm adminForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleSaveSpecialists (AdminForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        UserVO[] userVOs = extractSelectedRows(adminForm);
        ArrayList locationVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < userVOs.length; cnt++) {
            LocationVO[] locationVOs = userVOs[cnt].getLocationVOs();
            for (int locCnt = 0; locCnt < locationVOs.length; locCnt++) {
                if (locationVOs[locCnt].isSelected()) {
                    String startDateStr = locationVOs[locCnt].getStartDtStr();
                    Date date = CSSCUtil.parseDate(startDateStr, "MM/dd/yyyy");
                    locationVOs[locCnt].setStartDate(date);
                    String endDateStr = locationVOs[locCnt].getEndDtStr();
                    date = CSSCUtil.parseDate(endDateStr, "MM/dd/yyyy");
                    locationVOs[locCnt].setEndDate(date);
                    locationVOs[locCnt].setUserId(userVOs[cnt].getUsername());
                    locationVOAL.add(locationVOs[locCnt]);
                }
            }
        }
        LocationVO[] locationVOs = (LocationVO[]) locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
        locationVOAL = null;
        AdminBO adminBO = new AdminBO ();
        UserVO uservo = new UserVO();
        uservo.setUserType(SPEC);
        uservo.setUsername(userVO.getUsername());
        userVOs = adminBO.saveLocationVOs(locationVOs, uservo);
        adminForm.setUserVOs(userVOs);
        ActionMessage message = new ActionMessage (CSSC005M, "Save");
        messages.add (MESSAGES, message);
        logger.info ("End handleSaveSpecialists (AdminForm, HttpServletRequest, UserVO)");
        return messages;
    }
        
    private UserVO[] extractSelectedRows (AdminForm adminForm) {
        UserVO[] userVOs = adminForm.getUserVOs();
        ArrayList userVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < userVOs.length; cnt++) {
            if (userVOs[cnt].isSelected()) {
                userVOAL.add (userVOs[cnt]);
            } else {
                LocationVO[] locationVOs = userVOs[cnt].getLocationVOs();
                for (int locCnt = 0; locCnt < locationVOs.length; locCnt++) {
                    if (locationVOs[locCnt].isSelected()) {
                        userVOAL.add (userVOs[cnt]);
                        break;
                    }
                }
            }
        }
        UserVO[] uservos = (UserVO[]) userVOAL.toArray(new UserVO[userVOAL.size()]);
        userVOAL = null;
        return uservos;
    }
    
    private ActionMessages handleListSpecialists (AdminForm adminForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleListSpecialists (AdminForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        UserVO userVO = new UserVO ();
        userVO.setUserType(SPEC);
        logger.debug ("User Type: " + userVO.getUserType());
        AdminBO adminBO = new AdminBO ();
        UserVO[] userVOs = adminBO.fetchUsers(userVO);
        for (int cnt = 0; cnt < userVOs.length; cnt++) {
            LocationVO[] locationVOs = userVOs[cnt].getLocationVOs();
            for (int loccnt = 0; loccnt < locationVOs.length; loccnt++) {
                locationVOs[loccnt].process();
            }            
        }
        adminForm.setUserVOs(userVOs);
        logger.info ("End handleListSpecialists (AdminForm, HttpServletRequest)");
        return messages;
    }
    
    private ActionMessages handleListUserType (AdminForm adminForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        ActionMessages messages = new ActionMessages ();
        AdminBO adminBO = new AdminBO ();
        UserTypeVO[] userTypeVOs = adminBO.fetchUserTypeVOs();
        for (int cnt = 0; cnt < userTypeVOs.length; cnt++) {
            userTypeVOs[cnt].process ();
        }
        ArrayList userTypeVOAL = new ArrayList (Arrays.asList(userTypeVOs));
        Collections.sort(userTypeVOAL);
        userTypeVOs = (UserTypeVO[])userTypeVOAL.toArray(new UserTypeVO[userTypeVOAL.size()]);
        userTypeVOAL = null;
        adminForm.setUserTypeVOs(userTypeVOs);
        adminForm.setOperation(USERTYPE_OPS);
        return messages;
    }
    
    private ActionMessages handleDeleteUserType (AdminForm adminForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        ActionMessages messages = new ActionMessages ();
        UserTypeVO[] userTypeVOs = adminForm.getUserTypeVOs();
        ArrayList userTypeVOAL = new ArrayList (Arrays.asList(userTypeVOs));
        ArrayList deleteUserTypeVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < userTypeVOAL.size(); cnt++) {
            UserTypeVO userTypeVO = (UserTypeVO) userTypeVOAL.get (cnt);
            if (userTypeVO.isSelected()) {
                if (userTypeVO.getId() > 0) {
                    deleteUserTypeVOAL.add (userTypeVO);
                }
                userTypeVOAL.remove(cnt);
                cnt--;
            }
        }
        if (deleteUserTypeVOAL.isEmpty()) {
            userTypeVOs = (UserTypeVO[]) userTypeVOAL.toArray(new UserTypeVO [userTypeVOAL.size()]);
            userTypeVOAL = null;
        } else {
            UserTypeVO[] deleteUserTypeVOs = (UserTypeVO[]) deleteUserTypeVOAL.toArray(new UserTypeVO[deleteUserTypeVOAL.size()]);
            deleteUserTypeVOAL = null;
            AdminBO adminBO = new AdminBO ();
            userTypeVOs = adminBO.deleteUserTypeVOs(deleteUserTypeVOs);
        }
         for (int cnt = 0; cnt < userTypeVOs.length; cnt++) {
            userTypeVOs[cnt].process ();
        }
        userTypeVOAL = new ArrayList (Arrays.asList(userTypeVOs));
        Collections.sort(userTypeVOAL);
        userTypeVOs = (UserTypeVO[])userTypeVOAL.toArray(new UserTypeVO[userTypeVOAL.size()]);
        userTypeVOAL = null;
        adminForm.setUserTypeVOs(userTypeVOs);
        ActionMessage message = new ActionMessage (CSSC005M, "Delete");
        messages.add (MESSAGES, message);
        adminForm.setOperation(USERTYPE_OPS);
        return messages;
    }
    
    public ActionMessages handleSaveUserType (AdminForm adminForm, HttpServletRequest request, String userId) throws CSSCApplicationException, CSSCSystemException {
        ActionMessages messages = new ActionMessages ();
        UserTypeVO[] userTypeVOs = adminForm.getUserTypeVOs();
        ArrayList saveUserTypeVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < userTypeVOs.length; cnt++) {
            if (userTypeVOs[cnt].isSelected()) {
                saveUserTypeVOAL.add (userTypeVOs[cnt]);
            }
        }
        if (!saveUserTypeVOAL.isEmpty()) {
            UserTypeVO[] saveUserTypeVOs = (UserTypeVO[]) saveUserTypeVOAL.toArray(new UserTypeVO[saveUserTypeVOAL.size()]);
            saveUserTypeVOAL = null;
            AdminBO adminBO = new AdminBO ();
            userTypeVOs = adminBO.saveUserTypeVOs(saveUserTypeVOs, userId);
            for (int cnt = 0; cnt < userTypeVOs.length; cnt++) {
                userTypeVOs[cnt].process ();
            }
            ArrayList userTypeVOAL = new ArrayList (Arrays.asList(userTypeVOs));
            Collections.sort(userTypeVOAL);
            userTypeVOs = (UserTypeVO[])userTypeVOAL.toArray(new UserTypeVO[userTypeVOAL.size()]);
            userTypeVOAL = null;
            adminForm.setUserTypeVOs(userTypeVOs);
            ActionMessage message = new ActionMessage (CSSC005M, "Save");
            messages.add (MESSAGES, message);
        }
        adminForm.setOperation(USERTYPE_OPS);
        return messages;
    }
    
    private ActionMessages handleUploadFile (AdminForm adminForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleUploadFile (AdminForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        String realPath = Constants.getProperty(DEFAULT_UPLOAD_DIR);
        try {
            UserVO[] userVOs = adminForm.getUserVOs();
            ArrayList userVOAL = new ArrayList (10);
            for (int cnt = 0; cnt < userVOs.length; cnt++) {
                String username = userVOs[cnt].getUsername();
                String dirpath = File.separator + File.separator + username;
                FormFile formFile = userVOs[cnt].getFormFile();
                if (formFile != null && formFile.getFileSize() > 0) {
                    dirpath = realPath + dirpath;
                    File file = new File (dirpath);
                    logger.debug("File Path: " + file.getCanonicalPath());
                    if (!file.exists()) {
                        file.mkdirs();
                        logger.debug("Directory doesn't exists");
                    }
                    File[] files = file.listFiles();
                    for (int filecnt = 0; filecnt < files.length; filecnt++) {
                        files[filecnt].delete();
                    }
                    String filename = dirpath + File.separator + formFile.getFileName();
                    file = new File (filename);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    byte[] fileData = formFile.getFileData();
                    FileOutputStream fos = new FileOutputStream (file);
                    fos.write(fileData);
                    fos.close();
                    userVOs[cnt].setUploaded("Y");
                    userVOAL.add (userVOs[cnt]);
                }
            }
            if (!userVOAL.isEmpty()) {
                UserVO[] uservos = (UserVO[]) userVOAL.toArray(new UserVO[userVOAL.size()]);
                userVOAL = null;
                AdminBO adminBO = new AdminBO();
                adminBO.saveUserVOs(uservos, userVO);
                messages = handleListSpecialists(adminForm, request);
                ActionMessage message = new ActionMessage (CSSC005M, "File Upload");
                messages.add (MESSAGES, message);
            }
        } catch (IOException ioexp ) {
            logger.error("Error: " + ioexp);
        }
        
        logger.info ("End handleUploadFile (AdminForm, HttpServletRequest, UserVO)");
        return messages;
    }
}
