/*
 * AdminBO.java
 */

package com.cssc.spl.bo;

import com.cssc.spl.dao.AdminDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserTypeVO;
import com.cssc.spl.vo.UserVO;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 9, 2007, 1:35 PM
 */
public class AdminBO {
    private Logger logger = null;
    
    /** Creates a new instance of AdminBO */
    public AdminBO() {
        logger = Logger.getLogger (this.getClass());
    }
    
    public UserVO[] deleteSpecialists (UserVO[] userVOs, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start delete Specialists (UserVO[], UserVO)");
        ArrayList userVOAL = new ArrayList (10);
        ArrayList locationVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < userVOs.length; cnt++) {
            if (userVOs[cnt].isSelected()) {
                userVOAL.add(userVOs[cnt]);
                LocationVO[] locationVOs = userVOs[cnt].getLocationVOs();
                for (int locCnt = 0; locCnt < locationVOs.length; locCnt++) {
                    locationVOs[locCnt].setUserId(userVOs[cnt].getUsername());
                    locationVOAL.add(locationVOs[locCnt]);
                }
            } else {
                LocationVO[] locationVOs = userVOs[cnt].getLocationVOs();
                for (int locCnt = 0; locCnt < locationVOs.length; locCnt++) {
                    if (locationVOs[locCnt].isSelected()) {
                        locationVOs[locCnt].setUserId(userVOs[cnt].getUsername());
                        locationVOAL.add(locationVOs[locCnt]);
                        locationVOAL.add (locationVOs[locCnt]);
                    }
                }
            }
        }
        UserVO[] uservos = (UserVO[]) userVOAL.toArray(new UserVO[userVOAL.size()]);
        userVOAL = null;
        LocationVO[] locationVOs = (LocationVO[]) locationVOAL.toArray (new LocationVO[locationVOAL.size()]);
        locationVOAL = null;
        AdminDAO adminDAO = new AdminDAO ();
        adminDAO.deleteSpecialists(uservos, locationVOs);
        userVOs = fetchUsers(userVO);
        logger.info ("End delete Specialists (UserVO[], UserVO)");
        return userVOs;
    }
    
    public UserTypeVO[] deleteUserTypeVOs (UserTypeVO[] deleteUserTypeVOs) throws CSSCSystemException, CSSCApplicationException{
        logger.info ("Start deleteUserTypeVOs ()");
        AdminDAO adminDAO = new AdminDAO ();
        adminDAO.deleteUserTypes(deleteUserTypeVOs);
        UserTypeVO[] userTypesVOs = fetchUserTypeVOs();
        logger.info ("End deleteUserTypeVOs ()");
        return userTypesVOs;
    }
    
    public UserVO[] fetchUsers (UserVO userVO) throws CSSCSystemException, CSSCApplicationException {
        logger.info ("Start fetchUsers (UserVO)");
        AdminDAO adminDAO = new AdminDAO ();
        logger.debug ("User Type: " + userVO.getUserType());
        UserVO[] userVOs = adminDAO.fetchUsers(userVO);
        ArrayList userVOAL = new ArrayList (Arrays.asList(userVOs));
        ArrayList finalUserVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < userVOAL.size(); cnt++) {
            UserVO tmpuserVO = (UserVO) userVOAL.remove(cnt);
            LocationVO[] locVOs = tmpuserVO.getLocationVOs();
            if (locVOs != null && locVOs.length > 0) {
                for (int loccnt = 0; loccnt < locVOs.length; loccnt++) {
                    locVOs[loccnt].process();
                }
            }
            cnt--;
            int index = -1;
            while ((index = userVOAL.indexOf(tmpuserVO)) > -1) {
                UserVO tempuserVO = (UserVO) userVOAL.remove(index);
                LocationVO[] locationVOs = tempuserVO.getLocationVOs();
                for (int loccnt = 0; loccnt < locationVOs.length; loccnt++) {
                    locationVOs[loccnt].process();
                    tmpuserVO.addLocation(locationVOs[loccnt]);
                }
            }
            finalUserVOAL.add (tmpuserVO);
        }
        userVOAL = null;
        userVOs = (UserVO[]) finalUserVOAL.toArray(new UserVO [finalUserVOAL.size()]);
        finalUserVOAL = null;
        logger.info ("End fetchUsers (UserVO)");
        return userVOs;
    }
    
    public UserTypeVO[] fetchUserTypeVOs () throws CSSCSystemException, CSSCApplicationException{
        logger.info ("Start fetchUserTypeVOs ()");
        AdminDAO adminDAO = new AdminDAO ();
        UserTypeVO[] userTypeVOs = adminDAO.fetchUserTypes();
        logger.info ("End fetchUserTypeVOs ()");
        return userTypeVOs;
    }
    
    public UserVO[] saveUserVOs (UserVO[] uservos, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start saveUserVOs (UserVO[], UserVO)");
        AdminDAO adminDAO = new AdminDAO ();
        for (int cnt = 0; cnt < uservos.length; cnt++) {
            logger.debug ("Uploaded : " + uservos[cnt].getUploaded());
        }
        adminDAO.updateUserVOs(uservos, userVO.getUsername());
        UserVO[] userVOs = fetchUsers (userVO);        
        logger.info ("Start saveUserVOs (UserVO[], UserVO)");        
        return userVOs;
    }
    
    public UserVO[] saveLocationVOs (LocationVO[] locationVOs, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start saveLocationVOs (LocationVO[], UserVO)");
        AdminDAO adminDAO = new AdminDAO ();
        adminDAO.updateLocationVOs(locationVOs, userVO.getUsername());
        UserVO[] userVOs = fetchUsers (userVO);        
        logger.info ("Start saveLocationVOs (LocationVO[], USerVO)");        
        return userVOs;
    }
    
    public UserTypeVO[] saveUserTypeVOs (UserTypeVO[] saveUserTypeVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start saveUserTypeVOs (UserTypeVO[], String)");
        AdminDAO adminDAO = new AdminDAO ();
        adminDAO.updateUserTypes(saveUserTypeVOs, userId);
        UserTypeVO[] userTypesVOs = fetchUserTypeVOs();
        logger.info ("End saveUserTypeVOs (UserTypeVO[], String)");
        return userTypesVOs;
    }
}
