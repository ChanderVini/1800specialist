/*
 * RegisterForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.struts.form.common.CommonForm;
import com.cssc.spl.util.Diner;
import com.cssc.spl.util.EnRoute;
import com.cssc.spl.util.Validator;
import com.cssc.spl.vo.UserVO;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.CreditCardValidator;
import org.apache.commons.validator.EmailValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Chander Singh
 * Created on November 6, 2007, 10:52 AM
 */
public class RegisterForm extends CommonForm {
    private UserVO userVO = new UserVO ();
    
    private final String REGISTER_GEN_SUBMIT_MAP = "/genregsub";
    private final String REGISTER_SPEC_SUBMIT_MAP = "/spcregsub";
    
    private String repassword = "";
    private String promotionalCd = "";
    private String hearus = "";
    
    private boolean agree = false;
    private boolean charge = true;
    
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
    private double setupFee = 0.0;
    
    private final String CSSC001E = "errors.CSSC001E";
    private final String CSSC002E = "errors.CSSC002E";
    private final String CSSC003E = "errors.CSSC003E";
    private final String CSSC014E = "errors.CSSC014E";
    
    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }
    
    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getPromotionalCd() {
        return promotionalCd;
    }

    public void setPromotionalCd(String promotionalCd) {
        this.promotionalCd = promotionalCd;
    }
    
    public String getHearus() {
        return hearus;
    }

    public void setHearus(String hearus) {
        this.hearus = hearus;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }   
    
    public LabelValueBean[] getStateBeans() {
        return stateBeans;
    }

    public void setStateBeans(LabelValueBean[] stateBeans) {
        this.stateBeans = stateBeans;
    }

    public LabelValueBean[] getCountryBeans() {
        return countryBeans;
    }

    public void setCountryBeans(LabelValueBean[] countryBeans) {
        this.countryBeans = countryBeans;
    }

    public LabelValueBean[] getCcTypeBeans() {
        return ccTypeBeans;
    }

    public void setCcTypeBeans(LabelValueBean[] ccTypeBeans) {
        this.ccTypeBeans = ccTypeBeans;
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

    public String getCctype() {
        return cctype;
    }

    public void setCctype(String cctype) {
        this.cctype = cctype;
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

    public String getExpyear() {
        return expyear;
    }

    public void setExpyear(String expyear) {
        this.expyear = expyear;
    }

    public double getSetupFee() {
        return setupFee;
    }

    public void setSetupFee(double setupFee) {
        this.setupFee = setupFee;
    }
    
    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors ();
        
        String path = mapping.getPath();
        if (REGISTER_GEN_SUBMIT_MAP.equals(path)) {
            if (Validator.isBlank(userVO.getLocation())) {
                ActionMessage errorMessage = new ActionMessage (CSSC002E);
                errors.add (ERRORS+".label.location", errorMessage);
            }
            if (Validator.isBlank(userVO.getLocationPwd())) {
                ActionMessage errorMessage = new ActionMessage (CSSC002E);
                errors.add (ERRORS+".label.locpwd", errorMessage);
            }
            if (Validator.isBlank(userVO.getReferredCd())) {
                ActionMessage errorMessage = new ActionMessage (CSSC002E);
                errors.add (ERRORS+".label.specialist", errorMessage);
            }
            if (Validator.isBlank(userVO.getCompany())) {
                ActionMessage errorMessage = new ActionMessage (CSSC002E);
                errors.add (ERRORS+".label.company", errorMessage);
            }            
            validate (errors);                        
        }
        
        if (REGISTER_SPEC_SUBMIT_MAP.equals (path)) {
            validate (errors);
            if (!agree) {
                ActionMessage errorMessage = new ActionMessage (CSSC002E);
                errors.add (ERRORS+".label.terms", errorMessage);
            }
            if (!charge) {
                return errors;
            }
            boolean isblank = false;
            if (setupFee <= 0.0) {
                ActionMessage message = new ActionMessage (CSSC002E, "Setup Fee");
                errors.add (ERRORS + ".label.amtcharged", message);
                isblank = true;
            } 
            if (Validator.isBlank(ccNbr)) {
                ActionMessage message = new ActionMessage (CSSC002E, "Credit Card Nbr");
                errors.add (ERRORS + ".label.ccnbr", message);
                isblank = true;
            } 
            if (Validator.isBlank(secCode)) {
                ActionMessage message = new ActionMessage (CSSC002E, "Security Code");
                errors.add (ERRORS + ".label.seccode", message);
                isblank = true;
            } 
             if (!isblank) {
                CreditCardValidator creditCardValidator = new CreditCardValidator ();
                creditCardValidator.addAllowedCardType(new EnRoute ());
                creditCardValidator.addAllowedCardType(new Diner ());
                if (!creditCardValidator.isValid(ccNbr)) {
                    ActionMessage message = new ActionMessage (CSSC001E, "Credit Card");
                    errors.add (ERRORS, message);
                }
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
        return errors;
    }

    private void validate (ActionErrors errors) {
        String firstName = userVO.getFirstName();
        if (Validator.isBlank (firstName)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.fname", errorMessage);
        }

        String lastName = userVO.getLastName ();
        if (Validator.isBlank (lastName)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.lname", errorMessage);
        }

        String address1 = userVO.getAddress1();
        if (Validator.isBlank(address1)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.address1", errorMessage);
        }
        String city = userVO.getCity();
        if (Validator.isBlank (city)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.city", errorMessage);
        }
        String state = userVO.getState();
        if (Validator.isBlank (state)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.state", errorMessage);
        }
        String country = userVO.getCountry();
        if (Validator.isBlank (country)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.country", errorMessage);
        }
        String zipcode = userVO.getZipcode();
        if (Validator.isBlank (zipcode)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.zipcode", errorMessage);
        }
        String phoneNbr = userVO.getPhone1();
        if (Validator.isBlank (phoneNbr)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS + ".label.telephone", errorMessage);
        }
        String faxNbr = userVO.getFax();
        if (Validator.isBlank (faxNbr)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS+".label.fax", errorMessage);
        }
        String email = userVO.getUsername();
        if (Validator.isBlank (email)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS+".label.email", errorMessage);
        } else {
            EmailValidator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(email)) {
                ActionMessage errorMessage = new ActionMessage (CSSC001E, "");
                errors.add (ERRORS + ".label.email", errorMessage);
            }
        }
        String password = userVO.getPassword();
        if (Validator.isBlank (password)) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS+".label.password", errorMessage);
        } else if (password.length() < 6) {
            ActionMessage errorMessage = new ActionMessage (CSSC014E, "Password", "6");
            errors.add (ERRORS+".label.password", errorMessage);
        } else if (!password.equals(repassword)) {
            ActionMessage errorMessage = new ActionMessage (CSSC003E, "Password", "Retype Password");
            errors.add (ERRORS+".label.repassword", errorMessage);
        } 
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        userVO = new UserVO ();
        repassword = "";
        promotionalCd = "";                
        hearus = "";
        agree = false;
        charge = false;
    }
}