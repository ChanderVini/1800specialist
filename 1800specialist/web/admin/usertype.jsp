<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<title><bean:message key="label.usertypelist"/></title>
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
                    <li id="login" class="tabberinactive">
                       <html:link action="lspec.spl"><bean:message key="label.specialist"/></html:link>
                    </li>
                    <li id="register" class="tabberactive">
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
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <th class="nicetableheader">
                                    &nbsp;
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.code"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.name"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.createuser"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.createdt"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.updateuser"/>
                                </th>
                                <th class="nicetableheader">
                                    <bean:message key="label.updatedt"/>
                                </th>
                            </tr>
                            <html:form action="/sutype" method="POST">
                                <nested:iterate id="userTypeVO" name="AdminForm" property="userTypeVOs" indexId="rowCtr">
                                    <tr>  
                                        <td class="datarowborders" align="center">
                                            <nested:checkbox property="selected"/>
                                        </td>
                                        <td class="datarowborders" >
                                            <nested:greaterThan property="id" value="0">
                                                <nested:text property="userTypeCd" readonly="true" styleClass="criteriavalue" maxlength="8" size="8"/>
                                            </nested:greaterThan>
                                            <nested:lessEqual property="id" value="0">
                                                <nested:text property="userTypeCd" styleClass="criteriavalue" maxlength="8" size="8"/>
                                            </nested:lessEqual>
                                        </td>
                                        <td class="datarowborders" >
                                            <nested:text property="userTypeName" styleClass="criteriavalue"/>
                                        </td>
                                        <td class="datarowborders" >
                                            <div class="criteriaValue"><nested:write property="createUserIdCd"/></div>
                                        </td>
                                        <td class="datarowborders" >
                                            <div class="criteriaValue"><nested:write property="createDtStr"/></div>
                                        </td>
                                        <td class="datarowborders" >
                                            <div class="criteriaValue"><nested:write property="lastUpdateUserIdCd"/></div>
                                        </td>
                                        <td class="datarowborders" >
                                            <div class="criteriaValue"><nested:write property="lastUpdateDtStr"/></div>
                                        </td>
                                    </tr>
                                </nested:iterate>
                            <tr>
                                <td colspan="7">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="7" align="center">
                                    <html:submit property="save" styleClass="subtlebutton" onclick="handleSubmit ('sutype.spl')">
                                        <bean:message key="label.save"/>
                                    </html:submit>
                                    <html:submit property="add" styleClass="subtlebutton" onclick="handleSubmit ('autype.spl')">
                                        <bean:message key="label.add"/>
                                    </html:submit>
                                    <html:submit property="delete" styleClass="subtlebutton" onclick="handleSubmit ('dutype.spl')">
                                        <bean:message key="label.delete"/>
                                    </html:submit>
                                </td>
                            </tr>
                        </html:form>
                    </table>
                </tr>
            </table>
        </td>
    </tr>
</table>   