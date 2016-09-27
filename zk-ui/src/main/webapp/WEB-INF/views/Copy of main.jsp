<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="./include/include.jsp" %>


    <style type="text/css">
        label {
            width: 80px;
            display: inline-block;
            text-align: right;
        }

        #Tree li {
            line-height: 14px;
        }

        #showview li {
            width: 310px;

        }

    </style>

</head>
<body class="overflow">

<div class="content_wrap" style="width: 100%;">
    <div class="content_wrap_title" style="width: 100%">
        <span>权限管理</span>
    </div>
    <div class="zTreeDemoBackground left" style="width: 300px; margin-left:20px">
        <ul id="permissionTree" class="ztree" style="width: 300px;"></ul>
    </div>
    <div id="showview" style="width: 310px;margin-right: 10px;float: left;margin-top:15px" class="right">
        <ul>
            <li>
                <label>上级名称:</label>
                <input type="text" readonly="readonly" name="pname" id="pname" style="margin-left: 5px;"/>
            </li>
            <li id="codesuffixLi">
                <label>CODE:</label>
                <span id="codeprefix" style="text-align:right;margin-left: 6px;"></span>
                <input style="width:20px;margin-left: 1px;margin-right: 1px;" type="text" name="code" size="1"
                       maxlength="2" id="code"/>
                <span id="codesuffix" style="text-align:left;display: none"></span>
            </li>
            <li>
                <label>名称:</label>
                <input type="text" name="name" id="name" style="margin-left: 5px;"/>
            </li>
            <li>
                <label>KEY:</label>
                <input type="text" name="permissionKey" id="permissionKey" style="margin-left: 5px;"/>
            </li>

            <li>
                <div style="margin-left:111px;">
                    <input type="button" value="新增" name="addnode" id="addnode" class="bootstrap-button insert"/>
                    <shiro:hasPermission name="PermissionManager:save">
                        <input type="button" value="保存" name="savenode" id="savenode" class="bootstrap-button save"/>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="PermissionManager:remove">
                        <input type="button" value="删除" name="removenode" id="removenode" class="bootstrap-button"/>
                    </shiro:hasPermission>
                </div>
            </li>

        </ul>
    </div>
    <div style="float: left;margin-left: 100px">
        <ul id="Tree" class="ztree" style="width: 300px;display: none;">
        </ul>
        <input type="button" id="TreeClose" class="bootstrap-button" value="取消"
               style="margin-top: 20x;margin-left: 257px;display: none"/>
    </div>
</div>


</body>
</html>