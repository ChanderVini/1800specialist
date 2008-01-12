/*
 * Diner.java
 */

package com.cssc.spl.util;

import org.apache.commons.validator.CreditCardValidator;

/**
 *
 * @author Chander Singh
 * Created on November 16, 2007, 11:04 PM
 */
public class Diner implements CreditCardValidator.CreditCardType {
    public boolean matches(String card) {
        String digit2 = card.substring (0, 2);
        String digit3 = card.substring (0, 3);        
        return ((digit2.equals("36") || digit2.equals("38") || (digit3.compareTo("300")>=0 && digit3.compareTo("305")<=0)) && card.length () == 14);
    }    
}