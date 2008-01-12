<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<title><bean:message key="label.specialist"/>&nbsp;<bean:message key="label.accinfo"/></title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="900">
            <div class="tabberlive">
                <ul class="tabbernav">    
                    <logic:equal name="SpecialistForm" property="logout" value="false">
                        <li id="logout" class="tabberinactive">
                            <html:link action="logout.spl"><bean:message key="label.logout"/></html:link>
                        </li>
                    </logic:equal>                    
                    <li id="accinfo" class="tabberactive">
                        <html:link action="saccinfo.spl"><bean:message key="label.accinfo"/></html:link>
                    </li>
                    <li id="profile" class="tabberinactive">
                        <html:link action="sprofile.spl"><bean:message key="label.profile"/></html:link>
                    </li>
                    <li id="location" class="tabberinactive">
                        <html:link action="lspcloc.spl"><bean:message key="label.location"/></html:link>
                    </li>
                    <li id="symptoms" class="tabberinactive">
                        <html:link action="spcsymp.spl"><bean:message key="label.symptoms"/></html:link>
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
                        <table id="dataTable" width="100%" border="0" class="inputsubtable" cellpadding="0" cellspacing="1">
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="300">
                                    <bean:message key="label.status"/>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels">
                                    <bean:write name="SpecialistForm" property="status"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="300">
                                    <bean:message key="label.actvon"/>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels">
                                    <bean:write name="SpecialistForm" property="startDt"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="300">
                                    <bean:message key="label.expon"/>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels">
                                    <bean:write name="SpecialistForm" property="expDt"/>
                                </td>
                            </tr>    
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="300">
                                    <bean:message key="label.nboflocs"/>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels">
                                    <bean:write name="SpecialistForm" property="nbrOfLocations"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="300">
                                    <bean:message key="label.nbrofdwl"/>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels">
                                    <bean:write name="SpecialistForm" property="nbrOfDownloads"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="300">
                                    <bean:message key="label.dwlleft"/>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels">
                                    <bean:write name="SpecialistForm" property="nbrOfDownloadsLeft"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="300">
                                    <bean:message key="label.mthbill"/>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels">
                                    <bean:write name="SpecialistForm" property="recurringFee"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>