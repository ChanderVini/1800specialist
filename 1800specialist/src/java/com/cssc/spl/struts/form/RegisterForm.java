/*
 * RegisterForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.util.Diner;
import com.cssc.spl.util.EnRoute;
import com.cssc.spl.util.Validator;
import com.cssc.spl.vo.UserVO;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.CreditCardValidator;
import org.apache.commons.validator.EmailValidator;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Chander Singh
 * Created on November 6, 2007, 10:52 AM
 */
public class RegisterForm extends ActionForm {
    private UserVO userVO = new UserVO ();
    
    private final String REGISTER_GEN_SUBMIT_MAP = "/genregsub";
    private final String REGISTER_SPEC_SUBMIT_MAP = "/spcregsub";
    
    private String repassword = "";
    private String promotionalCd = "";
    private String hearus = "";
    
    private boolean agree = false;
    
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
    
    //Constant defined for Key to be associated with Messages to be displayed to the user on the screen.
    protected final String MESSAGES = "messages";
    //Constant defined for Key to be associated with Errors to be displayed to the user on the screen.
    protected final String ERRORS = "errors";
    //Constant defined for Key to be associated with Warnings to be displayed to the user on the screen.	
    protected final String WARNINGS = "warnings";
    
    //Properties used for Pagination if required on the screen.
    //Current Page Number
    private String pageNumber;
    //Total Number of Pages records are devided into.
    private String totalPages;
    //Total Number of records as per search.

    private String totalRecords;
    //First Record number being displayed on the Current Page
    private String pageFirstRecordNbr;
    //Last Record number being displayed on the Current Page    
    private String pageLastRecordNbr;
    
    //Operation request to be performed.
    private String operation;
    //Last Operation requested by the user.
    private String lastOperation;

    //Properties for Sorting Operation.
    //Order in which Sorting needs to be performed.
    private String sortorder = "A";
    //Column to be sorted.
    private String sortcolumn = "";    

    private boolean logout = false;
    
    private String displayname = "";
    
    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getPageFirstRecordNbr() {
        return pageFirstRecordNbr;
    }

    public void setPageFirstRecordNbr(String pageFirstRecordNbr) {
        this.pageFirstRecordNbr = pageFirstRecordNbr;
    }

    public String getPageLastRecordNbr() {
        return pageLastRecordNbr;
    }

    public void setPageLastRecordNbr(String pageLastRecordNbr) {
        this.pageLastRecordNbr = pageLastRecordNbr;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation = lastOperation;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getSortcolumn() {
        return sortcolumn;
    }

    public void setSortcolumn(String sortcolumn) {
        this.sortcolumn = sortcolumn;
    }    

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
    
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
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors ();
        
        String path = mapping.getPath();
        if (REGISTER_GEN_SUBMIT_MAP.equals(path)) {
            if (Validator.isBlank(userVO.getLocationPwd())) {
                ActionMessage errorMessage = new ActionMessage (CSSC002E);
                errors.add (ERRORS+".label.locpwd", errorMessage);
            }
            validate (errors);                        
        }
        
        if (REGISTER_SPEC_SUBMIT_MAP.equals (path)) {
            validate (errors);
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
            if (!agree) {
                ActionMessage errorMessage = new ActionMessage (CSSC002E);
                errors.add (ERRORS+".label.terms", errorMessage);
            }
            if (setupFee <= 0.0) {
                return errors;
            }
            boolean isblank = false;
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

        if (Validator.isBlank(userVO.getCompany())) {
            ActionMessage errorMessage = new ActionMessage (CSSC002E);
            errors.add (ERRORS+".label.company", errorMessage);
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
        
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        userVO = new UserVO ();
        repassword = "";
        promotionalCd = "";                
        hearus = "";
        agree = false;
    }
}