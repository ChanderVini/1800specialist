package com.cssc.spl.dao;

import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.exception.DbConnectionException;
import com.cssc.spl.util.DbConnection;
import com.cssc.spl.util.PasswordGenerator;
import com.cssc.spl.vo.GeneralistVO;
import com.cssc.spl.vo.UserVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * @created.on October 15, 2007
 */
public class UserDAO {
    private Logger logger = null;
    private final String CSSC004E = "errors.CSSC004E";
    private final String CSSC015E = "errors.CSSC015E";
    private final String CSSC016E = "errors.CSSC016E";
    
    public UserDAO() {
        logger = Logger.getLogger(UserDAO.class);
    }
    
    public UserVO createGeneralist (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start createGeneralist (UserVO, String)");
        Connection connection = null;
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
            boolean isvalid = validateGeneralist(connection, userVO);
            if (!isvalid) {
                DbConnection.close(connection, null, null);
                throw new CSSCApplicationException (CSSC016E, "");
            }
            
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO generalist_tbl (GENERALIST_ID, LOCATION_PWD, USER_ID, COMPANY_NAME, " +
                    "FIRST_NAME, LAST_NAME, ADDRESS1, ADDRESS2, CITY, STATE, COUNTRY, ZIP_CODE, FAX, PHONE, CREATE_USER_ID_CD, CREATE_DT, " +
                    "LAST_UPDATE_USER_ID_CD, LAST_UPDATE_DT, USER_TYPE_CD) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)");
            logger.debug ("Query: " + queryBuf.toString());
            ps = connection.prepareStatement(queryBuf.toString ());
            queryBuf = null;
            ps.setString (1, userVO.getUsername());
            ps.setString (2, userVO.getLocationPwd());
            logger.debug("Specialist: " + userVO.getReferredCd());
            ps.setString (3, userVO.getReferredCd());
            ps.setString (4, userVO.getCompany());
            ps.setString (5, userVO.getFirstName());
            ps.setString (6, userVO.getLastName());
            ps.setString (7, userVO.getAddress1());
            ps.setString (8, userVO.getAddress2());
            ps.setString (9, userVO.getCity());
            ps.setString (10, userVO.getState());
            ps.setString (11, userVO.getCountry());
            ps.setString (12, userVO.getZipcode());
            ps.setString (13, userVO.getPhone2());
            ps.setString (14, userVO.getPhone1());
            ps.setString (15, userId);
            ps.setString (16, userId);
            ps.setString (17, userVO.getUserType());
            logger.debug ("User Type Cd: " + userVO.getUserType());
            int code = ps.executeUpdate();
            logger.debug (code);
            if (code == -3) {
                DbConnection.close (connection, ps, rs);
                throw new CSSCApplicationException (CSSC015E, "");
            }
            DbConnection.close (connection, ps, rs);
            logger.info ("End createGeneralist (UserVO, String)");
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E, "");
        } catch (SQLException sqlexp) {
            int errorCode = sqlexp.getErrorCode();
            if (errorCode == 1062) {
                throw new CSSCApplicationException (CSSC015E, "");
            }
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E, "'");
        } finally {
            DbConnection.close(connection, ps, rs);
        }
        return userVO;
    }
    
    public boolean validateGeneralist (Connection connection, UserVO userVO)  throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start validateGeneralist (Connection, UserVO)");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            boolean isvalid = false;
            StringBuffer queryBuf = new StringBuffer ("SELECT USER_ID FROM user_tbl USR WHERE USR.USER_TYPE_CD = ? AND USR.LOCATION_PWD = ?");
            logger.debug ("Query: " + queryBuf.toString ());
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            ps.setString (1, userVO.getUserType());
            ps.setString (2, userVO.getLocationPwd());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                String userId = rs.getString ("USER_ID");
                if (userId != null) {
                    isvalid = true;
                    userVO.setReferredCd(userId);
                }
            }
            DbConnection.close(null, ps, rs);
            logger.info ("End validateGeneralist (Connection, UserVO)");
            return isvalid;
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(null, ps, rs);
        }
    }
    
    public UserVO createSpecialist (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start createUser (UserVO, String)");
        Connection connection = null;
        PreparedStatement ps = null;        
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
            generateLocPassword (connection, userVO);
            logger.debug ("Location Password: " + userVO.getLocationPwd());
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO user_tbl " +
                    "(USER_ID, PASSWORD, USER_TYPE_CD, STATUS, PHONE1, FAX, FIRST_NAME, LAST_NAME, ADDRESS1, ADDRESS2, CITY, STATE, COUNTRY, ZIP_CODE, " +
                    "CREATE_USER_ID_CD, CREATE_DT, LAST_UPDATE_USER_ID_CD, LAST_UPDATE_DT, LOCATION_PWD, END_DT, SETUP_FEE, START_DT, COMPANY) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?, ?, CURRENT_TIMESTAMP, ?)");
            logger.debug ("Query: " + queryBuf.toString ());
            
            ps = connection.prepareStatement(queryBuf.toString ());
            queryBuf = null;
            logger.debug ("User Name: " + userVO.getUsername());
            ps.setString (1, userVO.getUsername());
            logger.debug ("User Type: " + userVO.getUserType());
            ps.setString (2, userVO.getPassword());
            ps.setString (3, userVO.getUserType());
            ps.setString (4, userVO.getStatus());
            ps.setString (5, userVO.getPhone1());
            ps.setString (6, userVO.getFax());
            ps.setString (7, userVO.getFirstName());
            ps.setString (8, userVO.getLastName());
            ps.setString (9, userVO.getAddress1());
            ps.setString (10, userVO.getAddress2());
            ps.setString (11, userVO.getCity());
            ps.setString (12, userVO.getState());
            ps.setString (13, userVO.getCountry());
            ps.setString (14, userVO.getZipcode());
            ps.setString (15, userId);
            ps.setString (16, userId);
            if (userVO.getLocationPwd() != null && !"".equals(userVO.getLocationPwd().trim())) {
                ps.setString (17, userVO.getLocationPwd());
            } else {
                ps.setNull(17, Types.VARCHAR);
            }
            ps.setDate (18, userVO.getEndDt());
            ps.setDouble(19, userVO.getSetupFee());
            ps.setString (20, userVO.getCompany());
            int code = ps.executeUpdate();
            if (code == -3) {
                DbConnection.close (connection, ps, rs);
                throw new CSSCApplicationException (CSSC015E, "");
            }
            DbConnection.close (connection, ps, rs);
            logger.info("End createUser (UserVO, String)");
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            int errorCode = sqlexp.getErrorCode();
            if (errorCode == 1062) {
                throw new CSSCApplicationException (CSSC015E, "");
            }
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, rs);
        }
        return userVO;
    }
    
    public void generateLocPassword (Connection connection, UserVO userVO) throws CSSCSystemException {        
        logger.info ("Start validateLocPassword (Connection, USerVO)");
        PreparedStatement ps = null;
        ResultSet rs = null;        
        try {
            PasswordGenerator passwordGenerator = new PasswordGenerator ();
            String locationPwd = passwordGenerator.generate();
            StringBuffer queryBuf = new StringBuffer ("SELECT COUNT(1) FROM user_tbl where LOCATION_PWD = ?");
            logger.debug ("Query: " + queryBuf.toString());
            ps = connection.prepareStatement (queryBuf.toString ());
            queryBuf = null;
            ps.setString (1, locationPwd);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt (1);
                if (count == 1) {
                    DbConnection.close(null, ps, rs);
                    generateLocPassword(connection, userVO);
                }
            }
            DbConnection.close(null, ps, rs);
            userVO.setLocationPwd(locationPwd);
            logger.info ("End validateLocPassword (Connection, USerVO)");
         } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(null, ps, rs);
        }        
    }
    
    public GeneralistVO authenticateGeneralist (String locationPwd, String userName) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start authenticateGeneralist (String, String, String, String, String, String)");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT GEN.GENERALIST_ID GI, GEN.USER_ID UI, GEN.USER_TYPE_CD UTC " +
                    "FROM generalist_tbl GEN, user_tbl USR WHERE GEN.GENERALIST_ID = ? AND GEN.USER_TYPE_CD = 'SPEC' AND " +
                    "GEN.LOCATION_PWD = ? AND GEN.LOCATION_PWD = USR.LOCATION_PWD");
            connection = DbConnection.getConnection();
            
            ps = connection.prepareStatement(queryBuf.toString());
            logger.debug ("Query: " + queryBuf.toString());
            queryBuf = null;
            ps.setString (1, userName);
            ps.setString (2, locationPwd);
            
            rs = ps.executeQuery();
            GeneralistVO generalistVO = new GeneralistVO ();
            if (rs.next()) {
                String generalistid = rs.getString("GI");
                String userid = rs.getString ("UI");
                String usertypecd = rs.getString("UTC");
                
                generalistVO.setGeneralistid(generalistid);
                generalistVO.setUserid(userid);
                generalistVO.setUsertypecd(usertypecd);                
            }
            DbConnection.close(connection, ps, rs);
            logger.info ("Start authenticateGeneralist (String, String, String, String, String, String)");
            return generalistVO;
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
    
    public UserVO authenticateUser (UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start authenticateuser (UserVO)");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT USER_ID UI, USER_TYPE_CD UTC, STATUS STS, PHONE1 P1, PHONE2 P2, FAX FAX, MOBILE MOB, " +
                    "COMPANY COM, FIRST_NAME FN, LAST_NAME LN, ADDRESS1 ADD1, ADDRESS2 ADD2, CITY CT, STATE ST, COUNTRY CNT, ZIP_CODE ZC, IP_ADDRESS IA, " +
                    "REFERRAL_CD RLC, INDUSTRY_CD IC, REFERRED_CD RDC, CREATE_USER_ID_CD CUC, CREATE_DT CD, LAST_UPDATE_USER_ID_CD LUC, " +
                    "LAST_UPDATE_DT LUD, LOCATION_PWD LP, SETUP_FEE SF, RECURRING_FEE RF FROM user_tbl WHERE USER_ID = ? AND PASSWORD = ? AND " +
                    "USER_TYPE_CD = ?");
            connection = DbConnection.getConnection();
            logger.debug ("Query: " + queryBuf.toString());
            ps = connection.prepareStatement(queryBuf.toString());
            queryBuf = null;
            ps.setString(1, userVO.getUsername());
            ps.setString (2, userVO.getPassword());
            ps.setString(3, userVO.getUserType ());
            logger.debug("User name:" + userVO.getUsername());
            logger.debug("Password:" + userVO.getPassword());
            logger.debug("User Type:" + userVO.getUserType());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String userName = rs.getString("UI");
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
                userVO.setCompany(company);
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
                
                logger.debug("First Name: " + firstName);
            }
            DbConnection.close(connection, ps, rs);
            logger.info("End authenticateUser (UserVO)");
        } catch (DbConnectionException dbexp) {
            logger.error("Error Occured while extracting Database Conenction", dbexp);
            throw new CSSCSystemException (CSSC004E);
        } catch (SQLException sqlexp) {
            logger.error("Error occured while interacting with database", sqlexp);
            throw new CSSCSystemException (CSSC004E);
        } finally {
            DbConnection.close(connection, ps, rs);
        }
        logger.info ("End authenticateuser (UserVO)");
        return userVO;
    }
    
    public void saveUser (UserVO userVO, String userId) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start saveUser (UserVO, String)");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("UPDATE user_tbl " +
                    "SET PASSWORD = ?, STATUS = ?, PHONE1 = ?, FAX = ?, FIRST_NAME = ?, LAST_NAME = ?, ADDRESS1 = ?, ADDRESS2 = ?, " +
                    "CITY = ?, STATE = ?, COUNTRY = ?, ZIP_CODE = ?, LAST_UPDATE_USER_ID_CD = ?, LAST_UPDATE_DT = CURRENT_TIMESTAMP, LOCATION_PWD = ? " +
                    ", END_DT = ? WHERE USER_ID = ? AND USER_TYPE_CD = ? ");
            
            logger.debug ("Query: " + queryBuf.toString ());
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(queryBuf.toString ());
            queryBuf = null;
            ps.setString (1, userVO.getPassword());
            ps.setString (2, userVO.getStatus());
            ps.setString (3, userVO.getPhone1());
            ps.setString (4, userVO.getFax());
            ps.setString (5, userVO.getFirstName());
            ps.setString (6, userVO.getLastName());
            ps.setString (7, userVO.getAddress1());
            ps.setString (8, userVO.getAddress2());
            ps.setString (9, userVO.getCity());
            ps.setString (10, userVO.getState());
            ps.setString (11, userVO.getCountry());
            ps.setString (12, userVO.getZipcode());
            ps.setString (13, userId);
            ps.setString (14, userVO.getLocationPwd());
            ps.setDate(15, userVO.getEndDt());
            ps.setString (16, userVO.getUsername());
            ps.setString (17, userVO.getUserType());
            ps.executeUpdate();
            DbConnection.close(connection, ps, rs);
            logger.info("End createUser (UserVO, String)");
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
}