/*
 * GeneralistAction.java
 */

package com.cssc.spl.struts.action;

import com.cssc.spl.bo.GeneralistBO;
import com.cssc.spl.exception.CSSCApplicationException;
import com.cssc.spl.exception.CSSCSystemException;
import com.cssc.spl.struts.action.common.CommonAction;
import com.cssc.spl.struts.form.GeneralistForm;
import com.cssc.spl.util.Constants;
import com.cssc.spl.vo.GeneralistVO;
import com.cssc.spl.vo.LocationVO;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author Chander Singh
 * Created on November 27, 2007, 1:54 PM
 */
public class GeneralistAction extends CommonAction {
    private Logger logger = null;
    
    private final String DOWNLOAD = "DWN";
    private final String DOWNLOADCOM = "DWNCOM";
    private final String SYSTEM = "SYSTEM";
    private final String GEN_SESSION = "GEN";
    
    private final String DEFAULT_UPLOAD_DIR = "DEFAULT_UPLOAD_DIR";
    
    private final String GEN_DOWN_MAP = "/gendwn";
    private final String GEN_DOWN_SUB_MAP = "/gendwnsub";
    private final String FILE_DOWN_MAP = "/filedwn";
    
    private final String CSSC007E = "errors.CSSC007E";
    private final String CSSC012E = "errors.CSSC012E";
    private final String CSSC013E = "errors.CSSC013E";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger = Logger.getLogger(this.getClass());
        logger.info ("Start execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        ActionForward forward = null;
        GeneralistForm generalistForm = (GeneralistForm) form;
        ActionErrors errors = new ActionErrors ();
        ActionMessages messages = new ActionMessages ();
        try {
            String path = mapping.getPath();            
            HttpSession session = request.getSession();
            Object genProfileObj = session.getAttribute(GEN_SESSION);            
            logger.debug ("Generalist Object: " + genProfileObj);
            if (genProfileObj == null) {
                forward = mapping.findForward(NOACCESS);
                ActionMessage message = new ActionMessage (CSSC007E);
                messages.add (ERRORS, message);
                saveMessages(request, messages);
                return forward;
            }  
            GeneralistVO generalistVO = (GeneralistVO) genProfileObj;
            generalistForm.setLogout(false);
            if (GEN_DOWN_MAP.equals(path)) {                
                messages = handleFetchDownload (generalistForm, request, generalistVO);
                forward = mapping.findForward(SUCCESS);
            }         
            
            if (GEN_DOWN_SUB_MAP.equals(path)) {
                messages = handleFileDownload (generalistForm, request, generalistVO);
                logger.debug("Messgaes are Empty? " + messages.isEmpty());
                if (!messages.isEmpty()) {
                    forward = mapping.findForward(ERROR);
                } else {
                    forward = mapping.findForward(SUCCESS);
                }
            }         
            
            if (FILE_DOWN_MAP.equals(path)) {
                messages = handleDownload (generalistForm, request, response);
                if (!messages.isEmpty()) {
                    forward = mapping.findForward(ERROR);
                } else {
                    return null;
                }
            }            
         } catch (CSSCSystemException csscsexp) {
            logger.debug("Error Code: " + csscsexp.getMessage());
            messages.add(ERRORS, new ActionMessage (csscsexp.getMessage()));
            forward = mapping.findForward(ERROR);
        } catch (CSSCApplicationException csscaexp) {
            forward = mapping.findForward(ERROR);
        }
        if (!errors.isEmpty()) {
            Iterator errorIter = errors.get(ERRORS);
            while (errorIter.hasNext()) {
                messages.add(ERRORS, (ActionMessage) errorIter.next());
            }
            forward = mapping.findForward (ERROR);
        }
        saveMessages(request, messages);
        logger.info ("End execute (ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)");
        return forward;
    }
    
    private ActionMessages handleDownload (GeneralistForm generalistForm, HttpServletRequest request, HttpServletResponse response) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleDownload (GeneralistForm, HttpServletRequest, HttpServletResponse)");
        ActionMessages messages = new ActionMessages ();
        File file = generalistForm.getFile();
        if (file == null || !file.exists()) {
            ActionMessage message = new ActionMessage (CSSC013E, "");
            messages.add (ERRORS, message);
        } else {
            response.setContentType("application/octet-stream");
            response.setHeader( "Content-Disposition", "attachment; filename=\"setup.exe\"" );
            
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ServletOutputStream output = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int count = 0;
                int n = 0;
                while (-1 != (n = bis.read(buffer))) {
                    output.write(buffer, 0, n);
                    count += n;
                }
                if (bis != null) {
                    bis.close();
                }
                output.flush();
            } catch (IOException ioexp) {
                logger.error (ioexp);
            }
        }
        logger.info ("End handleDownload (GeneralistForm, HttpServletRequest, HttpServletResponse)");
        return messages;
    }
    
    private ActionMessages handleFetchDownload (GeneralistForm generalistForm, HttpServletRequest request, GeneralistVO generalistVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleFetchDownload (GeneralistForm, HttpServletRequest, GeneralistVO)");
        ActionMessages messages = new ActionMessages ();
        int remainingDownloads = generalistVO.getRemDownloads();
        String uploaded = generalistVO.getUploaded();
        if (remainingDownloads < 1) {
            ActionMessage message = new ActionMessage (CSSC012E, "");
            messages.add (ERRORS, message);
        } else if ("N".equals(uploaded)) {
            ActionMessage message = new ActionMessage (CSSC013E, "");
            messages.add (ERRORS, message);
        } else {
            generalistForm.setOperation(DOWNLOAD);
            generalistForm.setRemainingDownloads (remainingDownloads);
        }
        
        logger.info ("End handleFetchDownload (GeneralistForm, HttpServletRequest, GeneralistVO)");
        return messages;
    }
    
    private ActionMessages handleFileDownload (GeneralistForm generalistForm, HttpServletRequest request, GeneralistVO generalistVO) throws CSSCApplicationException, CSSCSystemException {
        logger.info ("Start handleFileDownload (GeneralistForm, HttpServletRequest, HttpservletResponse, GeneralistVO)");
        ActionMessages messages = new ActionMessages ();
        String realPath = Constants.getProperty(DEFAULT_UPLOAD_DIR);
        String username = generalistVO.getUserid();
        String locationName = generalistVO.getLocationname();
        String dirpath = File.separator + File.separator + username;
        dirpath = realPath + dirpath + File.separator + locationName;
        File file = new File (dirpath);
        if (!file.exists()) {
            logger.debug ("File doesnt exists");
            ActionMessage message = new ActionMessage (CSSC013E, "");
            messages.add (ERRORS, message);
        } else {
            File[] files = file.listFiles();
            if (files.length == 0) {
                logger.debug ("Files are not in list");
                ActionMessage message = new ActionMessage (CSSC013E, "");
                messages.add (ERRORS, message);
            } else {
                logger.debug ("Files are in list: " + files.length);
                for (int cnt = 0; cnt < files.length; cnt++) {
                    logger.debug ("Name of file: " + files[cnt]);
                }
                logger.debug ("Remaining Downloads: " + generalistForm.getRemainingDownloads());
                int remainingDownloads = generalistForm.getRemainingDownloads() - 1;
                LocationVO locationVO = new LocationVO ();
                locationVO.setRemDownloads(remainingDownloads);
                locationVO.setLocationName(locationName);
                GeneralistBO generalistBO = new GeneralistBO ();
                generalistBO.updateLocationVO(locationVO, SYSTEM);
                generalistForm.setOperation(DOWNLOADCOM);
                generalistForm.setRemainingDownloads(remainingDownloads);
                generalistForm.setFile(files[0]);
            }
        }
        logger.info ("End handleFileDownload (GeneralistForm, HttpServletRequest, HttpservletResponse, GeneralistVO)");
        return messages;
    }
}
