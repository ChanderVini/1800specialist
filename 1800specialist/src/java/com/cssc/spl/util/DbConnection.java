/*
 * DbConnection.java
 */

package com.cssc.spl.util;

import com.cssc.spl.exception.DbConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:15 PM
 */
public class DbConnection {
    private static final String SERVER = "localhost";
    private static final String PORT = "3306";
    private static final String DBNAME = "specialist";
    private static final String DBUSERNAME = "dbuser";
    private static final String DBPASSWORD = "rnymance";
            
    private static final String URL = "jdbc:mysql://";
    private static final String COLON = ":";
    private static final String SEPERATOR = "/";
        
    /** Creates a new instance of DbConnection */
    public DbConnection() {
    }
    
    public static Connection getConnection () throws DbConnectionException {
        Logger logger = Logger.getLogger(DbConnection.class);
        logger.info ("Start getConnection ()");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String server = Constants.getProperty("DB_SERVER");
            String port = Constants.getProperty("DB_PORT");
            String dbname = Constants.getProperty("DB_NAME");
            String username = Constants.getProperty("DB_USERNAME");
            String password = Constants.getProperty("DB_PASSWORD");
            StringBuffer dburl = new StringBuffer (URL);
            if (server == null || "".equals (server)) {
                server = SERVER;
            } 
            dburl.append(server);
            dburl.append (COLON);
            if (port == null || "".equals (port)) {
                port = PORT;
            }
            dburl.append (port);
            dburl.append (SEPERATOR);
            if (dbname == null || "".equals (dbname)) {
                dbname = DBNAME;
            }
            dburl.append (dbname);
            String url = dburl.toString ();    
            
            if (username == null || "".equals (username)) {
                username = DBUSERNAME;
            }
            
            if (password == null || "".equals (password)) {
                password = DBPASSWORD;
            }
            Connection connection = DriverManager.getConnection(url, username, password);
            logger.info ("End getConnection ()");
            return connection;
        } catch (SQLException sqlexp) {
            logger.error(sqlexp.getMessage(), sqlexp);
            throw new DbConnectionException ("Failed to get Database Connection.");
        } catch (ClassNotFoundException cnfexp) {
            logger.error(cnfexp.getMessage(), cnfexp);
            throw new DbConnectionException ("Database Operation failed.");
        }
    }
    
    public static void close (Connection connection, Statement stmt, ResultSet rs) {
       try {
           if (rs != null) {
               rs.close ();
           }
       } catch (SQLException sqlexp) {
           
       }
       
       try {
           if (stmt != null) {
               stmt.close ();
           }
       } catch (SQLException sqlexp) {
           
       }     
       
       try {
           if (connection != null) {
               if (!connection.isClosed ()) {
                   connection.close ();
               }
           }
       } catch (SQLException sqlexp) {
           
       }
    }    
    
    public static void main (String[] args) throws Exception {
         try {
            Class.forName("org.gjt.mm.mysql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://app.1800specialist.com:3306/1800specialist", "specialist", "Rnymance1");
            String query = "select * from user_tbl";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println (rs.getString("USER_ID"));
            }
            rs.close ();
            ps.close();
            connection.close ();
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        } catch (ClassNotFoundException cnfexp) {
            cnfexp.printStackTrace();
        }
        
    }
}