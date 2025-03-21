
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<footer>
        <div class="container">
            <div class="content__footer">
                <div class="left">
                    <div class="left_left">
                        <div class="logo__footer">
                                <a href="${pageContext.request.contextPath}/products"><img src="${pageContext.request.contextPath}/assets/images/logo.png" alt=""></a>
                                <div class="title__footer">
                                    <span ><h4 class="text-banner">Hội chợ</h4></span>
                                    <span ><h4 class="text-banner">Nông sản</h4></span>
                                </div>
                        </div>
                        <div class="content_left">
                            <p class="text-banner">ASSIGNMENT OF TEAM 4</p>
                        </div>
                    </div>
                    <div class="left_right">
                        <span>
                            <a href="#"><p>Nguyễn Tuấn Anh</p></a>
                        </span><span>
                            <a href="#"><p>Lê Hồng Phúc</p></a>
                        </span><span>
                            <a href="#"><p>Nguyễn Thiện Hoàng</p></a>
                        </span><span>
                            <a href="#"><p>Trần Văn Linh</p></a>
                        </span><span>
                            <a href="#"><p>Lê Đặng Nhật Huy</p></a>
                        </span>
                    </div>
                </div>
                <div class="right">
                    <c:forEach var="entry" items="${listCategoryGroup}">
                        <a href="${pageContext.request.getContextPath()}/cates?action=cateGroup&ID=${entry.groupID}" class="text-banner">${entry.groupName}</a>
                    </c:forEach>
                </div>
                </div>
            </div>
    </footer>
