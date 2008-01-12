<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:html>
    <head>
        <META Http-Equiv="Cache-Control" Content="no-cache">
        <META Http-Equiv="Pragma" Content="no-cache">
        <META Http-Equiv="Expires" Content="0"> 
        <link href="<html:rewrite page="/css/spl.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<html:rewrite page="/css/tab.css"/>" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="js/common.js"></script> 
    </head>
    <body marginwidth="0" marginheight="0" leftmargin="10" topmargin="0">
        <tiles:insert attribute="header" />
        <tiles:insert attribute="body" />
        <tiles:insert attribute="footer" />
    </body>
</html:html>
