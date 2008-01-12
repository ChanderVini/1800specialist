/*
 * GeneralistDAO.java
 */

package com.cssc.spl.dao;

import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.exception.DbConnectionException;
import com.cssc.spl.util.DbConnection;
import com.cssc.spl.vo.LocationVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 28, 2007, 2:59 PM
 */
public class GeneralistDAO {
    private Logger logger = null;
    
    private final String CSSC004E = "errors.CSSC004E";
    
    /** Creates a new instance of GeneralistDAO */
    public GeneralistDAO() {
        logger = Logger.getLogger(this.getClass ());
    }
    
    public void updateLocationVO (LocationVO updateLocationVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start updateLocationVO (LocationVO, String)");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("UPDATE location_tbl SET REMAINING_DOWNLOADS = ? WHERE LOCATION_NAME = ? AND USER_ID = ?");
            
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            logger.debug ("Query: " + queryBuf.toString ());
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;

            ps.setInt (1, updateLocationVO.getRemDownloads());
            ps.setString(2, updateLocationVO.getLocationName());
            ps.setString (3, updateLocationVO.getUserId());

            ps.executeUpdate();
            connection.commit();
            DbConnection.close (connection, ps, null);
            logger.info ("End updateLocationVOs(Connection, LocationVO[], String)");
         } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
         } catch (SQLException sqlexp) {
             try {
                connection.rollback();
           } catch (SQLException sqlExp) {}
            logger.error ("Error Occured while commiting/rollbacking operation: " + sqlexp.getMessage());
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, null);
        }    
    }
}
