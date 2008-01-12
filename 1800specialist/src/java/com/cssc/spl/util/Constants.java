/*
 * Constants.java
 */

package com.cssc.spl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 4:31 PM
 */
public class Constants {
    private static HashMap propMap = new HashMap (10);
    private static Logger logger;

    /** Creates a new instance of Constants */
    public Constants (String propertyFile) {
        logger = Logger.getLogger ("Constants");
        logger.info ("Start Constructor");
        logger.debug ("Property File Name: " + propertyFile);
        try {
            InputStream is = this.getClass ().getResourceAsStream (propertyFile);
            logger.debug (is);
            Properties prop = new Properties ();
            prop.load (is);
            Enumeration propNameEnum = prop.propertyNames ();
            while (propNameEnum.hasMoreElements ()) {
                String propName = (String) propNameEnum.nextElement ();
                String propValue = prop.getProperty (propName);
                logger.debug (propName + "=" + propValue);
                propMap.put (propName, propValue);
            }
        } catch (IOException ioexp) {
            logger.error ("IO Error in Constructor", ioexp);
        }
        logger.info ("End Constructor");
    }

    public static String getProperty (String propName) {
        String propValue = null;
        Object propValueObj = propMap.get (propName);
        if (propValueObj != null) {
            propValue = (String) propValueObj;
        }
        return propValue;
    }

    public static void setProperty (String propName, String propValue) {
        Object propValueObj = propMap.get (propName);
        if (propValueObj != null) {
            propMap.put (propName, propValue);
        }
    }

    public static void unloadResources () {
        logger.info ("Start Unloading Resources in Constants");
        propMap = null;
        logger.info ("End Unloading of Resources in Constants");
        logger = null;
    }
}
