/*
 * UserBO.java
 */

package com.cssc.spl.bo;

import com.cssc.spl.dao.UserDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.GeneralistVO;
import com.cssc.spl.vo.UserVO;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:28 PM
 */
public class UserBO {
    private Logger logger = null;
    
    public UserBO() {
        logger = Logger.getLogger(UserBO.class);
    }
    
    public UserVO createGeneralist (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start createGeneralist (UserVO, String)");
        UserDAO userDAO = new UserDAO ();
        userVO = userDAO.createGeneralist (userVO, userId);
        logger.info ("End createGeneralist (UserVO, String)");
        return userVO;
    }
    
    public UserVO createSpecialist (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start createUser (UserVO, String)");
        UserDAO userDAO = new UserDAO ();
        userVO = userDAO.createSpecialist (userVO, userId);
        logger.info ("End createUser (UserVO, String)");
        return userVO;
    }
    
    public GeneralistVO authenticateGeneralist (String locationName, String locationPwd, String userType, String userName, String specialistid, String password) 
    throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start authenticateGeneralist (String, String, String, String, String, String)");
        UserDAO userDAO = new UserDAO ();
        GeneralistVO generalistVO = userDAO.authenticateGeneralist(locationName, locationPwd, userType, userName, specialistid, password);
        logger.info ("Start authenticateGeneralist (String, String, String, String, String, String)");
        return generalistVO;
    }
    
    public UserVO authenticateUser (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start authenticateUser (UserVO)");
        UserDAO userDAO = new UserDAO ();
        userVO = userDAO.authenticateUser(userVO);
        logger.info("End authenticateUser (UserVO)");
        return userVO;
    }
    
    public UserVO saveUser (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start saveUser (UserVO, String)");
        UserDAO userDAO = new UserDAO ();
        userDAO.saveUser(userVO, userId);
        userVO = authenticateUser(userVO);
        logger.info("End saveUser (UserVO, String)");
        return userVO;
    }
}
