/*
 * UserTypeVO.java
 */

package com.cssc.spl.vo;

import com.cssc.spl.util.CSSCUtil;
import com.cssc.spl.util.Constants;
import com.cssc.spl.vo.common.CommonVO;
import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * Created on November 9, 2007, 1:16 PM
 */
public class UserTypeVO extends CommonVO implements Serializable, Comparable {
    private boolean selected = false;
    private String userTypeCd = "";
    private String userTypeName = "";

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public String getUserTypeCd() {
        return userTypeCd;
    }

    public void setUserTypeCd(String userTypeCd) {
        this.userTypeCd = userTypeCd;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public int compareTo(Object obj) {
        UserTypeVO userTypeVO = (UserTypeVO) obj;
        return (this.userTypeName.compareTo(userTypeVO.getUserTypeName()));
    }
    
    public void process () {
        createId ();
        if (getCreateDt() != null) {
            setCreateDtStr(CSSCUtil.convertDate(getCreateDt(), Constants.getProperty ("TIMESTAMP_FORMAT"), ""));
        }
        
        if (getLastUpdateDt() != null) {
            setLastUpdateDtStr(CSSCUtil.convertDate(getLastUpdateDt(), Constants.getProperty ("TIMESTAMP_FORMAT"), ""));
        }
    }
    
    public void createId () {
        setId(userTypeCd.hashCode());
    }
}
