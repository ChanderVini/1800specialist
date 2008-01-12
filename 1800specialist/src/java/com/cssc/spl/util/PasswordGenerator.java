/* GPW - Generate pronounceable passwords
   This program uses statistics on the frequency of three-letter sequences
   in English to generate passwords.  The statistics are
   generated from your dictionary by the program loadtris.

   See www.multicians.org/thvv/gpw.html for history and info.
   Tom Van Vleck

   THVV 06/01/94 Coded
   THVV 04/14/96 converted to Java
   THVV 07/30/97 fixed for Netscape 4.0
   */
/*
###############################################################################
###############################################################################
##
## PROPRIETARY AND CONFIDENTIAL
## 
## 1-800-Specialist is Copyright (C) 2007
## by Cambridge Software Solutions Corp.
## 
## All Rights Reserved.
## Written by Boris Katok
##
## $Id: Util.java,v 1.2 2006/03/31 07:13:00 bort Exp $
## 
###############################################################################
###############################################################################
*/
package com.cssc.spl.util;

import java.util.*;


public class PasswordGenerator {
    public double getRandomNum(int lbound, int ubound) {
        return (Math.floor(Math.random() * (ubound - lbound)) + lbound);
    }
    
    public char getRandomChar(boolean number, boolean lower, boolean upper, boolean other, String extra) {
        String numberChars = "0123456789";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String otherChars = "`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/? ";
        String charSet = extra;
        if (number == true)
            charSet += numberChars;
        if (lower == true)
            charSet += lowerChars;
        if (upper == true)
            charSet += upperChars;
        if (other == true)
            charSet += otherChars;
        return charSet.charAt((int)getRandomNum(0, charSet.length ()));
    }
    
    public String generate () {
        int length = 8;
        String extraChars = "";
        boolean firstNumber = true;
        boolean firstLower = false;
        boolean firstUpper = true;
        boolean firstOther = false;
        boolean latterNumber = true;
        boolean latterLower = true;
        boolean latterUpper = true;
        boolean latterOther = false;
        String rc = "";
        if (length > 0)
            rc = rc + getRandomChar(firstNumber, firstLower, firstUpper, firstOther, extraChars);
        for (int idx = 1; idx < length; ++idx) {
            rc = rc + getRandomChar(latterNumber, latterLower, latterUpper, latterOther, extraChars);
        }
        return rc;
    }
}
