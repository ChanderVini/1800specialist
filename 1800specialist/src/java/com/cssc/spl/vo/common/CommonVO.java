/*
 * CommonVO.java
 */

package com.cssc.spl.vo.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Chander Singh
 * Created on November 9, 2007, 1:26 PM
 */
public class CommonVO implements Serializable{
    private long id = -1;
    private String createUserIdCd = "";
    private Timestamp createDt = null;
    private String createDtStr = "";
    private String lastUpdateUserIdCd = "";
    private Timestamp lastUpdateDt = null;
    private String lastUpdateDtStr = "";
        
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateUserIdCd() {
        return createUserIdCd;
    }

    public void setCreateUserIdCd(String createUserIdCd) {
        this.createUserIdCd = createUserIdCd;
    }

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    public String getLastUpdateUserIdCd() {
        return lastUpdateUserIdCd;
    }

    public void setLastUpdateUserIdCd(String lastUpdateUserIdCd) {
        this.lastUpdateUserIdCd = lastUpdateUserIdCd;
    }

    public Timestamp getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(Timestamp lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }   

    public String getCreateDtStr() {
        return createDtStr;
    }

    public void setCreateDtStr(String createDtStr) {
        this.createDtStr = createDtStr;
    }

    public String getLastUpdateDtStr() {
        return lastUpdateDtStr;
    }

    public void setLastUpdateDtStr(String lastUpdateDtStr) {
        this.lastUpdateDtStr = lastUpdateDtStr;
    }
}