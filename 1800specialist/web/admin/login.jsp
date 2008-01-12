<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<title><bean:message key="label.admin"/>&nbsp;<bean:message key="label.login"/></title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="900">
            <div class="tabberlive">
                <ul class="tabbernav">    
                    <li id="login" class="tabberactive">
                       <html:link action="admin.spl"><bean:message key="label.login"/></html:link>
                    </li>
                </ul>
            </div>
        </td>
    </tr>
    <tr>
        <td width="2%">&nbsp;</td>
        <td class="labelLeft" valign="top">
            <table cellpadding="0" cellspacing="0" border="0" width="900" class="inputtable">
                <logic:messagesPresent name="errors" message="true">
                    <tr>
                        <td class="labelLeft" colspan="2" valign="top">
                            <html:messages id="errors" message="true" property="errors">
                                <font color="red" face="verdana"><li><bean:write name="errors"/></font>
                            </html:messages>
                        </td>
                    </tr>                            
                </logic:messagesPresent>
                <logic:messagesPresent name="messages" message="true">
                    <tr>
                        <td class="labelLeft" colspan="2" valign="top">
                            <html:messages id="messages" message="true" property="messages">
                                <font color="green" face="verdana"><li><bean:write name="messages"/></font>
                            </html:messages>
                        </td>
                    </tr>
                </logic:messagesPresent>
                <logic:messagesPresent name="warnings" message="true">
                    <tr>
                        <td colspan="2" valign="top">
                            <html:messages id="warnings" message="true" property="warnings">
                                <font color="orange" face="verdana"><li><bean:write name="warnings"/></font>
                            </html:messages>
                        </td>
                    </tr>
                </logic:messagesPresent>
                <tr>
                    <td valign="top" colspan="2">
                        <html:form action="/admlogin" method="POST">
                            <table id="dataTable" width="100%" border="0" class="inputsubtable" cellpadding="2" cellspacing="0">
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.userid"/>:
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="LoginForm" property="userid" styleClass="criteriaValue" maxlength="50" size="40"/>
                                        <html:messages id="error" property="errors.label.userid" message="true">
                                            &nbsp;<font color="red"><bean:write name="error"/></font>
                                        </html:messages>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.password"/>:
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:password name="LoginForm" property="password" styleClass="criteriaValue" maxlength="50" size="40"/>
                                        <html:messages id="error" property="errors.label.password" message="true">
                                            &nbsp;<font color="red"><bean:write name="error"/></font>
                                        </html:messages>
                                    </td>
                                </tr>
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td colspan="2" align="center">
                                        <html:hidden name="LoginForm" property="userType" value="ADMIN"/>
                                        <html:submit property="login" styleClass="subtleButton"><bean:message key="label.submit"/></html:submit>
                                        <html:reset property="reset" styleClass="subtleButton"><bean:message key="label.reset"/></html:reset>
                                    </td>
                                </tr>
                            </table>
                        </html:form>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script type="text/javascript">
    document.forms[0].userid.focus();
</script>