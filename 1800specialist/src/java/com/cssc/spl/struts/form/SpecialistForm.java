/*
 * SpecialistForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.struts.form.common.CommonForm;
import com.cssc.spl.util.Diner;
import com.cssc.spl.util.EnRoute;
import com.cssc.spl.util.Validator;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.UserVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.CreditCardValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Chander Singh
 * Created on November 13, 2007, 2:30 PM
 */
public class SpecialistForm extends CommonForm {    
    private final String PAY_LOCATION_MAP = "/pspcloc";
    private final String SAVE_LOCATION_MAP = "/sspcloc";
    private final String SPEC_SAVE_MAP = "/savsprofile";
    
    private final String CSSC001E = "errors.CSSC001E";
    private final String CSSC002E = "errors.CSSC002E";
    
    private LocationVO[] locationVOs = new LocationVO[0];
    private String status = "";
    private String locationPwd = "";
    private double setupFee = 0.0;
    private double recurringFee = 0.0;
    
    private LabelValueBean[] stateBeans = new LabelValueBean [0];
    private LabelValueBean[] countryBeans = new LabelValueBean [0];    
    private LabelValueBean[] ccTypeBeans = new LabelValueBean [0];
    
    private String[] months = new String [] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] years = new String [0];
    
    private String cctype = "";
    private String ccNbr = "";
    private String secCode = "";
    private String expmon = "";
    private String expyear = "";
    private double amount = 0;    
    
    private String startDt = "";
    private String expDt = "";
    private int nbrOfLocations = 0;
    private int nbrOfDownloads = 0;
    private int nbrOfDownloadsLeft = 0;
    
    private UserVO userVO = new UserVO ();
    
    public LocationVO[] getLocationVOs() {
        return locationVOs;
    }

    public void setLocationVOs(LocationVO[] locationVOs) {
        this.locationVOs = locationVOs;
    }

    public String getLocationPwd() {
        return locationPwd;
    }

    public void setLocationPwd(String locationPwd) {
        this.locationPwd = locationPwd;
    }
    
    public LabelValueBean[] getStateBeans() {
        return stateBeans;
    }

    public void setStateBeans (LabelValueBean[] stateBeans) {
        this.stateBeans = stateBeans;
    }

    public LabelValueBean[] getCountryBeans () {
        return countryBeans;
    }

    public void setCountryBeans (LabelValueBean[] countryBeans) {
        this.countryBeans = countryBeans;
    } 

    public double getSetupFee() {
        return setupFee;
    }

    public void setSetupFee(double setupFee) {
        this.setupFee = setupFee;
    }

    public double getRecurringFee() {
        return recurringFee;
    }

    public void setRecurringFee(double recurringFee) {
        this.recurringFee = recurringFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LabelValueBean[] getCcTypeBeans() {
        return ccTypeBeans;
    }

    public void setCcTypeBeans(LabelValueBean[] ccTypeBeans) {
        this.ccTypeBeans = ccTypeBeans;
    }

    public String getCctype() {
        return cctype;
    }

    public void setCctype(String cctype) {
        this.cctype = cctype;
    }
    
    public String getExpyear() {
        return expyear;
    }

    public void setExpyear(String expyear) {
        this.expyear = expyear;
    }

    public String[] getMonths() {
        return months;
    }

    public void setMonths(String[] months) {
        this.months = months;
    }

    public String[] getYears() {
        return years;
    }

    public void setYears(String[] years) {
        this.years = years;
    }

    public String getCcNbr() {
        return ccNbr;
    }

    public void setCcNbr(String ccNbr) {
        this.ccNbr = ccNbr;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getExpmon() {
        return expmon;
    }

    public void setExpmon(String expmon) {
        this.expmon = expmon;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }    

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }    

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getExpDt() {
        return expDt;
    }

    public void setExpDt(String expDt) {
        this.expDt = expDt;
    }

    public int getNbrOfLocations() {
        return nbrOfLocations;
    }

    public void setNbrOfLocations(int nbrOfLocations) {
        this.nbrOfLocations = nbrOfLocations;
    }

    public int getNbrOfDownloads() {
        return nbrOfDownloads;
    }

    public void setNbrOfDownloads(int nbrOfDownloads) {
        this.nbrOfDownloads = nbrOfDownloads;
    }

    public int getNbrOfDownloadsLeft() {
        return nbrOfDownloadsLeft;
    }

    public void setNbrOfDownloadsLeft(int nbrOfDownloadsLeft) {
        this.nbrOfDownloadsLeft = nbrOfDownloadsLeft;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            locationVOs[cnt].setSelected(false);
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors ();
        String path = mapping.getPath();
        if (PAY_LOCATION_MAP.equals(path)) {
            if (amount == -1) {
                ActionMessage message = new ActionMessage (CSSC002E);
                errors.add (ERRORS + ".label.amtcharged", message);
            }
            if (Validator.isBlank(ccNbr)) {
                ActionMessage message = new ActionMessage (CSSC002E);
                errors.add (ERRORS + ".label.ccnbr", message);
            } else {
                CreditCardValidator creditCardValidator = new CreditCardValidator ();
                creditCardValidator.addAllowedCardType(new EnRoute ());
                creditCardValidator.addAllowedCardType(new Diner ());
                if (!creditCardValidator.isValid(ccNbr)) {
                    ActionMessage message = new ActionMessage (CSSC002E, "Credit Card");
                    errors.add (ERRORS, message);
                }               
            }
             if (Validator.isBlank(secCode)) {
                ActionMessage message = new ActionMessage (CSSC002E);
                errors.add (ERRORS + ".label.seccode", message);
            }
            Calendar calendar = Calendar.getInstance ();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            
            int date = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            
            int ccMonth = Integer.parseInt(expmon);
            int ccYear = Integer.parseInt(expyear);
            
            Calendar ccCal = Calendar.getInstance();
            ccCal.set(Calendar.DATE, date);
            ccCal.set(Calendar.MONTH, ccMonth - 1);
            ccCal.set(Calendar.YEAR, ccYear);
            
            ccCal.set(Calendar.HOUR_OF_DAY, 0);
            ccCal.set(Calendar.MINUTE, 0);
            ccCal.set(Calendar.SECOND, 0);
            ccCal.set(Calendar.MILLISECOND, 0);
            if (ccCal.before(calendar)) {
                ActionMessage message = new ActionMessage (CSSC001E, "Expiry Date");
                errors.add (ERRORS, message);
            }
        }
        if (SAVE_LOCATION_MAP.equals (path)) {
            for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                if (locationVOs[cnt].isSelected()) {
                    String locationName = locationVOs[cnt].getLocationName();
                    if (Validator.isBlank(locationName)) {
                        ActionMessage message = new ActionMessage (CSSC002E, "Row #: " + (cnt+1), "Name");
                        errors.add(ERRORS, message);
                    }
                    String address1 = locationVOs[cnt].getAddress1();
                    if (Validator.isBlank(address1)) {
                        ActionMessage message = new ActionMessage (CSSC002E, "Row #: " + (cnt+1), "Address 1");
                        errors.add(ERRORS, message);
                    }
                    String city = locationVOs[cnt].getCity();
                    if (Validator.isBlank(city)) {
                        ActionMessage message = new ActionMessage (CSSC002E, "Row #: " + (cnt+1), "City");
                        errors.add(ERRORS, message);
                    }
                    String state = locationVOs[cnt].getState();
                    if (Validator.isBlank(state)) {
                        ActionMessage message = new ActionMessage (CSSC002E, "Row #: " + (cnt+1), "State");
                        errors.add(ERRORS, message);
                    }
                    String country = locationVOs[cnt].getCountry();
                    if (Validator.isBlank(country)) {
                        ActionMessage message = new ActionMessage (CSSC002E, "Row #: " + (cnt+1), "Country");
                        errors.add(ERRORS, message);
                    }
                    String zipcode = locationVOs[cnt].getZipCode();
                    if (Validator.isBlank(zipcode)) {
                        ActionMessage message = new ActionMessage (CSSC002E, "Row #: " + (cnt+1), "Zip Code");
                        errors.add(ERRORS, message);
                    }
                    String telephone = locationVOs[cnt].getPhone1();
                    if (Validator.isBlank(telephone)) {
                        ActionMessage message = new ActionMessage (CSSC002E, "Row #: " + (cnt+1), "Telephone");
                        errors.add(ERRORS, message);
                    }
                }
            }
        }
        if (SPEC_SAVE_MAP.equals (path)) {
            String firstName = userVO.getFirstName();
            if (firstName == null || "".equals (firstName.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.fname", errorMessage);
            }
            
            String lastName = userVO.getLastName ();
            if (lastName == null || "".equals (lastName.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.lname", errorMessage);
            }
            
            String address1 = userVO.getAddress1();
            if (address1 == null || "".equals (address1.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.address1", errorMessage);
            }
            String city = userVO.getCity();
            if (city == null || "".equals (city.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.city", errorMessage);
            }
            String state = userVO.getState();
            if (state == null || "".equals (state.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.state", errorMessage);
            }
            String country = userVO.getCountry();
            if (country == null || "".equals (country.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.country", errorMessage);
            }
            String zipcode = userVO.getZipcode();
            if (zipcode == null || "".equals (zipcode.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.zipcode", errorMessage);
            }
            String phoneNbr = userVO.getPhone1();
            if (phoneNbr == null || "".equals (phoneNbr.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS + ".label.telephone", errorMessage);
            }
            String faxNbr = userVO.getFax();
            if (faxNbr == null || "".equals (faxNbr.trim())) {
                ActionMessage errorMessage = new ActionMessage ("errors.CSSC002E");
                errors.add (ERRORS+".label.fax", errorMessage);
            }
        }
        return errors;
    }
}
