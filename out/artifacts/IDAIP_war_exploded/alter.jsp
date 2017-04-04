<%@ page import="core.Conexion" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Conexion conx = new Conexion();

    ResultSet res = conx.stm.executeQuery("select * from ARTICULOS");
%>

<html>
<head>
    <title>Alter index</title>
</head>
<body>

    <%
        while (res.next()){
            out.print(res.getString("articulo_clave")+"<br>");
        }
    %>

</body>
</html>
