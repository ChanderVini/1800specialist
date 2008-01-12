<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<title>Generalist Download</title>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
        <td width="2%">&nbsp;</td>
        <td width="273">
            <div class="tabberlive">
                <ul class="tabbernav">    
                    <li id="admin" class="tabberinactive">
                        <html:link action="admin.spl">
                            <bean:message key="label.admin"/>
                        </html:link>
                    </li>
                    <li id="specialist" class="tabberinactive">
                        <html:link action="spec.spl">
                            <bean:message key="label.specialist"/>
                        </html:link>
                    </li>
                    <li id="generalist" class="tabberinactive">
                        <html:link action="generalist.spl">
                            <bean:message key="label.generalist"/>
                        </html:link>
                    </li>
                </ul>
            </div>
        </td>
    </tr>
</table>