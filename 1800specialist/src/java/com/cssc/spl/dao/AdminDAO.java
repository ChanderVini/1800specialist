/*
 * AdminDAO.java
 */

package com.cssc.spl.dao;

import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.exception.DbConnectionException;
import com.cssc.spl.util.DbConnection;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserTypeVO;
import com.cssc.spl.vo.UserVO;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 9, 2007, 1:18 PM
 */
public class AdminDAO {
    private Logger logger = null;
    private final String CSSC004E = "errors.CSSC004E";
    private final String CSSC006E = "errors.CSSC006E";
        
    /** Creates a new instance of AdminDAO */
    public AdminDAO() {
        logger = Logger.getLogger(this.getClass());
    }
    
    public void deleteSpecialists (UserVO[] userVOs, LocationVO[] locationVOs)  throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start deleteSpecialists (UserVO[], LocationVO[])");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("DELETE FROM location_tbl WHERE LOCATION_NAME = ? AND USER_id = ?");
            logger.debug ("Query: " + queryBuf.toString());
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                ps.setString(1, locationVOs[cnt].getLocationName());
                ps.setString(2, locationVOs[cnt].getUserId ());
                ps.addBatch();
            }
            ps.executeBatch();
            DbConnection.close(null, ps, null);
            queryBuf = new StringBuffer ("DELETE FROM user_tbl WHERE USER_id = ? AND USER_TYPE_CD = ?");
            logger.debug ("Query: " + queryBuf.toString());
             ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < userVOs.length; cnt++) {
                ps.setString(1, userVOs[cnt].getUsername());
                ps.setString(2, userVOs[cnt].getUserType());
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
            DbConnection.close(connection, ps, null);
            logger.info("End deleteSpecialists (UserVO[], LocationVO[])");
         } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (BatchUpdateException buexp) {
            logger.error (buexp.getMessage(), buexp);
            try {
                connection.rollback();
            } catch (SQLException sqlexp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error (sqlexp.getMessage(), sqlexp);
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, null);
        }
    }
            
    public void deleteUserTypes (UserTypeVO[] userTypesVOs) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start deleteUserTypeVOs (UserTypeVO[])");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("DELETE FROM user_type_tbl WHERE USER_TYPE_CD = ?");
            logger.debug ("Query: " + queryBuf.toString());
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < userTypesVOs.length; cnt++) {
                ps.setString (1, userTypesVOs[cnt].getUserTypeCd());
                ps.addBatch();
            }
            int[] codes= ps.executeBatch();
            for (int cnt = 0; cnt < codes.length; cnt++) {
                if (codes[cnt] == -3) {
                    connection.rollback();
                    throw new CSSCApplicationException (CSSC006E, userTypesVOs[cnt].getUserTypeCd());
                }
            }
            connection.commit();
            DbConnection.close(connection, ps, null);
            logger.info("End deleteUserTypeVOs (UserTypeVO[])");
         } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (BatchUpdateException buexp) {
            logger.error (buexp.getMessage(), buexp);
            try {
                connection.rollback();
            } catch (SQLException sqlexp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error (sqlexp.getMessage(), sqlexp);
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, null);
        }
    }
    
     public UserVO[] fetchUsers (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchUsers (UserVO)");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT UT.USER_ID UI, UT.PASSWORD PWD, UT.USER_TYPE_CD UTC, UT.FIRST_NAME FN, " +
                    "UT.LAST_NAME LN, UT.LOCATION_PWD LP, UT.START_DT SD, UT.END_DT ED, LOC.LOCATION_NAME LLN, LOC.STATUS LST, " +
                    "LOC.REMAINING_DOWNLOADS RD, LOC.CREATE_DT LCD, LOC.START_DT LSD, LOC.END_DT LED, LOC.UPLOADED UPL " +
                    "FROM user_tbl UT LEFT OUTER JOIN location_tbl LOC ON UT.USER_ID = LOC.USER_ID WHERE USER_TYPE_CD = ?");
            logger.debug("Query: " + queryBuf.toString());
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            ps.setString (1, userVO.getUserType());
            logger.debug ("User Type: " + userVO.getUserType());
            rs = ps.executeQuery();
            ArrayList userAL = new ArrayList (10);
            while (rs.next()) {
                UserVO uservo = new UserVO ();
                String userId = rs.getString("UI");
                String password = rs.getString("PWD");
                String userType = rs.getString("UTC");
                String firstName = rs.getString ("FN");
                String lastName = rs.getString ("LN");
                String locationPwd = rs.getString("LP");
                Date userstartDt = rs.getDate("SD");
                Date userendDt = rs.getDate("ED");
                
                uservo.setUsername(userId);
                uservo.setPassword(password);
                uservo.setUserType(userType);
                uservo.setFirstName(firstName);
                uservo.setLastName(lastName);
                uservo.setLocationPwd(locationPwd);
                uservo.setStartDt(userstartDt);
                uservo.setEndDt(userendDt);
                
                LocationVO locationVO = new LocationVO ();
                
                String locationName = rs.getString("LLN");
                String status = rs.getString("LST");
                int remainingDownloads = rs.getInt("RD");
                Timestamp createDt = rs.getTimestamp("LCD");
                Date startDate = rs.getDate("LSD");
                Date endDate = rs.getDate("LED");
                String uploaded = rs.getString("UPL");
                
                locationVO.setLocationName(locationName);
                locationVO.setStatus(status);
                locationVO.setRemDownloads(remainingDownloads);
                locationVO.setCreateDt(createDt);
                locationVO.setStartDate(startDate);
                locationVO.setEndDate(endDate);
                locationVO.setUplaoded(uploaded);
                
                uservo.addLocation(locationVO);
                userAL.add (uservo);
            }
            logger.debug ("Size of User VOs: " + userAL.size());
            DbConnection.close(connection, ps, rs);
            UserVO[] userVOs = (UserVO[]) userAL.toArray(new UserVO[userAL.size()]);
            userAL = null;
            logger.info ("End fetchUsers (UserVO)");
            return userVOs;
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, rs);
        }
    }
    
    public UserTypeVO[] fetchUserTypes () throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchUserTypes()");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT USER_TYPE_CD UTC, DESCRIPTION DESCR, CREATE_USER_ID_CD CUIC, CREATE_DT CD, LAST_UPDATE_USER_ID_CD LUUIC, " +
                    "LAST_UPDATE_DT LUD FROM user_type_tbl";
            logger.debug("Query: " + query);
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList userTypeVOAL = new ArrayList (10);
            while (rs.next()) {
                UserTypeVO userTypeVO = new UserTypeVO ();
                String userTypeCd = rs.getString("UTC");
                String userTypeName = rs.getString("DESCR");
                String createUserIdCd = rs.getString("CUIC");
                Timestamp createDt = rs.getTimestamp ("CD");
                String lastUpdateUserIdCd = rs.getString("LUUIC");
                Timestamp lastUpdateDate = rs.getTimestamp("LUD");
                userTypeVO.setUserTypeCd(userTypeCd);
                userTypeVO.setUserTypeName(userTypeName);
                userTypeVO.setCreateUserIdCd(createUserIdCd);
                userTypeVO.setCreateDt(createDt);
                userTypeVO.setLastUpdateUserIdCd(lastUpdateUserIdCd);
                userTypeVO.setLastUpdateDt(lastUpdateDate);
                userTypeVOAL.add (userTypeVO);
            }
            DbConnection.close(connection, ps, rs);
            UserTypeVO[] userTypeVOs = (UserTypeVO[]) userTypeVOAL.toArray(new UserTypeVO [userTypeVOAL.size()]);
            userTypeVOAL = null;
            logger.info ("End fetchUserTypes()");
            return userTypeVOs;
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, rs);
        }
    }
    
    public void updateUserTypes (UserTypeVO[] userTypeVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start updateUserTypes (UserTypeVO[])");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            ArrayList insertUserTypeAL = new ArrayList  (10);
            ArrayList updateUserTypeAL = new ArrayList (10);
            for (int cnt = 0; cnt < userTypeVOs.length; cnt++) {
                if (userTypeVOs[cnt].getId() > 0) {
                    updateUserTypeAL.add (userTypeVOs[cnt]);
                } else {
                    insertUserTypeAL.add (userTypeVOs[cnt]);
                }
            }
            
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            
            StringBuffer insertQueryBuf = new StringBuffer ("INSERT INTO user_type_tbl (USER_TYPE_CD, DESCRIPTION, CREATE_USER_ID_CD, " +
                    "CREATE_DT, LAST_UPDATE_USER_ID_CD, LAST_UPDATE_DT) VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP)");
            logger.debug("Query: " + insertQueryBuf.toString());
            ps = connection.prepareStatement(insertQueryBuf.toString());
            insertQueryBuf = null;
            for (int cnt = 0; cnt < insertUserTypeAL.size(); cnt++) {
                UserTypeVO userTypeVO = (UserTypeVO) insertUserTypeAL.get(cnt);
                ps.setString(1, userTypeVO.getUserTypeCd());
                ps.setString(2, userTypeVO.getUserTypeName());
                ps.setString(3, userId);
                ps.setString(4, userId);
                ps.addBatch();
            }
            int[] codes= ps.executeBatch();
            for (int cnt = 0; cnt < codes.length; cnt++) {
                if (codes[cnt] == -3) {
                    connection.rollback();
                    throw new CSSCApplicationException (CSSC006E, ((UserTypeVO)insertUserTypeAL.get(cnt)).getUserTypeCd());
                }
            }
            DbConnection.close(null, ps, null);
            
            StringBuffer updateQueryBuf = new StringBuffer ("UPDATE user_type_tbl SET DESCRIPTION = ?, LAST_UPDATE_USER_ID_CD = 'CSINGH', " +
                    "LAST_UPDATE_DT = CURRENT_TIMESTAMP  WHERE USER_TYPE_CD= ?");
            logger.debug("Query: " + updateQueryBuf.toString());
            ps = connection.prepareStatement(updateQueryBuf.toString());
            updateQueryBuf = null;
            for (int cnt = 0; cnt < updateUserTypeAL.size(); cnt++) {
                UserTypeVO userTypeVO = (UserTypeVO) insertUserTypeAL.get(cnt);
                ps.setString(1, userTypeVO.getUserTypeName());
                ps.setString(2, userTypeVO.getUserTypeCd());
                ps.addBatch();
            }
            codes= ps.executeBatch();
            for (int cnt = 0; cnt < codes.length; cnt++) {
                if (codes[cnt] == -3) {
                    connection.rollback();
                    throw new CSSCApplicationException (CSSC006E, ((UserTypeVO)updateUserTypeAL.get(cnt)).getUserTypeCd());
                }
            }
            connection.commit();
            DbConnection.close(connection, ps, null);
            logger.info("End updateUserTypes (UserTypeVO[])");        
       } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (BatchUpdateException buexp) {
            logger.error (buexp.getMessage(), buexp);
            try {
                connection.rollback();
            } catch (SQLException sqlexp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error (sqlexp.getMessage(), sqlexp);
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, null);
        }
    }
    
    public void updateLocationVOs (LocationVO[] locationVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start updateLocationVOs(LocationVO[], String)");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("UPDATE location_tbl SET LAST_UPDATE_USER_ID_CD = ?, LAST_UPDATE_DT = CURRENT_TIMESTAMP, " +
                    "REMAINING_DOWNLOADS = ?, END_DT = ?, UPLOADED = ?, STATUS = ? WHERE LOCATION_NAME = ? AND USER_ID = ?");
            logger.debug ("Query: " + queryBuf.toString ());
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                ps.setString (1, userId);                
                ps.setInt (2, locationVOs[cnt].getRemDownloads());
                ps.setDate (3, locationVOs[cnt].getEndDate());
                ps.setString (4, locationVOs[cnt].getUploaded());
                ps.setString (5, locationVOs[cnt].getStatus());
                ps.setString (6, locationVOs[cnt].getLocationName());
                ps.setString (7, locationVOs[cnt].getUserId());
                ps.addBatch();
            }
            int[] codes = ps.executeBatch();            
            for (int codeCnt = 0; codeCnt < codes.length; codeCnt++) {
                if (codes[codeCnt] == -3) {
                    DbConnection.close (connection, ps, null);
                    throw new CSSCApplicationException (CSSC006E, locationVOs[codeCnt].getLocationName());
                }
            }
            connection.commit();
            DbConnection.close (connection, ps, null);
            logger.info ("End updateLocationVOs(Connection, LocationVO[], String)");
       } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (BatchUpdateException buexp) {
            logger.error (buexp.getMessage(), buexp);
            try {
                connection.rollback();
            } catch (SQLException sqlexp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error (sqlexp.getMessage(), sqlexp);
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, null);
        }
    }
    
    public void updateUserVOs (UserVO[] userVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start updateSpecialistVOs (UserVO[], String)");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("UPDATE user_tbl SET LAST_UPDATE_USER_ID_CD = ?, LAST_UPDATE_DT = CURRENT_TIMESTAMP, " +
                    "END_DT = ?, STATUS = ? WHERE USER_TYPE_CD = ? AND USER_ID = ?");
            logger.debug ("Query: " + queryBuf.toString ());
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < userVOs.length; cnt++) {
                ps.setString (1, userId);                
                ps.setDate (2, userVOs[cnt].getEndDt());
                ps.setString (3, userVOs[cnt].getStatus());
                ps.setString (4, userVOs[cnt].getUserType());
                ps.setString (5, userVOs[cnt].getUsername());
                ps.addBatch();
            }
            int[] codes = ps.executeBatch();            
            for (int codeCnt = 0; codeCnt < codes.length; codeCnt++) {
                if (codes[codeCnt] == -3) {
                    DbConnection.close (connection, ps, null);
                    throw new CSSCApplicationException (CSSC006E, userVOs[codeCnt].getUsername());
                }
            }
            connection.commit();
            DbConnection.close (connection, ps, null);
            logger.info ("End updateSpecialistVOs (UserVO[], String)");
       } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (BatchUpdateException buexp) {
            logger.error (buexp.getMessage(), buexp);
            try {
                connection.rollback();
            } catch (SQLException sqlexp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error (sqlexp.getMessage(), sqlexp);
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {
                
            }
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, null);
        }
    }    
}
