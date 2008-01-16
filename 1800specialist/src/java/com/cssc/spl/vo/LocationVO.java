/*
 * LocationVO.java
 */

package com.cssc.spl.vo;

import com.cssc.spl.util.CSSCUtil;
import com.cssc.spl.util.Constants;
import com.cssc.spl.vo.common.CommonVO;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Chander Singh
 * Created on October 16, 2007, 2:00 PM
 */
public class LocationVO extends CommonVO implements Serializable, Comparable {    
    private boolean selected = false;
    
    private String locationName = "";
    private String status = "";
    private String phone1 = "";
    private String phone2 = "";
    private String fax = "";
    private String mobile = "";
    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String zipCode = "";
    private String specialistName = "";
    private int remDownloads = 0;
       
    private Date startDate = null;
    private Date endDate = null;
    
    private String startDtStr = "";
    private String endDtStr = "";
    
    private String userId = "";
    
    private String locationPwd = "";
    
    /** Creates a new instance of LocationVO */
    public LocationVO() {
    }    

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public int getRemDownloads() {
        return remDownloads;
    }

    public void setRemDownloads(int remDownloads) {
        this.remDownloads = remDownloads;
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
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
    
    public String getUserId () {
        return userId;
    } 
    
    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getLocationPwd() {
        return locationPwd;
    }

    public void setLocationPwd(String locationPwd) {
        this.locationPwd = locationPwd;
    }
    
    public boolean equals(Object obj) {
       LocationVO locationVO = (LocationVO) obj;
       String locationName = locationVO.getLocationName();
       return this.locationName.equals(locationName);
    }  
    
    public int compareTo(Object obj) {
        LocationVO locationVO = (LocationVO) obj;
        String locationName = locationVO.getLocationName();
        return (this.locationName.compareTo(locationName));
    }
    
     public void process () {
        createId ();
        if (getCreateDt() != null) {
            setCreateDtStr(CSSCUtil.convertDate(getCreateDt(), Constants.getProperty ("DATE_FORMAT"), ""));
        }
        
        if (getLastUpdateDt() != null) {
            setLastUpdateDtStr(CSSCUtil.convertDate(getLastUpdateDt(), Constants.getProperty ("DATE_FORMAT"), ""));
        }
        
        if (getStartDate() != null) {
            setStartDtStr(CSSCUtil.convertDate(getStartDate(), Constants.getProperty ("DATE_FORMAT"), ""));
        }
        
        if (getEndDate() != null) {
            setEndDtStr(CSSCUtil.convertDate(getEndDate(), Constants.getProperty ("DATE_FORMAT"), ""));
        }
    }
    
    public void createId () {
        if (locationName != null) {
            setId(locationName.hashCode());
        }
    }
}