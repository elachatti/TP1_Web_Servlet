<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>expression.jsp</title></head>
<body>
<h2>Expression JSP</h2>
<%-- Une expression <%= ... %> est evaluee puis ecrite dans la page --%>
<p>Nombre aleatoire : <b><%= Math.random() %></b></p>
<p>Rechargez la page : la valeur change a chaque requete.</p>
</body>
</html>
