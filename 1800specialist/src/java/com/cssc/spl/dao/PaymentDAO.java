/*
 * PaymentDAO.java
 */

package com.cssc.spl.dao;

import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.exception.DbConnectionException;
import com.cssc.spl.util.DbConnection;
import com.cssc.spl.vo.PaymentVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 20, 2007, 4:25 PM
 */
public class PaymentDAO {
    private Logger logger = null;
    private final String CSSC004E = "errors.CSSC004E";
    
    /** Creates a new instance of PaymentDAO */
    public PaymentDAO() {
        logger = Logger.getLogger(this.getClass());
    }
    
    public void savePaymentDetails (PaymentVO[] paymentVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start savePaymentDetails (PaymentVO[])");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO payment_tbl (TRANSACTION_ID, RESPONSE_CODE, APPROVAL_CODE, " +
                    "RESPONSE_REASON_TXT, AVS_CODE, INVOICE_NBR, AMOUNT, LOCATION_NAME, USER_ID, CREATE_DT, CREATE_USER_ID_CD, " +
                    "LAST_UPDATE_USER_ID_CD, LAST_UPDATE_DT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, CURRENT_TIMESTAMP)");
            
            logger.debug ("Query: " + queryBuf.toString ());
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());
            
            for (int cnt = 0; cnt < paymentVOs.length; cnt++) {
                int transactionid = paymentVOs[cnt].getTransactionId();
                int responseCode = paymentVOs[cnt].getResponseCode();
                int approvalCode = paymentVOs[cnt].getApprovalCode();
                String responseTxt = paymentVOs[cnt].getResponseTxt();
                String avsCode = paymentVOs[cnt].getAvsCode();
                String invoiceNbr = paymentVOs[cnt].getInvoiceNbr();                
                double amount = paymentVOs[cnt].getAmount();
                String locationName = paymentVOs[cnt].getLocationName();
                String userid = paymentVOs[cnt].getUserId();
                
                ps.setInt (1, transactionid);
                ps.setInt (2, responseCode);
                ps.setInt (3, approvalCode);
                ps.setString (4, responseTxt);
                ps.setString (5, avsCode);
                ps.setString (6, invoiceNbr);
                ps.setDouble(7, amount);
                ps.setString (8, locationName);
                ps.setString (9, userid);
                ps.setString(10, userId);
                ps.setString(11, userId);
                ps.addBatch();
            }
            int[] codes= ps.executeBatch();
            
            connection.commit();
            DbConnection.close(connection, ps, null);
            logger.info ("End savePaymentDetails (PaymentVO[])");
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);       
        } catch (SQLException sqlexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            logger.error ("Error Occured whle commiting/rollbacking operation: " + sqlexp.getMessage());
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, null, null);
        }    
    }
}
