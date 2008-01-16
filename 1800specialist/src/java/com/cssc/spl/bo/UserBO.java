/*
 * UserBO.java
 */
package com.cssc.spl.bo;

import com.cssc.spl.dao.UserDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.GeneralistVO;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserVO;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * @created.on October 15, 2007, 5:28 PM
 */
public class UserBO {
    private Logger logger = null;
    
    public UserBO() {
        logger = Logger.getLogger(UserBO.class);
    }
    
    public GeneralistVO createGeneralist (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start createGeneralist (UserVO, String)");
        UserDAO userDAO = new UserDAO ();
        userVO = userDAO.createGeneralist (userVO, userId);
        String username = userVO.getUsername();
        String locationPwd = userVO.getLocationPwd();
        GeneralistVO generalistVO = authenticateGeneralist(locationPwd, username);
        logger.info ("End createGeneralist (UserVO, String)");
        return generalistVO;
    }
    
    public UserVO createSpecialist (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start createUser (UserVO, String)");
        UserDAO userDAO = new UserDAO ();
        userVO = userDAO.createSpecialist (userVO, userId);
        SpecialistBO specialistBO = new SpecialistBO ();
        LocationVO locationVO = new LocationVO ();
        locationVO.setUserId(userVO.getUsername());
        locationVO.setLocationName(userVO.getCompany());
        locationVO.setStatus("NEW");
        locationVO.setAddress1(userVO.getAddress1());
        locationVO.setAddress2(userVO.getAddress2());
        locationVO.setCity(userVO.getCity());
        locationVO.setState(userVO.getState());
        locationVO.setCountry(userVO.getCountry());
        locationVO.setZipCode(userVO.getZipcode());
        locationVO.setPhone1(userVO.getPhone1());
        specialistBO.saveLocationVOs(new LocationVO[] {locationVO}, userVO);
        UserVO uservo = authenticateUser(userVO);
        logger.info ("End createUser (UserVO, String)");
        return uservo;
    }
    
    public GeneralistVO authenticateGeneralist (String locationPwd, String userName) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start authenticateGeneralist (String, String, String)");
        UserDAO userDAO = new UserDAO ();
        GeneralistVO generalistVO = userDAO.authenticateGeneralist(locationPwd, userName);
        logger.info ("Start authenticateGeneralist (String, String, String)");
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