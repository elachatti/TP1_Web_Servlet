<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>young.jsp</title></head>
<body>
<%-- type= et non class= : la JSP ne cree pas l'objet, elle utilise celui du Controleur --%>
<jsp:useBean id="person" type="lab.mvc.model.Person" scope="request"/>

<h2>Vue young.jsp : personne nee en 1990 ou apres</h2>
<table border="1" cellpadding="5">
    <tr><th>First name</th><td><jsp:getProperty name="person" property="firstName"/></td></tr>
    <tr><th>Last name</th><td><jsp:getProperty name="person" property="lastName"/></td></tr>
    <tr><th>Birth date</th><td><jsp:getProperty name="person" property="birthDay"/></td></tr>
    <tr><th>Sex</th><td><jsp:getProperty name="person" property="sex"/></td></tr>
</table>
<p><a href="mvc.html">Retour</a></p>
</body>
</html>
