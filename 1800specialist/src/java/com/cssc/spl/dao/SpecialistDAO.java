/*
 * SpecialistDAO.java
 */

package com.cssc.spl.dao;

import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.exception.DbConnectionException;
import com.cssc.spl.util.DbConnection;
import com.cssc.spl.vo.LocationVO;
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
 * Created on October 16, 2007, 8:29 AM
 */
public class SpecialistDAO {
    private Logger logger = null;
    private final String CSSC004E = "errors.CSSC004E";
    private final String CSSC006E = "errors.CSSC006E";
    private final String CSSC009E = "errors.CSSC009E";
    
    /**
     * Creates a new instance of SpecialistDAO
     */
    public SpecialistDAO() {
        logger = Logger.getLogger(SpecialistDAO.class);
    }
    
    public UserVO fetchAccountInformation (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start fetchAccountInformation (UserVO)");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserVO uservo = new UserVO ();
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT UT.STATUS US, UT.START_DT USD, UT.END_DT UED, UT.RECURRING_FEE URF, " +
                    "LT.REMAINING_DOWNLOADS LRD, LT.STATUS LS, UT.COMPANY COM FROM user_tbl UT LEFT OUTER JOIN location_tbl LT ON " +
                    "UT.USER_ID = LT.USER_ID WHERE UT.USER_ID = ? AND UT.USER_TYPE_CD = ? ");
            connection = DbConnection.getConnection();
            logger.debug ("Query: " + queryBuf.toString());
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            String userId = userVO.getUsername ();
            String userTypeCd = userVO.getUserType();
            
            ps.setString (1, userId);
            ps.setString (2, userTypeCd);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                String userStatus = rs.getString("US");
                Date startDt = rs.getDate ("USD");
                Date endDt = rs.getDate ("UED");
                double recurringFee = rs.getDouble("URF");
                
                int remainingDownloads = rs.getInt("LRD");
                String locStatus = rs.getString("LS");
                
                String company = rs.getString ("COM");
                
                uservo.setStatus(userStatus);
                uservo.setStartDt(startDt);
                uservo.setEndDt(endDt);
                uservo.setRecurringFee(recurringFee);
                uservo.setCompany(company);
                
                LocationVO locationVO = new LocationVO ();
                locationVO.setStatus(locStatus);
                locationVO.setRemDownloads(remainingDownloads);
                
                uservo.addLocation(locationVO);
            }
            DbConnection.close(connection, ps, rs);
         } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, ps, rs);
        }
        logger.info("Start fetchAccountInformation (UserVO)");
        return uservo;
    }
    
    public UserVO fetchSpecialist (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchSpecialist (UserVO)");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT USER_ID UI, PASSWORD PWD, USER_TYPE_CD UTC, STATUS STS, PHONE1 P1, PHONE2 P2, FAX FAX, MOBILE MOB, " +
                    "COMPANY COM, FIRST_NAME FN, LAST_NAME LN, ADDRESS1 ADD1, ADDRESS2 ADD2, CITY CT, STATE ST, COUNTRY CNT, ZIP_CODE ZC, IP_ADDRESS IA, " +
                    "REFERRAL_CD RLC, INDUSTRY_CD IC, REFERRED_CD RDC, CREATE_USER_ID_CD CUC, CREATE_DT CD, LAST_UPDATE_USER_ID_CD LUC, " +
                    "LAST_UPDATE_DT LUD, LOCATION_PWD LP, SETUP_FEE SF, RECURRING_FEE RF FROM user_tbl WHERE USER_ID = ? AND USER_TYPE_CD = ?");
            connection = DbConnection.getConnection();
            logger.debug ("Query: " + queryBuf.toString());
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            ps.setString(1, userVO.getUsername());
            ps.setString(2, userVO.getUserType ());
            logger.debug("User name:" + userVO.getUsername());
            logger.debug("User Type:" + userVO.getUserType());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String userName = rs.getString("UI");
                String password = rs.getString("PWD");
                String userType = rs.getString("UTC");
                String status = rs.getString("STS");
                String phone1 = rs.getString("P1");
                String phone2 = rs.getString("P2");
                String fax = rs.getString("FAX");
                String mobile = rs.getString("MOB");
                String company = rs.getString("COM");
                String firstName = rs.getString("FN");
                String lastName = rs.getString("LN");
                String address1 = rs.getString("ADD1");
                String address2 = rs.getString("ADD2");
                String city = rs.getString("CT");
                String state = rs.getString("ST");
                String country = rs.getString("CNT");
                String zipCode = rs.getString("ZC");
                String ipaddress = rs.getString("IA");
                String referralCd = rs.getString("RLC");
                String industryCd = rs.getString("IC");
                String referredCd = rs.getString("RDC");
                String createUserIdCd = rs.getString("CUC");
                Timestamp createDt = rs.getTimestamp("CD");
                String lastUpdateuserIdCd = rs.getString("LUC");
                Timestamp lastUpdateDt = rs.getTimestamp("LUD");
                String locationPwd = rs.getString ("LP");
                double setupFee = rs.getDouble("SF");
                double recurringFee = rs.getDouble("RF");
                
                userVO.setUsername(userName);
                userVO.setPassword(password);
                userVO.setUserType(userType);
                userVO.setStatus(status);
                userVO.setPhone1(phone1);
                userVO.setPhone2 (phone2);
                userVO.setFax(fax);
                userVO.setMobile(mobile);
                userVO.setCompany(company);
                userVO.setFirstName(firstName);
                userVO.setLastName(lastName);
                userVO.setAddress1(address1);
                userVO.setAddress2 (address2);
                userVO.setCity(city);
                logger.debug("State: " + state);
                userVO.setState(state);
                userVO.setCountry(country);
                userVO.setZipcode(zipCode);
                userVO.setIpaddress(ipaddress);
                userVO.setReferralCd(referralCd);
                userVO.setIndustryCd(industryCd);
                userVO.setReferredCd(referredCd);
                userVO.setCreateUserIdCd(createUserIdCd);
                userVO.setCreateDt(createDt);
                userVO.setLastUpdateUserIdCd(lastUpdateuserIdCd);
                userVO.setLastUpdateDt(lastUpdateDt);
                userVO.setLocationPwd(locationPwd);
                userVO.setSetupFee(setupFee);
                userVO.setRecurringFee(recurringFee);
            }
            DbConnection.close(connection, ps, rs);
            logger.info("End fetchSpecialist (UserVO)");
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, ps, rs);
        }
        logger.info ("End fetchSpecialist (UserVO)");
        return userVO;
    }
    
    public void saveLocationVOs (LocationVO[] locationVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start saveLocationVOs (LocationVO[])");
        Connection connection = null;
        try {
            ArrayList insertLocationVOAL = new ArrayList (10);
            ArrayList updateLocationVOAL = new ArrayList (10);
            
            for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                if (locationVOs[cnt].getId() != -1) {
                    updateLocationVOAL.add (locationVOs[cnt]);
                }
                if (locationVOs[cnt].getId() == -1) {
                    insertLocationVOAL.add (locationVOs[cnt]);
                }
            }
            if (!insertLocationVOAL.isEmpty() || !updateLocationVOAL.isEmpty()) {
                connection = DbConnection.getConnection();
                connection.setAutoCommit(false);
            } else {
                return;
            }
            if (!insertLocationVOAL.isEmpty()) {
                LocationVO[] insertLocationVOs = (LocationVO[]) insertLocationVOAL.toArray(new LocationVO[insertLocationVOAL.size()]);
                insertLocationVOAL = null;                
                insertLocationVOs(connection, insertLocationVOs, userId);
            }
            
            if (!updateLocationVOAL.isEmpty()) {
                LocationVO[] updateLocationVOs = (LocationVO[]) updateLocationVOAL.toArray(new LocationVO[updateLocationVOAL.size()]);
                updateLocationVOAL = null;
                updateLocationVOs(connection, updateLocationVOs, userId);
            }
            connection.commit();
            DbConnection.close(connection, null, null);
       } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (CSSCApplicationException csscaexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCApplicationException (csscaexp.getErrorCode(), csscaexp.getMessage());
        } catch (CSSCSystemException csscsexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCSystemException (csscsexp.getMessage());
        } catch (SQLException sqlexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            logger.error ("Error Occured whle commiting/rollbacking operation: " + sqlexp.getMessage());
            throw new CSSCSystemException (CSSC004E, "'");
        } finally {
            DbConnection.close(connection, null, null);
        }    
        logger.info ("End saveLocationVOs (LocationVO[])");
    }
    
    public void saveSpecialist (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start fetchSpecialist (UserVO)");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("UPDATE user_tbl SET FIRST_NAME = ?, LAST_NAME = ?, ADDRESS1 = ?, ADDRESS2 = ?, CITY = ?, " +
                    "STATE = ?, ZIP_CODE = ?, STATE = ?, COUNTRY = ?, PHONE1 = ?, FAX = ?, LAST_UPDATE_DT = CURRENT_TIMESTAMP, " +
                    "LAST_UPDATE_USER_ID_CD = ? WHERE USER_ID = ? AND USER_TYPE_CD = ?");
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());
            logger.debug("Query: " + queryBuf.toString());
            queryBuf = null;
            
            String firstname = userVO.getFirstName();
            String lastname = userVO.getLastName();
            String address1 = userVO.getAddress1();
            String address2 = userVO.getAddress2();
            String city = userVO.getCity();
            String state = userVO.getState();
            String zipcode = userVO.getZipcode();
            String country = userVO.getCountry();
            String phone1 = userVO.getPhone1 ();
            String fax = userVO.getFax();
            String username = userVO.getUsername();
            String usertype = userVO.getUserType();
            
            ps.setString (1, firstname);
            ps.setString (2, lastname);
            ps.setString (3, address1);
            ps.setString (4, address2);
            ps.setString (5, city);
            ps.setString (6, state);
            ps.setString (7, zipcode);
            ps.setString (8, state);
            ps.setString (9, country);
            ps.setString (10, phone1);
            ps.setString (11, fax);
            ps.setString (12, userId);
            ps.setString (13, username);
            ps.setString (14, usertype);
            ps.executeUpdate();
            
            connection.commit();
            DbConnection.close(connection, ps, null);
            logger.info("End fetchSpecialist (UserVO)");
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error (sqlexp.getMessage(), sqlexp);
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, ps, null);
        }
        
    }
    
    public void deleteLocationVOs (LocationVO[] locationVOs) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start deleteLocationVOs (LocationVO[])");
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("DELETE FROM location_tbl WHERE LOCATION_NAME = ? AND USER_ID = ?");
            logger.debug ("Query: " + queryBuf.toString());
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                ps.setString (1, locationVOs[cnt].getLocationName());
                ps.setString (2, locationVOs[cnt].getUserId ());
                ps.addBatch();
            }
            int[] codes= ps.executeBatch();
            for (int cnt = 0; cnt < codes.length; cnt++) {
                if (codes[cnt] == -3) {
                    connection.rollback();
                    throw new CSSCApplicationException (CSSC006E, locationVOs[cnt].getLocationName());
                }
            }
            connection.commit();
            DbConnection.close(connection, ps, null);
            logger.info("End deleteLocationVOs (LocationVO[])");
         } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (BatchUpdateException buexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            logger.error (buexp.getMessage(), buexp);
             int[] codes = buexp.getUpdateCounts();
            for (int codeCnt = 0; codeCnt < codes.length; codeCnt++) {
                if (codes[codeCnt] == -3) {
                    DbConnection.close(null, ps, null);
                    throw new CSSCApplicationException (CSSC006E, locationVOs[codeCnt].getLocationName());
                }
            }
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error (sqlexp.getMessage(), sqlexp);
            try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, ps, null);
        }    
    }
    
    public LocationVO[] fetchLocationVOs () throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchLocationVOs ()");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT LOCATION_NAME LN, STATUS STAT, PHONE1 PH1, ADDRESS1 ADD1, ADDRESS2 ADD2, CITY CITY, " +
                    "STATE STATE, COUNTRY CNTR, ZIP_CODE ZIP, REMAINING_DOWNLOADS RD, START_DT SD, END_DT ED, USER_ID UI FROM location_tbl");
            logger.debug ("Query: " + queryBuf.toString());
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(queryBuf.toString ());
            queryBuf = null;
            rs = ps.executeQuery();
            ArrayList locationVOAL = new ArrayList (10);
            while (rs.next()) {
                LocationVO locationVO = new LocationVO ();
                String locationName = rs.getString("LN");
                String status = rs.getString("STAT");
                String phone1 = rs.getString ("PH1");
                String address1 = rs.getString ("ADD1");
                String address2 = rs.getString("ADD2");
                String city = rs.getString("CITY");
                String state = rs.getString("STATE");
                String country = rs.getString("CNTR");
                String zipcode = rs.getString ("ZIP");
                int remainingDownloads = rs.getInt("RD");
                Date startDt = rs.getDate("SD");
                Date endDt = rs.getDate("ED");
                String userId = rs.getString ("UI");
                
                locationVO.setLocationName(locationName);
                locationVO.setStatus(status);
                locationVO.setPhone1(phone1);
                locationVO.setAddress1(address1);
                locationVO.setAddress2 (address2);
                locationVO.setCity(city);
                locationVO.setState (state);
                locationVO.setCountry(country);
                locationVO.setZipCode(zipcode);
                locationVO.setRemDownloads(remainingDownloads);
                locationVO.setStartDate(startDt);
                locationVO.setEndDate(endDt);
                locationVO.setUserId (userId);
                
                locationVOAL.add(locationVO);                        
            }
            DbConnection.close (connection, ps, rs);
            LocationVO[] locationVOs = (LocationVO[]) locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
            locationVOAL = null;
            logger.info ("End fetchLocationVOs ()");
            return locationVOs;
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, ps, rs);
        }
    }
    
    public LocationVO[] fetchLocationVOs (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start fetchLocationVOs (UserVO)");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT LOCATION_NAME LN, STATUS STAT, PHONE1 PH1, ADDRESS1 ADD1, ADDRESS2 ADD2, CITY CITY, " +
                    "STATE STATE, COUNTRY CNTR, ZIP_CODE ZIP, REMAINING_DOWNLOADS RD, START_DT SD, END_DT ED, USER_ID UI " +
                    "FROM location_tbl WHERE USER_ID = ? ");
            logger.debug ("Query: " + queryBuf.toString());
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(queryBuf.toString ());
            queryBuf = null;
            ps.setString (1, userVO.getUsername());
            rs = ps.executeQuery();
            ArrayList locationVOAL = new ArrayList (10);
            while (rs.next()) {
                LocationVO locationVO = new LocationVO ();
                String locationName = rs.getString("LN");
                String status = rs.getString("STAT");
                String phone1 = rs.getString ("PH1");
                String address1 = rs.getString ("ADD1");
                String address2 = rs.getString("ADD2");
                String city = rs.getString("CITY");
                String state = rs.getString("STATE");
                String country = rs.getString("CNTR");
                String zipcode = rs.getString ("ZIP");
                int remainingDownloads = rs.getInt("RD");
                Date startDt = rs.getDate("SD");
                Date endDt = rs.getDate("ED");
                String userId = rs.getString ("UI");
                
                locationVO.setLocationName(locationName);
                locationVO.setStatus(status);
                locationVO.setPhone1(phone1);
                locationVO.setAddress1(address1);
                locationVO.setAddress2 (address2);
                locationVO.setCity(city);
                locationVO.setState (state);
                locationVO.setCountry(country);
                locationVO.setZipCode(zipcode);
                locationVO.setRemDownloads(remainingDownloads);
                locationVO.setStartDate(startDt);
                locationVO.setEndDate(endDt);
                locationVO.setUserId (userId);
                
                locationVOAL.add(locationVO);                        
            }
            DbConnection.close (connection, ps, rs);
            LocationVO[] locationVOs = (LocationVO[]) locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
            locationVOAL = null;
            logger.info ("End fetchLocationVOs (UserVO)");
            return locationVOs;
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, ps, rs);
        }
    }
    
     public void insertLocationVOs (LocationVO[] insertLocationVOs, String userId)  throws CSSCApplicationException, CSSCSystemException {
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            insertLocationVOs(connection, insertLocationVOs, userId);
            connection.commit();
            DbConnection.close(connection, null, null);
       } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (CSSCApplicationException csscaexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCApplicationException (csscaexp.getErrorCode(), csscaexp.getMessage());
        } catch (CSSCSystemException csscsexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCSystemException (csscsexp.getMessage());
        } catch (SQLException sqlexp) {
             try {
                connection.rollback();
           } catch (SQLException sqlExp) {}
            logger.error ("Error Occured whle commiting/rollbacking operation: " + sqlexp.getMessage());
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, null, null);
        }    
    }
     
    private void insertLocationVOs (Connection connection, LocationVO[] insertLocationVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
         logger.info ("Start insertLocationVOs (Connection, LocationVO[], String)");
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO location_tbl (LOCATION_NAME, ADDRESS1, ADDRESS2, CITY, STATE, COUNTRY, " +
                    "ZIP_CODE, USER_ID, CREATE_USER_ID_CD, CREATE_DT, LAST_UPDATE_USER_ID_CD, LAST_UPDATE_DT, PHONE1)  VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");
            logger.debug ("Query: " + queryBuf.toString ());
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < insertLocationVOs.length; cnt++) {
                ps.setString(1, insertLocationVOs[cnt].getLocationName());
                ps.setString(2, insertLocationVOs[cnt].getAddress1());
                ps.setString(3, insertLocationVOs[cnt].getAddress2());
                ps.setString(4, insertLocationVOs[cnt].getCity());
                ps.setString(5, insertLocationVOs[cnt].getState());
                ps.setString(6, insertLocationVOs[cnt].getCountry());
                ps.setString(7, insertLocationVOs[cnt].getZipCode());
                ps.setString(8, userId);
                ps.setString(9, userId);
                ps.setString(10, userId);
                ps.setString (11, insertLocationVOs[cnt].getPhone1());
                ps.addBatch();
            }
            int[] codes = ps.executeBatch();
            for (int codeCnt = 0; codeCnt < codes.length; codeCnt++) {
                if (codes[codeCnt] == -3) {
                    DbConnection.close(null, ps, null);
                    throw new CSSCApplicationException (CSSC009E, insertLocationVOs[codeCnt].getLocationName());
                }
            }
            DbConnection.close(null, ps, null);
            logger.info ("End insertLocationVOs (Connection, LocationVO[], String)");
        } catch (BatchUpdateException buexp) {
            logger.error ("BatchUpdate Exception", buexp);            
             int[] codes = buexp.getUpdateCounts();
            for (int codeCnt = 0; codeCnt < codes.length; codeCnt++) {
                if (codes[codeCnt] == -3) {
                    DbConnection.close(null, ps, null);
                    throw new CSSCApplicationException (CSSC009E, insertLocationVOs[codeCnt].getLocationName());
                }
            }
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error ("SQLException", sqlexp);
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(null, ps, null);
        }    
    }
     
    public void updateLocationVOs (LocationVO[] updateLocationVOs, String userId)  throws CSSCApplicationException, CSSCSystemException {
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
            logger.debug ("Size of Locations:" + updateLocationVOs.length);
            updateLocationVOs(connection, updateLocationVOs, userId);
            connection.commit();
            DbConnection.close(connection, null, null);
       } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (CSSCApplicationException csscaexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCApplicationException (csscaexp.getErrorCode(), csscaexp.getMessage());
        } catch (CSSCSystemException csscsexp) {
             try {
                connection.rollback();
            } catch (SQLException sqlExp) {}
            throw new CSSCSystemException (csscsexp.getMessage ());
        } catch (SQLException sqlexp) {
             try {
                connection.rollback();
           } catch (SQLException sqlExp) {}
            logger.error ("Error Occured while commiting/rollbacking operation: " + sqlexp.getMessage());
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(connection, null, null);
        }    
    }
    
    private void updateLocationVOs (Connection connection, LocationVO[] updateLocationVOs, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start updateLocationVOs(Connection, LocationVO[], String)");
        PreparedStatement ps = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("UPDATE location_tbl SET ADDRESS1 = ?, ADDRESS2 = ?, CITY = ?, STATE = ?, COUNTRY = ?, " +
                    "ZIP_CODE = ?, LAST_UPDATE_USER_ID_CD = ?, LAST_UPDATE_DT = CURRENT_TIMESTAMP, REMAINING_DOWNLOADS = ? " +
                    ", PHONE1 = ?, STATUS = ?, START_DT = ?, END_DT = ? WHERE LOCATION_NAME = ? AND USER_ID = ?");
            logger.debug ("Query: " + queryBuf.toString ());
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            for (int cnt = 0; cnt < updateLocationVOs.length; cnt++) {
                ps.setString(1, updateLocationVOs[cnt].getAddress1());
                ps.setString(2, updateLocationVOs[cnt].getAddress2());
                ps.setString(3, updateLocationVOs[cnt].getCity());
                ps.setString(4, updateLocationVOs[cnt].getState());
                ps.setString(5, updateLocationVOs[cnt].getCountry());
                ps.setString(6, updateLocationVOs[cnt].getZipCode());
                if (userId == null || "".equals(userId)) {
                    ps.setString (7, updateLocationVOs[cnt].getUserId());
                } else {
                    ps.setString(7, userId);
                }
                ps.setInt (8, updateLocationVOs[cnt].getRemDownloads());
                ps.setString (9, updateLocationVOs[cnt].getPhone1());
                logger.debug("Status: " + updateLocationVOs[cnt].getStatus());
                ps.setString (10, updateLocationVOs[cnt].getStatus());
                ps.setDate (11, updateLocationVOs[cnt].getStartDate());
                ps.setDate (12, updateLocationVOs[cnt].getEndDate());
                ps.setString(13, updateLocationVOs[cnt].getLocationName());
                ps.setString (14, updateLocationVOs[cnt].getUserId());
                ps.addBatch();
            }
            int[] codes = ps.executeBatch();
            for (int codeCnt = 0; codeCnt < codes.length; codeCnt++) {
                if (codes[codeCnt] == -3) {
                    DbConnection.close (null, ps, null);
                    throw new CSSCApplicationException (CSSC006E, updateLocationVOs[codeCnt].getLocationName());
                }
            }
            DbConnection.close (null, ps, null);
            logger.info ("End updateLocationVOs(Connection, LocationVO[], String)");
        } catch (BatchUpdateException buexp) {
            logger.error ("BatchUpdate Exception", buexp);            
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            logger.error ("SQLException", sqlexp);
            throw new CSSCSystemException (CSSC004E, "");
        } finally {
            DbConnection.close(null, ps, null);
        }    
    }
}