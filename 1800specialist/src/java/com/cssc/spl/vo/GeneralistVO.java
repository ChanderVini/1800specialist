package com.cssc.spl.vo;

import com.cssc.spl.vo.common.CommonVO;
import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on December 25, 2007
 */
public class GeneralistVO extends CommonVO implements Serializable {
    private String generalistid = "";
    private String userid = "";
    private String locationname = "";
    private String usertypecd = "";
    private String uploaded = "";
    private int remDownloads = 0;
    
    /**
     * Default constructor of GeneralistVO
     */
    public GeneralistVO() {
    }

    public String getGeneralistid() {
        return generalistid;
    }

    public void setGeneralistid(String generalistid) {
        this.generalistid = generalistid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getUsertypecd() {
        return usertypecd;
    }

    public void setUsertypecd(String usertypecd) {
        this.usertypecd = usertypecd;
    }    

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public int getRemDownloads() {
        return remDownloads;
    }

    public void setRemDownloads(int remDownloads) {
        this.remDownloads = remDownloads;
    }    
}
