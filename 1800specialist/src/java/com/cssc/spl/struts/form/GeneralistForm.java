/*
 * GeneralistForm.java
 */

package com.cssc.spl.struts.form;

import java.io.File;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author Chander Singh
 * Created on November 27, 2007, 1:52 PM
 */
public class GeneralistForm extends ActionForm {
    private int remainingDownloads = -1;
    private File file = null;
    
    //Constant defined for Key to be associated with Messages to be displayed to the user on the screen.
    protected final String MESSAGES = "messages";
    //Constant defined for Key to be associated with Errors to be displayed to the user on the screen.
    protected final String ERRORS = "errors";
    //Constant defined for Key to be associated with Warnings to be displayed to the user on the screen.	
    protected final String WARNINGS = "warnings";
    
    //Properties used for Pagination if required on the screen.
    //Current Page Number
    private String pageNumber;
    //Total Number of Pages records are devided into.
    private String totalPages;
    //Total Number of records as per search.

    private String totalRecords;
    //First Record number being displayed on the Current Page
    private String pageFirstRecordNbr;
    //Last Record number being displayed on the Current Page    
    private String pageLastRecordNbr;
    
    //Operation request to be performed.
    private String operation;
    //Last Operation requested by the user.
    private String lastOperation;

    //Properties for Sorting Operation.
    //Order in which Sorting needs to be performed.
    private String sortorder = "A";
    //Column to be sorted.
    private String sortcolumn = "";    

    private boolean logout = false;
    
    private String displayname = "";
    
    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getPageFirstRecordNbr() {
        return pageFirstRecordNbr;
    }

    public void setPageFirstRecordNbr(String pageFirstRecordNbr) {
        this.pageFirstRecordNbr = pageFirstRecordNbr;
    }

    public String getPageLastRecordNbr() {
        return pageLastRecordNbr;
    }

    public void setPageLastRecordNbr(String pageLastRecordNbr) {
        this.pageLastRecordNbr = pageLastRecordNbr;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation = lastOperation;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getSortcolumn() {
        return sortcolumn;
    }

    public void setSortcolumn(String sortcolumn) {
        this.sortcolumn = sortcolumn;
    }    

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
    
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
