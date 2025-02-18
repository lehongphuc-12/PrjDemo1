<%-- 
    Document   : CateColumn
    Created on : Feb 15, 2025, 11:30:25 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div style="width: 80%; margin: 10px auto;">
    <c:forEach var="cateRoot" items="${cateParent}">
        <details >   
            <summary style="list-style: none"><a href="category?action=getChild&idParent=${cateRoot.id}">${cateRoot.categoryName}</a></summary>
                <c:if test="${cateRoot.ID eq selectedParentId and not empty subCategories}">
                    <ul>
                        <c:forEach var="sub" items="${cateChilds}">
                            <li>${sub.categoryName}</li>
                        </c:forEach>
                    </ul>
                </c:if>
        </details>
    </c:forEach>
</div>
