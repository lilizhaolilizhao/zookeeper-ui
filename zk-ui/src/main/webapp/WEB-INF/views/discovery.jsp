<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="./include/include.jsp" %>
    <script type="text/javascript" src="<%=path%>/resources/js/user/tree.js"></script>

    <style type="text/css">


    </style>

</head>
<body class="overflow">
<c:forEach items="${list }" var="d">
    ${d } <br>
</c:forEach>

</body>
</html>