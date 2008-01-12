/*
 * PaymentVO.java
 */

package com.cssc.spl.vo;

import com.cssc.spl.vo.common.CommonVO;
import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * Created on November 20, 2007, 4:11 PM
 */
public class PaymentVO extends CommonVO implements Serializable {
    private int transactionId = -1;
    private int responseCode = -1;
    private int approvalCode = -1;
    private String responseTxt = "";
    private String avsCode = "";
    private String invoiceNbr = "";
    private String locationName = "";
    private String userId = "";
    private double amount = 0;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(int approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getResponseTxt() {
        return responseTxt;
    }

    public void setResponseTxt(String responseTxt) {
        this.responseTxt = responseTxt;
    }

    public String getAvsCode() {
        return avsCode;
    }

    public void setAvsCode(String avsCode) {
        this.avsCode = avsCode;
    }

    public String getInvoiceNbr() {
        return invoiceNbr;
    }

    public void setInvoiceNbr(String invoiceNbr) {
        this.invoiceNbr = invoiceNbr;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}
