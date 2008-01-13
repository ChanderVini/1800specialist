<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<title><bean:message key="label.specialist"/>&nbsp;<bean:message key="label.profile"/></title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr height="15">
        <td width="2%">&nbsp;</td>
        <td class="labelLeft"><b>Welcome: <bean:write name="SpecialistForm" property="displayname"/></b></td>
    </tr>        
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
                     <li id="accinfo" class="tabberinactive">
                        <html:link action="saccinfo.spl"><bean:message key="label.accinfo"/></html:link>
                    </li>
                    <li id="profile" class="tabberactive">
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
                <html:form action="/savsprofile" method="POST">
                    <tr>
                        <td colspan="2">
                            <table id="dataTable" width="100%" border="0" class="inputsubtable" cellpadding="0" cellspacing="1">
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td class="specCriteriaTextLabelsRight" width="200">
                                        <bean:message key="label.fname"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="SpecialistForm" property="userVO.firstName" styleClass="criteriavalue" maxlength="50" size="30" tabindex="1"/>&nbsp;
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
                                        <html:text name="SpecialistForm" property="userVO.lastName" styleClass="criteriavalue" maxlength="50" size="30" tabindex="2"/>&nbsp;
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
                                        <html:text name="SpecialistForm" property="userVO.address1" styleClass="criteriavalue" maxlength="75" size="40"  tabindex="3"/>&nbsp;
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
                                        <html:text name="SpecialistForm" property="userVO.address2" styleClass="criteriavalue" maxlength="75" size="40"  tabindex="4"/>&nbsp;
                                    </td>
                                </tr>                               
                                <tr>
                                    <td class="specCriteriaTextLabelsRight">
                                        <bean:message key="label.city"/>&nbsp;<font color="red">*</font>:&nbsp;&nbsp;
                                    </td>
                                    <td class="specCriteriaTextLabels">
                                        <html:text name="SpecialistForm" property="userVO.city" styleClass="criteriavalue" maxlength="25" size="20"  tabindex="5"/>&nbsp;
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
                                        <html:select  name="SpecialistForm" property="userVO.state" tabindex="6" styleClass="criteriavalue"  tabindex="6">
                                            <html:option value="">Select One</html:option>
                                            <html:option value="AL">Alabama</html:option>
                                            <html:option value="AK">Alaska</html:option>
                                            <html:option value="AZ">Arizona</html:option>
                                            <html:option value="AR">Arkansas</html:option>
                                            <html:option value="CA">California</html:option>
                                            <html:option value="CO">Colorado</html:option>
                                            <html:option value="CT">Connecticut</html:option>
                                            <html:option value="DE">Delaware</html:option>
                                            <html:option value="DC">District of Columbia</html:option>
                                            <html:option value="FL">Florida</html:option>
                                            <html:option value="GA">Georgia</html:option>
                                            <html:option value="HI">Hawaii</html:option>
                                            <html:option value="ID">Idaho</html:option>
                                            <html:option value="IL">Illinois</html:option>
                                            <html:option value="IN">Indiana</html:option>
                                            <html:option value="IA">Iowa</html:option>
                                            <html:option value="KS">Kansas</html:option>
                                            <html:option value="KY">Kentucky</html:option>
                                            <html:option value="LA">Louisiana</html:option>
                                            <html:option value="ME">Maine</html:option>
                                            <html:option value="MD">Maryland</html:option>
                                            <html:option value="MA">Massachusetts</html:option>
                                            <html:option value="MI">Michigan</html:option>
                                            <html:option value="MN">Minnesota</html:option>
                                            <html:option value="MS">Mississippi</html:option>
                                            <html:option value="MO">Missouri</html:option>
                                            <html:option value="MT">Montana</html:option>
                                            <html:option value="NE">Nebraska</html:option>
                                            <html:option value="NV">Nevada</html:option>
                                            <html:option value="NH">New Hampshire</html:option>
                                            <html:option value="NJ">New Jersey</html:option>
                                            <html:option value="NM">New Mexico</html:option>
                                            <html:option value="NY">New York</html:option>
                                            <html:option value="NC">North Carolina</html:option>
                                            <html:option value="ND">North Dakota</html:option>
                                            <html:option value="OH">Ohio</html:option>
                                            <html:option value="OK">Oklahoma</html:option>
                                            <html:option value="OR">Oregon</html:option>
                                            <html:option value="PA">Pennsylvania</html:option>
                                            <html:option value="PR">Puerto Rico</html:option>
                                            <html:option value="RI">Rhode Island</html:option>
                                            <html:option value="SC">South Carolina</html:option>
                                            <html:option value="SD">South Dakota</html:option>
                                            <html:option value="TN">Tennessee</html:option>
                                            <html:option value="TX">Texas</html:option>
                                            <html:option value="UT">Utah</html:option>
                                            <html:option value="VT">Vermont</html:option>
                                            <html:option value="VA">Virginia</html:option>
                                            <html:option value="WA">Washington</html:option>
                                            <html:option value="DC">Washington D.C.</html:option>
                                            <html:option value="WV">West Virginia</html:option>
                                            <html:option value="WI">Wisconsin</html:option>
                                            <html:option value="WY">Wyoming</html:option>
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
                                        <html:text name="SpecialistForm" property="userVO.zipcode" styleClass="criteriavalue" maxlength="6" size="6"  tabindex="7"/>&nbsp;
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
                                        <html:select  name="SpecialistForm" property="userVO.country" styleClass="criteriavalue" tabindex="8">
                                            <html:option value="US">United States</html:option>
                                            <html:option value="CA">Canada</html:option>
                                            <html:option value="UK">United Kingdom</html:option>
                                            <html:option value="AU">Australia</html:option>
                                            <html:option value="NZ">New Zealand (Aotearoa)</html:option>
                                            <html:option value="NL">Netherlands</html:option>
                                            <html:option value="FR">France</html:option>
                                            <html:option value="AF">Afghanistan</html:option>
                                            <html:option value="AL">Albania</html:option>
                                            <html:option value="DZ">Algeria</html:option>
                                            <html:option value="AS">American Samoa</html:option>
                                            <html:option value="AD">Andorra</html:option>
                                            <html:option value="AO">Angola</html:option>
                                            <html:option value="AI">Anguilla</html:option>
                                            <html:option value="AQ">Antarctica</html:option>
                                            <html:option value="AG">Antigua and Barbuda</html:option>
                                            <html:option value="AR">Argentina</html:option>
                                            <html:option value="AM">Armenia</html:option>
                                            <html:option value="AW">Aruba</html:option>
                                            <html:option value="AT">Austria</html:option>
                                            <html:option value="AZ">Azerbaijan</html:option>
                                            <html:option value="BS">Bahamas</html:option>
                                            <html:option value="BH">Bahrain</html:option>
                                            <html:option value="BD">Bangladesh</html:option>
                                            <html:option value="BB">Barbados</html:option>
                                            <html:option value="BY">Belarus</html:option>
                                            <html:option value="BE">Belgium</html:option>
                                            <html:option value="BZ">Belize</html:option>
                                            <html:option value="BJ">Benin</html:option>
                                            <html:option value="BM">Bermuda</html:option>
                                            <html:option value="BT">Bhutan</html:option>
                                            <html:option value="BO">Bolivia</html:option>
                                            <html:option value="BA">Bosnia and Herzegovin</html:option>a
                                            <html:option value="BW">Botswana</html:option>
                                            <html:option value="BV">Bouvet Island</html:option>
                                            <html:option value="BR">Brazil</html:option>
                                            <html:option value="IO">British Indian Ocean Territory</html:option>
                                            <html:option value="BN">Brunei Darussalam</html:option>
                                            <html:option value="BG">Bulgaria</html:option>
                                            <html:option value="BF">Burkina Faso</html:option>
                                            <html:option value="BI">Burundi</html:option>
                                            <html:option value="KH">Cambodia</html:option>
                                            <html:option value="CM">Cameroon</html:option>
                                            <html:option value="CV">Cape Verde</html:option>
                                            <html:option value="KY">Cayman Islands</html:option>
                                            <html:option value="CF">Central African Republic</html:option>
                                            <html:option value="TD">Chad</html:option>
                                            <html:option value="CL">Chile</html:option>
                                            <html:option value="CN">China</html:option>
                                            <html:option value="CX">Christmas Island</html:option>
                                            <html:option value="CC">Cocos (Keeling) Islands</html:option>
                                            <html:option value="CO">Colombia</html:option>
                                            <html:option value="KM">Comoros</html:option>
                                            <html:option value="CG">Congo</html:option>
                                            <html:option value="CK">Cook Islands</html:option>
                                            <html:option value="CR">Costa Rica</html:option>
                                            <html:option value="CI">Cote D'Ivoire (Ivory Coast)</html:option>
                                            <html:option value="HR">Croatia (Hrvatska)</html:option>
                                            <html:option value="CU">Cuba</html:option>
                                            <html:option value="CY">Cyprus</html:option>
                                            <html:option value="CZ">Czech Republic</html:option>
                                            <html:option value="CS">Czechoslovakia (former)</html:option>
                                            <html:option value="DK">Denmark</html:option>
                                            <html:option value="DJ">Djibouti</html:option>
                                            <html:option value="DM">Dominica</html:option>
                                            <html:option value="DO">Dominican Republic</html:option>
                                            <html:option value="TP">East Timor</html:option>
                                            <html:option value="EC">Ecuador</html:option>
                                            <html:option value="EG">Egypt</html:option>
                                            <html:option value="SV">El Salvador</html:option>
                                            <html:option value="GQ">Equatorial Guinea</html:option>
                                            <html:option value="ER">Eritrea</html:option>
                                            <html:option value="EE">Estonia</html:option>
                                            <html:option value="ET">Ethiopia</html:option>
                                            <html:option value="FK">Falkland Islands (Malvinas)</html:option>
                                            <html:option value="FO">Faroe Islands</html:option>
                                            <html:option value="FJ">Fiji</html:option>
                                            <html:option value="FI">Finland</html:option>
                                            <html:option value="GF">French Guiana</html:option>
                                            <html:option value="PF">French Polynesia</html:option>
                                            <html:option value="TF">French Southern Territories</html:option>
                                            <html:option value="GA">Gabon</html:option>
                                            <html:option value="GM">Gambia</html:option>
                                            <html:option value="GE">Georgia</html:option>
                                            <html:option value="DE">Germany</html:option>
                                            <html:option value="GH">Ghana</html:option>
                                            <html:option value="GI">Gibraltar</html:option>
                                            <html:option value="GR">Greece</html:option>
                                            <html:option value="GL">Greenland</html:option>
                                            <html:option value="GD">Grenada</html:option>
                                            <html:option value="GP">Guadeloupe</html:option>
                                            <html:option value="GU">Guam</html:option>
                                            <html:option value="GT">Guatemala</html:option>
                                            <html:option value="GN">Guinea</html:option>
                                            <html:option value="GW">Guinea-Bissau</html:option>
                                            <html:option value="GY">Guyana</html:option>
                                            <html:option value="HT">Haiti</html:option>
                                            <html:option value="HM">Heard and McDonald Islands</html:option>
                                            <html:option value="HN">Honduras</html:option>
                                            <html:option value="HK">Hong Kong</html:option>
                                            <html:option value="HU">Hungary</html:option>
                                            <html:option value="IS">Iceland</html:option>
                                            <html:option value="IN">India</html:option>
                                            <html:option value="ID">Indonesia</html:option>
                                            <html:option value="IQ">Iraq</html:option>
                                            <html:option value="IE">Ireland</html:option>
                                            <html:option value="IL">Israel</html:option>
                                            <html:option value="IT">Italy</html:option>
                                            <html:option value="JM">Jamaica</html:option>
                                            <html:option value="JP">Japan</html:option>
                                            <html:option value="JO">Jordan</html:option>
                                            <html:option value="KZ">Kazakhstan</html:option>
                                            <html:option value="KE">Kenya</html:option>
                                            <html:option value="KI">Kiribati</html:option>
                                            <html:option value="KR">Korea (South)</html:option>
                                            <html:option value="KW">Kuwait</html:option>
                                            <html:option value="KG">Kyrgyzstan</html:option>
                                            <html:option value="LA">Laos</html:option>
                                            <html:option value="LV">Latvia</html:option>
                                            <html:option value="LB">Lebanon</html:option>
                                            <html:option value="LS">Lesotho</html:option>
                                            <html:option value="LR">Liberia</html:option>
                                            <html:option value="LY">Libya</html:option>
                                            <html:option value="LI">Liechtenstein</html:option>
                                            <html:option value="LT">Lithuania</html:option>
                                            <html:option value="LU">Luxembourg</html:option>
                                            <html:option value="MO">Macau</html:option>
                                            <html:option value="MK">Macedonia</html:option>
                                            <html:option value="MG">Madagascar</html:option>
                                            <html:option value="MW">Malawi</html:option>
                                            <html:option value="MY">Malaysia</html:option>
                                            <html:option value="MV">Maldives</html:option>
                                            <html:option value="ML">Mali</html:option>
                                            <html:option value="MT">Malta</html:option>
                                            <html:option value="MH">Marshall Islands</html:option>
                                            <html:option value="MQ">Martinique</html:option>
                                            <html:option value="MR">Mauritania</html:option>
                                            <html:option value="MU">Mauritius</html:option>
                                            <html:option value="YT">Mayotte</html:option>
                                            <html:option value="MX">Mexico</html:option>
                                            <html:option value="FM">Micronesia</html:option>
                                            <html:option value="MD">Moldova</html:option>
                                            <html:option value="MC">Monaco</html:option>
                                            <html:option value="MN">Mongolia</html:option>
                                            <html:option value="MS">Montserrat</html:option>
                                            <html:option value="MA">Morocco</html:option>
                                            <html:option value="MZ">Mozambique</html:option>
                                            <html:option value="MM">Myanmar</html:option>
                                            <html:option value="NA">Namibia</html:option>
                                            <html:option value="NR">Nauru</html:option>
                                            <html:option value="NP">Nepal</html:option>
                                            <html:option value="AN">Netherlands Antilles</html:option>
                                            <html:option value="NT">Neutral Zone</html:option>
                                            <html:option value="NC">New Caledonia</html:option>
                                            <html:option value="NI">Nicaragua</html:option>
                                            <html:option value="NE">Niger</html:option>
                                            <html:option value="NG">Nigeria</html:option>
                                            <html:option value="NU">Niue</html:option>
                                            <html:option value="NF">Norfolk Island</html:option>
                                            <html:option value="MP">Northern Mariana Islands</html:option>
                                            <html:option value="NO">Norway</html:option>
                                            <html:option value="OM">Oman</html:option>
                                            <html:option value="PK">Pakistan</html:option>
                                            <html:option value="PW">Palau</html:option>
                                            <html:option value="PA">Panama</html:option>
                                            <html:option value="PG">Papua New Guinea</html:option>
                                            <html:option value="PY">Paraguay</html:option>
                                            <html:option value="PE">Peru</html:option>
                                            <html:option value="PH">Philippines</html:option>
                                            <html:option value="PN">Pitcairn</html:option>
                                            <html:option value="PL">Poland</html:option>
                                            <html:option value="PT">Portugal</html:option>
                                            <html:option value="PR">Puerto Rico</html:option>
                                            <html:option value="QA">Qatar</html:option>
                                            <html:option value="RE">Reunion</html:option>
                                            <html:option value="RO">Romania</html:option>
                                            <html:option value="RU">Russian Federation</html:option>
                                            <html:option value="RW">Rwanda</html:option>
                                            <html:option value="GS">S. Georgia And S. Sandwich Isls.</html:option>
                                            <html:option value="KN">Saint Kitts and Nevis</html:option>
                                            <html:option value="LC">Saint Lucia</html:option>
                                            <html:option value="WS">Samoa</html:option>
                                            <html:option value="SM">San Marino</html:option>
                                            <html:option value="ST">Sao Tome and Principe</html:option>
                                            <html:option value="SA">Saudi Arabia</html:option>
                                            <html:option value="SN">Senegal</html:option>
                                            <html:option value="SC">Seychelles</html:option>
                                            <html:option value="SL">Sierra Leone</html:option>
                                            <html:option value="SG">Singapore</html:option>
                                            <html:option value="SK">Slovak Republic</html:option>
                                            <html:option value="SI">Slovenia</html:option>
                                            <html:option value="SB">Solomon Islands</html:option>
                                            <html:option value="SO">Somalia</html:option>
                                            <html:option value="ZA">South Africa</html:option>
                                            <html:option value="ES">Spain</html:option>
                                            <html:option value="LK">Sri Lanka</html:option>
                                            <html:option value="SH">St. Helena</html:option>
                                            <html:option value="PM">St. Pierre and Miquelon</html:option>
                                            <html:option value="VC">St. Vincent And the Grenadines</html:option>
                                            <html:option value="SD">Sudan</html:option>
                                            <html:option value="SR">Suriname</html:option>
                                            <html:option value="SJ">Svalbard and Jan Mayen Isls.</html:option>
                                            <html:option value="SZ">Swaziland</html:option>
                                            <html:option value="SE">Sweden</html:option>
                                            <html:option value="CH">Switzerland</html:option>
                                            <html:option value="TW">Taiwan</html:option>
                                            <html:option value="TJ">Tajikistan</html:option>
                                            <html:option value="TZ">Tanzania</html:option>
                                            <html:option value="TH">Thailand</html:option>
                                            <html:option value="TG">Togo</html:option>
                                            <html:option value="TK">Tokelau</html:option>
                                            <html:option value="TO">Tonga</html:option>
                                            <html:option value="TT">Trinidad and Tobago</html:option>
                                            <html:option value="TN">Tunisia</html:option>
                                            <html:option value="TR">Turkey</html:option>
                                            <html:option value="TM">Turkmenistan</html:option>
                                            <html:option value="TC">Turks and Caicos Islands</html:option>
                                            <html:option value="TV">Tuvalu</html:option>
                                            <html:option value="UM">US Minor Outlying Islands</html:option>
                                            <html:option value="UG">Uganda</html:option>
                                            <html:option value="UA">Ukraine</html:option>
                                            <html:option value="AE">United Arab Emirate</html:option>
                                            <html:option value="UY">Uruguay</html:option>
                                            <html:option value="UZ">Uzbekistan</html:option>
                                            <html:option value="VU">Vanuatu</html:option>
                                            <html:option value="VA">Vatican City State</html:option>
                                            <html:option value="VE">Venezuela</html:option>
                                            <html:option value="VN">Viet Nam</html:option>
                                            <html:option value="VG">Virgin Islands (British)</html:option>
                                            <html:option value="VI">Virgin Islands (U.S.)</html:option>
                                            <html:option value="WF">Wallis and Futuna Islands</html:option>
                                            <html:option value="EH">Western Sahara</html:option>
                                            <html:option value="YE">Yemen</html:option>
                                            <html:option value="YU">Yugoslavia</html:option>
                                            <html:option value="ZR">Zaire</html:option>
                                            <html:option value="ZM">Zambia</html:option>
                                            <html:option value="ZW">Zimbabwe</html:option>
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
                                        <html:text name="SpecialistForm" property="userVO.phone1" styleClass="criteriavalue" maxlength="20" size="20"  tabindex="9"/>&nbsp;
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
                                        <html:text name="SpecialistForm" property="userVO.fax" styleClass="criteriavalue" maxlength="20" size="20"  tabindex="10"/>&nbsp;
                                        <html:messages id="error" message="true" property="errors.label.fax">
                                            <font color="red"><bean:write name="error"/></font>
                                        </html:messages>                                            
                                    </td>
                                </tr>              
                            </table>
                        </td>
                    </tr>
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr>
                        <td colspan="2" align="center">           
                            <html:submit property="submit" value="Save" styleClass="subtlebutton"><bean:message key="label.save"/></html:submit>
                            <html:reset property="reset" value="Reset" styleClass="subtlebutton"><bean:message key="label.reset"/></html:reset>
                        </td>
                    </tr>
                </html:form>
            </table>
        </tr>
    </td>
</table>