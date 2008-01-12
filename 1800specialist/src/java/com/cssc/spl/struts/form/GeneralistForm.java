/*
 * GeneralistForm.java
 */

package com.cssc.spl.struts.form;

import com.cssc.spl.struts.form.common.CommonForm;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Chander Singh
 * Created on November 27, 2007, 1:52 PM
 */
public class GeneralistForm extends CommonForm {
    private int remainingDownloads = -1;
    private File file = null;
    
    public int getRemainingDownloads () {
        return remainingDownloads;
    }

    public void setRemainingDownloads (int remainingDownloads) {
        this.remainingDownloads = remainingDownloads;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }    
}
