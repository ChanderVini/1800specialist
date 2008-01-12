/*
 * UserVO.java
 */

package com.cssc.spl.vo;

import com.cssc.spl.util.Validator;
import com.cssc.spl.vo.common.CommonVO;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:27 PM
 */
public class UserVO extends CommonVO implements Serializable {
    private String username = "";
    private String password = "";
    private String userType = "";
    private String status = "";
    private String phone1 = "";
    private String phone2 = "";
    private String fax = "";
    private String mobile = "";
    private String company = "";
    private String firstName = "";
    private String lastName = "";
    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String zipcode = "";
    private String ipaddress = "";
    private String referralCd = "";
    private String industryCd = "";
    private String referredCd = "";
    private String locationPwd = "";
    private String location = "";
    private double setupFee = 0.0;
    private double recurringFee = 0.0;
    
    private Date startDt = null;
    private Date endDt = null;
    
    private String startDtStr = "";
    private String endDtStr = "";
    
    private boolean selected = false;
    
    private LocationVO[] locationVOs = new LocationVO [0];
         
    /** Creates a new instance of UserVO */
    public UserVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getReferralCd() {
        return referralCd;
    }

    public void setReferralCd(String referralCd) {
        this.referralCd = referralCd;
    }

    public String getIndustryCd() {
        return industryCd;
    }

    public void setIndustryCd(String industryCd) {
        this.industryCd = industryCd;
    }

    public String getReferredCd() {
        return referredCd;
    }

    public void setReferredCd(String referredCd) {
        this.referredCd = referredCd;
    }

    public String getLocationPwd() {
        return locationPwd;
    }

    public void setLocationPwd(String locationPwd) {
        this.locationPwd = locationPwd;
    }

    public LocationVO[] getLocationVOs() {
        return locationVOs;
    }

    public void setLocationVOs(LocationVO[] locationVOs) {
        this.locationVOs = locationVOs;
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

    public String getLocation () {
        return location;
    }
    
    public void setLocation (String location) {
        this.location = location;
    }
    
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getStartDtStr() {
        return startDtStr;
    }

    public void setStartDtStr(String startDtStr) {
        this.startDtStr = startDtStr;
    }

    public String getEndDtStr() {
        return endDtStr;
    }

    public void setEndDtStr(String endDtStr) {
        this.endDtStr = endDtStr;
    }    

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public int compareTo(Object obj) {
        UserVO userVO = (UserVO) obj;
        String username = userVO.getUsername();
        return (this.username.compareTo(username));
    }

    public boolean equals(Object obj) {
        UserVO userVO = (UserVO) obj;
        String username = userVO.getUsername();
        return (this.username.equals(username));
    }  
    
    public void addLocation (LocationVO locationVO) {
        ArrayList locationAL = new ArrayList (Arrays.asList(locationVOs));
        locationAL.add (locationVO);
        locationVOs = (LocationVO[]) locationAL.toArray(new LocationVO[locationAL.size()]);
        locationAL = null;
    }
}
