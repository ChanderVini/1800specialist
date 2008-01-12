/*
 * PaymentBO.java
 */

package com.cssc.spl.bo;

import com.cssc.spl.dao.PaymentDAO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.vo.PaymentVO;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 20, 2007, 4:10 PM
 */
public class PaymentBO {
    private Logger logger = null;
    
    /** Creates a new instance of PaymentBO */
    public PaymentBO() {
        logger = Logger.getLogger(this.getClass());
    }
    
    public void savePaymentDetails (PaymentVO[] paymentVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start savePaymentDetail (PaymentVO[], String)");
        PaymentDAO paymentDAO = new PaymentDAO ();
        paymentDAO.savePaymentDetails(paymentVOs, userId);
        logger.info ("End savePaymentDetail (PaymentVO[], String)");
    }
}
