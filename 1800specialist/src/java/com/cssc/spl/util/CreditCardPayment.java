/*
 * CreditCardPayment.java
 */

package com.cssc.spl.util;

import com.cssc.spl.exception.CSSCSystemException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.log4j.Logger;

/**
 *
 * @author Chander Singh
 * Created on November 18, 2007, 6:11 PM
 */
public class CreditCardPayment {
    private Logger logger = null;
    
    private String loginId = "";
    private String transactionKey = "";
    private String version = "";
    private String url = "";
    
    private HttpsURLConnection urlConnection = null;
    private final String URL_STRING = "https://secure.authorize.net/gateway/transact.dll";
    
    /** Creates a new instance of CreditCardPayment */
    public CreditCardPayment(String loginId, String transactionKey, String version, String url) throws CSSCSystemException {
        logger = Logger.getLogger(this.getClass());
        
        this.loginId = loginId;
        this.transactionKey = transactionKey;
        this.version = version;
        if (url == null || "".equals (url.trim())) {
            this.url = URL_STRING;
        } else {
            this.url = url;
        }
        try {
            URL urlName = new URL (url);
            urlConnection = (HttpsURLConnection) urlName.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
        } catch (IOException ioexp) {
            logger.error (ioexp.getMessage(), ioexp);
            throw new CSSCSystemException ("Some");
        }       
    }
    
    public String makePayment (long invoiceNbr, double amount, String ccNbr, String expMon, String expYear, String securityCode, String firstName, 
            String lastName, String street, String city, String state, String zip, String country, String email, String phone, String fax) throws CSSCSystemException {
        StringBuffer requestBuf = new StringBuffer ("x_Login=");
        requestBuf.append (loginId);
        requestBuf.append ("&");
        requestBuf.append ("x_Tran_Key=");
        requestBuf.append (transactionKey);
        requestBuf.append ("&");
        requestBuf.append ("x_Version=");
        requestBuf.append (version);
        requestBuf.append ("&");
        requestBuf.append ("x_Invoice_Num=");
        requestBuf.append (invoiceNbr);
        requestBuf.append ("&");
        requestBuf.append ("x_Test_Request=TRUE");
        requestBuf.append ("&");
        requestBuf.append ("x_Amount=");
        requestBuf.append (amount);
        requestBuf.append ("&");
        requestBuf.append ("x_Method=CC");
        requestBuf.append ("&");
        requestBuf.append ("x_Type=AUTH_CAPTURE");
        requestBuf.append ("&");
        requestBuf.append ("x_Card_Num=");
        requestBuf.append (ccNbr);
        requestBuf.append ("&");
        requestBuf.append ("x_Exp_Date=");
        requestBuf.append (expMon + "-" + expYear);
        requestBuf.append ("&");
        requestBuf.append ("x_card_code=");
        requestBuf.append (securityCode);
        requestBuf.append ("&");
        requestBuf.append ("x_First_Name=");
        requestBuf.append (firstName);
        requestBuf.append ("&");
        requestBuf.append ("x_Last_Name=");
        requestBuf.append (lastName);
        requestBuf.append ("&");
        requestBuf.append("x_Address=");
        requestBuf.append (street);
        requestBuf.append ("&");
        requestBuf.append("x_City=");
        requestBuf.append (city);
        requestBuf.append ("&");
        requestBuf.append("x_State=");
        requestBuf.append (state);
        requestBuf.append ("&");
        requestBuf.append ("x_Zip=");
        requestBuf.append (zip);
        requestBuf.append ("&");
        requestBuf.append ("x_Country=");
        requestBuf.append (country);
        requestBuf.append ("&");
        requestBuf.append ("x_Email=");
        requestBuf.append (email);
        requestBuf.append ("&");
        requestBuf.append ("x_Phone=");
        requestBuf.append (phone);
        requestBuf.append ("&");
        requestBuf.append ("x_fax=");
        requestBuf.append (fax);
        requestBuf.append ("&");
        
        String request = requestBuf.toString();
        requestBuf = null;
        
        try {
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            byte[] requestByte = request.getBytes();
            dos.write(requestByte);
            dos.close();
            BufferedReader in = new BufferedReader(new InputStreamReader (urlConnection.getInputStream()));
            String response = in.readLine();
            logger.debug ("Response: " + response);
            in.close();
            return response;
        } catch (IOException ioexp) {
            logger.error (ioexp.getMessage(), ioexp);
            throw new CSSCSystemException ("Some");
        }
    }
}
