<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<script type="text/javascript" src="js/loc.js"></script> 
<title><bean:message key="label.locationlist"/></title>
<bean:size id="locsize" name="SpecialistForm" property="locationVOs"/>
<script type="text/javascript">
    locsize = <bean:write name="locsize"/>;
    reccuringamt = <bean:write name="SpecialistForm" property="recurringFee"/>;
    setupfee = <bean:write name="SpecialistForm" property="setupFee"/>;
</script>
<table cellspacing="0" cellpadding="0" border="0" width="1250">
    <tr height="15">
        <td width="2%">&nbsp;</td>
        <td class="labelLeft"><b>Welcome: <bean:write name="SpecialistForm" property="displayname"/></b></td>
    </tr>            
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="98%">
            <div class="tabberlive">
                <ul class="tabbernav">    
                    <logic:equal name="SpecialistForm" property="logout" value="false">
                        <li id="logout" class="tabberinactive">
                            <html:link action="logout.spl"><bean:message key="label.logout"/></html:link>
                        </li>
                    </logic:equal>                    
                    <li id="login" class="tabberinactive">
                        <html:link action="saccinfo.spl"><bean:message key="label.accinfo"/></html:link>
                    </li>
                    <li id="register" class="tabberinactive">
                        <html:link action="sprofile.spl"><bean:message key="label.profile"/></html:link>
                    </li>
                    <li id="register" class="tabberactive">
                        <html:link action="lspcloc.spl"><bean:message key="label.location"/></html:link>
                    </li>
                    <li id="register" class="tabberinactive">
                        <html:link action="spcsymp.spl"><bean:message key="label.symptoms"/></html:link>
                    </li>
                </ul>
            </div>
        </td>
    </tr>
    <tr>
        <td width="2%">&nbsp;</td>
        <td valign="top">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputtable">
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
                <html:form action="/sspcloc" method="POST">
                    <tr>
                        <td colspan="2">
                            <table id="dataTable" width="100%" border="0" class="inputsubtable" cellpadding="2" cellspacing="1">
                                <tr><td colspan="10">&nbsp;</td></tr>
                                <tr>
                                    <td colspan="10" class="sectionheader">
                                        <bean:message key="label.location"/>&nbsp;<bean:message key="label.password"/>: <bean:write name="SpecialistForm" property="locationPwd"/>
                                    </td>
                                </tr>                                
                                <tr>
                                    <th class="nicetableheader" width="20">
                                        &nbsp;
                                    </th>
                                    <th class="nicetableheader" width="136">
                                        <bean:message key="label.name"/>
                                    </th>
                                    <th class="nicetableheader" width="70">
                                        <bean:message key="label.status"/>
                                    </th>
                                    <th class="nicetableheader" width="166">
                                        <bean:message key="label.address1"/>
                                    </th>
                                    <th class="nicetableheader" width="166">
                                        <bean:message key="label.address2"/>
                                    </th>
                                    <th class="nicetableheader" width="85">
                                        <bean:message key="label.city"/>
                                    </th>
                                    <th class="nicetableheader" width="152">
                                        <bean:message key="label.state"/>
                                    </th>
                                    <th class="nicetableheader" width="67">
                                        <bean:message key="label.zipcode"/>
                                    </th>
                                    <th class="nicetableheader" width="235">
                                        <bean:message key="label.country"/>
                                    </th>
                                    <th class="nicetableheader">
                                        <bean:message key="label.telephone"/>
                                    </th>
                                </tr>
                            </table>
                            <bean:size id="rowSize" name="SpecialistForm" property="locationVOs"/>
                            <SCRIPT TYPE="text/javascript">
                                nbrOfRows = '<bean:write name="rowSize"/>';
                            </SCRIPT>
                            <div id="dataTableDiv" style="overflow:auto; width:1250px;height:200px;">
                                <table id="dataTable" width="98.5%" border="0" cellpadding="2" cellspacing="1" >
                                    <nested:iterate id="locationVO" name="SpecialistForm" property="locationVOs" indexId="rowCtr">
                                        <tr>  
                                            <td class="datarowborders" align="center" width="4%">
                                                <nested:checkbox property="selected" onclick="javascript:updateAmount()"/>
                                                <nested:hidden property="id"/>
                                            </td>
                                            <td class="datarowborders" width="15%">
                                                <nested:notEqual property="id" value="-1">
                                                    <nested:text property="locationName"  styleClass="criteriavalue" maxlength="75" size="20"/>
                                                </nested:notEqual>
                                                <nested:equal property="id" value="-1">
                                                    <nested:text property="locationName" styleClass="criteriavalue" maxlength="75" size="30"/>
                                                </nested:equal>
                                            </td>
                                            <td class="datarowborders" width="7%">
                                                <nested:text property="status" readonly="true" size="8" styleClass="criteriavalue"/>
                                            </td>
                                            <td class="datarowborders">
                                                <nested:text property="address1" styleClass="criteriavalue" maxlength="75" size="25"/>
                                            </td>
                                            <td class="datarowborders">
                                                <nested:text property="address2" styleClass="criteriavalue" maxlength="75" size="25"/>
                                            </td>
                                            <td class="datarowborders" >
                                                <nested:text property="city" maxlength="25" size="11" styleClass="criteriavalue"/>
                                            </td>
                                            <td class="datarowborders" >
                                                <nested:select property="state" styleClass="criteriavalue">
                                                    <html:option value="">Select a state</html:option>
                                                    <html:optionsCollection name="SpecialistForm" property="stateBeans"/>
                                                </nested:select>
                                            </td>
                                            <td class="datarowborders" >
                                                <nested:text property="zipCode" maxlength="10" size="8" styleClass="criteriavalue"/>
                                            </td>
                                            <td class="datarowborders" >
                                                <nested:select property="country" styleClass="criteriavalue">
                                                    <html:optionsCollection name="SpecialistForm" property="countryBeans"/>
                                                </nested:select>
                                            </td>
                                            <td class="datarowborders" >
                                                <nested:text property="phone1" maxlength="20" size="14" styleClass="criteriavalue"/>
                                            </td>
                                        </tr>
                                    </nested:iterate>
                                </table>
                            </div>      
                            <logic:notEqual name="SpecialistForm" property="status" value="EXPIRE">
                                <table id="dataTable" width="98.5%" border="0" cellpadding="2" cellspacing="1" 
                                    <tr><td>&nbsp;</td></tr>
                                    <tr>
                                        <td align="center">
                                            <html:submit property="save" styleClass="subtlebutton" onclick="handleSubmit ('sspcloc.spl')"><bean:message key="label.save"/></html:submit>
                                            <html:submit property="add" styleClass="subtlebutton" onclick="handleSubmit ('aspcloc.spl')"><bean:message key="label.add"/></html:submit>
                                            <html:submit property="delete" styleClass="subtlebutton" onclick="handleSubmit ('dspcloc.spl')"><bean:message key="label.delete"/></html:submit>
                                        </td>
                                    </tr>
                                </table>
                            </logic:notEqual>
                        </td>
                    </tr>
                </table>
                <logic:equal name="SpecialistForm" property="operation" value="CHARGE">
                    <table border="0" cellspacing="1" cellpadding="0" width="90%">
                        <tr>
                            <td colspan="2">
                                <fieldset>
                                    <legend>Payment Details</legend>
                                    <table cellspacing="0" cellpadding="0" border="0" width="100%">
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td class="criteriavalue">
                                                Each location will be billed per month through the end of your contract.
                                            </td>
                                        </tr>
                                         <tr>
                                            <td class="datarowborders" >
                                                <div class="specCriteriaTextLabelsRight"><bean:message key="label.amtcharged"/>&nbsp;</div>
                                            </td>
                                            <td class="datarowborders" >
                                                <html:text name="SpecialistForm" property="amount" readonly="true" size="10" styleClass="criteriavalue"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="datarowborders" >
                                                <div class="specCriteriaTextLabelsRight"><bean:message key="label.cctype"/>&nbsp;</div>
                                            </td>
                                            <td class="datarowborders" >
                                                <html:select name="SpecialistForm" property="cctype" styleClass="criteriavalue">
                                                    <html:optionsCollection name="SpecialistForm" property="ccTypeBeans"/>
                                                </html:select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="datarowborders" >
                                                <div class="specCriteriaTextLabelsRight"><bean:message key="label.ccnbr"/>&nbsp;</div>
                                            </td>
                                            <td class="datarowborders" >
                                                <html:text name="SpecialistForm" property="ccNbr" styleClass="criteriavalue"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="datarowborders" >
                                                <div class="specCriteriaTextLabelsRight"><bean:message key="label.seccode"/>&nbsp;</div>
                                            </td>
                                            <td class="datarowborders" >
                                                <html:password name="SpecialistForm" property="secCode"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="datarowborders" >
                                                <div class="specCriteriaTextLabelsRight"><bean:message key="label.expmon"/>&nbsp;</div>
                                            </td>
                                            <td class="datarowborders" >
                                                <html:select name="SpecialistForm" property="expmon" styleClass="criteriavalue">
                                                    <html:options name="SpecialistForm" property="months"/>
                                                </html:select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="datarowborders" >
                                                <div class="specCriteriaTextLabelsRight"><bean:message key="label.expyear"/>&nbsp;</div>
                                            </td>
                                            <td class="datarowborders" >
                                                <html:select name="SpecialistForm" property="expyear" styleClass="criteriavalue">
                                                    <html:options name="SpecialistForm" property="years"/>
                                                </html:select>
                                            </td>
                                        </tr>
                                        <tr><td colspan="2">&nbsp;</td></tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>
                                                <html:submit property="submit" styleClass="subtlebutton" onclick="javascript:handleSubmit('pspcloc.spl')"><bean:message key="label.submit"/></html:submit>
                                            </td>                                        
                                        </tr>
                                    </fieldset>
                                </td>
                            </tr>
                        </table>
                    </logic:equal>
                </html:form>
            </table>
        </td>
    </tr>
</table>