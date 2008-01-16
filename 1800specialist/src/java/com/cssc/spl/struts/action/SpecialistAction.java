/*
 * SpecialistAction.java
 */

package com.cssc.spl.struts.action;

import com.cssc.spl.bo.PaymentBO;
import com.cssc.spl.bo.SpecialistBO;
import com.cssc.spl.bo.UserBO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.struts.form.SpecialistForm;
import com.cssc.spl.util.CSSCUtil;
import com.cssc.spl.util.Constants;
import com.cssc.spl.util.CreditCardPayment;
import com.cssc.spl.vo.LocationVO;
import com.cssc.spl.vo.PaymentVO;
import com.cssc.spl.vo.UserVO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

/**
 *
 * @author Chander Singh
 * Created on November 13, 2007, 1:36 PM
 */
public class SpecialistAction extends Action {
    //Constants for defining Errors, Messages and warnings.
    private final String ERRORS = "errors";
    private final String MESSAGES = "messages";
    private final String WARNINGS = "warnings";
    
    //Constants defined for forward mapping results
    private final String SUCCESS = "success";
    private final String ERROR = "error";
    private final String NOACCESS = "noaccess";
    
    private Logger logger = null;
    
    private final String ACTV = "ACTIVE";
    
    private final String CHARGE = "CHARGE";
    private final String SPEC_SESSION = "SPEC";
    private final String STATE_DATA = "STATE_DATA";
    private final String COUNTRY_DATA = "COUNTRY_DATA";
    private final String CREDIT_CARD_TYPE_DATA = "CREDIT_CARD_TYPE_DATA";
    
    private final String CSSC005M = "messages.CSSC005M";
    private final String CSSC006E = "errors.CSSC006E";
    private final String CSSC007E = "errors.CSSC007E";
    private final String CSSC008W = "warnings.CSSC008W";
    private final String CSSC009E = "errors.CSSC009E";
    private final String CSSC010E = "errors.CSSC010E";
    
    private final String LIST_LOCATION_MAP = "/lspcloc";
    private final String ADD_LOCATION_MAP = "/aspcloc";
    private final String SAVE_LOCATION_MAP = "/sspcloc";
    private final String DELETE_LOCATION_MAP = "/dspcloc";
    private final String PAY_LOCATION_MAP = "/pspcloc";
    
    private final String SPEC_INFO_MAP = "/saccinfo";
    private final String SPEC_PROFILE_MAP = "/sprofile";
    private final String SPEC_SAVE_MAP = "/savsprofile";
        
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger(this.getClass());
        logger.info ("Start execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        ActionForward forward = null;
        SpecialistForm specialistForm = (SpecialistForm) form;
        ActionErrors errors = new ActionErrors ();
        ActionMessages messages = new ActionMessages ();
        try {
            String path = mapping.getPath();            
            HttpSession session = request.getSession();
            Object specProfileObj = session.getAttribute(SPEC_SESSION);            
            if (specProfileObj == null) {
                forward = mapping.findForward(NOACCESS);
                ActionMessage message = new ActionMessage (CSSC007E);
                messages.add (ERRORS, message);
                saveMessages(request, messages);
                return forward;
            }       
            UserVO userVO = (UserVO) specProfileObj;
            String firstName = userVO.getFirstName();
            String lastName = userVO.getLastName();
            specialistForm.setDisplayname(firstName + " " + lastName);
            specialistForm.setLogout(false);
            if (SPEC_INFO_MAP.equals(path)) {                
                messages = handleAccountInformation (specialistForm, request, userVO);
                forward = mapping.findForward(SUCCESS);
            }
            if (ADD_LOCATION_MAP.equals(path)) {
                LocationVO[] locationVOs = specialistForm.getLocationVOs();
                ArrayList locationVOAL = new ArrayList (Arrays.asList(locationVOs));
                locationVOAL.add (new LocationVO ());
                locationVOs = (LocationVO[]) locationVOAL.toArray(new LocationVO [locationVOAL.size()]);
                locationVOAL = null;
                specialistForm.setLocationVOs(locationVOs);
                forward = mapping.findForward(SUCCESS);
            }
            if (DELETE_LOCATION_MAP.equals (path)) {
                messages = handleDeleteLocations (specialistForm, request, userVO);
                forward = mapping.findForward(SUCCESS);
            }
            if (LIST_LOCATION_MAP.equals (path)) {
                specialistForm.setAmount(0);
                specialistForm.setCcNbr("");
                specialistForm.setSecCode("");
                specialistForm.setExpmon("");
                specialistForm.setExpyear("");
                messages = handleListLocations (specialistForm, request, userVO);
                forward = mapping.findForward(SUCCESS);
            }
            if (PAY_LOCATION_MAP.equals (path)) {
                errors = specialistForm.validate(mapping, request);
                if (errors.isEmpty()) {
                    messages = handlePayLocations (specialistForm, request, userVO);
                    specialistForm.setCcNbr("");
                    specialistForm.setSecCode ("");
                    forward = mapping.findForward(SUCCESS);
                } else {
                    Iterator errorIter = errors.get(ERRORS+".label.amtcharged");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        logger.debug ("Error Code: " + ERRORS+".label.amtcharged");
                        messages.add (ERRORS+".label.amtcharged", message);
                    }
                    errorIter = errors.get(ERRORS+".label.ccnbr");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        logger.debug ("Error Code: " + ERRORS+".label.ccnbr");
                        messages.add (ERRORS+".label.ccnbr", message);
                    }
                    errorIter = errors.get(ERRORS+".label.seccode");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.seccode", message);
                    }
                    forward = mapping.findForward(ERROR);
                }
            }
            if (SAVE_LOCATION_MAP.equals(path)) {
                errors = specialistForm.validate(mapping, request);
                if (errors.isEmpty()) {
                    messages = handleSaveLocations (specialistForm, request, userVO);
                    forward = mapping.findForward(SUCCESS);
                } else {
                    forward = mapping.findForward(ERROR);
                }
            }
            if (SPEC_SAVE_MAP.equals (path)) {
                errors = specialistForm.validate(mapping, request);
                if (errors.isEmpty()) {
                    messages = handleSaveSpecProfile (specialistForm, request, userVO);
                    forward = mapping.findForward(SUCCESS);
                } else {
                    Iterator errorIter = errors.get(ERRORS+".label.fname");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        logger.debug ("Error Code: " + ERRORS+".label.fname");
                        messages.add (ERRORS+".label.fname", message);
                    }
                    errorIter = errors.get(ERRORS+".label.lname");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        logger.debug ("Error Code: " + ERRORS+".label.lname");
                        messages.add (ERRORS+".label.lname", message);
                    }
                    errorIter = errors.get(ERRORS+".label.address1");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.address1", message);
                    }
                    errorIter = errors.get(ERRORS+".label.city");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.city", message);
                    }
                    errorIter = errors.get(ERRORS+".label.state");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.state", message);
                    }
                    errorIter = errors.get(ERRORS+".label.country");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.country", message);
                    }
                    errorIter = errors.get(ERRORS+".label.zipcode");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.zipcode", message);
                    }
                    errorIter = errors.get(ERRORS+".label.telephone");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.telephone", message);
                    }
                    errorIter = errors.get(ERRORS+".label.fax");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.fax", message);
                    }
                    forward = mapping.findForward(ERROR);
                }
            }
            if (SPEC_PROFILE_MAP.equals (path)) {
                specialistForm.setUserVO(userVO);
                forward = mapping.findForward(SUCCESS);
            }
        } catch (CSSCSystemException csscsexp) {
            logger.debug("Error Code: " + csscsexp.getMessage());
            messages.add(ERRORS, new ActionMessage (csscsexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        } catch (CSSCApplicationException csscaexp) {
            if (CSSC006E.equals (csscaexp.getErrorCode()) || CSSC009E.equals (csscaexp.getErrorCode())) {
                LocationVO[] locationVOs = specialistForm.getLocationVOs();
                String locationName = csscaexp.getMessage();
                LocationVO locationVO = new LocationVO ();
                locationVO.setLocationName(locationName);
                ArrayList locationVOAL = new ArrayList (Arrays.asList (locationVOs));
                int index = locationVOAL.indexOf(locationVO);
                if (index > -1) {
                    messages.add(ERRORS, new ActionMessage (csscaexp.getErrorCode(), (index + 1), csscaexp.getMessage()));
                }
            }
            forward = mapping.findForward(ERROR);
        }
        if (!errors.isEmpty()) {
            Iterator errorIter = errors.get(ERRORS);
            while (errorIter.hasNext()) {
                messages.add(ERRORS, (ActionMessage) errorIter.next());
            }
            forward = mapping.findForward (ERROR);
        }
        saveMessages(request, messages);
        logger.info ("End execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        return forward;
    }   
    
    private ActionMessages handleAccountInformation (SpecialistForm specialistForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleAccountInformation (SpecialistForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        SpecialistBO specialistBO = new SpecialistBO ();
        UserVO uservo = specialistBO.fetchAccountInformation (userVO);
        LocationVO[] locationVOs = uservo.getLocationVOs();
        
        double recurringFee = 0;
        int nbrOfLocs = 0;
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            if (ACTV.equals(locationVOs[cnt].getStatus())) {
                recurringFee += uservo.getRecurringFee();
                nbrOfLocs ++;
            }
            specialistForm.setRecurringFee (recurringFee);
        }
        specialistForm.setCompany(uservo.getCompany());
        specialistForm.setNbrOfLocations(nbrOfLocs);
        specialistForm.setStatus (uservo.getStatus());
        
        Date startDt = uservo.getStartDt();
        String startDtStr = CSSCUtil.convertDate(startDt, Constants.getProperty ("DATE_FORMAT"), "");
        
        Date endDt = uservo.getEndDt();
        String endDtStr = CSSCUtil.convertDate(endDt, Constants.getProperty ("DATE_FORMAT"), "");
        
        specialistForm.setStartDt (startDtStr);
        specialistForm.setExpDt (endDtStr);
        logger.info ("End handleAccountInformation (SpecialistForm, HttpServletRequest, UserVO)");
        return messages;
    }
    
    private ActionMessages handleDeleteLocations (SpecialistForm specialistForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleDeleteLocations (SpecialistForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        LocationVO[] locationVOs = specialistForm.getLocationVOs();
        ArrayList locationVOAL = new ArrayList (Arrays.asList(locationVOs));
        ArrayList deleteLocationVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < locationVOAL.size(); cnt++) {
            LocationVO locationVO = (LocationVO) locationVOAL.get (cnt);
            if (locationVO.isSelected()) {
                if (locationVO.getId() != -1) {
                    deleteLocationVOAL.add (locationVO);
                }
                locationVOAL.remove(cnt);
                cnt--;
            }
        }
        if (deleteLocationVOAL.isEmpty()) {
            locationVOs = (LocationVO[]) locationVOAL.toArray(new LocationVO [locationVOAL.size()]);
            locationVOAL = null;
        } else {
            LocationVO[] deleteLocationVOs = (LocationVO[]) deleteLocationVOAL.toArray(new LocationVO[deleteLocationVOAL.size()]);
            deleteLocationVOAL = null;
            SpecialistBO specialistBO = new SpecialistBO ();
            locationVOs = specialistBO.deleteLocationVOs(deleteLocationVOs, userVO);
        }
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            locationVOs[cnt].process ();
        }
        locationVOAL = new ArrayList (Arrays.asList(locationVOs));
        Collections.sort(locationVOAL);
        locationVOs = (LocationVO[])locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
        locationVOAL = null;
        
        boolean toBeCharged = false;
        double amount = 0;
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            if (!ACTV.equals(locationVOs[cnt].getStatus())) {
                toBeCharged = true;
                locationVOs[cnt].setSelected(true);
                amount += userVO.getRecurringFee();
                specialistForm.setRecurringFee(userVO.getRecurringFee());
            }
        }
        specialistForm.setStatus(userVO.getStatus());
        if (!ACTV.equals (userVO.getStatus ())) {
            amount += userVO.getSetupFee();
            specialistForm.setSetupFee(userVO.getSetupFee());
            toBeCharged = true;
        } else {
            specialistForm.setSetupFee(0);
        }
        specialistForm.setAmount(amount);
        if (toBeCharged) {
            specialistForm.setOperation(CHARGE);
        }        
        specialistForm.setLocationVOs(locationVOs);
        
        ActionMessage message = new ActionMessage (CSSC005M, "Delete");
        messages.add (MESSAGES, message);
        logger.info ("End handleDeleteLocations (SpecialistForm, HttpServletRequest, UserVO)");
        return messages;    
    }
    
    private ActionMessages handleListLocations (SpecialistForm specialistForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleListLocations (SpecialistForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        SpecialistBO specialistBO = new SpecialistBO ();
        LocationVO[] locationVOs = specialistBO.fetchLocationVOs(userVO);
        boolean toBeCharged = false;
        double amount = 0;
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            if (!ACTV.equals(locationVOs[cnt].getStatus())) {
                toBeCharged = true;
                locationVOs[cnt].setSelected(true);
                amount += userVO.getRecurringFee();
                specialistForm.setRecurringFee(userVO.getRecurringFee());
            }
        }
        specialistForm.setStatus(userVO.getStatus());
        if (!ACTV.equals (userVO.getStatus ())) {
            amount += userVO.getSetupFee();
            toBeCharged = true;
            specialistForm.setSetupFee(userVO.getSetupFee());
        }
        specialistForm.setAmount(amount);
        if (toBeCharged) {
            specialistForm.setOperation(CHARGE);
        } else {
            specialistForm.setOperation("");
            specialistForm.setStatus(userVO.getStatus());
            specialistForm.setAmount(0);
            specialistForm.setSetupFee(0);
            specialistForm.setRecurringFee(0);
        }
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            locationVOs[cnt].process ();
        }
        ArrayList locationVOAL = new ArrayList (Arrays.asList(locationVOs));
        Collections.sort(locationVOAL);
        locationVOs = (LocationVO[])locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
        locationVOAL = null;
        specialistForm.setLocationVOs(locationVOs);
        specialistForm.setLocationPwd(userVO.getLocationPwd());
        
        String stateData = Constants.getProperty(STATE_DATA);
        LabelValueBean[] stateBeans = CSSCUtil.getLabelValueBeans(stateData, ":", ";");
        specialistForm.setStateBeans (stateBeans);
                
        String countryData = Constants.getProperty(COUNTRY_DATA);
        LabelValueBean[] countryBeans = CSSCUtil.getLabelValueBeans(countryData, ":", ";");
        specialistForm.setCountryBeans (countryBeans);
        
        String ccTypeData = Constants.getProperty(CREDIT_CARD_TYPE_DATA);
        LabelValueBean[] ccTypeBeans = CSSCUtil.getLabelValueBeans(ccTypeData, ":", ";");
        specialistForm.setCcTypeBeans (ccTypeBeans);
        
        Calendar cal = Calendar.getInstance();
        int currentyear = cal.get(Calendar.YEAR);
        String[] years = new String[9];
        for (int cnt = 0; cnt < 9; cnt++) {
            years[cnt] = String.valueOf (currentyear + cnt);
        }
        specialistForm.setYears(years);
        
        if (locationVOs == null || locationVOs.length < 1) {
            ActionMessage message = new ActionMessage (CSSC008W);
            messages.add (WARNINGS, message);
        }
        logger.info ("End handleListLocations (SpecialistForm, HttpServletRequest, UserVO)");
        return messages;
    }
    
    private ActionMessages handlePayLocations (SpecialistForm specialistForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handlePayLocation (SpecialistForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        
        long invoiceNbr = Calendar.getInstance().getTimeInMillis();        
        double amount = specialistForm.getAmount();
        String ccNbr = specialistForm.getCcNbr();
        String expMon = specialistForm.getExpmon();
        String expYear = specialistForm.getExpyear();
        String secCode = specialistForm.getSecCode();
        
        String firstName = userVO.getFirstName();
        String lastName = userVO.getLastName();
        String address = userVO.getAddress1() + "\r\n" + userVO.getAddress2();
        String city = userVO.getCity();
        String state = userVO.getState();
        String zipCode = userVO.getZipcode();
        String country = userVO.getCountry();
        String email = userVO.getUsername();
        String phone = userVO.getPhone1();
        String fax = userVO.getPhone2();
        
        String loginId = Constants.getProperty("CC_LOGIN_ID");
        String transactionKey = Constants.getProperty ("CC_TRANSACTION_KEY");
        String versionNbr = Constants.getProperty("CC_VERSION_NBR");
        String connectionURL = Constants.getProperty("CC_URL");
        
        CreditCardPayment ccPayment = new CreditCardPayment (loginId, transactionKey, versionNbr, connectionURL);                
        String response = ccPayment.makePayment (invoiceNbr, amount, ccNbr, expMon, expYear, secCode, firstName, lastName, address, city, state, zipCode, country, email, phone, fax);
        response = response.replace('"', ' ');
        logger.debug("Response: " + response);
        String[] responseArr = response.split(",");
        String responseCd = responseArr [0].trim();
        String responseTxt = responseArr [3].trim();
        String approvalCd = responseArr [4].trim();
        String avsCd = responseArr [5].trim();
        String transactionId = responseArr [6].trim();
        
        int responseCode = Integer.parseInt(responseCd);
        if (responseCode != 1) {
            ActionMessage error = new ActionMessage (CSSC010E);
            messages.add (ERRORS, error);
        }
        
        LocationVO[] locationVOs = specialistForm.getLocationVOs();
        ArrayList saveLocationVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            if (locationVOs[cnt].isSelected()) {
                saveLocationVOAL.add (locationVOs[cnt]);
            }
        }
         if (!saveLocationVOAL.isEmpty() || !ACTV.equals(userVO.getStatus())) {
            LocationVO[] saveLocationVOs = (LocationVO[]) saveLocationVOAL.toArray(new LocationVO [saveLocationVOAL.size()]);
            saveLocationVOAL = null;
            
            PaymentVO[] paymentVOs = null;
            if (!ACTV.equals(userVO.getStatus())) {
                paymentVOs = new PaymentVO [saveLocationVOs.length + 1];
            } else {
                paymentVOs = new PaymentVO [saveLocationVOs.length];
            }
            for (int cnt = 0; cnt < saveLocationVOs.length; cnt++) {
                PaymentVO paymentVO = new PaymentVO ();
                paymentVO.setAmount(userVO.getRecurringFee());
                paymentVO.setApprovalCode(Integer.parseInt(approvalCd));
                paymentVO.setAvsCode(avsCd);
                paymentVO.setInvoiceNbr(String.valueOf(invoiceNbr));
                paymentVO.setLocationName(saveLocationVOs[cnt].getLocationName());
                paymentVO.setResponseCode(Integer.parseInt(responseCd));
                paymentVO.setResponseTxt(responseTxt);
                paymentVO.setTransactionId(Integer.parseInt(transactionId));
                paymentVO.setUserId(userVO.getUsername());
                paymentVOs[cnt] = paymentVO;
            }
            if (!ACTV.equals(userVO.getStatus())) {
                PaymentVO paymentVO = new PaymentVO ();
                paymentVO.setAmount(userVO.getSetupFee());
                paymentVO.setApprovalCode(Integer.parseInt(approvalCd));
                paymentVO.setAvsCode(avsCd);
                paymentVO.setInvoiceNbr(String.valueOf(invoiceNbr));
                paymentVO.setLocationName("SETUP");
                paymentVO.setResponseCode(Integer.parseInt(responseCd));
                paymentVO.setResponseTxt(responseTxt);
                paymentVO.setTransactionId(Integer.parseInt(transactionId));
                paymentVO.setUserId(userVO.getUsername());
                paymentVOs[saveLocationVOs.length] = paymentVO;
            }
            PaymentBO paymentBO = new PaymentBO ();
            String userId = userVO.getUsername();
            paymentBO.savePaymentDetails(paymentVOs, userId);
            if (responseCode == 1) {
                GregorianCalendar cal = new GregorianCalendar ();
                cal.set(Calendar.DATE, 1);
                cal.add (Calendar.MONTH, 1);
                long timeinmillis = cal.getTimeInMillis();        
                Date startDate = new Date (timeinmillis);
                cal.set(Calendar.DATE, 1);
                cal.add(Calendar.MONTH, 25);
                Date endDate = new Date (cal.getTimeInMillis());
                for (int cnt = 0; cnt < saveLocationVOs.length; cnt++) {
                    saveLocationVOs[cnt].setStatus(ACTV);
                    saveLocationVOs[cnt].setStartDate(startDate);
                    saveLocationVOs[cnt].setEndDate(endDate);
                }
                SpecialistBO specialistBO = new SpecialistBO ();
                locationVOs = specialistBO.saveLocationVOs(saveLocationVOs, userVO);
                if (!ACTV.equals(userVO.getStatus())) {
                    cal = new GregorianCalendar();
                    cal.setLenient(false);
                    timeinmillis = cal.getTimeInMillis();
                    startDate = new Date (timeinmillis);
                    cal.set(Calendar.DATE, 1);
                    cal.add(Calendar.MONTH, 24);
                    endDate = new Date (cal.getTimeInMillis());
                    logger.debug ("End Date: " + endDate);
                    UserBO userBO = new UserBO ();
                    userVO.setStatus(ACTV);
                    userVO.setStartDt(startDate);
                    userVO.setEndDt(endDate);
                    userVO = userBO.saveUser(userVO, userId);
                    HttpSession session = request.getSession();
                    session.setAttribute(userVO.getUserType(), userVO);
                }
                ArrayList locationVOAL = new ArrayList (Arrays.asList(locationVOs));
                Collections.sort(locationVOAL);
                locationVOs = (LocationVO[])locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
                locationVOAL = null;
                boolean toBeCharged = false;
                amount = 0;
                for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                    if (!ACTV.equals(locationVOs[cnt].getStatus())) {
                        toBeCharged = true;
                        locationVOs[cnt].setSelected(true);
                        amount += userVO.getRecurringFee();
                        specialistForm.setRecurringFee(userVO.getRecurringFee());
                    }
                }
                specialistForm.setStatus(userVO.getStatus());
                if (!ACTV.equals (userVO.getStatus ())) {
                    amount += userVO.getSetupFee();
                    toBeCharged = true;
                    specialistForm.setSetupFee(userVO.getSetupFee());
                }
                specialistForm.setAmount(amount);
                if (toBeCharged) {
                    specialistForm.setOperation(CHARGE);
                } else {
                    specialistForm.setOperation("");
                    specialistForm.setStatus(userVO.getStatus());
                    specialistForm.setAmount(0);
                    specialistForm.setSetupFee(0);
                    specialistForm.setRecurringFee(0);
                }
                specialistForm.setLocationVOs(locationVOs);
                ActionMessage message = new ActionMessage (CSSC005M, "Payment");
                messages.add (MESSAGES, message);
            }            
         }
        logger.info ("End handlePayLocations (SpecialistForm, HttpServletRequest, UserVO)");
        return messages;
    }
    
    private ActionMessages handleSaveLocations (SpecialistForm specialistForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleSaveLocations (SpecialistForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        LocationVO[] locationVOs = specialistForm.getLocationVOs();
        ArrayList saveLocationVOAL = new ArrayList (10);
        for (int cnt = 0; cnt < locationVOs.length; cnt++) {
            if (locationVOs[cnt].isSelected()) {
                saveLocationVOAL.add (locationVOs[cnt]);
            }
        }
        if (!saveLocationVOAL.isEmpty()) {
            LocationVO[] saveLocationVOs = (LocationVO[]) saveLocationVOAL.toArray(new LocationVO [saveLocationVOAL.size()]);
            saveLocationVOAL = null;
            SpecialistBO specialistBO = new SpecialistBO ();
            locationVOs = specialistBO.saveLocationVOs (saveLocationVOs, userVO);
            ArrayList locationVOAL = new ArrayList (Arrays.asList(locationVOs));
            locationVOs = (LocationVO[]) locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
            locationVOAL = null;
            
            boolean toBeCharged = false;
            double amount = 0;
            for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                if (!ACTV.equals(locationVOs[cnt].getStatus())) {
                    toBeCharged = true;
                    locationVOs[cnt].setSelected(true);
                    amount += userVO.getRecurringFee();
                    specialistForm.setRecurringFee(userVO.getRecurringFee());
                }
            }
            specialistForm.setStatus(userVO.getStatus());
            if (!ACTV.equals (userVO.getStatus ())) {
                amount += userVO.getSetupFee();
                specialistForm.setSetupFee(userVO.getSetupFee());
                toBeCharged = true;
            }
            specialistForm.setAmount(amount);           
            if (toBeCharged) {
                specialistForm.setOperation(CHARGE);
            } else {
                specialistForm.setOperation("");
                specialistForm.setStatus(userVO.getStatus());
                specialistForm.setAmount(0);
                specialistForm.setSetupFee(0);
                specialistForm.setRecurringFee(0);
            }
            locationVOAL = new ArrayList (Arrays.asList(locationVOs));
            Collections.sort(locationVOAL);
            locationVOs = (LocationVO[])locationVOAL.toArray(new LocationVO[locationVOAL.size()]);
            locationVOAL = null;
            for (int cnt = 0; cnt < locationVOs.length; cnt++) {
                locationVOs[cnt].process();
            }
            specialistForm.setLocationVOs(locationVOs);
            
            ActionMessage message = new ActionMessage (CSSC005M, "Save");
            messages.add (MESSAGES, message);
        }
        logger.info ("End handleSaveLocations (SpecialistForm, HttpServletRequest, userVO)");
        return messages;
    }
    
    private ActionMessages handleSaveSpecProfile (SpecialistForm specialistForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleSaveSpecProfile (SpecialistForm, HttpServletRequestm UserVO)");
        ActionMessages messages = new ActionMessages ();
        SpecialistBO specialistBO = new SpecialistBO ();
        UserVO uservo = specialistForm.getUserVO();
        userVO = specialistBO.saveSpecialistProfile (uservo, userVO.getUsername());
        ActionMessage message = new ActionMessage (CSSC005M, "Save");
        messages.add (MESSAGES, message);
        specialistForm.setUserVO(userVO);
        HttpSession session = request.getSession();
        session.setAttribute(userVO.getUserType(), userVO);
        logger.info ("End handleSaveSpecProfile (SpecialistForm, HttpServletRequestm UserVO)");
        return messages;
    }
}