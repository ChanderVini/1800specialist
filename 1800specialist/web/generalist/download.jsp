<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<title><bean:message key="label.generalist"/>&nbsp;<bean:message key="label.download"/></title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">    
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="900">
            <div class="tabberlive">
                <ul class="tabbernav">    
                    <logic:equal name="GeneralistForm" property="logout" value="true">
                        <li id="login" class="tabberinactive">
                            <html:link action="generalist.spl"><bean:message key="label.login"/></html:link>
                        </li>
                    </logic:equal>
                    <logic:equal name="GeneralistForm" property="logout" value="false">
                        <li id="login" class="tabberinactive">
                            <html:link action="logout.spl"><bean:message key="label.logout"/></html:link>
                        </li>
                    </logic:equal>
                    <li id="register" class="tabberactive">
                        <html:link action="gendwn.spl"><bean:message key="label.download"/></html:link>
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
                <logic:equal name="GeneralistForm" property="operation" value="DWN">
                    <tr>
                        <td valign="top" colspan="2">
                            <html:form action="/gendwnsub" method="POST">
                                <table id="dataTable" width="200" border="0" class="inputsubtable" cellpadding="2" cellspacing="0">
                                    <tr><td colspan="2">&nbsp;</td></tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <html:submit property="download" styleClass="subtleButton"><bean:message key="label.download"/></html:submit>
                                        </td>
                                    </tr>
                                </table>
                            </html:form>
                        </td>
                    </tr>
                </logic:equal>
                <logic:equal name="GeneralistForm" property="operation" value="DWNCOM">
                    <tr>
                        <td valign="top" colspan="2">
                            <html:form action="/gendwnsub" method="POST">
                            <table id="dataTable" width="200" border="0" class="inputsubtable" cellpadding="2" cellspacing="0" bgcolor="#CCCCCC">
                                    <tr><td colspan="2">&nbsp;</td></tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <html:submit property="download" styleClass="subtleButton" ><bean:message key="label.download"/></html:submit>
                                        </td>
                                    </tr>
                                </table>
                            </html:form>
                        </td>
                    </tr>
                </logic:equal>
            </table>
        </td>
    </tr>
</table>
<logic:equal name="GeneralistForm" property="operation" value="DWNCOM">
    <script type="text/javascript">
        document.forms[0].action="filedwn.spl";
        document.forms[0].submit();
    </script>
</logic:equal>    