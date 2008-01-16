<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<title><bean:message key="label.generalist"/>&nbsp;<bean:message key="label.registration"/></title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="900">
            <div class="tabberlive">
                <ul class="tabbernav">    
                    <li id="login" class="tabberinactive">
                        <a href="generalist.spl"><bean:message key="label.login"/></a>
                    </li>
                    <li id="register" class="tabberactive">
                        <html:link action="genreg.spl"><bean:message key="label.register"/></html:link>
                    </li>
                </ul>
            </div>
        </td>
    </tr>
    <tr>
        <td width="2%">&nbsp;</td>
        <td valign="top">
            <table cellspacing="0" cellpadding="0" border="0" class="inputtable" width="900">
                <logic:messagesPresent name="errors" message="true">
                    <tr>
                        <td class="labelLeft" colspan="2">
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
                <tr><td colspan="2">&nbsp;</td></tr>                
                <html:form action="/genregsub" method="POST">
                    <tr>
                        <td colspan="2">
                            <table id="dataTable" width="100%" border="0" class="inputsubtable"  cellpadding="0" cellspacing="1">
                                <tr>
                                    <td class="specCriteriaTextLabelsRight" width="200">
                                        <bean:message key="label.email"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.username" styleClass="criteriavalue" maxlength="50" size="40"  tabindex="1"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.email">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>         
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="specCriteriaTextLabels">
                                        <bean:message key="label.emailmsg"/>
                                    </td>
                                </tr>                                
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.download"/>&nbsp;<bean:message key="label.password"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:password name="RegisterForm" property="userVO.locationPwd" styleClass="criteriavalue" maxlength="15" size="15"  tabindex="5"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.locpwd">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>    
                                <tr><td colspan="2"><hr></td></tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight" width="200">
                                        <bean:message key="label.company"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.company" styleClass="criteriavalue" maxlength="50" size="30" tabindex="7"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.company">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight" width="200">
                                        <bean:message key="label.fname"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.firstName" styleClass="criteriavalue" maxlength="50" size="30" tabindex="8"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.fname">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.lname"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.lastName" styleClass="criteriavalue" maxlength="50" size="30" tabindex="9"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.lname">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.address1"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.address1" styleClass="criteriavalue" maxlength="75" size="40"  tabindex="10"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.address1">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>
                                    </td>
                                </tr>   
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.address2"/>&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.address2" styleClass="criteriavalue" maxlength="75" size="40"  tabindex="11"/>&nbsp;
                                    </td>
                                </tr>                               
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.city"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.city" styleClass="criteriavalue" maxlength="25" size="20"  tabindex="12"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.city">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>
                                    </td>
                                </tr>                               
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.state"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:select  name="RegisterForm" property="userVO.state" tabindex="6" styleClass="criteriavalue"  tabindex="13">
                                             <html:option value="">Select a state</html:option>
                                                <html:optionsCollection name="RegisterForm" property="stateBeans"/>
                                        </html:select>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.state">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>                               
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.zipcode"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.zipcode" styleClass="criteriavalue" maxlength="10" size="8"  tabindex="14"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.zipcode">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>      
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.country"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:select  name="RegisterForm" property="userVO.country" styleClass="criteriavalue" tabindex="15">
                                            <nested:select property="country" styleClass="criteriavalue">
                                                <html:optionsCollection name="RegisterForm" property="countryBeans"/>
                                            </nested:select>
                                        </html:select>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.country">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>                                  
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.telephone"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.phone1" styleClass="criteriavalue" maxlength="20" size="20"  tabindex="16"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.telephone">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>     
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.fax"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.fax" styleClass="criteriavalue" maxlength="20" size="20"  tabindex="17"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.fax">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>              
                            </table>
                        </td>
                    </tr>
                    <tr><td colspan="2"><hr></td></tr>                    
                    
                    <tr>
                        <td colspan="2" align="center">
                            <html:hidden name="RegisterForm" property="userVO.userType" value="SPEC"/>                            
                            <html:submit property="submit" value="Signup" styleClass="subtlebutton"><bean:message key="label.signup"/></html:submit>
                            <html:reset property="reset" value="Reset" styleClass="subtlebutton"><bean:message key="label.reset"/></html:reset>
                        </td>
                    </tr>
                </html:form>
            </table>
        </td>
    </tr>
</table>
<script type="text/javascript">
    document.getElementsByName ('userVO.username')[0].focus();
</script>