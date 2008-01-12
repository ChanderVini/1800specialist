/*
 * SpecialistBO.java
 */

package com.cssc.spl.bo;

import com.cssc.spl.dao.SpecialistDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserVO;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 13, 2007, 3:07 PM
 */
public class SpecialistBO {
    private Logger logger = null;
    
    /** Creates a new instance of SpecialistBO */
    public SpecialistBO() {
        logger = Logger.getLogger(this.getClass());
    }
    
    public UserVO fetchAccountInformation (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchAccountInformation (UserVO)");
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        UserVO uservo = specialistDAO.fetchAccountInformation(userVO);
        logger.info ("End fetchAccountInformation (UserVO)");
        return uservo;
    }
    
    public LocationVO[] fetchLocationVOs (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchLocations (UserVO)");
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        LocationVO[] locationVOs = specialistDAO.fetchLocationVOs(userVO);
        logger.info ("End fetchLocations (UserVO)");
        return locationVOs;
    }
    
    public LocationVO[] deleteLocationVOs (LocationVO[] locationVOs, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start deleteLocationVos (LocationVO[])");
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        specialistDAO.deleteLocationVOs(locationVOs);
        locationVOs = fetchLocationVOs (userVO);
        logger.info ("End deleteLocationVos (LocationVO[])");
        return locationVOs;
    }
    
    public LocationVO[] saveLocationVOs (LocationVO[] locationVOs, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start saveLocationVOs (LocationVO[], UserVO)");
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        String userId = userVO.getUsername();
        specialistDAO.saveLocationVOs(locationVOs, userId);
        locationVOs = fetchLocationVOs(userVO);
        logger.info ("End saveLocationVOs (LocationVO[], UserVO)");
        return locationVOs;
    }    
    
    public UserVO fetchSpecialist (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchSpecialist  (UserVO)");
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        UserVO uservo = specialistDAO.fetchSpecialist (userVO);
        logger.info ("End fetchSpecialist  (UserVO)");
        return uservo;        
    }
    
    public UserVO saveSpecialistProfile (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start saveSpecialistProfile (UserVO)");
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        specialistDAO.saveSpecialist (userVO, userId);
        UserVO uservo = fetchSpecialist(userVO);
        logger.info ("End saveSpecialistProfile (UserVO)");
        return uservo;
    }
}
