<%-- 
    Document   : newjsp
    Created on : Feb 17, 2025, 10:06:20 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%= (new java.util.Date()).toLocaleString() %>
    
    <%!
        int data = 20;
    %>
    <%
        out.println(data);
    %>
    <%= data %>
    
    <%
        int data = 100;
    %>
    <%
        out.println(data);
    %>
    
    <%!
        int sum(int a, int b) {
            return a + b;
        }
    %>
    <%= sum(5, 35) %>
    </body>
</html>
