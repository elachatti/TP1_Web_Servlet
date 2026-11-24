<%@ page contentType="text/html;charset=UTF-8" import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head><title>scriptlet.jsp</title></head>
<body>
<h2>Scriptlet JSP</h2>
<%
    // Un scriptlet contient du code Java execute a chaque requete
    Date now = new Date();
    String host = request.getRemoteHost();
    out.println("<p>Heure courante : <b>" + now + "</b></p>");
    out.println("<p>Adresse (request.getRemoteHost()) : <b>" + host + "</b></p>");
%>
</body>
</html>
