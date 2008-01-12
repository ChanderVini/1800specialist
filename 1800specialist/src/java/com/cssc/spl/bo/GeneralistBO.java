/*
 * GeneralistBO.java
 */

package com.cssc.spl.bo;

import com.cssc.spl.dao.GeneralistDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.LocationVO;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 28, 2007, 3:05 PM
 */
public class GeneralistBO {
    private Logger logger = null;
    
    /** Creates a new instance of GeneralistBO */
    public GeneralistBO() {
        logger = Logger.getLogger(this.getClass());
    }
    
    public void updateLocationVO (LocationVO locationVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start updateLocationVO (LocationVO, String)");
        GeneralistDAO generalistDAO = new GeneralistDAO ();
        generalistDAO.updateLocationVO(locationVO, userId);
        logger.info ("Start updateLocationVO (LocationVO, String)");
    }
}
