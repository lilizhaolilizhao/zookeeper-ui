<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="./include/include.jsp" %>
    <style type="text/css">
        table tr {
            height: 50px
        }
    </style>

</head>
<body class="overflow">
<div align="center">
    <form action="login">
        <table>
            <tr>
                <td> 用户名：</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td> 密码：</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="提交">
                </td>
            </tr>
        </table>
    </form>
    <div> ${msg }</div>
</div>

</body>
</html>