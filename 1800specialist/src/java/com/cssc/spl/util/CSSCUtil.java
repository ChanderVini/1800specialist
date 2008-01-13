/*
 * CSSCUtil.java
 */

package com.cssc.spl.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Chander Singh
 * Created on November 11, 2007, 9:45 PM
 */
public class CSSCUtil {
    	//Property for Formatting Decimal (double) properties to String. 
    private static DecimalFormat dblFmt = new DecimalFormat ();
    //Property for Formatting Integer (int) properties to String.
    private static NumberFormat intFmt = NumberFormat.getIntegerInstance();
    //Property for Formatting Decimal (double) properties to String with Percent format.
    private static DecimalFormat pctFmt = new DecimalFormat ();
    
    //Static section where Maximum Fraction digit a format can format is defined.
    static {
        dblFmt.setMinimumFractionDigits(2);
        dblFmt.setMaximumFractionDigits(2);
        intFmt.setMaximumFractionDigits(0);
    }
    
    public static java.sql.Date parseDate (String data, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat (pattern);
            Date date = dateFormat.parse(data);
            long time = date.getTime();
            java.sql.Date sqldate = new java.sql.Date (time);
            return sqldate;
        } catch (ParseException pexp) {
            return null;
        }
        
    }
    
    /**
     * Static utility method for parsing a String to a int value.
     * @param data - String, data to be parsed.
     * @return - int, Converted int value.
     */
    public static int parseInt (String data) {
    	//Default int value to be returned incase of any error or if data is null or blank.
    	int convertedData = -1;
    	//Data is null or blank.
    	if (data == null || "".equals(data.trim())) {
    	    return convertedData;
    	}
    	//Parse the String.
    	try {
    		//Extract Number
    	    Number number = intFmt.parse(data);
    	    //get its Int value.
    		convertedData = number.intValue();
    	} catch (ParseException pexp) {
    	}
    	return convertedData;
    }
    
    /**
     * Static utility method for parsing a String to double value.
     * @param data - String, data to be parsed.
     * @return - double, parsed double value.
     */
    public static double parseDouble (String data) {
        //Default double value to be returned incase of any error or if data is null or blank.
        double convertedData = -1;
        //Data is null or blank.
    	if (data == null || "".equals(data.trim())) {
    	    return convertedData;
    	}
    	//Parse the data.
    	try {
        	//Extract Number
            Number number = dblFmt.parse(data);
            //Get its Double value.
    		convertedData = number.doubleValue();
    	} catch (ParseException pexp) {
    	}    	
    	return convertedData;
    }
    
    /**
     * Static utility method parsing Array if String to corresponding double values.
     * The method utilizes existing parseDouble method for parsing each String data. 
     * @param datas - String[], array of data to be parsed.
     * @return - double[], array of parsed double value.
     */
    public static double[] parseDouble (String[] datas) {
    	//Create blank array of double values of size of String array to be parsed.
        double[] convertedDatas = new double[datas.length];
        //Loop through all String data and get parsed double value by calling parseDouble utility method.
    	for (int cnt = 0; cnt < datas.length; cnt++) {
    		convertedDatas[cnt] = parseDouble(datas[cnt]);
    	}
    	return convertedDatas;
    }
    
    /**
     * Static utility method parsing Array of String to corresponding int values.
     * The method utilizes existing parseInt method for parsing each String data. 
     * @param datas - String[], array of data to be parsed.
     * @return - int[], Array of parsed int values.
     */
    public static int[] parseInt (String[] datas) {
    	//Create blank array of int values of size of String array to be parsed.    	
    	int[] convertedDatas = new int[datas.length];
    	//Loop through all String data and get parsed double value by calling parseInt utility method.
    	for (int cnt = 0; cnt < datas.length; cnt++) {
    		convertedDatas[cnt] = parseInt(datas[cnt]);
    	}
    	return convertedDatas;
    }
    
    /**
     * Static utility method for formatting an int value to String.
     * @param data - int, data to be formatted.
     * @return - String, formatted String value.
     */
    public static String formatInt (int data) {
    	//If data is less than 0 then return blank
        if (data < 0) {
            return "";
        } else {
            return intFmt.format(data);
        }
    }
    
    /**
     * Static utility method for formatting array of int values to String array.
     * The method calls formatInt method to format the int value.
     * @param datas - int[], array of int to be formatted.
     * @return - String[], Array of formatted String values.
     */
    public static String[] formatInt (int[] datas) {
    	//Create String array of size of int array.
        String[] convertedDatas = new String[datas.length];
        //Loop through the data and call existing getInt method for formatting.
    	for (int cnt = 0; cnt < datas.length; cnt++) {
    		convertedDatas[cnt] = formatInt(datas[cnt]);
    	}
    	return convertedDatas;
    }
    
    /**
     * Static utility method for formatting double value to String.
     * @param data - double, data to be formatted.
     * @return - String, Formatted String value.
     */
    public static String formatDouble (double data) {
    	//If data is less than 0 then return blank.
        if (data < 0) {
            return "";
        } else {
            return dblFmt.format(data);
        }
    }
    
    /**
     * Static utility method for formatting double value to String.
     * @param data - double, data to be formatted.
     * @param maxFrcDig - int, maximum decimal place.
     * @return - String, Formatted String value.
     */
    public static String formatDouble (double data, int maxFrcDig) {
    	//If data is less than 0 then return blank.
        if (data < 0) {
            return "";
        } else {
        	dblFmt.setMaximumFractionDigits(maxFrcDig);
            String value = dblFmt.format(data);
            dblFmt.setMaximumFractionDigits(2);
            return value;
        }
    }
    
    /**
     * Static utility method for formatting array of double values to String array.
     * The method calls formatDouble method to format the double value.
     * @param datas - double[], array of double to be parsed.
     * @return - String[], Formatted String array.
     */
    public static String[] formatDouble (double[] datas) {
    	//Create String array of size of double array.
        String[] convertedDatas = new String[datas.length];
        //Loop through the double values and call formatDouble method to get formatted String
    	for (int cnt = 0; cnt < datas.length; cnt++) {
    		convertedDatas[cnt] = formatDouble(datas[cnt]);
    	}
    	return convertedDatas;
    }
    
    /**
     * Static utility method for formatting array of double values to String array.
     * The method calls formatDouble method to format the double value.
     * @param datas - double[], array of double to be parsed.
     * @param maxFrcDig - int, maximum decimal place.
     * @return - String[], Formatted String array.
     */
    public static String[] formatDouble (double[] datas, int maxFrcDig) {
    	//Create String array of size of double array.
        String[] convertedDatas = new String[datas.length];
        //Loop through the double values and call formatDouble method to get formatted String
    	for (int cnt = 0; cnt < datas.length; cnt++) {
    		convertedDatas[cnt] = formatDouble(datas[cnt], maxFrcDig);
    	}
    	return convertedDatas;
    }
    
    /**
     * Static Utility method formats Date object to String as per format passed as paramater.
     * @param date - Date, Date which needs to be formatted.
     * @param format - String, Format in which date needs to be formatted.
     * @param replace - String, Incase of any error or date is not present to be returned.
     * @return - String, Formatted date object in String.
     */
    public static String convertDate(Date date, String format, String replace) {
        //If Date is not null
        if(date != null) {
        	//Create SimpleDateFormat object with Format
            SimpleDateFormat dateFmt = new SimpleDateFormat(format);
            //Make sure it is not lenient so that proper date is returned else it will format it by adding or removing date. 
            dateFmt.setLenient(false);
            //Do formatting.
            replace = dateFmt.format(date);
        }
        return replace;
    }
    
    public static LabelValueBean[] getLabelValueBeans (String data, String delimiter1, String delimiter2) {
        LabelValueBean[] labelvalueBeans = new LabelValueBean [0];
        if (data != null && !"".equals(data)) {
            String[] splitData = data.split(delimiter2);
            labelvalueBeans = new LabelValueBean [splitData.length];
            for (int cnt = 0; cnt < splitData.length; cnt++) {
                String[] arrayData = splitData[cnt].split(delimiter1);
                LabelValueBean labelValueBean = new LabelValueBean (arrayData[1], arrayData[0]);
                labelvalueBeans[cnt] = labelValueBean;
            }
        }
        return labelvalueBeans;
    }
}
