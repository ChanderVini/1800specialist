<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<title><bean:message key="label.specialist"/>&nbsp;<bean:message key="label.registration"/></title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="900">
            <div class="tabberlive">
                <ul class="tabbernav">    
                    <li id="login" class="tabberinactive">
                        <a href="spec.spl"><bean:message key="label.login"/></a>
                    </li>
                    <li id="register" class="tabberactive">
                        <html:link action="spcreg.spl"><bean:message key="label.register"/></html:link>
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
                <html:form action="/spcregsub" method="POST">
                    <tr>
                        <td colspan="2">
                            <table id="dataTable" width="100%" border="0" class="inputsubtable"  cellpadding="0" cellspacing="1">
                                <tr>
                                    <td class="specCriteriaTextLabelsRight" width="200">
                                        <bean:message key="label.email"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.username" styleClass="criteriavalue" maxlength="50" size="40"/>&nbsp;
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
                                        <bean:message key="label.password"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:password name="RegisterForm" property="userVO.password" styleClass="criteriavalue" maxlength="15" size="15"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.password">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>         
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="specCriteriaTextLabels">
                                        <bean:message key="label.passwordmsg"/>
                                    </td>
                                </tr>                                
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.repassword"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:password name="RegisterForm" property="repassword" styleClass="criteriavalue" maxlength="15" size="15"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.repassword">
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
                                        <html:text name="RegisterForm" property="userVO.company" styleClass="criteriavalue" maxlength="50" size="30"/>&nbsp;
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
                                        <html:text name="RegisterForm" property="userVO.firstName" styleClass="criteriavalue" maxlength="50" size="30"/>&nbsp;
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
                                        <html:text name="RegisterForm" property="userVO.lastName" styleClass="criteriavalue" maxlength="50" size="30"/>&nbsp;
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
                                        <html:text name="RegisterForm" property="userVO.address1" styleClass="criteriavalue" maxlength="75" size="40"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.address1">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>
                                    </td>
                                </tr>   
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.address2"/>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.address2" styleClass="criteriavalue" maxlength="75" size="40"/>&nbsp;
                                    </td>
                                </tr>                               
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.city"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="userVO.city" styleClass="criteriavalue" maxlength="25" size="20"/>&nbsp;
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
                                        <html:select  name="RegisterForm" property="userVO.state"styleClass="criteriavalue">
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
                                        <html:text name="RegisterForm" property="userVO.zipcode" styleClass="criteriavalue" maxlength="6" size="6"/>&nbsp;
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
                                        <html:select  name="RegisterForm" property="userVO.country" styleClass="criteriavalue">
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
                                        <html:text name="RegisterForm" property="userVO.phone1" styleClass="criteriavalue" maxlength="20" size="20"/>&nbsp;
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
                                        <html:text name="RegisterForm" property="userVO.fax" styleClass="criteriavalue" maxlength="20" size="20"/>&nbsp;
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
                        <td colspan="2">
                        <table id="dataTable" width="100%" border="0" class="inputsubtable"  cellpadding="0" cellspacing="1">
                            <tr>
                                <td class="specCriteriaTextLabelsRight" width="200">
                                    <bean:message key="label.amtcharged"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels" >
                                    <html:text name="RegisterForm" property="setupFee" size="10" styleClass="criteriavalue"/>
                                    <html:messages id="error" message="true" property="errors.label.amtcharged">
                                        <font color="red"><bean:write name="error"/></font>
                                    </html:messages>
                                </td>
                            </tr>
                            <tr>
                                <td  class="specCriteriaTextLabelsRight">
                                    Credit Card Type&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels" >
                                    <html:select name="RegisterForm" property="cctype" styleClass="criteriavalue">
                                        <html:optionsCollection name="RegisterForm" property="ccTypeBeans"/>
                                    </html:select>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight">
                                    <bean:message key="label.ccnbr"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels" >
                                    <html:text name="RegisterForm" property="ccNbr" styleClass="criteriavalue"/>
                                    <html:messages id="error" message="true" property="errors.label.ccnbr">
                                        <font color="red"><bean:write name="error"/></font>
                                    </html:messages>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight">
                                    <bean:message key="label.seccode"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels" >
                                    <html:password name="RegisterForm" property="secCode" size="4"/>
                                    <html:messages id="error" message="true" property="errors.label.seccode">
                                        <font color="red"><bean:write name="error"/></font>
                                    </html:messages>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight">
                                    <bean:message key="label.expmon"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels" >
                                    <html:select name="RegisterForm" property="expmon" styleClass="criteriavalue">
                                        <html:options name="RegisterForm" property="months"/>
                                    </html:select>
                                </td>
                            </tr>
                            <tr>
                                <td class="specCriteriaTextLabelsRight">
                                    <bean:message key="label.expyear"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                </td>
                                <td class="specCriteriaTextLabels" >
                                    <html:select name="RegisterForm" property="expyear" styleClass="criteriavalue">
                                        <html:options name="RegisterForm" property="years"/>
                                    </html:select>
                                </td>
                            </tr>
                        </table>
                    </tr>
                    <tr><td colspan="2"><hr></td></tr>
                    <tr>
                        <td>
                            <table id="dataTable" width="100%" border="0" class="inputsubtable" cellpadding="0" cellspacing="1">
                                <tr>
                                    <td width="200" class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.hearus"/>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:select name="RegisterForm" property="hearus" styleClass="criteriavalue">
                                            <html:option value="">Please select ...</html:option>
                                            <html:option value="Google">Google</html:option>
                                            <html:option value="MSN">MSN</html:option>
                                            <html:option value="Yahoo!">Yahoo!</html:option>
                                            <html:option value="Clusty">Clusty</html:option>
                                            <html:option value="Other Search Engine">Other Search Engine</html:option>
                                            <html:option value="Blog">Blog</html:option>
                                            <html:option value="Friend">Friend</html:option>
                                            <html:option value="Other">Other</html:option>
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="specCriteriaTextLabels">
                                        <bean:message key="label.promotionmsg"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.promotion"/>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="RegisterForm" property="promotionalCd" styleClass="criteriavalue" maxlength="50" size="40"/>&nbsp;                                           
                                    </td>
                                </tr> 
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="specCriteriaTextLabels">
                                        <html:checkbox name="RegisterForm" property="agree" styleClass="criteriavalue"/>
                                        <bean:message key="label.terms.agree"/>&nbsp;<a href="#" onclick="window.open ('spcterms.spl')"><bean:message key="label.terms.condn"/></a>&nbsp;<bean:message key="label.terms.services"/><font color="red">*</font>
                                        <html:messages id="error" message="true" property="errors.label.terms">
                                            &nbsp;<font color="red"><bean:write name="error"/></font>
                                        </html:messages>      
                                    </td>
                                </tr>                                     
                            </table>
                        </td>
                    </tr>                          
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