<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<title><bean:message key="label.speclist"/></title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr height="15">
        <td width="2%">&nbsp;</td>
        <td class="labelLeft"><b>Welcome: <bean:write name="AdminForm" property="displayname"/></b></td>
    </tr>        
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="900">
            <div class="tabberlive">
                <ul class="tabbernav">  
                    <logic:equal name="AdminForm" property="logout" value="false">
                        <li id="logout" class="tabberinactive">
                            <html:link action="logout.spl"><bean:message key="label.logout"/></html:link>
                        </li>
                    </logic:equal>
                    <li id="lspecialist" class="tabberactive">
                        <html:link action="lspec.spl"><bean:message key="label.specialist"/></html:link>
                    </li>                    
                    <li id="usertype" class="tabberinactive">
                        <html:link action="lutype.spl"><bean:message key="label.usertype"/></html:link>
                    </li>
                </ul>
            </div>
        </td>
    </tr>
    <tr>
        <td width="2%">&nbsp;</td>
        <td valign="top">
            <table cellspacing="0" cellpadding="0" border="0" class="inputtable" width="900">                    
                <logic:messagesPresent name="messages" message="true">
                    <tr>
                        <td class="labelLeft" colspan="2">
                            <html:messages id="messages" message="true" property="messages">
                                <font color="green" face="verdana"><li><bean:write name="messages"/></font>
                            </html:messages>
                        </td>
                    </tr>
                </logic:messagesPresent>
                <logic:messagesPresent name="warnings" message="true">
                    <tr>
                        <td class="labelLeft" colspan="2">
                            <html:messages id="warnings" message="true" property="warnings">
                                <font color="orange" face="verdana"><li><bean:write name="warnings"/></font>
                            </html:messages>
                        </td>
                    </tr>
                </logic:messagesPresent>
                <tr>
                    <td colspan="2">                        
                        <table id="dataTable" width="100%" border="0" class="inputsubtable" cellpadding="2" cellspacing="1">
                            <tr><td colspan="5">&nbsp;</td></tr>
                            <tr>
                                <th class="nicetableheader">
                                    &nbsp;
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.location"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.status"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.startdt"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.enddt"/>
                                </th>
                            </tr>
                            <html:form action="/sutype" method="POST" enctype="multipart/form-data">
                                <nested:iterate id="userVO" name="AdminForm" property="userVOs" indexId="rowCtr" type="com.cssc.spl.vo.UserVO">                                 
                                    <%
                                        String errortag = "errors." + userVO.getUsername ();
                                    %>
                                    <logic:messagesPresent name="<%=errortag%>" message="true">
                                        <tr>
                                            <td class="labelLeft" colspan="7">
                                                <html:messages id="errors" message="true" property="<%=errortag%>">
                                                    <font color="red" face="verdana"><li><bean:write name="errors"/></font>
                                                </html:messages>
                                            </td>
                                        </tr>
                                    </logic:messagesPresent>                
                                    <tr valign="top">  
                                        <nested:size id="nbrOfLocs" property="locationVOs"/>
                                        <% String togglejs = "toggleChildRow(this, 'userVO"+rowCtr.intValue()+"', "+nbrOfLocs+");";%>
                                       <td class="sectionheader" rowspan="2">
                                            <nested:checkbox property="selected"/>&nbsp;
                                            <img src="images/arrow_collapse.gif" width="9" height="9" onclick="<%=togglejs%>" />&nbsp;&nbsp;&nbsp;
                                        </td>
                                        <td class="sectionheader">
                                            <b><bean:message key="label.name"/>:</b> <nested:write property="firstName"/>&nbsp;<nested:write property="lastName"/>&nbsp;&nbsp;&nbsp;&nbsp;
                                        </td>
                                        <td class="sectionheader">
                                            <b><bean:message key="label.email"/>:</b> <nested:write property="username"/>&nbsp;&nbsp;&nbsp;&nbsp;
                                        </td>
                                        <td class="sectionheader" colspan="2">
                                            <b><bean:message key="label.password"/>:</b> <nested:write property="password"/>
                                        </td>
                                    </tr>
                                    <tr valign="top">  
                                        <td class="sectionheader">
                                            <b><bean:message key="label.download"/>&nbsp;<bean:message key="label.password"/>:</b><nested:write property="locationPwd"/>&nbsp;&nbsp;
                                        </td>
                                        <td class="sectionheader">
                                            <b><bean:message key="label.uploaded"/>:</b><nested:write property="uploaded"/>&nbsp;&nbsp;
                                        </td>
                                        <td class="sectionheader" colspan="2">
                                            <nested:file property="formFile" styleClass="inputsubtleButton"/>
                                            <html:submit property="submit" styleClass="subtleSmallButton" onclick="handleSubmit ('uadmloc.spl')"><bean:message key="label.upload"/></html:submit>
                                        </td>
                                    </tr>
                                    <nested:iterate id="locationVO" property="locationVOs" indexId="locRowCtr">          
                                        <tr id='<%="userVO"+rowCtr.intValue()+""+locRowCtr.intValue()%>'>
                                            <td class="datarowborders">
                                                <div class="criteriaValue"><nested:checkbox property="selected"/></div>
                                            </td>
                                            <td class="datarowborders">
                                                <div class="criteriaValue"><nested:write property="locationName"/></div>
                                            </td>
                                            <td class="datarowborders" >
                                                <div class="criteriaValue"><nested:write property="status"/></div>
                                            </td>
                                            <td class="datarowborders" >
                                                <div class="criteriaValue"><nested:text property="startDtStr" styleClass="criteriavalue" size="12"/></div>
                                            </td>
                                            <td class="datarowborders" >
                                                <div class="criteriaValue"><nested:text property="endDtStr" styleClass="criteriavalue" size="12"/></div>
                                            </td>    
                                            <td class="datarowborders" align="center">
                                                
                                            </td>
                                        </tr>
                                    </nested:iterate>
                                </nested:iterate>
                                <tr>
                                    <td colspan="5">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="5" align="center">
                                        <html:submit property="delete" value="Delete" styleClass="subtlebutton" onclick="handleSubmit ('dspec.spl')">
                                            <bean:message key="label.delete"/>
                                        </html:submit>
                                        <html:submit property="save" value="Save" styleClass="subtlebutton" onclick="handleSubmit ('sspec.spl')">
                                            <bean:message key="label.save"/>
                                        </html:submit>
                                    </td>
                                </tr>                                    
                            </html:form>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>