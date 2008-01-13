/*
 * Expire.java
 */

package com.cssc.spl.util;

import com.cssc.spl.dao.AdminDAO;
import com.cssc.spl.dao.SpecialistDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserVO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 23, 2007, 6:12 PM
 */
public class Expire extends Thread {
    private final String EXPR = "EXPIRE";
    private final String SPEC = "SPEC";
    
    private Logger logger = null;
        
    public Expire () {
        logger = Logger.getLogger(this.getClass());
    }
    
    public void run () {
        try {
            expire ();
        } catch (CSSCApplicationException csscaexp) {
            logger.error(csscaexp);
        } catch (CSSCSystemException csscsexp) {
            logger.error(csscsexp);
        }
    }
    
    public synchronized void expire () throws CSSCApplicationException, CSSCSystemException {
        System.out.println ("Start expireSpecialists ()");
        AdminDAO adminDAO = new AdminDAO ();
        UserVO userVO = new UserVO ();
        userVO.setUserType(SPEC);
        
        UserVO[] specialistVOs = adminDAO.fetchUsers(userVO);
        ArrayList specialistVOAL = new ArrayList (10) ;
        Calendar cal = Calendar.getInstance();
        Date currentDate = new Date (cal.getTimeInMillis());
        for (int cnt = 0; cnt < specialistVOs.length; cnt++) {
            Date endDt = specialistVOs[cnt].getEndDt();
            if (endDt != null) {
                if (currentDate.after(endDt)) {
                    specialistVOs[cnt].setStatus(EXPR);
                    specialistVOAL.add (specialistVOs[cnt]);
                }
            }
        }
        System.out.println ("Number of Expired specialists: " + specialistVOAL.size());
        UserVO[] userVOs = (UserVO[]) specialistVOAL.toArray(new UserVO[specialistVOAL.size()]);
        specialistVOAL = null;
        expireSpecialists(userVOs);
        
        ArrayList locationVOAL = new ArrayList (10) ;
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        LocationVO[] locationVOs = specialistDAO.fetchLocationVOs();
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            Date endDt = locationVOs[cnt].getEndDate();
            if (endDt != null) {
                if (currentDate.after(endDt)) {
                    locationVOs[cnt].setStatus(EXPR);
                    locationVOAL.add(locationVOs[cnt]);
                }
            }
        }
        System.out.println ("Number of Expired locations: " + locationVOAL.size());
        locationVOs = (LocationVO[]) locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
        locationVOAL = null;
        expireLocations (locationVOs);
    }
    
    private void expireSpecialists (UserVO[] userVOs) throws CSSCApplicationException, CSSCSystemException {
        System.out.println ("Start sxpireSpecialists (UserVO[])");
        AdminDAO adminDAO = new AdminDAO ();
        adminDAO.updateUserVOs(userVOs, "SYSTEM");
        System.out.println ("End expireSpecialists (UserVO[])");
    }
    
    public void expireLocations (LocationVO[] locationVOs) throws CSSCApplicationException, CSSCSystemException {
        System.out.println ("Start expireLocations (UserVO[])");
        SpecialistDAO specialistDAO = new SpecialistDAO ();
        specialistDAO.updateLocationVOs (locationVOs, "SYSTEM");
        System.out.println ("End expireLocations (UserVO[])");    
    }
    
    public static void main (String[] args) {
        try {
            Expire expire = new Expire ();
            //expire.sleep(10000);
            expire.start();
            expire.sleep(24 * 60 * 60 * 60 * 1000);
        } catch (InterruptedException iexp) {
            
        }
    }
}
