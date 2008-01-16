/*
 * RegisterAction.java
 */

package com.cssc.spl.struts.action;

import com.cssc.spl.bo.PaymentBO;
import com.cssc.spl.bo.UserBO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.struts.form.RegisterForm;
import com.cssc.spl.util.CSSCUtil;
import com.cssc.spl.util.Constants;
import com.cssc.spl.util.CreditCardPayment;
import com.cssc.spl.vo.GeneralistVO;
import com.cssc.spl.vo.PaymentVO;
import com.cssc.spl.vo.UserVO;
import com.lavantech.net.mail.AttachmentException;
import com.lavantech.net.mail.EmailAddress;
import com.lavantech.net.mail.InvalidAddressException;
import com.lavantech.net.mail.MailException;
import com.lavantech.net.mail.Message;
import com.lavantech.net.mail.SMTPAuthException;
import com.lavantech.net.mail.SMTPMailer;
import java.sql.Date;
import java.util.Calendar;
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
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Chander Singh
 * Created on November 6, 2007, 10:50 AM
 */
public class RegisterAction extends Action {
    //Constants for defining Errors, Messages and warnings.
    private final String ERRORS = "errors";
    private final String MESSAGES = "messages";
    
    //Constants defined for forward mapping results
    private final String SUCCESS = "success";
    private final String ERROR = "error";
    
    private Logger logger = null;
    
    private final String FROM_ADDRESS = "FROM_ADDRESS";
    private final String FROM_ALIAS = "FROM_ALIAS";
    
    private final String STATE_DATA = "STATE_DATA";
    private final String COUNTRY_DATA = "COUNTRY_DATA";
    private final String CREDIT_CARD_TYPE_DATA = "CREDIT_CARD_TYPE_DATA";
    private final String DEFAULT_SETUP_FEE = "DEFAULT_SETUP_FEE";
    
    private final String GENERALIST= "GEN";
    private final String SPECIALIST = "SPEC";
    private final String SYSTEM = "SYSTEM";
    
    private final String ACTV = "ACTIVE";
    
    private final String GEN_REGISTER_MAP = "/genreg";
    private final String GEN_REGISTER_SUBMIT_MAP = "/genregsub";
    
    private final String SPEC_REGISTER_MAP = "/spcreg";
    private final String SPEC_REGISTER_SUBMIT_MAP = "/spcregsub";
    private final String SPEC_TERMS_MAP = "/spcterms";
    
    private final String WELCOME_SUBJECT = "label.welcomesub";
    private final String WELCOME_MESSAGE = "label.welcomemsg";
            
    private final String CSSC005M = "messages.CSSC005M";
    private final String CSSC010E = "errors.CSSC010E";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger (this.getClass());
        logger.info ("Start execute (Actionmapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        String path = mapping.getPath();
        ActionForward forward = null;
        RegisterForm registerForm = (RegisterForm) form;
        ActionMessages messages = new ActionMessages ();
        try {
            logger.debug ("Path requested: " + path);
            if (GEN_REGISTER_MAP.equals (path)) {
                logger.debug("In Register Map");
                messages = handleListGenRegister (registerForm, request);
                forward = mapping.findForward(SUCCESS);
            }
            if (SPEC_REGISTER_MAP.equals (path)) {
                logger.debug("In Register Map");
                messages = handleListSpecRegister (registerForm, request);
                forward = mapping.findForward(SUCCESS);
            }
            if (SPEC_TERMS_MAP.equals(path)) {
                forward = mapping.findForward(SUCCESS);
            }
            if (GEN_REGISTER_SUBMIT_MAP.equals (path)) {
                ActionErrors errors = registerForm.validate(mapping, request);
                if (errors.isEmpty()) {
                    messages = handleGenRegister (registerForm, request);
                    forward = mapping.findForward (SUCCESS);
                } else {
                    extractErrors (messages, errors);
                    Iterator errorIter = errors.get(ERRORS+".label.location");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.location", message);
                    }
                    errorIter = errors.get(ERRORS+".label.locpwd");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.locpwd", message);
                    }
                    errorIter = errors.get(ERRORS+".label.specialist");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        messages.add (ERRORS+".label.specialist", message);
                    }
                    forward = mapping.findForward(ERROR);        
                }
            }
            
            if (SPEC_REGISTER_SUBMIT_MAP.equals(path)) {
                ActionErrors errors = registerForm.validate(mapping, request);
                if (errors.isEmpty()) {
                    messages = handleSpecRegister (registerForm, request);
                    UserVO userVO = registerForm.getUserVO();
                    request.getSession().setAttribute(userVO.getUserType(), userVO);
                    forward = mapping.findForward(SUCCESS);
                } else {
                    extractErrors (messages, errors);
                    Iterator errorIter = errors.get(ERRORS+".label.terms");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage) errorIter.next();
                        messages.add (ERRORS + ".label.terms", message);
                    }
                    errorIter = errors.get (ERRORS+".label.amtcharged");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage) errorIter.next();
                        messages.add (ERRORS + ".label.amtcharged", message);
                    }
                    errorIter = errors.get (ERRORS+".label.ccnbr");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage) errorIter.next();
                        messages.add (ERRORS + ".label.ccnbr", message);
                    }
                    errorIter = errors.get (ERRORS+".label.seccode");
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage) errorIter.next();
                        messages.add (ERRORS + ".label.seccode", message);
                    }
                    errorIter = errors.get(ERRORS);
                    while (errorIter.hasNext()) {
                        ActionMessage message = (ActionMessage)errorIter.next();
                        logger.debug ("Error Code: " + ERRORS);
                        messages.add (ERRORS, message);
                    }                    
                    forward = mapping.findForward(ERROR);                    
                }
            }
        } catch (CSSCSystemException csscsexp) {
            messages.add(ERRORS, new ActionMessage (csscsexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        } catch (CSSCApplicationException csscaexp) {
            messages.add(ERRORS, new ActionMessage (csscaexp.getErrorCode()));
            forward = mapping.findForward(ERROR);
        }
        saveMessages(request, messages);
        logger.debug("Forward Request: " + forward.getPath());
        return forward;
    }
    
    private void extractErrors (ActionMessages messages, ActionErrors errors) {
        Iterator errorIter = errors.get(ERRORS+".label.fname");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.fname", message);
        }        
        errorIter = errors.get(ERRORS+".label.company");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.company", message);
        }
        errorIter = errors.get(ERRORS+".label.lname");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
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
            Object obj = errorIter.next();
            logger.debug (obj);
            ActionMessage message = (ActionMessage)obj;
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
        errorIter = errors.get(ERRORS+".label.email");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.email", message);
        }
        errorIter = errors.get(ERRORS+".label.password");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.password", message);
        }
        errorIter = errors.get(ERRORS+".label.repassword");
        while (errorIter.hasNext()) {
            ActionMessage message = (ActionMessage)errorIter.next();
            messages.add (ERRORS+".label.repassword", message);
        }
    }
    
    private ActionMessages handleListGenRegister (RegisterForm registerForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleListGenRegister (RegisterForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        String stateData = Constants.getProperty(STATE_DATA);
        LabelValueBean[] stateBeans = CSSCUtil.getLabelValueBeans(stateData, ":", ";");
        registerForm.setStateBeans (stateBeans);
                
        String countryData = Constants.getProperty(COUNTRY_DATA);
        LabelValueBean[] countryBeans = CSSCUtil.getLabelValueBeans(countryData, ":", ";");
        registerForm.setCountryBeans (countryBeans);
        
        logger.info ("End handleListGenRegister (RegisterForm, HttpServletRequest)");
        return messages;
    }
    
    private ActionMessages handleListSpecRegister (RegisterForm registerForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleListSpecRegister (RegisterForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        String stateData = Constants.getProperty(STATE_DATA);
        LabelValueBean[] stateBeans = CSSCUtil.getLabelValueBeans(stateData, ":", ";");
        registerForm.setStateBeans (stateBeans);
                
        String countryData = Constants.getProperty(COUNTRY_DATA);
        LabelValueBean[] countryBeans = CSSCUtil.getLabelValueBeans(countryData, ":", ";");
        registerForm.setCountryBeans (countryBeans);
        
        String ccTypeData = Constants.getProperty(CREDIT_CARD_TYPE_DATA);
        LabelValueBean[] ccTypeBeans = CSSCUtil.getLabelValueBeans(ccTypeData, ":", ";");
        registerForm.setCcTypeBeans (ccTypeBeans);
        
        String setupFeeStr = Constants.getProperty(DEFAULT_SETUP_FEE);
        double setupFee = CSSCUtil.parseDouble(setupFeeStr);
        registerForm.setSetupFee(setupFee);
        
        Calendar cal = Calendar.getInstance();
        int currentyear = cal.get(Calendar.YEAR);
        String[] years = new String[9];
        for (int cnt = 0; cnt < 9; cnt++) {
            years[cnt] = String.valueOf (currentyear + cnt);
        }
        registerForm.setYears(years);
        logger.info ("End handleListSpecRegister (RegisterForm, HttpServletRequest)");
        return messages;
    }
    
     private ActionMessages handleGenRegister (RegisterForm registerForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleGenRegister (RegisterForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        UserVO userVO = registerForm.getUserVO();
        userVO.setUserType(SPECIALIST);
        UserBO userBO = new UserBO ();
        GeneralistVO generalistVO = userBO.createGeneralist (userVO, SYSTEM);                
        HttpSession session = request.getSession();
        session.setAttribute(GENERALIST, generalistVO);
        logger.info ("End handleGenRegister (RegisterForm, HttpServletRequest)");
        return messages;
    }
    
    private ActionMessages handleSpecRegister (RegisterForm registerForm, HttpServletRequest request) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleSpecRegister (RegisterForm, HttpServletRequest)");
        ActionMessages messages = new ActionMessages ();
        UserVO userVO = registerForm.getUserVO();
        double setupfee = registerForm.getSetupFee();
        if (setupfee > 0.0) {
            messages = handlePay (registerForm, request, userVO);
            if (!messages.isEmpty()) {
                return messages;
            }
        } else {
            registerForm.setSetupFee(0);
        }
        GregorianCalendar cal = new GregorianCalendar();        
        cal.setLenient(false);
        cal.set(Calendar.DATE, 1);
        cal.add (Calendar.MONTH, 1);
        long timeinmillis = cal.getTimeInMillis();
        Date startDate = new Date (timeinmillis);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 24);
        Date endDate = new Date (cal.getTimeInMillis());
        double setupFee = registerForm.getSetupFee();
        userVO.setStatus(ACTV);
        userVO.setStartDt(startDate);
        userVO.setEndDt(endDate);
        userVO.setSetupFee(setupFee);
        userVO.setUserType(SPECIALIST);       
        
        UserBO userBO = new UserBO ();
        userVO = userBO.createSpecialist (userVO, SYSTEM);                
        messages.add(MESSAGES, new ActionMessage (CSSC005M, "Save"));
        registerForm.setUserVO(userVO);
        registerForm.setCcNbr("");
        registerForm.setSecCode("");
        registerForm.setCcNbr("");
        registerForm.setExpmon("");
        registerForm.setExpyear("");
        String fromAddr = Constants.getProperty (FROM_ADDRESS);
        String fromAlias = Constants.getProperty(FROM_ALIAS);
        
        try {
            EmailAddress fromAddress = new EmailAddress(fromAddr, fromAlias);
            String email = userVO.getUsername();
            StringBuffer nameBuf = new StringBuffer (userVO.getFirstName());
            nameBuf.append (" ");
            nameBuf.append (userVO.getLastName());
            String name = nameBuf.toString();
            EmailAddress[] toAddress = new EmailAddress[] {new EmailAddress (email, name)};
            //SMTPMailer.SMTP_PORT = 2525;
            MessageResources resources = (MessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
            String subject = resources.getMessage(WELCOME_SUBJECT);
            String message = resources.getMessage(WELCOME_MESSAGE, name);
            logger.debug (message);
            Message msg = new Message(fromAddress, toAddress, null, null, subject, message);
            //SMTPMailer.SMTP_PORT = 2525;
            SMTPMailer.sendMail(msg, "ip-208-109-86-25.ip.secureserver.net", null, null);
            logger.debug("Email send");
        } catch (InvalidAddressException iaexp) {
            logger.error(iaexp);
        } catch (AttachmentException aexp) {
            logger.error(aexp);
        } catch (SMTPAuthException saexp) {
            logger.error(saexp);
        } catch (MailException mexp) {
            logger.error(mexp);
        }
        logger.info ("End handleSpecRegister (RegisterForm, HttpServletRequest)");
        return messages;
    }
    
    private ActionMessages handlePay (RegisterForm registerForm, HttpServletRequest request, UserVO userVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info("Start handlePay (RegisterForm, HttpServletRequest, UserVO)");
        ActionMessages messages = new ActionMessages ();
        long invoiceNbr = Calendar.getInstance().getTimeInMillis();        
        double amount = registerForm.getSetupFee();
        String ccNbr = registerForm.getCcNbr();
        String expMon = registerForm.getExpmon();
        String expYear = registerForm.getExpyear();
        String secCode = registerForm.getSecCode();
        
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
        
        PaymentBO paymentBO = new PaymentBO ();
        String userId = userVO.getUsername();
        paymentBO.savePaymentDetails(new PaymentVO [] {paymentVO}, userId);
                
        logger.info("End handlePay (RegisterForm, HttpServletRequest, UserVO)");
        return messages;
    }
}
