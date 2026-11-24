<%@ page contentType="text/html;charset=UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head><title>bean.jsp</title></head>
<body>
<%-- 1) Creation (ou recuperation) du bean dans la portee "request" --%>
<jsp:useBean id="person" class="isep.lab1.bean.Person" scope="request"/>

<%-- 2) property="*" : chaque parametre du formulaire va dans la propriete de meme nom --%>
<jsp:setProperty name="person" property="*"/>

<h2>Personne enregistree dans le bean</h2>
<table border="1" cellpadding="5">
    <tr><th>First name</th><th>Last name</th><th>Birth date</th><th>Sex</th></tr>
    <tr>
        <%-- 3) Lecture depuis le bean --%>
        <td><jsp:getProperty name="person" property="firstName"/></td>
        <td><jsp:getProperty name="person" property="lastName"/></td>
        <td><jsp:getProperty name="person" property="birthDay"/></td>
        <td><jsp:getProperty name="person" property="sex"/></td>
    </tr>
</table>
<p><a href="form-jsp.html">Retour</a></p>
</body>
</html>
